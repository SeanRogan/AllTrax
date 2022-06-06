package com.seanrogandev.alltrax.model;

public class Album {

    private String title;
    private String link;
    private String artist;
    private String id;

    public Album(String title, String artist, String link, String id) {
        this.title = title;
        this.link = link;
        this.artist = artist;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public String getLink() {
        return link;
    }
    public String getArtists() {return artist;}

}
