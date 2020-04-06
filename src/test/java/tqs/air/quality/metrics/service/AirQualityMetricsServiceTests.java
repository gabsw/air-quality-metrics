package tqs.air.quality.metrics.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static tqs.air.quality.metrics.Utils.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tqs.air.quality.metrics.exception.ApiServerException;
import tqs.air.quality.metrics.exception.ResultNotFoundException;
import tqs.air.quality.metrics.model.CacheStats;

public class AirQualityMetricsServiceTests {


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

        when(api.getBreezometerResult(latitude, longitude, currentLocalDateTime)).thenReturn(breezometerResult);

        assertEquals(airQualityMetrics, service.getAirQualityMetrics(locationDatetime));
    }

    @Test
    void whenResultWasNotFoundByApi_ResultNotFoundExceptionShouldBeThrownByGetAirQualityMetricsService() {
        when(api.getBreezometerResult(latitude, longitude, currentLocalDateTime)).
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
        when(api.getBreezometerResult(latitude, longitude, currentLocalDateTime)).
                thenThrow(new ApiServerException("Server error in the Breezometer API."));

        Exception exception = assertThrows(ApiServerException.class,
                () -> service.getAirQualityMetrics(locationDatetime));

        String expectedMessage = "Server error in the Breezometer API.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getCacheStats() {
        when(api.getBreezometerResult(latitude, longitude, currentLocalDateTime)).thenReturn(breezometerResult);
        service.getAirQualityMetrics(locationDatetime);

        CacheStats cacheStats = new CacheStats(1, 0, 1, 0);

        assertEquals(cacheStats, service.getCacheStats());
    }
}
