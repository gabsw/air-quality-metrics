package tqs.air.quality.metrics.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.air.quality.metrics.AirQualityMetricsApplication;
import tqs.air.quality.metrics.mocks.MockBase;
import tqs.air.quality.metrics.service.AirQualityMetricsService;

import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static tqs.air.quality.metrics.mocks.PresentMocks.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = AirQualityMetricsApplication.class)
@AutoConfigureMockMvc
public class AirQualityControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AirQualityMetricsService service;

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    private String formattedLocalDateTime = currentLocalDateTime.format(formatter);

    @Test
    void givenAirQualityURIWithPostAndFormData_WithAllParameters_thenStatus200() throws Exception {
        mvc.perform(post("/air-quality")
                .contentType(MediaType.TEXT_HTML)
                .param("latitude", String.valueOf(MockBase.latitude))
                .param("longitude", String.valueOf(MockBase.longitude))
                .param("datetime", formattedLocalDateTime))
                .andExpect(view().name("results"))
                .andExpect(status().isOk());
    }


    @Test
    void givenAirQualityURIWithPostAndFormData_WithRequiredParameters_thenStatus200() throws Exception {
        mvc.perform(post("/air-quality")
                .contentType(MediaType.TEXT_HTML)
                .param("latitude", String.valueOf(MockBase.latitude))
                .param("longitude", String.valueOf(MockBase.longitude)))
                .andExpect(view().name("results"))
                .andExpect(status().isOk());
    }
}
