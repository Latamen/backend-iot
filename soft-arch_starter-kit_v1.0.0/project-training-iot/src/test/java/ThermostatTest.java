import org.example.models.Thermostat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ThermostatTest {
    private Thermostat thermostat;

    @Before
    public void before() {
        thermostat = new Thermostat(10, 50);
    }

    @Test
    public void setTemperatureWithinValidRangeShouldSucceed() {
        Assert.assertEquals(thermostat.getMinTemperature(), thermostat.getTemperature());
        thermostat.setTemperature(thermostat.getMaxTemperature());

        Assert.assertEquals(thermostat.getMaxTemperature(), thermostat.getTemperature());
    }

    @Test
    public void setTemperatureBelowMinShouldFail() {
        Assert.assertEquals(thermostat.getMinTemperature(), thermostat.getTemperature());
        thermostat.setTemperature(thermostat.getMinTemperature() - 1);

        Assert.assertEquals(thermostat.getMinTemperature(), thermostat.getTemperature());
    }

    @Test
    public void setTemperatureAboveMaxShouldFail() {
        Assert.assertEquals(thermostat.getMinTemperature(), thermostat.getTemperature());
        thermostat.setTemperature(thermostat.getMaxTemperature() + 1);

        Assert.assertEquals(thermostat.getMinTemperature(), thermostat.getTemperature());
    }
}
