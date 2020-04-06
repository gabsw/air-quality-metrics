package tqs.air.quality.metrics.controller;

import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.air.quality.metrics.exception.ResultNotFoundException;
import tqs.air.quality.metrics.service.AirQualityMetricsService;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tqs.air.quality.metrics.Utils.*;

@WebMvcTest(RestAirQualityController.class)
public class RestAirQualityControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AirQualityMetricsService service;

    private String expectedJSONContent = "{\"latitude\":" +
            latitude +
            ",\"longitude\":" +
            longitude +
            ",\"dateTime\":\"" +
            currentLocalDateTime +
            "\",\"gases\":[{\"initials\":\"" +
            pollutantInitials +
            "\",\"name\":\"" +
            pollutantFullName +
            "\",\"amount\":" +
            concentrationValue +
            ",\"units\":\"" +
            concentrationUnits +
            "\"}]}";


    @Test
    void givenAllParameters_whenGetMetrics_thenReturnJson() throws Exception {
        given(service.getAirQualityMetrics(locationDatetime)).willReturn(airQualityMetrics);

        mvc.perform(get("/api/air-quality-metrics")
                .contentType(MediaType.APPLICATION_JSON)
                .param("latitude", String.valueOf(latitude))
                .param("longitude", String.valueOf(longitude))
                .param("datetime", String.valueOf(currentLocalDateTime))
        )
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJSONContent));


        verify(service, VerificationModeFactory.times(1)).getAirQualityMetrics(locationDatetime);
        reset(service);
    }


    @Test
    void givenRequiredParameters_whenGetMetrics_thenReturnJson() throws Exception {

        given(service.getAirQualityMetrics(locationDatetimeWithNull)).willReturn(airQualityMetricsWithNull);

        mvc.perform(
                get("/api/air-quality-metrics")
                .contentType(MediaType.APPLICATION_JSON)
                .param("latitude", String.valueOf(latitude))
                .param("longitude", String.valueOf(longitude))
        ).andExpect(status().isOk())
                .andExpect(content().json(expectedJSONContent));
        verify(service, VerificationModeFactory.times(1)).getAirQualityMetrics(locationDatetimeWithNull);
        reset(service);
    }

    @Test
    void givenResultNotFoundException_whenGetMetrics_thenThrowHTTPStatusNotFound() throws Exception {

        given(service.getAirQualityMetrics(locationDatetime)).willThrow(
                new ResultNotFoundException());

        mvc.perform(get("/api/air-quality-metrics")
                .contentType(MediaType.APPLICATION_JSON)
                .param("latitude", String.valueOf(latitude))
                .param("longitude", String.valueOf(longitude))
                .param("datetime", String.valueOf(currentLocalDateTime))
        )
                .andExpect(status().isNotFound());
        verify(service, VerificationModeFactory.times(1)).getAirQualityMetrics(locationDatetime);
        reset(service);
    }


}
