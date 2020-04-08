package tqs.air.quality.metrics.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static tqs.air.quality.metrics.mocks.PresentMocks.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tqs.air.quality.metrics.exception.ApiServerException;
import tqs.air.quality.metrics.exception.ResultNotFoundException;
import tqs.air.quality.metrics.mocks.MockBase;
import tqs.air.quality.metrics.model.CacheStats;

public class AirQualityMetricsServiceTests {


    @Mock
    private BreezometerApi api;

    @InjectMocks
    private AirQualityMetricsService service;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAirQualityMetricsWithValidResult() {

        when(api.getBreezometerResult(MockBase.latitude, MockBase.longitude, currentLocalDateTime)).thenReturn(currentBreezometerResult);

        assertEquals(currentAirQualityMetrics, service.getAirQualityMetrics(currentLocationDatetime));
    }

    @Test
    void whenResultWasNotFoundByApi_ResultNotFoundExceptionShouldBeThrownByGetAirQualityMetricsService() {
        when(api.getBreezometerResult(MockBase.latitude, MockBase.longitude, currentLocalDateTime)).
                thenThrow(new ResultNotFoundException("Breezometer result not found for: " +
                        "latitude=" + MockBase.latitude + ", longitude=" + MockBase.longitude));

        Exception exception = assertThrows(ResultNotFoundException.class,
                () -> service.getAirQualityMetrics(currentLocationDatetime));

        String expectedMessage = "Breezometer result not found for: " +
                "latitude=" + MockBase.latitude + ", longitude=" + MockBase.longitude;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenServerApiFails_ApiServerExceptionShouldBeThrownByGetAirQualityMetricsService() {
        when(api.getBreezometerResult(MockBase.latitude, MockBase.longitude, currentLocalDateTime)).
                thenThrow(new ApiServerException("Server error in the Breezometer API."));

        Exception exception = assertThrows(ApiServerException.class,
                () -> service.getAirQualityMetrics(currentLocationDatetime));

        String expectedMessage = "Server error in the Breezometer API.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getCacheStats() {
        when(api.getBreezometerResult(MockBase.latitude, MockBase.longitude, currentLocalDateTime)).thenReturn(currentBreezometerResult);
        service.getAirQualityMetrics(currentLocationDatetime);

        CacheStats cacheStats = new CacheStats(1, 0, 1, 0);

        assertEquals(cacheStats, service.getCacheStats());
    }
}
