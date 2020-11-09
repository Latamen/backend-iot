package org.example;

import org.example.core.Conf;
import org.example.core.Template;
import org.example.middlewares.LoggerMiddleware;
import org.example.models.Light;
import org.example.models.Thermostat;
import spark.Spark;

public class App {
    public static void main(String[] args) {
        initialize();

        SystemLogger logger = new SystemLogger();
        HomeSystem homeSystem = new HomeSystem(logger);
        Light light = new Light();
        light.setName("TV Light");
        homeSystem.addThing(light);

        Thermostat thermostat = new Thermostat(10, 50);
        thermostat.setName("Bedroom");
        thermostat.setTemperature(21);
        homeSystem.addThing(thermostat);

        light = new Light();
        light.setName("Bed light");
        homeSystem.addThing(light);
        light.setOnLightChangedListener(homeSystem);

        HomeController home = new HomeController(homeSystem);
        ThingController thing = new ThingController(homeSystem);

        Spark.get("/", (req, res) -> home.list(req, res));
        Spark.get("/things/:id", (req, res) -> thing.detail(req, res));
    }

    static void initialize() {
        Template.initialize();

        // Display exceptions in logs
        Spark.exception(Exception.class, (e, req, res) -> e.printStackTrace());

        // Serve static files (img/css/js)
        Spark.staticFiles.externalLocation(Conf.STATIC_DIR.getPath());

        // Configure server port
        Spark.port(Conf.HTTP_PORT);

        final LoggerMiddleware log = new LoggerMiddleware();
        Spark.before(log::process);
    }
}
