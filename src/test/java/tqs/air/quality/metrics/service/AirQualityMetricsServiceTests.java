package tqs.air.quality.metrics.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tqs.air.quality.metrics.exception.ApiServerException;
import tqs.air.quality.metrics.exception.ResultNotFoundException;
import tqs.air.quality.metrics.model.AirQualityMetrics;
import tqs.air.quality.metrics.model.CacheStats;
import tqs.air.quality.metrics.model.LocationDatetime;
import tqs.air.quality.metrics.model.breezometer.BreezometerConcentration;
import tqs.air.quality.metrics.model.breezometer.BreezometerData;
import tqs.air.quality.metrics.model.breezometer.BreezometerPollutant;
import tqs.air.quality.metrics.model.breezometer.BreezometerResult;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AirQualityMetricsServiceTests {

    private double latitude = 48.857456;
    private double longitude = 2.354611;
    private LocalDateTime localDateTime = LocalDateTime.now();
    private LocationDatetime locationDatetime = new LocationDatetime(latitude, longitude, localDateTime);

    private BreezometerResult breezometerResult = generateMockBreezometerResult();
    private AirQualityMetrics airQualityMetrics = new AirQualityMetrics(latitude, longitude, breezometerResult);

    @Mock
    private BreezometerAPI api;

    @InjectMocks
    private AirQualityMetricsService service;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAirQualityMetricsWithValidResult() {

        when(api.getBreezometerResult(latitude, longitude, localDateTime)).thenReturn(breezometerResult);

        assertEquals(airQualityMetrics, service.getAirQualityMetrics(locationDatetime));
    }

    @Test
    void whenResultWasNotFoundByApi_ResultNotFoundExceptionShouldBeThrownByGetAirQualityMetricsService() {
        when(api.getBreezometerResult(latitude, longitude, localDateTime)).
                thenThrow(new ResultNotFoundException("Breezometer result not found for: " +
                        "latitude=" + latitude + ", longitude=" + longitude));

        Exception exception = assertThrows(ResultNotFoundException.class,
                () -> service.getAirQualityMetrics(locationDatetime));

        String expectedMessage = "Breezometer result not found for: " +
                "latitude=" + latitude + ", longitude=" + longitude;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenServerApiFails_ApiServerExceptionShouldBeThrownByGetAirQualityMetricsService() {
        when(api.getBreezometerResult(latitude, longitude, localDateTime)).
                thenThrow(new ApiServerException("Server error in the Breezometer API."));

        Exception exception = assertThrows(ApiServerException.class,
                () -> service.getAirQualityMetrics(locationDatetime));

        String expectedMessage = "Server error in the Breezometer API.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getCacheStats() {
        when(api.getBreezometerResult(latitude, longitude, localDateTime)).thenReturn(breezometerResult);
        service.getAirQualityMetrics(locationDatetime);

        CacheStats cacheStats = new CacheStats(1, 0, 1, 0);

        assertEquals(cacheStats, service.getCacheStats());
    }


    private BreezometerResult generateMockBreezometerResult() {
        BreezometerConcentration breezometerConcentration = new BreezometerConcentration(136.77, "ppb");
        BreezometerPollutant breezometerPollutant = new BreezometerPollutant("CO",
                "Carbon monoxide", breezometerConcentration);
        Map<String, BreezometerPollutant> pollutants = new HashMap<>();
        pollutants.put("co", breezometerPollutant);
        BreezometerData breezometerData = new BreezometerData(localDateTime, true, pollutants);
        return new BreezometerResult(null, breezometerData);
    }

}
