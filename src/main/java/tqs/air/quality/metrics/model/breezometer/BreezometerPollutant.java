package tqs.air.quality.metrics.model.breezometer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BreezometerPollutant {
    @JsonProperty("display_name")
    private String initials;
    @JsonProperty("full_name")
    private String fullName;
    private BreezometerConcentration concentration;

    public BreezometerPollutant() {
    }

    public BreezometerPollutant(String initials, String fullName, BreezometerConcentration concentration) {
        this.initials = initials;
        this.fullName = fullName;
        this.concentration = concentration;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public BreezometerConcentration getConcentration() {
        return concentration;
    }

    public void setConcentration(BreezometerConcentration concentration) {
        this.concentration = concentration;
    }
}
