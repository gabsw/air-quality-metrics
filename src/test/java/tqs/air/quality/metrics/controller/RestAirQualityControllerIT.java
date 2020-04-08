package tqs.air.quality.metrics.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.air.quality.metrics.AirQualityMetricsApplication;
import tqs.air.quality.metrics.mocks.MockBase;

import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tqs.air.quality.metrics.mocks.PresentMocks.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = AirQualityMetricsApplication.class)
@AutoConfigureMockMvc
public class RestAirQualityControllerIT {
    @Autowired
    private MockMvc mvc;

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    private String formattedLocalDateTime = currentLocalDateTime.minusHours(10).format(formatter);

    @Test
    void givenAllParameters_whenGetMetrics_thenStatus200() throws Exception {
        mvc.perform(get("/api/air-quality-metrics")
                .contentType(MediaType.APPLICATION_JSON)
                .param("latitude", String.valueOf(MockBase.latitude))
                .param("longitude", String.valueOf(MockBase.longitude))
                .param("datetime", formattedLocalDateTime)
        ).andExpect(status().isOk());
    }


    @Test
    void givenRequiredParameters_whenGetMetrics_thenStatus200() throws Exception {

        mvc.perform(
                get("/api/air-quality-metrics")
                .contentType(MediaType.APPLICATION_JSON)
                .param("latitude", String.valueOf(MockBase.latitude))
                .param("longitude", String.valueOf(MockBase.longitude))
        ).andExpect(status().isOk());
    }
}
