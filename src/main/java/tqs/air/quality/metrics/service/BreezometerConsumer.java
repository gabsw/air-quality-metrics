package tqs.air.quality.metrics.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tqs.air.quality.metrics.exception.BreezometerResultNotFoundException;
import tqs.air.quality.metrics.model.breezometer.BreezometerResult;

public class BreezometerConsumer {
    private static final String API_KEY = "55d98126b0ed483da9ff706420b37411";
    private static final String uri = "https://api.breezometer.com/air-quality/v2/current-conditions";
    private static final String POLLUTANT_FEATURE = "pollutants_concentrations";

    private ResponseEntity<BreezometerResult> pollutantRequest(double latitude, double longitude) {
        RestTemplate resttemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("key", API_KEY)
                .queryParam("features", POLLUTANT_FEATURE);

        return resttemplate.getForEntity(builder.build().encode().toUri(),
                BreezometerResult.class);
    }

    public BreezometerResult getCurrentBreezometerResult(double latitude, double longitude)
            throws Exception {

        ResponseEntity<BreezometerResult> response = pollutantRequest(latitude, longitude);

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
}
