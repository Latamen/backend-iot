package org.example;

import org.example.core.Template;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class HomeController {
    private final HomeSystem homeSystem;

    public HomeController(HomeSystem homeSystem) {
        this.homeSystem = homeSystem;
    }

    public String list(Request request, Response response) {
        Map<String, Object> model = new HashMap<>();

        String thingType = request.queryParamOrDefault("thing_type", "");
        String action = request.queryParamOrDefault("action", "");

        if (thingType.equals("light")) {
            switch (action) {
                case "toggle_off":
                    homeSystem.toggleAllLights(false);
                    break;
                case "toggle_on":
                    homeSystem.toggleAllLights(true);
                    break;
            }
        }

        model.put("things", homeSystem.getThings());
        return Template.render("home.html", model);
    }
}
