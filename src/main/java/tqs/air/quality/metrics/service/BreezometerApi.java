package tqs.air.quality.metrics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tqs.air.quality.metrics.exception.ApiServerException;
import tqs.air.quality.metrics.exception.BadInputException;
import tqs.air.quality.metrics.exception.ResultNotFoundException;
import tqs.air.quality.metrics.model.breezometer.BreezometerResult;
import tqs.air.quality.metrics.utils.Time;
import tqs.air.quality.metrics.utils.TimeImpl;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
public class BreezometerApi {
    private static final String URI_PRESENT = "https://api.breezometer.com/air-quality/v2/current-conditions";
    private static final String URI_PAST = "https://api.breezometer.com/air-quality/v2/historical/hourly";
    private static final String URI_FUTURE = "https://api.breezometer.com/air-quality/v2/forecast/hourly";
    private static final String POLLUTANT_FEATURE = "pollutants_concentrations";
    private static final String LATITUDE = "lat";
    private static final String LONGITUDE = "lon";
    private static final String KEY = "key";
    private static final String DATETIME = "datetime";
    private static final String FEATURES = "features";

    @Value("${breezometer.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Time time;


    public BreezometerResult getBreezometerResult(double latitude, double longitude, LocalDateTime localDateTime)
            throws ResultNotFoundException, ApiServerException {

        URI uri = buildUriForRequest(latitude, longitude, localDateTime);
        ResponseEntity<BreezometerResult> response;
        try {
            response = restTemplate.getForEntity(uri, BreezometerResult.class);
        } catch (RestClientException ex) {
            throw new ApiServerException("Server error in the Breezometer API.", ex);
        }

        if (response.getStatusCodeValue() == 404) {
            throw new ResultNotFoundException("Breezometer result not found for: " +
                    "latitude=" + latitude + ", longitude=" + longitude);
        }

        if (response.getStatusCodeValue() != 200) {
            throw new BadInputException("Breezometer request not valid for: " +
                    "latitude=" + latitude + ", longitude=" + longitude);
        }
        return response.getBody();
    }

    URI buildUriForRequest(double latitude, double longitude,
                           LocalDateTime localDateTime) {

        LocalDateTime currentTime = time.now();
        LocalDateTime cutOffTime = currentTime.truncatedTo(ChronoUnit.DAYS).plusHours(10);

        if (localDateTime == null) {
            return buildUriForPresentRequest(latitude, longitude);
        } else if (localDateTime.isBefore(currentTime) && localDateTime.isBefore(cutOffTime)) {
            return buildUriForPastRequest(latitude, longitude, localDateTime);
        } else {
            return buildUriForFutureRequest(latitude, longitude, localDateTime);
        }
    }

    private URI buildUriForPresentRequest(double latitude, double longitude) {
        return UriComponentsBuilder.fromHttpUrl(URI_PRESENT)
                .queryParam(LATITUDE, latitude)
                .queryParam(LONGITUDE, longitude)
                .queryParam(KEY, apiKey)
                .queryParam(FEATURES, POLLUTANT_FEATURE)
                .build()
                .encode()
                .toUri();
    }

    private URI buildUriForPastRequest(double latitude, double longitude,
                                       LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String formattedLocalDateTime = localDateTime.format(formatter);
        return UriComponentsBuilder.fromHttpUrl(URI_PAST)
                .queryParam(LATITUDE, latitude)
                .queryParam(LONGITUDE, longitude)
                .queryParam(KEY, apiKey)
                .queryParam(DATETIME, formattedLocalDateTime)
                .queryParam(FEATURES, POLLUTANT_FEATURE)
                .build()
                .encode()
                .toUri();
    }

    private URI buildUriForFutureRequest(double latitude, double longitude,
                                         LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String formattedLocalDateTime = localDateTime.format(formatter);
        return UriComponentsBuilder.fromHttpUrl(URI_FUTURE)
                .queryParam(LATITUDE, latitude)
                .queryParam(LONGITUDE, longitude)
                .queryParam(KEY, apiKey)
                .queryParam(DATETIME, formattedLocalDateTime)
                .queryParam(FEATURES, POLLUTANT_FEATURE)
                .build()
                .encode()
                .toUri();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Time time() {
        return new TimeImpl();
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
