package tqs.air.quality.metrics.model.breezometer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BreezometerResult {
    private String error;
    private BreezometerData data;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public BreezometerData getData() {
        return data;
    }

    public void setData(BreezometerData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BreezometerResult{" +
                "error='" + error + '\'' +
                ", data=" + data +
                '}';
    }
}
