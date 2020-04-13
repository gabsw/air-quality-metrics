package tqs.air.quality.metrics.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tqs.air.quality.metrics.model.breezometer.BreezometerConcentration;
import tqs.air.quality.metrics.model.breezometer.BreezometerData;
import tqs.air.quality.metrics.model.breezometer.BreezometerPollutant;
import tqs.air.quality.metrics.model.breezometer.BreezometerResult;
import tqs.air.quality.metrics.utils.Equals;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AirQualityMetricsTests {

    private AirQualityMetrics metrics;
    private AirQualityMetrics differentMetrics;
    private AirQualityMetrics equalMetrics;
    private Gas gas;
    private Gas differentGas;
    private Gas equalGas;

    @BeforeEach
    void initializeAirQualityMetrics() {
        BreezometerPollutant pollutant1 = new BreezometerPollutant("CO", "Carbon monoxide",
                new BreezometerConcentration(136.77, "ppb"));
        gas = new Gas(pollutant1);
        BreezometerPollutant pollutant2 = new BreezometerPollutant("SO2", "Sulfur Dioxide",
                new BreezometerConcentration(150.48, "m3"));
        differentGas = new Gas(pollutant2);
        BreezometerPollutant pollutant3 = new BreezometerPollutant("CO", "Carbon monoxide",
                new BreezometerConcentration(136.77, "ppb"));
        equalGas = new Gas(pollutant3);

        Map<String, BreezometerPollutant> pollutants1 = new HashMap<>();
        pollutants1.put("co", pollutant1);
        BreezometerData data1 = new BreezometerData(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), true, pollutants1);
        BreezometerResult result1 = new BreezometerResult("false", data1);

        Map<String, BreezometerPollutant> pollutants2 = new HashMap<>();
        pollutants2.put("so2", pollutant2);
        BreezometerData data2 = new BreezometerData(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plusHours(1), true, pollutants2);
        BreezometerResult result2 = new BreezometerResult("false", data2);

        Map<String, BreezometerPollutant> pollutants3 = new HashMap<>();
        pollutants3.put("co", pollutant3);
        BreezometerData data3 = new BreezometerData(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), true, pollutants3);
        BreezometerResult result3 = new BreezometerResult("false", data3);

        metrics = new AirQualityMetrics(48, 2, result1);
        differentMetrics = new AirQualityMetrics(50, 10, result2);
        equalMetrics = new AirQualityMetrics(48, 2, result3);
    }

    @Test
    void checkEquals() {
        Equals.verifyEquals(AirQualityMetrics.class);
    }

    @Test
    void checkHashCode() {
        assertAll("hashCode",
                () -> assertEquals(metrics.hashCode(), equalMetrics.hashCode()),
                () -> assertNotEquals(metrics.hashCode(), differentMetrics.hashCode())
        );
    }

    @Test
    void checkToString() {
        assertAll("toString",
                () -> assertEquals(metrics.toString(), equalMetrics.toString()),
                () -> assertNotEquals(metrics.toString(), differentMetrics.toString())
        );
    }

    @Test
    void checkSetLatitude() {
        metrics.setLatitude(19);
        assertEquals(19, metrics.getLatitude());
    }

    @Test
    void checkSetLongitude() {
        metrics.setLongitude(19);
        assertEquals(19, metrics.getLongitude());
    }

    @Test
    void checkSetGases() {
        List<Gas> gases = new ArrayList<>();
        gases.add(gas);
        gases.add(differentGas);
        metrics.setGases(gases);
        assertEquals(gases, metrics.getGases());
    }

    @Test
    void checkSetDatetime() {
        metrics.setDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plusHours(1));
        assertEquals(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plusHours(1), metrics.getDateTime());
    }
}
