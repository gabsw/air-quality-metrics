package tqs.air.quality.metrics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tqs.air.quality.metrics.model.CacheStatsDTO;
import tqs.air.quality.metrics.service.AirQualityMetricsService;

@RestController
@RequestMapping("api/cache-stats")
public class RestCacheStatsController {

    @Autowired
    private AirQualityMetricsService service;

    @GetMapping("")
    public CacheStatsDTO getCacheStats() {
        return service.getCacheStatsDTO();
    }
}
