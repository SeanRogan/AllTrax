package com.seanrogandev.alltrax.model;

public class Playlist {
    private String title;
    private String link;

    public Playlist(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }
    public String getLink() {
        return link;
    }
}
