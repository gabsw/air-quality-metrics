package tqs.air.quality.metrics.mocks;

import tqs.air.quality.metrics.model.breezometer.BreezometerConcentration;
import tqs.air.quality.metrics.model.breezometer.BreezometerData;
import tqs.air.quality.metrics.model.breezometer.BreezometerPollutant;
import tqs.air.quality.metrics.model.breezometer.BreezometerResult;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MockBase {
    // Default geographic constants
    public static final double latitude = 48.857456;
    public static final double longitude = 2.354611;

    // Default gas/pollutant
    public static final double concentrationValue = 136.77;
    public static final String concentrationUnits = "ppb";
    public static final String pollutantInitials = "CO";
    public static final String pollutantFullName = "Carbon monoxide";
    public static final String pollutantKey = "co";

    static BreezometerResult generateMockBreezometerResult(LocalDateTime localDateTime) {
        BreezometerConcentration breezometerConcentration = new BreezometerConcentration(concentrationValue,
                concentrationUnits);
        BreezometerPollutant breezometerPollutant = new BreezometerPollutant(pollutantInitials,
                pollutantFullName, breezometerConcentration);

        Map<String, BreezometerPollutant> pollutants = new HashMap<>();
        pollutants.put(pollutantKey, breezometerPollutant);
        BreezometerData breezometerData;

        if (localDateTime == null) {
            breezometerData = new BreezometerData(PresentMocks.currentLocalDateTime, true, pollutants);
        } else {
            breezometerData = new BreezometerData(localDateTime, true, pollutants);
        }
        return new BreezometerResult(null, breezometerData);
    }
}
