package com.codflix.backend.features.media;

import com.codflix.backend.core.Template;
import com.codflix.backend.features.episode.EpisodeDao;
import com.codflix.backend.models.Episode;
import com.codflix.backend.models.Media;
import spark.Request;
import spark.Response;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaController {
    private final MediaDao mediaDao = new MediaDao();
    private final EpisodeDao episodeDao = new EpisodeDao();

    public String list(Request request, Response response) {
        List<Media> medias;

        String title = request.queryParams("title");

        if (title != null && !title.isEmpty()) {
            medias = mediaDao.filterMedias(title);
        } else {
            medias = mediaDao.getAllMedias();
        }

        Map<String, Object> model = new HashMap<>();
        model.put("medias", medias);
        return Template.render("media_list.html", model);
    }

    public String detail(Request request, Response res) {
        int id = Integer.parseInt(request.params(":id"));
        int season = 1;

        if (request.params(":season") != null) {
            season = Integer.parseInt(request.params(":season"));
        }


        Media media = mediaDao.getMediaById(id);
        List<Episode> episodes;

        Map<String, Object> model = new HashMap<>();


        if (media.getType().equals("serie")) {
            episodes = episodeDao.getEpisodesByIdAndBySeason(media.getId(), season);

            //Get the total number of seasons.
            Episode episode = episodeDao.getAllEpisodes(media.getId()).stream().max(Comparator.comparing(Episode::getSeasonNumber)).get();

            model.put("seasonNumber", episode.getSeasonNumber());
            model.put("episodes", episodes);
        }

        model.put("media", media);
        return Template.render("media_detail.html", model);
    }
}
