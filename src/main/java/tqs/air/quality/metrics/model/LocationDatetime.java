package tqs.air.quality.metrics.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class LocationDatetime {

    private double latitude;
    private double longitude;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime localDateTime;

    public LocationDatetime(double latitude, double longitude, LocalDateTime localDateTime) {
        this.latitude = latitude;
        this.longitude = longitude;
        if (localDateTime != null) {
            this.localDateTime = localDateTime.truncatedTo(ChronoUnit.SECONDS);
        } else {
            this.localDateTime = null;
        }
    }

    public LocationDatetime() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationDatetime that = (LocationDatetime) o;
        return Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0 &&
                Objects.equals(localDateTime, that.localDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, localDateTime);
    }

    @Override
    public String toString() {
        return "LocationDatetime{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
