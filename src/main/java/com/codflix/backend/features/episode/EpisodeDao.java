package com.codflix.backend.features.episode;

import com.codflix.backend.core.Database;
import com.codflix.backend.models.Episode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class EpisodeDao {

    public List<Episode> getEpisodesByIdAndBySeason(int mediaId, int season) {
        List<Episode> episodes = new ArrayList<>();

        Connection connection = Database.get().getConnection();
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM episode WHERE media_id=? AND season_number=? ORDER BY episode_number ASC");

            st.setInt(1, mediaId);
            st.setInt(2, season);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                episodes.add(mapToEpisode(rs));
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }

        return episodes;
    }

    public List<Episode> getAllEpisodes(int mediaId) {
        List<Episode> episodes = new ArrayList<>();

        Connection connection = Database.get().getConnection();
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM episode WHERE media_id=? ORDER BY episode_number ASC");

            st.setInt(1, mediaId);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                episodes.add(mapToEpisode(rs));
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }

        return episodes;
    }


    private Episode mapToEpisode(ResultSet rs) throws SQLException, ParseException {
        return new Episode(
                rs.getInt(1), // id
                rs.getString(2), // title
                rs.getInt(3), // episode_number
                rs.getInt(4), // season_number
                rs.getString(5), // episode_url
                rs.getInt(6) // media_id
        );
    }

}
