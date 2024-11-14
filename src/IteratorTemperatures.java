import java.util.*;
import java.net.URL;
import java.io.File;


public class IteratorTemperatures extends Temperatures {

    public IteratorTemperatures(Iterable<Temperature> temperatures) {
        super(temperatures);
    }

    public IteratorTemperatures(File csv) {
        super(csv);
    }

    public IteratorTemperatures(URL url) {
        super(url);
    }

    public long size() {
        int size = 0;
        for (Temperature t: this)
            size++;
        return size;
    }

    public List<java.util.Date> dates() {
        final Set<java.util.Date> dateSet = new HashSet<>();
        for (Temperature t: this)
            dateSet.add(t.getDate());
        List<java.util.Date> dates = new ArrayList<>(dateSet);
        Collections.sort(dates);
        return dates;
    }

    public Set<String> cities() {
        final Set<String> cities = new HashSet<>();
        for (Temperature t: this)
            cities.add(t.getCity());
        return cities;
    }

    public Set<String> countries() {
        final Set<String> countries = new HashSet<>();
        for (Temperature t: this)
            countries.add(t.getCountry());
        return countries;
    }

    public Map<String, Temperatures> temperaturesByCountry() {
        final Map<String, List<Temperature>> countryTemperatures = new HashMap<>();
        for (Temperature t: this)
            if (countryTemperatures.keySet().contains(t.getCountry()))
                countryTemperatures.get(t.getCountry()).add(t);
            else
                countryTemperatures.put(t.getCountry(), new ArrayList<Temperature>(Arrays.asList(t)));

        final Map<String, Temperatures> result = new HashMap<>();
        for (Map.Entry<String, List<Temperature>> entry: countryTemperatures.entrySet())
            result.put(entry.getKey(), new IteratorTemperatures(entry.getValue()));
        return result;
    }

    public String coldestCountryAbs() {
        Temperature minTemp = new Temperature(
                new java.util.Date(),
                Double.MAX_VALUE,
                0d,
                "",
                "",
                0,
                0
        );
        for (Temperature t: this)
            if (t.getAverageTemperature() < minTemp.getAverageTemperature())
                minTemp = t;
        return minTemp.getCountry();
    }

    public String hottestCountryAbs() {
        Temperature maxTemp = new Temperature(
                new java.util.Date(),
                Double.MIN_VALUE,
                0d,
                "",
                "",
                0,
                0
        );
        for (Temperature t: this)
            if (t.getAverageTemperature() > maxTemp.getAverageTemperature())
                maxTemp = t;
        return maxTemp.getCountry();
    }

    public String coldestCountryAvg() {
        final Map<String, Double> avgTemperaturesPerCountry = new HashMap<>();
        double avg;
        int numTemperatures;
        for (String c: countries()) {
            avg = 0.0;
            numTemperatures = 0;
            for (Temperature t: this)
                if (t.getCountry().equals(c)) {
                    numTemperatures++;
                    avg += t.getAverageTemperature();
                }
            avg /= numTemperatures;
            avgTemperaturesPerCountry.put(c, avg);
        }

        String coldestCountry = "";
        double coldestTemperature = Double.MAX_VALUE;
        for (Map.Entry<String, Double> entry: avgTemperaturesPerCountry.entrySet())
            if (entry.getValue() < coldestTemperature) {
                coldestTemperature = entry.getValue();
                coldestCountry = entry.getKey();
            }
        return coldestCountry;
    }

    public String hottestCountryAvg() {
        final Map<String, Double> avgTemperaturesPerCountry = new HashMap<>();
        double avg;
        int numTemperatures;
        for (String c: countries()) {
            avg = 0.0;
            numTemperatures = 0;
            for (Temperature t: this)
                if (t.getCountry().equals(c)) {
                    numTemperatures++;
                    avg += t.getAverageTemperature();
                }
            avg /= numTemperatures;
            avgTemperaturesPerCountry.put(c, avg);
        }

        String hottestCountry = "";
        double hottestTemperature = Double.MIN_VALUE;
        for (Map.Entry<String, Double> entry: avgTemperaturesPerCountry.entrySet())
            if (entry.getValue() > hottestTemperature) {
                hottestTemperature = entry.getValue();
                hottestCountry = entry.getKey();
            }
        return hottestCountry;
    }

    @Override
    public Map<String, Double> countriesAvgTemperature() {
        Map<String, Temperatures> countryTemperatures = temperaturesByCountry();
        Map<String, Double> countriesAvgTemperature = new HashMap<>();
        double avg;
        int numTemperatures;
        for (Map.Entry<String, Temperatures> entry: countryTemperatures.entrySet()) {
            avg = 0;
            numTemperatures = 0;
            for (Temperature t: entry.getValue()) {
                numTemperatures++;
                avg += t.getAverageTemperature();
            }
            avg /= numTemperatures;
            countriesAvgTemperature.put(entry.getKey(), avg);
        }
        return countriesAvgTemperature;
    }

    public static void main(final String[] args) {
        File csvFile = new File(args[0]);
        IteratorTemperatures temperatures = new IteratorTemperatures(csvFile);
        temperatures.printSummary();
    }
}