package tqs.air.quality.metrics.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.air.quality.metrics.AirQualityMetricsApplication;
import tqs.air.quality.metrics.service.AirQualityMetricsService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = AirQualityMetricsApplication.class)
@AutoConfigureMockMvc
public class RestCacheStatsControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AirQualityMetricsService service;

    @Test
    void whenGetMetrics_thenStatus200() throws Exception {
        mvc.perform(get("/api/cache-stats")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }
}
