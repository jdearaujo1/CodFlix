package com.codflix.backend.features.user;

import com.codflix.backend.core.Conf;
import com.codflix.backend.core.Database;
import com.codflix.backend.core.Template;
import com.codflix.backend.models.User;
import com.codflix.backend.utils.URLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import spark.Request;
import spark.Response;
import spark.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserDao userDao = new UserDao();

    public String login(Request request, Response response) {
        if (request.requestMethod().equals("GET")) {
            Map<String, Object> model = new HashMap<>();
            return Template.render("auth_login.html", model);
        }

        // Get parameters
        Map<String, String> query = URLUtils.decodeQuery(request.body());
        String email = query.get("email");
        String password = query.get("password");


        // Authenticate user
        User user = userDao.getUserByEmail(email);
        if (user == null) {
            logger.info("User not found. Redirect to login");
            response.removeCookie("session");
            response.redirect("/login");
            return "KO";
        }

        boolean checkHash = BCrypt.checkpw(password, user.getPassword());

        if (!checkHash) {
            logger.info("Incorrect password. Try again");
            response.removeCookie("session");
            response.redirect("/login");
            return "KO";
        }

        // Create session
        Session session = request.session(true);
        session.attribute("user_id", user.getId());
        response.cookie("/", "user_id", "" + user.getId(), 3600, true);

        // Redirect to medias page
        response.redirect(Conf.ROUTE_LOGGED_ROOT);
        return "OK";
    }

    public String signUp(Request request, Response response) {
        if (request.requestMethod().equals("GET")) {
            Map<String, Object> model = new HashMap<>();
            return Template.render("auth_signup.html", model);
        }

        Connection connection = Database.get().getConnection();

        // Get parameters
        Map<String, String> query = URLUtils.decodeQuery(request.body());
        String email = query.get("email");
        String password = query.get("password");
        String password_confirm = query.get("password_confirm");

        if (!password.equals(password_confirm)) {
            logger.info("Password does not match. Please try again");
            response.redirect("/signup");
            return "KO";
        }

        User user = userDao.getUserByEmail(email);
        if (user != null) {
            logger.info("User already exists. Choose another email address");
            response.redirect("/signup");
            return "KO";
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));

        // Add user in database
        try {
            PreparedStatement st = connection.prepareStatement("INSERT INTO user (email, password) VALUES (?, ?)");

            logger.info(email + password);

            st.setString(1, email);
            st.setString(2, hashedPassword);

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.redirect(Conf.ROUTE_LOGIN);
        return "OK";
    }

    public String logout(Request request, Response response) {
        Session session = request.session(false);
        if (session != null) {
            session.invalidate();
        }
        response.removeCookie("session");
        response.removeCookie("JSESSIONID");
        response.redirect("/");

        return "";
    }
}
