package hooks;

import io.cucumber.core.logging.LoggerFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import java.util.logging.Logger;

public class Hooks {
    static final Logger log = Logger.getLogger(Hooks.class.getName());


    @Before
    public static void beforeAll() {
        log.info("Test is starting..");
    }

    public static void info(String message) {
        log.info(message);
    }

    public static void warn(String message) {
        log.warning(message);
    }

    @After
    public static void afterAll() {
        log.info("Test is ended");
    }
}