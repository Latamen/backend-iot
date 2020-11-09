import org.example.HomeSystem;
import org.example.SystemLogger;
import org.example.models.Light;
import org.example.models.Thing;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class HomeSystemTest {

    private HomeSystem homeSystem;
    private SystemLogger logger;

    @Before
    public void before() {
        logger = Mockito.mock(SystemLogger.class);
        homeSystem = new HomeSystem(logger);
    }

    @Test
    public void addLightSuccess() {
        homeSystem.addThing(new Light()) ;
        Assert.assertEquals(1, homeSystem.getThings().size());
    }

    @Test
    public void toggleLightsWhenAllLightsAreReachableShouldSucceed() {
        homeSystem.addThing(new Light());
        homeSystem.addThing(new Light());

        homeSystem.toggleAllLights(true);

        for (Light light : homeSystem.getLights()) {
            Assert.assertEquals(Thing.State.REACHABLE, light.getState());
            Assert.assertTrue(light.isLightOn());
        }
    }

    @Test
    public void toggleLightsWhenHomeSystemIsOffShouldFail() {
        homeSystem.addThing(new Light());
        homeSystem.addThing(new Light());
        homeSystem.setState(HomeSystem.State.OFF);

        homeSystem.toggleAllLights(true);

        for (Light light : homeSystem.getLights()) {
            Assert.assertFalse(light.isLightOn());
        }
    }

    @Test
    public void toggleLightTriggersHomeSystem() {

        Light light = new Light();
        light.setOnLightChangedListener(homeSystem);

        light.setLightOn(true);

        Mockito.verify(logger).addLog(Mockito.anyString());
    }
}
