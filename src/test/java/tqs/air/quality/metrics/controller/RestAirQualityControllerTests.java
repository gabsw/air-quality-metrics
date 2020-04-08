package tqs.air.quality.metrics.controller;

import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.air.quality.metrics.exception.ResultNotFoundException;
import tqs.air.quality.metrics.mocks.MockBase;
import tqs.air.quality.metrics.service.AirQualityMetricsService;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tqs.air.quality.metrics.mocks.PresentMocks.*;

@WebMvcTest(RestAirQualityController.class)
public class RestAirQualityControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AirQualityMetricsService service;

    private String expectedJSONContent = "{\"latitude\":" +
            MockBase.latitude +
            ",\"longitude\":" +
            MockBase.longitude +
            ",\"dateTime\":\"" +
            currentLocalDateTime +
            "\",\"gases\":[{\"initials\":\"" +
            MockBase.pollutantInitials +
            "\",\"name\":\"" +
            MockBase.pollutantFullName +
            "\",\"amount\":" +
            MockBase.concentrationValue +
            ",\"units\":\"" +
            MockBase.concentrationUnits +
            "\"}]}";


    @Test
    void givenAllParameters_whenGetMetrics_thenReturnJson() throws Exception {
        given(service.getAirQualityMetrics(currentLocationDatetime)).willReturn(currentAirQualityMetrics);

        mvc.perform(get("/api/air-quality-metrics")
                .contentType(MediaType.APPLICATION_JSON)
                .param("latitude", String.valueOf(MockBase.latitude))
                .param("longitude", String.valueOf(MockBase.longitude))
                .param("datetime", String.valueOf(currentLocalDateTime))
        )
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJSONContent));


        verify(service, VerificationModeFactory.times(1)).getAirQualityMetrics(currentLocationDatetime);
        reset(service);
    }


    @Test
    void givenRequiredParameters_whenGetMetrics_thenReturnJson() throws Exception {
        given(service.getAirQualityMetrics(currentLocationDatetimeWithNull)).willReturn(currentAirQualityMetricsWithNull);

        mvc.perform(
                get("/api/air-quality-metrics")
                .contentType(MediaType.APPLICATION_JSON)
                .param("latitude", String.valueOf(MockBase.latitude))
                .param("longitude", String.valueOf(MockBase.longitude))
        ).andExpect(status().isOk())
                .andExpect(content().json(expectedJSONContent));
        verify(service, VerificationModeFactory.times(1))
                .getAirQualityMetrics(currentLocationDatetimeWithNull);
        reset(service);
    }

    @Test
    void givenResultNotFoundException_whenGetMetrics_thenThrowHTTPStatusNotFound() throws Exception {
        given(service.getAirQualityMetrics(currentLocationDatetime)).willThrow(
                new ResultNotFoundException());

        mvc.perform(get("/api/air-quality-metrics")
                .contentType(MediaType.APPLICATION_JSON)
                .param("latitude", String.valueOf(MockBase.latitude))
                .param("longitude", String.valueOf(MockBase.longitude))
                .param("datetime", String.valueOf(currentLocalDateTime))
        )
                .andExpect(status().isNotFound());
        verify(service, VerificationModeFactory.times(1)).getAirQualityMetrics(currentLocationDatetime);
        reset(service);
    }


}
