package tqs.air.quality.metrics.model.breezometer;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Map;

public class BreezometerData {
    @JsonProperty("datetime")
    private LocalDateTime dateTime;
    @JsonProperty("date_available")
    private Boolean dateAvailable;
    private Map<String, BreezometerPollutant> pollutants;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Boolean getDateAvailable() {
        return dateAvailable;
    }

    public void setDateAvailable(Boolean dateAvailable) {
        this.dateAvailable = dateAvailable;
    }

    public Map<String, BreezometerPollutant> getPollutants() {
        return pollutants;
    }

    public void setPollutants(Map<String, BreezometerPollutant> pollutants) {
        this.pollutants = pollutants;
    }

}
