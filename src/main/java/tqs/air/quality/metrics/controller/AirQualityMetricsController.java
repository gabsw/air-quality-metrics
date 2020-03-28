package tqs.air.quality.metrics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tqs.air.quality.metrics.model.AirQualityMetrics;
import tqs.air.quality.metrics.service.AirQualityMetricsService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("air-quality-metrics")
public class AirQualityMetricsController {

    @Autowired
    private AirQualityMetricsService service;

    @GetMapping("")
    public AirQualityMetrics getAirQualityMetrics(@RequestParam double latitude,
                                                      @RequestParam double longitude,
                                                      @RequestParam(name = "datetime", required = false)
                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                              LocalDateTime localDateTime) throws Exception {
        return service.getAirQualityMetrics(latitude, longitude, localDateTime);

    }
}
