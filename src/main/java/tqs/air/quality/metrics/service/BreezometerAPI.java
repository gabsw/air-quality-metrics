package tqs.air.quality.metrics.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tqs.air.quality.metrics.exception.ApiServerException;
import tqs.air.quality.metrics.exception.BadInputException;
import tqs.air.quality.metrics.exception.ResultNotFoundException;
import tqs.air.quality.metrics.model.breezometer.BreezometerResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class BreezometerAPI {
    private static final String API_KEY = "55d98126b0ed483da9ff706420b37411";
    private static final String URI_PRESENT = "https://api.breezometer.com/air-quality/v2/current-conditions";
    private static final String URI_PAST = "https://api.breezometer.com/air-quality/v2/historical/hourly";
    private static final String URI_FUTURE = "https://api.breezometer.com/air-quality/v2/forecast/hourly";
    private static final String POLLUTANT_FEATURE = "pollutants_concentrations";
    private static final String LATITUDE = "lat";
    private static final String LONGITUDE = "lon";
    private static final String KEY = "key";
    private static final String DATETIME = "datetime";
    private static final String FEATURES = "features";


    public BreezometerResult getBreezometerResult(double latitude, double longitude, LocalDateTime localDateTime)
            throws ResultNotFoundException, ApiServerException {

        ResponseEntity<BreezometerResult> response = pollutantRequest(latitude, longitude, localDateTime);

        if (response.getStatusCodeValue() == 404) {
            throw new ResultNotFoundException("Breezometer result not found for: " +
                    "latitude=" + latitude + ", longitude=" + longitude);
        }

        if (response.getStatusCodeValue() >= 500) {
            throw new ApiServerException("Server error in the Breezometer API.");
        }

        if (response.getStatusCodeValue() != 200) {
            throw new BadInputException("Breezometer request not valid for: " +
                    "latitude=" + latitude + ", longitude=" + longitude);
        }
        return response.getBody();
    }

    private ResponseEntity<BreezometerResult> pollutantRequest(double latitude, double longitude,
                                                                           LocalDateTime localDateTime) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = buildUriForRequest(latitude, longitude, localDateTime);

        return restTemplate.getForEntity(builder.build().encode().toUri(),
                BreezometerResult.class);
    }

    private UriComponentsBuilder buildUriForRequest(double latitude, double longitude,
                                                    LocalDateTime localDateTime) {

        LocalDateTime currentTime = LocalDateTime.now();

        if (localDateTime == null) {
            return buildUriForPresentRequest(latitude, longitude);
        } else if (localDateTime.isBefore(currentTime)) {
            return buildUriForPastRequest(latitude, longitude, localDateTime);
        } else {
            return buildUriForFutureRequest(latitude, longitude, localDateTime);
        }
    }

    private UriComponentsBuilder buildUriForPresentRequest(double latitude, double longitude) {
        return UriComponentsBuilder.fromHttpUrl(URI_PRESENT)
                .queryParam(LATITUDE, latitude)
                .queryParam(LONGITUDE, longitude)
                .queryParam(KEY, API_KEY)
                .queryParam(FEATURES, POLLUTANT_FEATURE);
    }

    private UriComponentsBuilder buildUriForPastRequest(double latitude, double longitude,
                                                           LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String formattedLocalDateTime = localDateTime.format(formatter);
        return UriComponentsBuilder.fromHttpUrl(URI_PAST)
                .queryParam(LATITUDE, latitude)
                .queryParam(LONGITUDE, longitude)
                .queryParam(KEY, API_KEY)
                .queryParam(DATETIME, formattedLocalDateTime)
                .queryParam(FEATURES, POLLUTANT_FEATURE);
    }

    private UriComponentsBuilder buildUriForFutureRequest(double latitude, double longitude,
                                                        LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String formattedLocalDateTime = localDateTime.format(formatter);
        return UriComponentsBuilder.fromHttpUrl(URI_FUTURE)
                .queryParam(LATITUDE, latitude)
                .queryParam(LONGITUDE, longitude)
                .queryParam(KEY, API_KEY)
                .queryParam(DATETIME, formattedLocalDateTime)
                .queryParam(FEATURES, POLLUTANT_FEATURE);
    }
}
