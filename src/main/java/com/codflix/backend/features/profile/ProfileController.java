package com.codflix.backend.features.profile;

import com.codflix.backend.core.Database;
import com.codflix.backend.core.Template;
import com.codflix.backend.features.user.UserDao;
import com.codflix.backend.models.User;
import com.codflix.backend.utils.URLUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.Spark;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ProfileController {

    public String home(Request request, Response response) {

        Map<String, Object> model = new HashMap<>();

        Session session = request.session();
        UserDao userDao = new UserDao();

        String userIdStr = session.attribute("user_id").toString();
        if (userIdStr == null || userIdStr.isEmpty()) {
            Spark.halt(401, "No user id provded to see history");
        }
        int userId = Integer.parseInt(userIdStr);

        User user = userDao.getUserById(userId);

        model.put("user", user);

        return Template.render("profile.html", model);

    }

    public String updateEmail(Request request, Response response) {

        Connection connection = Database.get().getConnection();
        UserDao userDao = new UserDao();

        // Get parameters
        Map<String, String> query = URLUtils.decodeQuery(request.body());
        String email = query.get("email");
        String id = query.get("id");

        if (StringUtils.isBlank(email)) {
            response.status(400);
            response.body("L'adresse mail est vide");

            return response.body();
        } else if (userDao.checkIfUserExists(email)) {
            response.status(400);
            response.body("L'adresse mail est déjà utilisée");

            return response.body();
        }

        if (StringUtils.isBlank(id)) {
            response.status(401);
            response.body("Veuillez vous reconnecter pour modifier votre adresse mail");

            return response.body();
        }

        try {
            PreparedStatement st = connection.prepareStatement("UPDATE user SET email = ? WHERE id = ?;");

            st.setString(1, email);
            st.setString(2, id);

            st.executeUpdate();

            response.status(200);
            response.body("L'adresse mail a été mise à jour");

        } catch (SQLException e) {
            response.status(400);
            response.body("Erreur lors de la mise à jour de l'adresse mail");
            e.printStackTrace();
        }

        return response.body();

    }

    public String updatePassword(Request request, Response response) {

        Connection connection = Database.get().getConnection();
        UserDao userDao = new UserDao();

        // Get parameters
        Map<String, String> query = URLUtils.decodeQuery(request.body());
        String oldPassword = query.get("oldPassword");
        String newPassword = query.get("newPassword");
        String confirmPassword = query.get("confirmPassword");
        String id = query.get("id");

        if (StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword) || StringUtils.isBlank(confirmPassword)) {
            response.status(400);
            response.body("Veuillez remplir tous les champs");
            return response.body();
        }

        if (StringUtils.isBlank(id)) {
            response.status(401);
            response.body("Veuillez vous reconnecter pour modifier votre mot de passe");

            return response.body();
        }

        User user = userDao.getUserById(Integer.parseInt(id));
        boolean checkHash = BCrypt.checkpw(newPassword, user.getPassword());

        String hashedPassword;

        if (checkHash) {
            response.status(400);
            response.body("Votre ancien mot de passe doit être différent du nouveau mot de passe");
            return response.body();
        } else if (!newPassword.equals(confirmPassword)) {
            response.status(400);
            response.body("Les mots de passe ne correspondent pas");
            return response.body();
        } else {
            hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(10));
        }

        try {
            PreparedStatement st = connection.prepareStatement("UPDATE user SET password = ? WHERE id = ?;");

            st.setString(1, hashedPassword);
            st.setString(2, id);

            st.executeUpdate();

            response.status(200);
            response.body("Votre mot de passe a été mise à jour");

        } catch (SQLException e) {
            response.status(400);
            response.body("Erreur lors de la mise à jour du mot de passe");
            e.printStackTrace();
        }

        return response.body();

    }
}
