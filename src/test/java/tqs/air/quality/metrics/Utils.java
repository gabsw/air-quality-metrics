package tqs.air.quality.metrics;

import tqs.air.quality.metrics.model.AirQualityMetrics;
import tqs.air.quality.metrics.model.LocationDatetime;
import tqs.air.quality.metrics.model.breezometer.BreezometerConcentration;
import tqs.air.quality.metrics.model.breezometer.BreezometerData;
import tqs.air.quality.metrics.model.breezometer.BreezometerPollutant;
import tqs.air.quality.metrics.model.breezometer.BreezometerResult;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static double latitude = 48.857456;
    public static double longitude = 2.354611;
    public static LocalDateTime currentLocalDateTime = LocalDateTime.now();
    public static LocationDatetime locationDatetime = new LocationDatetime(latitude, longitude, currentLocalDateTime);
    public static LocationDatetime locationDatetimeWithNull = new LocationDatetime(latitude, longitude,
            null);

    public static double concentrationValue = 136.77;
    public static String concentrationUnits = "ppb";

    public static String pollutantInitials = "CO";
    public static String pollutantFullName = "Carbon monoxide";
    public static String pollutantKey = "co";

    public static BreezometerResult breezometerResult = generateMockBreezometerResult(currentLocalDateTime);
    public static AirQualityMetrics airQualityMetrics = new AirQualityMetrics(latitude, longitude, breezometerResult);

    public static BreezometerResult breezometerResultWithNull = generateMockBreezometerResult(null);
    public static AirQualityMetrics airQualityMetricsWithNull = new AirQualityMetrics(latitude, longitude,
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
