package tqs.air.quality.metrics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tqs.air.quality.metrics.exception.ResultNotFoundException;
import tqs.air.quality.metrics.model.AirQualityMetrics;
import tqs.air.quality.metrics.model.LocationDatetime;
import tqs.air.quality.metrics.service.AirQualityMetricsService;


@Controller
@RequestMapping("/")
public class AirQualityController {

    @Autowired
    AirQualityMetricsService service;

    @GetMapping("")
    public String getLocationAndDate(Model model) {
        model.addAttribute("locationDateTime", new LocationDatetime());
        return "index";
    }

    @PostMapping("/air-quality")
    public String displayResults(LocationDatetime locationDatetime, Model model) {
        AirQualityMetrics metrics = service.getAirQualityMetrics(locationDatetime);
        model.addAttribute("metrics", metrics);
        return "results";
    }
}
