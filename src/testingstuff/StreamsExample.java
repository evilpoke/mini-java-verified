package testingstuff;

import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.function.Function;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.stream.Stream;
import java.util.Date;

public class StreamsExample {

  public static void main(String[] args) {
    File inputCsvFile = new File(args[0]);
    File outputCsvFile = new File(args[1]);

    List<DateTemperature> allTemperatures = parseDateTemperatures(inputCsvFile);
    Stream<DateTemperature> avgTemperaturesPerYear = IntStream.range(2010, 2018).boxed().map(year -> year + "-01-01")
        .map(year -> parseDate(year)).map(year -> {
          double avgTemperature = allTemperatures.stream().filter(it -> it.getDate().getYear() == year.getYear())
              .mapToDouble(it -> it.getTemperature()).average().getAsDouble();
          return new DateTemperature(year, avgTemperature);
        });

    String csvString = avgTemperaturesPerYear.map(it -> it.toString()).collect(Collectors.joining("\n"));

    writeStringToFile(csvString, outputCsvFile);
  }

  static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  static private Date parseDate(String dateString) {
    try {
      return dateFormat.parse(dateString);
    } catch (ParseException e) {
      return new Date();
    }
  }

  static private void writeStringToFile(String string, File file) {
    try {
      if (file.exists())
        file.delete();
      PrintWriter out = new PrintWriter(file);
      out.print(string);
      out.close();
    } catch (FileNotFoundException e) {
      System.out.println("File not found: " + file);
    }
  }

  static private List<DateTemperature> parseDateTemperatures(File csvFile) {
    List<DateTemperature> dateTemperatures = new ArrayList<DateTemperature>();
    try {
      InputStream inputStream = new FileInputStream(csvFile);
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));


      dateTemperatures = reader.lines() // iterate over the csv line by line (stream of Strings)
          .skip(1) // skip header of csv file
          .map(mapLineToDateTemperature) // convert line to DateTemperature
          .filter(it -> it != null) // filter null values due to parsing errors
          .collect(Collectors.toList());

      reader.close();
      return dateTemperatures;
    } catch (IOException e) {
      System.out.println("File " + csvFile + " not found.");
      return null;
    }
  }

  static private Function<String, DateTemperature> mapLineToDateTemperature = (line) -> {
    String[] fields = line.split(";");// a CSV with semicolon separated lines
    try {
      Date date = dateFormat.parse(fields[0]);
      double temperature = Double.parseDouble(fields[2]);
      return new DateTemperature(date, temperature);
    } catch (Exception e) {
      return null;
    }
  };
}