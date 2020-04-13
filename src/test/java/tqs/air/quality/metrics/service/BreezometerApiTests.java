package tqs.air.quality.metrics.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import tqs.air.quality.metrics.exception.ApiServerException;
import tqs.air.quality.metrics.exception.BadInputException;
import tqs.air.quality.metrics.exception.ResultNotFoundException;
import tqs.air.quality.metrics.mocks.MockBase;
import tqs.air.quality.metrics.model.breezometer.BreezometerResult;
import tqs.air.quality.metrics.utils.Time;

import java.net.URI;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static tqs.air.quality.metrics.mocks.FutureMocks.futureBreezometerResult;
import static tqs.air.quality.metrics.mocks.FutureMocks.futureLocalDateTime;
import static tqs.air.quality.metrics.mocks.PastMocks.*;
import static tqs.air.quality.metrics.mocks.PresentMocks.currentBreezometerResult;
import static tqs.air.quality.metrics.mocks.PresentMocks.currentLocalDateTime;

public class BreezometerApiTests {
    private static final String API_KEY = "55d98126b0ed483da9ff706420b37411";

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private Time time;

    @InjectMocks
    private BreezometerApi service;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
        when(time.now()).thenReturn(currentLocalDateTime);
        service.setApiKey(API_KEY);
    }

    @Test
    void getValidPresentBreezometerResult() {
        URI uri = service.buildUriForRequest(MockBase.latitude, MockBase.longitude, null);

        ResponseEntity<BreezometerResult> result = new ResponseEntity<>(currentBreezometerResult, HttpStatus.OK);

        when(restTemplate.getForEntity(uri, BreezometerResult.class)).thenReturn(result);

        String expectedUri = "https://api.breezometer.com/air-quality/v2/current-conditions?lat=48.857456&lon=2.354611&key=55d98126b0ed483da9ff706420b37411&features=pollutants_concentrations";

        assertAll("present breezometer call",
                () -> assertEquals(currentBreezometerResult, service.getBreezometerResult(MockBase.latitude, MockBase.longitude, null)),
                () -> assertEquals(expectedUri, uri.toString()));
    }

    @Test
    void getValidPastBreezometerResult() {
        URI uri = service.buildUriForRequest(MockBase.latitude, MockBase.longitude, pastLocalDateTime);

        ResponseEntity<BreezometerResult> result = new ResponseEntity<>(pastBreezometerResult, HttpStatus.OK);

        when(restTemplate.getForEntity(uri, BreezometerResult.class)).thenReturn(result);

        String formattedDate = pastLocalDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
        String expectedUri = "https://api.breezometer.com/air-quality/v2/historical/hourly?lat=48.857456&lon=2.354611&key=55d98126b0ed483da9ff706420b37411&datetime=" + formattedDate + "&features=pollutants_concentrations";
        assertAll("past breezometer call",
                () -> assertEquals(pastBreezometerResult, service.getBreezometerResult(MockBase.latitude, MockBase.longitude, pastLocalDateTime)),
                () -> assertEquals(expectedUri, uri.toString()));
    }

    @Test
    void getValidFutureBreezometerResult() {
        URI uri = service.buildUriForRequest(MockBase.latitude, MockBase.longitude, futureLocalDateTime);

        ResponseEntity<BreezometerResult> result = new ResponseEntity<>(futureBreezometerResult, HttpStatus.OK);

        when(restTemplate.getForEntity(uri, BreezometerResult.class)).thenReturn(result);

        String formattedDate = futureLocalDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
        String expectedUri = "https://api.breezometer.com/air-quality/v2/forecast/hourly?lat=48.857456&lon=2.354611&key=55d98126b0ed483da9ff706420b37411&datetime=" + formattedDate + "&features=pollutants_concentrations";
        assertAll("future breezometer call",
                () -> assertEquals(futureBreezometerResult, service.getBreezometerResult(MockBase.latitude, MockBase.longitude, futureLocalDateTime)),
                () -> assertEquals(expectedUri, uri.toString()));
    }

    @Test
    void getValidBreezometerResultAfterCutOffTime() {
        URI uri = service.buildUriForRequest(MockBase.latitude, MockBase.longitude, pastLocalDateTimeAfterCutOff);

        ResponseEntity<BreezometerResult> result = new ResponseEntity<>(pastBreezometerResultAfterCutOff, HttpStatus.OK);

        when(restTemplate.getForEntity(uri, BreezometerResult.class)).thenReturn(result);

        String formattedDate = pastLocalDateTimeAfterCutOff.format(DateTimeFormatter.ISO_DATE_TIME);
        String expectedUri = "https://api.breezometer.com/air-quality/v2/forecast/hourly?lat=48.857456&lon=2.354611&key=55d98126b0ed483da9ff706420b37411&datetime=" + formattedDate + "&features=pollutants_concentrations";
        assertAll("breezometer call after cut off time",
                () -> assertEquals(pastBreezometerResultAfterCutOff,
                        service.getBreezometerResult(MockBase.latitude, MockBase.longitude, pastLocalDateTimeAfterCutOff)),
                () -> assertEquals(expectedUri, uri.toString()));
    }

    @Test
    void whenResultWasNotFoundByExternalApi_ResultNotFoundExceptionShouldBeThrownByBreezometerResult() {
        URI uri = service.buildUriForRequest(MockBase.latitude, MockBase.longitude, futureLocalDateTime);
        ResponseEntity<BreezometerResult> result = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        when(restTemplate.getForEntity(uri, BreezometerResult.class)).thenReturn(result);

        assertThrows(ResultNotFoundException.class,
                () -> service.getBreezometerResult(MockBase.latitude, MockBase.longitude, futureLocalDateTime));
    }

    @Test
    void whenBadRequestWasReturnedByExternalApi_BadInputExceptionShouldBeThrownByBreezometerResult() {
        URI uri = service.buildUriForRequest(MockBase.latitude, MockBase.longitude, futureLocalDateTime);
        ResponseEntity<BreezometerResult> result = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        when(restTemplate.getForEntity(uri, BreezometerResult.class)).thenReturn(result);

        assertThrows(BadInputException.class,
                () -> service.getBreezometerResult(MockBase.latitude, MockBase.longitude, futureLocalDateTime));
    }

    @Test
    void whenRestClientExceptionWasReturnedByExternalApi_BadInputExceptionShouldBeThrownByBreezometerResult() {
        URI uri = service.buildUriForRequest(MockBase.latitude, MockBase.longitude, futureLocalDateTime);

        when(restTemplate.getForEntity(uri, BreezometerResult.class)).thenThrow(new RestClientException(""));

        assertThrows(ApiServerException.class,
                () -> service.getBreezometerResult(MockBase.latitude, MockBase.longitude, futureLocalDateTime));
    }


    @Test
    void testBeans() {

        assertAll("beans",
                () -> assertNotNull(service.restTemplate()),
                () -> assertNotNull(service.time()));
    }
}
