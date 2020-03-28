package tqs.air.quality.metrics.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tqs.air.quality.metrics.exception.BreezometerResultNotFoundException;
import tqs.air.quality.metrics.model.breezometer.BreezometerResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class BreezometerAPI {
    private static final String API_KEY = "55d98126b0ed483da9ff706420b37411";
    private static final String uri = "https://api.breezometer.com/air-quality/v2/current-conditions";
    private static final String POLLUTANT_FEATURE = "pollutants_concentrations";

    public BreezometerResult getBreezometerResult(double latitude, double longitude, LocalDateTime localDateTime)
            throws Exception {

        ResponseEntity<BreezometerResult> response;

        if (localDateTime == null) {
            response = currentPollutantRequest(latitude, longitude);
        } else {
            response = pastOrFuturePollutantRequest(latitude, longitude, localDateTime);
        }

        if (response.getStatusCodeValue() == 404) {
            throw new BreezometerResultNotFoundException("Breezometer result not found for: " +
                    "latitude=" + latitude + ", longitude=" + longitude);
        }

        if (response.getStatusCodeValue() != 200) {
            throw new Exception("Breezometer request not valid for: " +
                    "latitude=" + latitude + ", longitude=" + longitude);
        }

        return response.getBody();
    }

    private ResponseEntity<BreezometerResult> currentPollutantRequest(double latitude, double longitude) {
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("key", API_KEY)
                .queryParam("features", POLLUTANT_FEATURE);

        return restTemplate.getForEntity(builder.build().encode().toUri(),
                BreezometerResult.class);
    }

    private ResponseEntity<BreezometerResult> pastOrFuturePollutantRequest(double latitude, double longitude,
                                                                           LocalDateTime localDateTime) {
        RestTemplate restTemplate = new RestTemplate();

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String formattedLocalDateTime = localDateTime.format(formatter);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("key", API_KEY)
                .queryParam("datetime", formattedLocalDateTime)
                .queryParam("features", POLLUTANT_FEATURE);

        return restTemplate.getForEntity(builder.build().encode().toUri(),
                BreezometerResult.class);
    }
}
