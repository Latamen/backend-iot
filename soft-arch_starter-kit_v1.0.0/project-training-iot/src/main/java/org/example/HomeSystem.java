package org.example;

import org.example.models.Light;
import org.example.models.Thing;

import java.util.ArrayList;
import java.util.List;

public class HomeSystem implements Light.OnLightChangedListener {

    // VERSION BEFORE LOGGER CLASS
//    private final List<String> logs = new ArrayList<>();
//    @Override
//    public void onLightChanged(Light sender, boolean isLightOn) {
//        String message = "HomeSystem - Light " + sender.getName() + " updated it's light=" + isLightOn;
//
//        logs.add(message);
//        System.out.println(message);
//    }

    public enum State {
        OFF,
        ON,
    }

    private final SystemLogger logger;
    private final List<Thing> things;
    private State state;

    public HomeSystem(SystemLogger logger) {
        this.logger = logger;
        things = new ArrayList<>();
        state = State.ON;
    }

    @Override
    public void onLightChanged(Light sender, boolean isLightOn) {
        String message = "HomeSystem - Light " + sender.getName() + " updated it's light=" + isLightOn;
        logger.addLog(message);
    }

    public boolean addThing(Thing thing) {
        return things.add(thing);
    }

    public List<Thing> getThings() {
        return things;
    }

    // CODE SMELL => S'il y a trop d'objet, HomeSystem va devenir un monstre
    public void toggleAllLights(boolean isLightOn) {
        if (state != State.ON) {
            return;
        }

        for (Light l : getLights()) {
            l.setLightOn(isLightOn);
        }
    }

    public List<Light> getLights() {
        List<Light> lights = new ArrayList<>();
        for (Thing t : things) {
            if (t instanceof Light) {
                lights.add((Light) t);
            }
        }
        return lights;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
