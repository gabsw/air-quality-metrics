package tqs.air.quality.metrics.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.air.quality.metrics.model.AirQualityMetrics;
import tqs.air.quality.metrics.model.CacheStatsDTO;
import tqs.air.quality.metrics.model.breezometer.BreezometerResult;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class AirQualityMetricsService {
    private LoadingCache<RequestParameters, AirQualityMetrics> cache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .recordStats()
            .build(
                    new CacheLoader<RequestParameters, AirQualityMetrics>() {
                        @Override
                        public AirQualityMetrics load(RequestParameters parameters) throws Exception {
                            return getMetricsFromAPI(parameters.latitude,parameters.longitude,parameters.localDateTime);
                        }
                    });

    @Autowired
    private BreezometerAPI api;

    private AirQualityMetrics getMetricsFromAPI(double latitude, double longitude, LocalDateTime localDateTime)
            throws Exception {
        BreezometerResult breezometerResult = api.getBreezometerResult(latitude, longitude, localDateTime);
        return new AirQualityMetrics(latitude, longitude, breezometerResult);
    }

    public AirQualityMetrics getAirQualityMetrics(double latitude, double longitude, LocalDateTime localDateTime) throws
            Exception {
        return cache.get(new RequestParameters(latitude, longitude, localDateTime));
    }

    public CacheStatsDTO getCacheStatsDTO() {
        return new CacheStatsDTO(cache.stats());
    }

    private static class RequestParameters {
        private final double latitude;
        private final double longitude;
        private final LocalDateTime localDateTime;

        RequestParameters(double latitude, double longitude, LocalDateTime localDateTime) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.localDateTime = localDateTime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RequestParameters that = (RequestParameters) o;
            return Double.compare(that.latitude, latitude) == 0 &&
                    Double.compare(that.longitude, longitude) == 0 &&
                    Objects.equals(localDateTime, that.localDateTime);
        }

        @Override
        public int hashCode() {
            return Objects.hash(latitude, longitude, localDateTime);
        }
    }
}
