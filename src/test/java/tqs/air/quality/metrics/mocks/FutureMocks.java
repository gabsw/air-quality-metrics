package tqs.air.quality.metrics.mocks;

import tqs.air.quality.metrics.model.AirQualityMetrics;
import tqs.air.quality.metrics.model.LocationDatetime;
import tqs.air.quality.metrics.model.breezometer.BreezometerResult;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static tqs.air.quality.metrics.mocks.PresentMocks.currentLocalDateTime;

public class FutureMocks {
    public static final LocalDateTime futureLocalDateTime = currentLocalDateTime.truncatedTo(ChronoUnit.DAYS).plusHours(20);
    public static final LocationDatetime futureLocationDatetime = new LocationDatetime(MockBase.latitude, MockBase.longitude,
            futureLocalDateTime);

    // Default BreezometerResult
    public static final BreezometerResult futureBreezometerResult = MockBase.generateMockBreezometerResult(futureLocalDateTime);
    public static final AirQualityMetrics futureAirQualityMetrics = new AirQualityMetrics(MockBase.latitude, MockBase.longitude,
            futureBreezometerResult);
}
