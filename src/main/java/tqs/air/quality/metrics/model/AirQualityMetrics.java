package tqs.air.quality.metrics.model;

import tqs.air.quality.metrics.model.breezometer.BreezometerPollutant;
import tqs.air.quality.metrics.model.breezometer.BreezometerResult;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AirQualityMetrics implements Serializable {
    private double latitude;
    private double longitude;
    private LocalDateTime dateTime;
    private List<Gas> gases;

    public AirQualityMetrics(double latitude, double longitude, BreezometerResult breezometerResult) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateTime = breezometerResult.getData().getDateTime();
        this.gases = getGases(breezometerResult.getData().getPollutants());
    }

    private List<Gas> getGases(Map<String, BreezometerPollutant> breezometerPollutantMap) {
        List<BreezometerPollutant> allBreezometerPollutants = new ArrayList<>(breezometerPollutantMap.values());

        List<Gas> allGases = new ArrayList<>();

        for (BreezometerPollutant breezometerPollutant : allBreezometerPollutants) {
            allGases.add(new Gas(breezometerPollutant));
        }

        return allGases;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<Gas> getGases() {
        return gases;
    }

    public void setGases(List<Gas> gases) {
        this.gases = gases;
    }
}
