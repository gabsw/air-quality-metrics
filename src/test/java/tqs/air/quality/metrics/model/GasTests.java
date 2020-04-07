package tqs.air.quality.metrics.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tqs.air.quality.metrics.model.breezometer.BreezometerConcentration;
import tqs.air.quality.metrics.model.breezometer.BreezometerPollutant;

import static org.junit.jupiter.api.Assertions.*;

public class GasTests {

    private Gas gas;
    private Gas differentGas;
    private Gas equalGas;

    @BeforeEach
    void initializeGas() {
        gas = new Gas(new BreezometerPollutant("CO", "Carbon monoxide",
                new BreezometerConcentration(136.77, "ppb")));
        differentGas = new Gas(new BreezometerPollutant("SO2", "Sulfur Dioxide",
                new BreezometerConcentration(150.48, "m3")));
        equalGas = new Gas(new BreezometerPollutant("CO", "Carbon monoxide",
                new BreezometerConcentration(136.77, "ppb")));
    }

    @Test
    void checkEquals() {
        assertAll("equals",
                () -> assertEquals(gas, equalGas),
                () -> assertNotEquals(gas, differentGas)
        );
    }

    @Test
    void checkHashCode() {
        assertAll("hashCode",
                () -> assertEquals(gas.hashCode(), equalGas.hashCode()),
                () -> assertNotEquals(gas.hashCode(), differentGas.hashCode())
        );
    }

    @Test
    void checkToString() {
        assertAll("toString",
                () -> assertEquals(gas.toString(), equalGas.toString()),
                () -> assertNotEquals(gas.toString(), differentGas.toString())
        );
    }

    @Test
    void checkSetInitials() {
        gas.setInitials("COM");
        assertEquals("COM", gas.getInitials());
    }

    @Test
    void checkSetName() {
        gas.setName("Carbon dioxide");
        assertEquals("Carbon dioxide", gas.getName());
    }

    @Test
    void checkSetAmount() {
        gas.setAmount(160.22);
        assertEquals(160.22, gas.getAmount());
    }

    @Test
    void checkSetUnits() {
        gas.setUnits("m3");
        assertEquals("m3", gas.getUnits());
    }
}
