package tqs.air.quality.metrics;

import tqs.air.quality.metrics.model.AirQualityMetrics;
import tqs.air.quality.metrics.model.LocationDatetime;
import tqs.air.quality.metrics.model.breezometer.BreezometerConcentration;
import tqs.air.quality.metrics.model.breezometer.BreezometerData;
import tqs.air.quality.metrics.model.breezometer.BreezometerPollutant;
import tqs.air.quality.metrics.model.breezometer.BreezometerResult;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static final double latitude = 48.857456;
    public static final double longitude = 2.354611;
    public static final LocalDateTime currentLocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    public static final LocationDatetime locationDatetime = new LocationDatetime(latitude, longitude,
            currentLocalDateTime);
    public static final LocationDatetime locationDatetimeWithNull = new LocationDatetime(latitude, longitude,
            null);

    public static final double concentrationValue = 136.77;
    public static final String concentrationUnits = "ppb";

    public static final String pollutantInitials = "CO";
    public static final String pollutantFullName = "Carbon monoxide";
    public static final String pollutantKey = "co";

    public static final BreezometerResult breezometerResult = generateMockBreezometerResult(currentLocalDateTime);
    public static final AirQualityMetrics airQualityMetrics = new AirQualityMetrics(latitude, longitude,
            breezometerResult);

    public static final BreezometerResult breezometerResultWithNull = generateMockBreezometerResult(null);
    public static final AirQualityMetrics airQualityMetricsWithNull = new AirQualityMetrics(latitude, longitude,
            breezometerResultWithNull);

    private static BreezometerResult generateMockBreezometerResult(LocalDateTime localDateTime) {
        BreezometerConcentration breezometerConcentration = new BreezometerConcentration(concentrationValue,
                concentrationUnits);
        BreezometerPollutant breezometerPollutant = new BreezometerPollutant(pollutantInitials,
                pollutantFullName, breezometerConcentration);

        Map<String, BreezometerPollutant> pollutants = new HashMap<>();
        pollutants.put(pollutantKey, breezometerPollutant);
        BreezometerData breezometerData;

        if (localDateTime == null) {
            breezometerData = new BreezometerData(currentLocalDateTime, true, pollutants);
        } else {
            breezometerData = new BreezometerData(localDateTime, true, pollutants);
        }
        return new BreezometerResult(null, breezometerData);
    }
}
