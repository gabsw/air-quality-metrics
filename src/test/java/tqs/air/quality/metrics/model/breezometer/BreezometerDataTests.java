package tqs.air.quality.metrics.model.breezometer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BreezometerDataTests {

    private BreezometerData data;

    @BeforeEach
    void setUp() {
        data = new BreezometerData();
    }

    @Test
    void checkSetDateTime() {
        data.setDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        assertEquals(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), data.getDateTime());
    }

    @Test
    void checkSetDataAvailable() {
        data.setDateAvailable(true);
        assertEquals(true, data.getDateAvailable());
    }

    @Test
    void checkSetPollutants() {
        BreezometerPollutant pollutant = new BreezometerPollutant("CO", "Carbon monoxide",
                new BreezometerConcentration(136.77, "ppb"));
        Map<String, BreezometerPollutant> pollutantsMap = new HashMap<>();
        pollutantsMap.put("co", pollutant);

        data.setPollutants(pollutantsMap);
        assertEquals(pollutantsMap, data.getPollutants());
    }
}
