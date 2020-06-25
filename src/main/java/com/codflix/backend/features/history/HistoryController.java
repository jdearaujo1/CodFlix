package com.codflix.backend.features.history;

import com.codflix.backend.core.Template;
import com.codflix.backend.features.user.UserDao;
import com.codflix.backend.models.History;
import com.codflix.backend.models.User;
import com.codflix.backend.utils.URLUtils;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.Spark;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HistoryController {
    private final HistoryDao historyDao = new HistoryDao();
    private final UserDao userDao = new UserDao();


    public String list(Request request, Response res) {
        List<History> histories;

        Session session = request.session(true);
        String userIdStr = session.attribute("user_id").toString();
        if (userIdStr == null || userIdStr.isEmpty()) {
            Spark.halt(401, "No user id provded to see history");
        }
        int userId = Integer.parseInt(userIdStr);

        User user = userDao.getUserById(userId);
        histories = historyDao.getStreamsHistoryForUser(userId);

        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        model.put("histories", histories);
        return Template.render("history_list.html", model);
    }

    public String create(Request request, Response response) {

        // Get parameters
        Map<String, String> query = URLUtils.decodeQuery(request.body());
        String userId = query.get("userId");
        String mediaId = query.get("mediaId");

        List<History> histories = historyDao.getStreamsHistoryForUser(Integer.parseInt(userId));

        List<History> filteredHistory = histories.stream()
                .filter(history -> history.getMediaId() == 4)
                .collect(Collectors.toList());

        // Here we check if the item is already in the databse
        if (filteredHistory.size() >= 1) {
            response.status(400);
            return response.body();
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();

        String startDate = dateFormat.format(date);

        historyDao.create(userId, mediaId, startDate);

        return response.body();
    }

    public String update(Request request, Response response) {

        // Get parameters
        Map<String, String> query = URLUtils.decodeQuery(request.body());
        String userId = query.get("userId");
        String mediaId = query.get("mediaId");
        String watchDuration = query.get("watchDuration");

        List<History> histories = historyDao.getStreamsHistoryForUser(Integer.parseInt(userId));

        List<History> filteredHistory = histories.stream()
                .filter(history -> history.getMediaId() == 4)
                .collect(Collectors.toList());

        // Here we check if the item is already in the database
        if (filteredHistory.size() == 0) {
            response.status(400);
            return response.body();
        }

        historyDao.update(userId, mediaId, watchDuration);

        return response.body();
    }
}
