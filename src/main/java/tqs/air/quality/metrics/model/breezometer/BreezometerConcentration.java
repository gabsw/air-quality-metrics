package tqs.air.quality.metrics.model.breezometer;

public class BreezometerConcentration {
    private double value;
    private String units;

    public BreezometerConcentration(double value, String units) {
        this.value = value;
        this.units = units;
    }

    public BreezometerConcentration() {
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
