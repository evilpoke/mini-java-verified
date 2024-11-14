package testingstuff;

import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;

public class DateTemperature {
  final private Date date;
  final private double temperature;
  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  public DateTemperature(final Date date, final double temperature) {
    this.date = date;
    this.temperature = temperature;
  }

  public double getTemperature() {
    return temperature;
  }

  public Date getDate() {
    return date;
  }

  public String toString() {
    return dateFormat.format(date) + ";" + temperature;
  }
}