import java.util.Date;

public class Temperature {
    private final Date date;
    private final double averageTemperature;
    private final double averageTemperatureUncertainty;
    private final String city;
    private final String country;
    private final double latitude;
    private final double longitude;

    public Temperature(
        Date date,
        double averageTemperature,
        double averageTemperatureUncertainty,
        String city,
        String country,
        double latitude,
        double longitude
    ) {
        this.date = date;
        this.averageTemperature = averageTemperature;
        this.averageTemperatureUncertainty = averageTemperatureUncertainty;
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Date getDate() {
        return this.date;
    }

    public double getAverageTemperature() {
        return this.averageTemperature;
    }

    public double getAverageTemperatureUncertainty() {
        return this.averageTemperatureUncertainty;
    }

    public String getCity() {
        return this.city;
    }

    public String getCountry() {
        return this.country;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }
}