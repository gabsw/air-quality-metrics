package tqs.air.quality.metrics.mocks;

import tqs.air.quality.metrics.model.AirQualityMetrics;
import tqs.air.quality.metrics.model.LocationDatetime;
import tqs.air.quality.metrics.model.breezometer.BreezometerResult;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static tqs.air.quality.metrics.mocks.PresentMocks.currentLocalDateTime;

public class PastMocks {
    public static final LocalDateTime pastLocalDateTime = currentLocalDateTime.truncatedTo(ChronoUnit.DAYS).plusHours(9);
    public static final LocationDatetime pastLocationDatetime = new LocationDatetime(MockBase.latitude, MockBase.longitude,
            pastLocalDateTime);

    // Default BreezometerResult
    public static final BreezometerResult pastBreezometerResult = MockBase.generateMockBreezometerResult(pastLocalDateTime);
    public static final AirQualityMetrics pastAirQualityMetrics = new AirQualityMetrics(MockBase.latitude, MockBase.longitude,
            pastBreezometerResult);
}
