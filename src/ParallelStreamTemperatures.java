import java.net.MalformedURLException;
import java.util.*;
import java.net.URL;
import java.io.File;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class ParallelStreamTemperatures extends Temperatures {

    public ParallelStreamTemperatures(Iterable<Temperature> temperatures) {
        super(temperatures);
    }

    public ParallelStreamTemperatures(File csv) {
        super(csv);
    }

    public ParallelStreamTemperatures(URL url) {
        super(url);
    }

    public long size() {
        return stream().parallel().count();
    }

    public List<java.util.Date> dates() {
        return stream()
                .parallel()
                .map(Temperature::getDate)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public Set<String> cities() {
        return stream().parallel().map(Temperature::getCity).collect(Collectors.toSet());
    }

    public Set<String> countries() {
        return stream().parallel().map(Temperature::getCountry).collect(Collectors.toSet());
    }

    public Map<String, Temperatures> temperaturesByCountry() {
        return countries().stream().parallel().collect(Collectors.toMap(
                country -> country,
                country -> new StreamTemperatures(
                        this.stream()
                                .parallel()
                                .filter(it -> it.getCountry().equals(country))
                                .collect(Collectors.toList())
                )
        ));
    }

    @Override
    public Map<String, Double> countriesAvgTemperature() {
        return temperaturesByCountry()
                .entrySet()
                .stream()
                .parallel()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry
                                .getValue()
                                .stream()
                                .parallel()
                                .mapToDouble(Temperature::getAverageTemperature)
                                .average()
                                .getAsDouble()
                ));
    }

    @Override
    public String coldestCountryAbs() {
        return stream()
                .parallel()
                .min(Comparator.comparing(Temperature::getAverageTemperature))
                .get()
                .getCountry();
    }

    @Override
    public String hottestCountryAbs() {
        return stream()
                .parallel()
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
                .parallel()
                .map(country ->
                        countryTemperatures.get(country)
                                .stream()
                                .parallel()
                                .mapToDouble(Temperature::getAverageTemperature)
                                .average()
                                .getAsDouble()
                )
                .collect(Collectors.toList());

        final Double minTemperature = avgTemperatures.stream().parallel().mapToDouble(it -> it).min().getAsDouble();
        final int index = avgTemperatures.indexOf(minTemperature);
        return countries.get(index);
    }

    @Override
    public String hottestCountryAvg() {
        final Map<String, Temperatures> countryTemperatures = temperaturesByCountry();
        final List<String> countries = new ArrayList<>(countryTemperatures.keySet());
        final List<Double> avgTemperatures = countries
                .stream()
                .parallel()
                .map(country ->
                        countryTemperatures.get(country)
                                .stream()
                                .parallel()
                                .mapToDouble(Temperature::getAverageTemperature)
                                .average()
                                .getAsDouble()
                )
                .collect(Collectors.toList());

        final Double maxTemperature = avgTemperatures
                .stream()
                .parallel()
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
                .parallel()
                .collect(Collectors.toMap(
                        country -> country,
                        country ->  stream()
                                .parallel()
                                .filter(t -> t.getCountry().equals(country))
                                .map(t -> new AbstractMap.SimpleEntry<>(t.getCountry(), t.getCity()))
                                .distinct()
                                .mapToDouble(countryCity -> {
                                    final List<Temperature> cityTemperatures = this
                                            .stream()
                                            .parallel()
                                            .filter(t ->
                                                    t.getCity().equals(countryCity.getValue()) &&
                                                            t.getCountry().equals(countryCity.getKey())
                                            )
                                            .sorted(Comparator.comparing(Temperature::getDate))
                                            .collect(Collectors.toList());

                                    final double[] cityTemperaturesPerYear = cityTemperatures
                                            .stream()
                                            .parallel()
                                            .map(Temperature::getDate)
                                            .map(getYear)
                                            .distinct()
                                            .mapToDouble(year -> cityTemperatures
                                                    .stream()
                                                    .parallel()
                                                    .filter(t -> getYear.apply(t.getDate()).equals(year))
                                                    .mapToDouble(Temperature::getAverageTemperature)
                                                    .average()
                                                    .getAsDouble()
                                            ).toArray();

                                    return IntStream
                                            .range(0, cityTemperaturesPerYear.length - 1)
                                            .boxed()
                                            .parallel()
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
                        .parallel()
                        .mapToDouble(it -> it)
                        .average()
                        .getAsDouble()
        );
        return avgTempDeltaPerCountry;
    }

    public static void main(final String[] args) {
        ParallelStreamTemperatures temperatures;
        try {
            final URL linkToCsvFile = new URL(args[0]);
            temperatures = new ParallelStreamTemperatures(linkToCsvFile);
        } catch (MalformedURLException e) {
            final File csvFile = new File(args[0]);
            temperatures = new ParallelStreamTemperatures(csvFile);
        }
        temperatures.printSummary();
        final Map<String, Double> values = temperatures.avgTemperatureDeltaPerYearPerCountry();

        print(
                "Averaged yearly temperature delta per country:",
                Arrays.toString(values.entrySet().toArray())
        );
    }

}