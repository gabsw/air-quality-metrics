package tqs.air.quality.metrics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.air.quality.metrics.exception.ApiServerException;
import tqs.air.quality.metrics.exception.ResultNotFoundException;
import tqs.air.quality.metrics.model.AirQualityMetrics;
import tqs.air.quality.metrics.model.Cache;
import tqs.air.quality.metrics.model.CacheStats;
import tqs.air.quality.metrics.model.LocationDatetime;
import tqs.air.quality.metrics.model.breezometer.BreezometerResult;
import tqs.air.quality.metrics.validator.Validators;

@Service
public class AirQualityMetricsService {
    @Autowired
    private BreezometerAPI api;
    private Cache<LocationDatetime, AirQualityMetrics> cache = new Cache<>(60L * 1000, locationDatetime -> {
        BreezometerResult breezometerResult =
                api.getBreezometerResult(locationDatetime.getLatitude(),
                        locationDatetime.getLongitude(),
                        locationDatetime.getLocalDateTime());
        return new AirQualityMetrics(locationDatetime.getLatitude(), locationDatetime.getLongitude(), breezometerResult);
    });

    public AirQualityMetrics getAirQualityMetrics(LocationDatetime locationDatetime)
            throws ResultNotFoundException, ApiServerException {
        Validators.checkIfUserInputIsValid(locationDatetime);
        return cache.get(locationDatetime);
    }

    public CacheStats getCacheStats() {
        return cache.computeStats();
    }

}
