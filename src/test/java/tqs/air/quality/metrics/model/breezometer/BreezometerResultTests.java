package tqs.air.quality.metrics.model.breezometer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BreezometerResultTests {

    private BreezometerResult result;

    @BeforeEach
    void setUp() {
        result = new BreezometerResult();
    }

    @Test
    void checkSetError() {
        result.setError("null");
        assertEquals("null", result.getError());
    }

    @Test
    void checkSetData() {
        BreezometerPollutant pollutant = new BreezometerPollutant("CO", "Carbon monoxide",
                new BreezometerConcentration(136.77, "ppb"));
        Map<String, BreezometerPollutant> pollutantsMap = new HashMap<>();
        pollutantsMap.put("co", pollutant);

        BreezometerData data = new BreezometerData(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                true, pollutantsMap);

        result.setData(data);

        assertAll("data",
                () -> assertEquals(pollutantsMap, result.getData().getPollutants()),
                () -> assertEquals(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                        result.getData().getDateTime()),
                () -> assertEquals(true, result.getData().getDateAvailable()));
    }
}
