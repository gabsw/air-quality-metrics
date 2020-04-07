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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


@Controller
@RequestMapping("/")
public class AirQualityController {

    @Autowired
    private AirQualityMetricsService service;

    @GetMapping("")
    public String getLocationAndDate(Model model) {
        model.addAttribute("locationDateTime", new LocationDatetime());
        return "index";
    }

    @PostMapping("/air-quality")
    public String displayResults(LocationDatetime locationDatetime, Model model)
            throws ResultNotFoundException {
        AirQualityMetrics metrics = service.getAirQualityMetrics(locationDatetime);
        if (metrics != null) {
            model.addAttribute("metrics", metrics);
            return "results";
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            String formattedLocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(formatter);

            model.addAttribute("error", "Result Not Found");
            model.addAttribute("status", "404 Not Found");
            model.addAttribute("message", "Breezometer result not found for: " +
                    "latitude=" + locationDatetime.getLatitude() + ", longitude=" + locationDatetime.getLongitude());
            model.addAttribute("timestamp", formattedLocalDateTime);
            return "error";
        }

    }
}
