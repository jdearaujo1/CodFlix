package com.codflix.backend.models;

public class Episode {

    private int id;
    private String title;
    private int episodeNumber;
    private int seasonNumber;
    private String episodeUrl;
    private int mediaId;

    public Episode(int id, String title, int episodeNumber, int seasonNumber, String episodeUrl, int mediaId) {
        this.id = id;
        this.title = title;
        this.episodeNumber = episodeNumber;
        this.seasonNumber = seasonNumber;
        this.episodeUrl = episodeUrl;
        this.mediaId = mediaId;
    }

    @Override
    public String toString() {
        return "Episode{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", episodeNumber=" + episodeNumber +
                ", seasonNumber=" + seasonNumber +
                ", episodeUrl='" + episodeUrl + '\'' +
                ", mediaId=" + mediaId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getEpisodeUrl() {
        return episodeUrl;
    }

    public void setEpisodeUrl(String episodeUrl) {
        this.episodeUrl = episodeUrl;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }
}
