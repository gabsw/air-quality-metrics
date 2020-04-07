package tqs.air.quality.metrics.controller;

import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.air.quality.metrics.model.CacheStats;
import tqs.air.quality.metrics.service.AirQualityMetricsService;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(RestCacheStatsController.class)
public class RestCacheStatsControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AirQualityMetricsService service;

    private long totalRequests = 2;
    private long hitCount = 1;
    private long missCount = 1;
    private long evictionCount = 0;
    private double hitRate = 0.5;
    private double missRate = 0.5;

    private CacheStats cacheStats = new CacheStats(totalRequests, hitCount, missCount, evictionCount);

    private String expectedJSONContent = "{\"totalRequests\":" +
            totalRequests +
            ",\"hitCount\":" +
            hitCount +
            ",\"hitRate\":" +
            hitRate +
            ",\"missCount\":" +
            missCount +
            ",\"missRate\":" +
            missRate +
            ",\"evictionCount\":" +
            evictionCount +
            "}";


    @Test
    void whenGetMetrics_thenReturnJson() throws Exception {
        given(service.getCacheStats()).willReturn(cacheStats);

        mvc.perform(get("/api/cache-stats")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJSONContent));


        verify(service, VerificationModeFactory.times(1)).getCacheStats();
        reset(service);
    }
}
