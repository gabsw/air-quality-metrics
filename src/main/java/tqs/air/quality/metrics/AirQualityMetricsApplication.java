package tqs.air.quality.metrics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tqs.air.quality.metrics.model.breezometer.BreezometerResult;
import tqs.air.quality.metrics.service.BreezometerConsumer;

@SpringBootApplication
public class AirQualityMetricsApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AirQualityMetricsApplication.class, args);

		BreezometerConsumer consumer = new BreezometerConsumer();
		BreezometerResult result = consumer.getCurrentBreezometerResult(48.857456, 2.354611);

		System.out.println(result);


	}

}
