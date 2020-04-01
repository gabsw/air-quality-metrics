package tqs.air.quality.metrics.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.air.quality.metrics.exception.ApiServerException;
import tqs.air.quality.metrics.exception.BadInputException;
import tqs.air.quality.metrics.exception.ResultNotFoundException;
import tqs.air.quality.metrics.exception.UnexpectedException;
import tqs.air.quality.metrics.model.AirQualityMetrics;
import tqs.air.quality.metrics.model.CacheStatsDTO;
import tqs.air.quality.metrics.model.LocationDatetime;
import tqs.air.quality.metrics.model.breezometer.BreezometerResult;
import tqs.air.quality.metrics.validator.Validators;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class AirQualityMetricsService {
    @Autowired
    private BreezometerAPI api;
    private LoadingCache<LocationDatetime, AirQualityMetrics> cache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .recordStats()
            .build(
                    new CacheLoader<LocationDatetime, AirQualityMetrics>() {

                        private AirQualityMetrics getMetricsFromAPI(double latitude, double longitude, LocalDateTime localDateTime)
                                throws ResultNotFoundException, ApiServerException {
                            BreezometerResult breezometerResult = api.getBreezometerResult(latitude, longitude, localDateTime);
                            return new AirQualityMetrics(latitude, longitude, breezometerResult);
                        }

                        @Override
                        public AirQualityMetrics load(LocationDatetime parameters) throws Exception {
                            return getMetricsFromAPI(parameters.getLatitude(),
                                    parameters.getLongitude(), parameters.getLocalDateTime());
                        }
                    });

    public AirQualityMetrics getAirQualityMetrics(double latitude, double longitude, LocalDateTime localDateTime)
            throws ResultNotFoundException {
        Validators.checkIfUserInputIsValid(latitude, longitude, localDateTime);

        try {
            return cache.get(new LocationDatetime(latitude, longitude, localDateTime));
        } catch (ExecutionException e) {
            Throwable throwable = e.getCause();

            if (throwable instanceof BadInputException) {
                throw (BadInputException) throwable;
            } else if (throwable instanceof ResultNotFoundException) {
                throw (ResultNotFoundException) throwable;
            } else {
                throw new UnexpectedException(throwable);
            }
        }
    }

    public CacheStatsDTO getCacheStatsDTO() {
        return new CacheStatsDTO(cache.stats());
    }

}
