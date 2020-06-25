package com.codflix.backend.features.history;

import com.codflix.backend.core.Database;
import com.codflix.backend.models.History;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryDao {

    public List<History> getStreamsHistoryForUser(int userId) {
        List<History> histories = new ArrayList<>();
        Connection connection = Database.get().getConnection();
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM history WHERE user_id=?");

            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                History history = mapToStreamHistory(rs);
                histories.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return histories;
    }

    public void create(String userId, String mediaId, String startDate) {

        Connection connection = Database.get().getConnection();
        try {
            PreparedStatement st = connection.prepareStatement("INSERT INTO history (user_id, media_id, start_date, finish_date, watch_duration) " +
                    " VALUES (?, ?, ?, ?, ?)");

            st.setString(1, userId);
            st.setString(2, mediaId);
            st.setString(3, startDate);
            st.setNull(4, Types.DATE);
            st.setInt(5, 0);

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(String userId, String mediaId, String watchDuration) {

        Connection connection = Database.get().getConnection();
        try {
            PreparedStatement st = connection.prepareStatement("UPDATE history SET watch_duration = ? WHERE user_id = ? AND media_id = ?; ");

            st.setString(1, watchDuration);
            st.setString(2, userId);
            st.setString(3, mediaId);

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private History mapToStreamHistory(ResultSet rs) throws SQLException {
        return new History(
                rs.getInt(1), // id
                rs.getInt(2), // user_id
                rs.getInt(3), // stream_id
                rs.getString(4), // startDate
                rs.getString(5), // endDate
                rs.getInt(6) // watchDuration
        );
    }
}
