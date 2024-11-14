import java.net.MalformedURLException;
import java.util.*;
import java.net.URL;
import java.io.File;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class StreamTemperatures extends Temperatures {

    public StreamTemperatures(Iterable<Temperature> temperatures) {
        super(temperatures);
    }

    public StreamTemperatures(File csv) {
        super(csv);
    }

    public StreamTemperatures(URL url) {
        super(url);
    }

    public long size() {
        return stream().count();
    }

    public List<java.util.Date> dates() {
        return stream()
                .map(Temperature::getDate)
                .collect(Collectors.toSet())
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public Set<String> cities() {
        return stream().map(Temperature::getCity).collect(Collectors.toSet());
    }

    public Set<String> countries() {
        return stream().map(Temperature::getCountry).collect(Collectors.toSet());
    }

    public Map<String, Temperatures> temperaturesByCountry() {
        final Map<String, Temperatures> countryTemperatures = new HashMap<>();
        countries().forEach(
                country ->
                        countryTemperatures.put(
                                country,
                                new StreamTemperatures(
                                        this.stream()
                                                .filter(it -> it.getCountry().equals(country))
                                                .collect(Collectors.toList())
                                )
                        )
        );
        return countryTemperatures;
    }

    @Override
    public Map<String, Double> countriesAvgTemperature() {
        return temperaturesByCountry()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry
                                    .getValue()
                                    .stream()
                                    .mapToDouble(Temperature::getAverageTemperature)
                                    .average()
                                    .getAsDouble()
                ));
    }

    @Override
    public String coldestCountryAbs() {
        return stream()
                .min(Comparator.comparing(Temperature::getAverageTemperature))
                .get()
                .getCountry();
    }

    @Override
    public String hottestCountryAbs() {
        return stream()
                .max(Comparator.comparing(Temperature::getAverageTemperature))
                .get()
                .getCountry();
    }

    @Override
    public String coldestCountryAvg() {
        final Map<String, Temperatures> countryTemperatures = temperaturesByCountry();
        final List<String> countries = new ArrayList<>(countryTemperatures.keySet());
        final List<Double> avgTemperatures = countries
                .stream()
                .map(country ->
                        countryTemperatures.get(country)
                                .stream()
                                .mapToDouble(Temperature::getAverageTemperature)
                                .average()
                                .getAsDouble()
                )
                .collect(Collectors.toList());

        final Double minTemperature = avgTemperatures.stream().mapToDouble(it -> it).min().getAsDouble();
        final int index = avgTemperatures.indexOf(minTemperature);
        return countries.get(index);
    }

    @Override
    public String hottestCountryAvg() {
        final Map<String, Temperatures> countryTemperatures = temperaturesByCountry();
        final List<String> countries = new ArrayList<>(countryTemperatures.keySet());
        final List<Double> avgTemperatures = countries
                .stream()
                .map(country ->
                        countryTemperatures.get(country)
                                .stream()
                                .mapToDouble(Temperature::getAverageTemperature)
                                .average()
                                .getAsDouble()
                )
                .collect(Collectors.toList());

        final Double maxTemperature = avgTemperatures
                .stream()
                .mapToDouble(it -> it)
                .max()
                .getAsDouble();
        final int index = avgTemperatures.indexOf(maxTemperature);
        return countries.get(index);
    }

    public Map<String, Double> avgTemperatureDeltaPerYearPerCountry() {
        final Function<java.util.Date, Integer> getYear = date -> {
            final Calendar cal = new GregorianCalendar();
            cal.setTime(date);
            return cal.get(Calendar.YEAR);
        };
        final Map<String, Double> avgTempDeltaPerCountry = countries()
                .stream()
                .collect(Collectors.toMap(
                        country -> country,
                        country ->  stream()
                                .filter(t -> t.getCountry().equals(country))
                                .map(t -> new AbstractMap.SimpleEntry<>(t.getCountry(), t.getCity()))
                                .distinct()
                                .mapToDouble(countryCity -> {
                                    final List<Temperature> cityTemperatures = this
                                            .stream()
                                            .filter(t ->
                                                    t.getCity().equals(countryCity.getValue()) &&
                                                            t.getCountry().equals(countryCity.getKey())
                                            )
                                            .sorted(Comparator.comparing(Temperature::getDate))
                                            .collect(Collectors.toList());

                                    final double[] cityTemperaturesPerYear = cityTemperatures
                                            .stream()
                                            .map(Temperature::getDate)
                                            .map(getYear)
                                            .distinct()
                                            .mapToDouble(year -> cityTemperatures
                                                    .stream()
                                                    .filter(t -> getYear.apply(t.getDate()).equals(year))
                                                    .mapToDouble(Temperature::getAverageTemperature)
                                                    .average()
                                                    .getAsDouble()
                                            ).toArray();

                                    return IntStream
                                            .range(0, cityTemperaturesPerYear.length - 1)
                                            .boxed()
                                            .mapToDouble(i -> cityTemperaturesPerYear[i+1] - cityTemperaturesPerYear[i])
                                            .average()
                                            .getAsDouble();
                                })
                                .average()
                                .getAsDouble()
                ));
        avgTempDeltaPerCountry.put(
                "Globally",
                avgTempDeltaPerCountry
                        .values()
                        .stream()
                        .mapToDouble(it -> it)
                        .average()
                        .getAsDouble()
        );
        return avgTempDeltaPerCountry;
    }

    public static void main(final String[] args) {
        StreamTemperatures temperatures;
        try {
            final URL linkToCsvFile = new URL(args[0]);
            temperatures = new StreamTemperatures(linkToCsvFile);
        } catch (MalformedURLException e) {
            final File csvFile = new File(args[0]);
            temperatures = new StreamTemperatures(csvFile);
        }
        temperatures.printSummary();
        final Map<String, Double> values = temperatures.avgTemperatureDeltaPerYearPerCountry();

        print(
                "Averaged yearly temperature delta per country:",
                Arrays.toString(values.entrySet().toArray())
        );
    }

}