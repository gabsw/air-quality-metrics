package tqs.air.quality.metrics.controller;

import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.air.quality.metrics.exception.ResultNotFoundException;
import tqs.air.quality.metrics.model.LocationDatetime;
import tqs.air.quality.metrics.service.AirQualityMetricsService;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tqs.air.quality.metrics.Utils.*;

@WebMvcTest(AirQualityController.class)
public class AirQualityControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AirQualityMetricsService service;

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    private String formattedLocalDateTime = currentLocalDateTime.truncatedTo(ChronoUnit.SECONDS).format(formatter);

    @Test
    void givenHomePageURI_thenReturnIndexView() throws Exception {
        mvc.perform(get("/")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(view().name("index"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("locationDateTime", new LocationDatetime()));
    }

    @Test
    void givenAllParameters_whenPost_thenReturnResultsView() throws Exception {
        given(service.getAirQualityMetrics(locationDatetime)).willReturn(airQualityMetrics);

        mvc.perform(post("/air-quality")
                .contentType(MediaType.TEXT_HTML)
                .param("latitude", String.valueOf(latitude))
                .param("longitude", String.valueOf(longitude))
                .param("localDateTime", formattedLocalDateTime)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("results"))
                .andExpect(model().attribute("metrics", airQualityMetrics));


        verify(service, VerificationModeFactory.times(1)).getAirQualityMetrics(locationDatetime);
        reset(service);
    }

    @Test
    void givenRequiredParameters_whenPost_thenReturnResultsView() throws Exception {
        given(service.getAirQualityMetrics(locationDatetimeWithNull)).willReturn(airQualityMetricsWithNull);

        mvc.perform(post("/air-quality")
                .contentType(MediaType.TEXT_HTML)
                .param("latitude", String.valueOf(latitude))
                .param("longitude", String.valueOf(longitude))
        )
                .andExpect(status().isOk())
                .andExpect(view().name("results"))
                .andExpect(model().attribute("metrics", airQualityMetricsWithNull));
        verify(service, VerificationModeFactory.times(1))
                .getAirQualityMetrics(locationDatetimeWithNull);
        reset(service);
    }

    @Test
    void givenResultNotFoundException_whenPost_thenStatusIs404() throws Exception {
        given(service.getAirQualityMetrics(locationDatetime)).willThrow(
                new ResultNotFoundException());

        mvc.perform(post("/air-quality")
                .contentType(MediaType.TEXT_HTML)
                .param("latitude", String.valueOf(latitude))
                .param("longitude", String.valueOf(longitude))
                .param("localDateTime", formattedLocalDateTime)
        )
                .andExpect(status().isNotFound());
        verify(service, VerificationModeFactory.times(1)).getAirQualityMetrics(locationDatetime);
        reset(service);
    }


}
