package org.example;

import org.example.core.Template;
import org.example.models.Light;
import org.example.models.Thermostat;
import org.example.models.Thing;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ThingController {

    private final HomeSystem homeSystem;

    public ThingController(HomeSystem homeSystem) {
        this.homeSystem = homeSystem;
    }

    public String detail(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        int index = id - 1;

        Thing thing = homeSystem.getThings().get(index);

        // CODE SMELL => S'il y a trop d'objet, ThingController va devenir un monstre
        if (thing instanceof Light) {
            return detailLight(request, response, id, (Light) thing);
        } else if (thing instanceof Thermostat) {
            return detailThermostat(request, response, id, (Thermostat) thing);
        }

        Map<String, Object> model = new HashMap<>();
        return Template.render("home.html", model);
    }

    private String detailLight(Request request, Response response,
                               int id,
                               Light light) {

        String action = request.queryParamOrDefault("action", "");

        if (action.equals("toggle")) {
            light.setLightOn(!light.isLightOn());
            System.out.println("TOGGLED " + light.isLightOn());
        }

        Map<String, Object> model = new HashMap<>();
        model.put("id", id);
        model.put("light", light);
        return Template.render("thing_light.html", model);
    }

    private String detailThermostat(Request request, Response response,
                                    int id,
                                    Thermostat thermostat) {

        String action = request.queryParamOrDefault("action", "");
        String value = request.queryParamOrDefault("value", "");
        String message = "";

        if (action.equals("set_temperature")) {
            int temperature = Integer.parseInt(value);
            if (!thermostat.setTemperature(temperature)) {
                message = "Desired temperature=" + temperature + " is outside of valid range=["
                        + thermostat.getMinTemperature() + ", "
                        + thermostat.getMaxTemperature()
                        + "]";
            }
        }

        Map<String, Object> model = new HashMap<>();
        model.put("id", id);
        model.put("thermostat", thermostat);
        model.put("message", message);

        return Template.render("thing_thermostat.html", model);
    }
}
