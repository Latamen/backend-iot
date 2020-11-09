import org.example.models.Light;
import org.example.models.Thing;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class LightTest {

    private Light light;

    @Before
    public void before() {
        light = new Light();
    }

    @Test
    public void setLightOnInReachableStateShouldSucceed() {
        light.setLightOn(true);

        Assert.assertEquals(Thing.State.REACHABLE, light.getState());
        Assert.assertTrue(light.isLightOn());
    }

    @Test
    public void setLightOnInUnreachableStateShouldFail() {
        light.setState(Thing.State.UNREACHABLE);
        light.setLightOn(true);

        Assert.assertEquals(Thing.State.UNREACHABLE, light.getState());
        Assert.assertFalse(light.isLightOn());
    }

    @Test
    public void setLightShouldTriggerListener() {
        Light.OnLightChangedListener listener = Mockito.mock(Light.OnLightChangedListener.class);

        light.setOnLightChangedListener(listener);
        light.setLightOn(true);

        Mockito.verify(listener).onLightChanged(light, true);
    }
}
