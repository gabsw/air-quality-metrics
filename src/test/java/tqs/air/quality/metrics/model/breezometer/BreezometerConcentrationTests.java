package tqs.air.quality.metrics.model.breezometer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BreezometerConcentrationTests {

    private BreezometerConcentration concentration;

    @BeforeEach
    void setUp() {
        concentration = new BreezometerConcentration();
    }

    @Test
    void checkSetValue() {
        concentration.setValue(150.22);
        assertEquals(150.22, concentration.getValue());
    }

    @Test
    void checkSetUnits() {
        concentration.setUnits("ppm");
        assertEquals("ppm", concentration.getUnits());
    }
}
