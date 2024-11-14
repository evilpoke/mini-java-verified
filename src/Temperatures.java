import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.*;


public abstract class Temperatures implements Iterable<Temperature> {

    protected final Iterable<Temperature> temperatures;
    private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Temperatures(final Iterable<Temperature> temperatures) {
        this.temperatures = temperatures;
    }

    public Temperatures(final File csv) {
        this.temperatures = parseCsvFile(csv);
    }

    public Temperatures(final URL url) {
        this.temperatures = downloadAndParseCsvFile(url);
    }

    public Iterator<Temperature> iterator() {
        return temperatures.iterator();
    }

    public Stream<Temperature> stream() {
        return StreamSupport.stream(temperatures.spliterator(), false);
    }

    public void printSummary() {
        print("Number of temperature datapoints:", size());
        print("Cities:", cities());
        print("Countries:", countries());
        print("Coldest Country (absolute Temperature):", coldestCountryAbs());
        print("Coldest Country (average  Temperature):", coldestCountryAvg());
        print("Hottest Country (absolute Temperature):", hottestCountryAbs());
        print("Hottest Country (average  Temperature):", hottestCountryAvg());
        print("Countries' average temperature:", countriesAvgTemperature());

    }

    protected static void print(final Object... objects) {
        final String msg = Arrays
                .stream(objects)
                .map(Object::toString)
                .collect(Collectors.joining(" "));
        System.out.println(msg);
    }

    static private Iterable<Temperature> parseCsvFile(final File csvFile) {
        List<Temperature> dateTemperatures;
        try {
            final InputStream inputStream = new FileInputStream(csvFile);
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            dateTemperatures = reader.lines() // iterate over the csv line by line (stream of Strings)
                    .skip(1) // skip header of csv file
                    .map(lineToTemperature) // convert line to DateTemperature
                    .filter(Objects::nonNull) // filter null values due to parsing errors
                    .collect(Collectors.toList());

            reader.close();
            return dateTemperatures;
        } catch (IOException e) {
            System.out.println("File " + csvFile + " not found.");
            return null;
        }
    }

    static private Function<String, Temperature> lineToTemperature = (line) -> {
        try {
            final String[] fields = line.split(",");  // a CSV with comma-separated lines
            final java.util.Date date = dateFormat.parse(fields[0]);
            final double avgTemperature = Double.parseDouble(fields[1]);
            final double avgTemperatureUncertainty = Double.parseDouble(fields[2]);
            final String city = fields[3];
            final String country = fields[4];
            final double latitude = Double.parseDouble(fields[5].substring(0, fields[5].length() - 1));
            final double longitude = Double.parseDouble(fields[6].substring(0, fields[6].length() - 1));
            return new Temperature(
                    date,
                    avgTemperature,
                    avgTemperatureUncertainty,
                    city,
                    country,
                    latitude,
                    longitude
            );
        } catch (Exception e) {
            return null;
        }
    };


    private static Iterable<Temperature> downloadAndParseCsvFile(final URL url) {
        final List<Temperature> dateTemperatures;
        try {
            //Socket socket = new Socket(url.getHost(), url.getDefaultPort());
            final URLConnection socket = url.openConnection();

            socket.setDoOutput(true);
            final PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.print("GET " + url.toString() + "\r\n\r\n");
            out.flush();
            out.close();

            final BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dateTemperatures = in.lines() // iterate over the csv line by line (stream of Strings)
                    .skip(1) // skip header of csv file
                    .map(lineToTemperature) // convert line to DateTemperature
                    .filter(Objects::nonNull) // filter null values due to parsing errors
                    .collect(Collectors.toList());
            in.close();

            return dateTemperatures;

        } catch (IOException e) {
            print("URL", url, "could not be opened.");
            return null;
        }
    }

    public abstract long size();

    public abstract List<java.util.Date> dates();

    public abstract Set<String> cities();

    public abstract Set<String> countries();

    public abstract Map<String, Temperatures> temperaturesByCountry();

    public abstract Map<String, Double> countriesAvgTemperature();

    public abstract String coldestCountryAbs();

    public abstract String hottestCountryAbs();

    public abstract String coldestCountryAvg();

    public abstract String hottestCountryAvg();
}