package tqs.air.quality.metrics.mocks;

import tqs.air.quality.metrics.model.AirQualityMetrics;
import tqs.air.quality.metrics.model.LocationDatetime;
import tqs.air.quality.metrics.model.breezometer.BreezometerResult;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PresentMocks {
    public static final LocalDateTime currentLocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).plusHours(14).plusMinutes(30).plusSeconds(13);

    public static final LocationDatetime currentLocationDatetime = new LocationDatetime(MockBase.latitude, MockBase.longitude,
            currentLocalDateTime);
    public static final LocationDatetime currentLocationDatetimeWithNull = new LocationDatetime(MockBase.latitude, MockBase.longitude,
            null);


    // Default BreezometerResult
    public static final BreezometerResult currentBreezometerResult = MockBase.generateMockBreezometerResult(null);
    public static final AirQualityMetrics currentAirQualityMetrics = new AirQualityMetrics(MockBase.latitude, MockBase.longitude,
            currentBreezometerResult);

    public static final BreezometerResult currentBreezometerResultWithNull = MockBase.generateMockBreezometerResult(null);
    public static final AirQualityMetrics currentAirQualityMetricsWithNull = new AirQualityMetrics(MockBase.latitude, MockBase.longitude,
            currentBreezometerResultWithNull);

}
