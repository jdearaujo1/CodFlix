package com.codflix.backend;

import com.codflix.backend.core.Conf;
import com.codflix.backend.core.Database;
import com.codflix.backend.core.Template;
import com.codflix.backend.features.contact.ContactController;
import com.codflix.backend.features.genre.GenreController;
import com.codflix.backend.features.history.HistoryController;
import com.codflix.backend.features.media.MediaController;
import com.codflix.backend.features.other.HomeController;
import com.codflix.backend.features.profile.ProfileController;
import com.codflix.backend.features.user.AuthController;
import com.codflix.backend.middlewares.AuthMiddleware;
import com.codflix.backend.middlewares.LoggerMiddleware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.simple.SimpleLogger;
import spark.Spark;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    static {
        System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "INFO");
    }

    public static void main(String[] args) {
        initializeServer();

        // Controllers
        // Additional controllers should be declared and used here
        HomeController home = new HomeController();
        AuthController auth = new AuthController();
        GenreController genre = new GenreController();
        MediaController media = new MediaController();
        HistoryController history = new HistoryController();
        ContactController contact = new ContactController();
        ProfileController profile = new ProfileController();

        // Routes
        // Every request should be mapped here to a controller method
        logger.info("Welcome to CodFlix Backend!");
        Spark.get("/login", (req, res) -> auth.login(req, res));
        Spark.post("login", (req, res) -> auth.login(req, res));
        Spark.get("/signup", (req, res) -> auth.signUp(req, res));
        Spark.post("/signup", (req, res) -> auth.signUp(req, res));
        Spark.get("logout", (req, res) -> auth.logout(req, res));

        Spark.get("/genres/", (req, res) -> genre.list(req, res));
        Spark.get("/medias/:id", (req, res) -> media.detail(req, res));
        Spark.get("/medias/:id/:season", (req, res) -> media.detail(req, res));
        Spark.get("/medias/", (req, res) -> media.list(req, res));
        Spark.get("/histories/", (req, res) -> history.list(req, res));

        Spark.get("/", (req, res) -> home.home(req, res));

        Spark.get("/contact", (req, res) -> contact.home(req, res));

        Spark.get("/profile", (req, res) -> profile.home(req, res));
        Spark.post("/profile/updateEmail", (req, res) -> profile.updateEmail(req, res));
        Spark.post("/profile/updatePassword", (req, res) -> profile.updatePassword(req, res));
    }

    private static void initializeServer() {
        Template.initialize();
        Database.get().checkConnection();

        // Display exceptions in logs
        Spark.exception(Exception.class, (e, req, res) -> e.printStackTrace());

        // Serve static files (img/css/js)
        Spark.staticFiles.externalLocation(Conf.STATIC_DIR.getPath());

        // Configure server port
        Spark.port(Conf.HTTP_PORT);

        // Log each request with the HTTP verb (GET / POST / etc)
        if (Conf.DEBUG_MODE) {
            final LoggerMiddleware log = new LoggerMiddleware();
            Spark.before(log::process);
        }

        // Protect logged routes
        final AuthMiddleware auth = new AuthMiddleware();
        Spark.before((auth::process));
    }

}
