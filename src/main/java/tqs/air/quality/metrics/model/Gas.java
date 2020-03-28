package tqs.air.quality.metrics.model;

import tqs.air.quality.metrics.model.breezometer.BreezometerPollutant;

import java.io.Serializable;

public class Gas implements Serializable {

    private String initials;
    private String name;
    private double amount;
    private String units;

    public Gas(BreezometerPollutant breezometerPollutant) {
        this.initials = breezometerPollutant.getInitials();
        this.name = breezometerPollutant.getFullName();
        this.amount = breezometerPollutant.getConcentration().getValue();
        this.units = breezometerPollutant.getConcentration().getUnits();
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
