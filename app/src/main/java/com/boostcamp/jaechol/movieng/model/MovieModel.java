package com.boostcamp.jaechol.movieng.model;

/**
 * 영화 데이터 Model 클래스
 */

public class MovieModel {

    private String title;
    private String link;
    private String imageUrl;
    private String director;
    private String actor;
    private String pubDate;
    private float userRating;

    public MovieModel(String title, String link, String imageUrl, String director, String actor, String pubDate, float userRating) {
        this.title = title;
        this.link = link;
        this.imageUrl = imageUrl;
        this.director = director;
        this.actor = actor;
        this.pubDate = pubDate;
        this.userRating = userRating/2;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String image_url) {
        this.imageUrl = image_url;
    }

    public String getDirector() {
        return this.director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return this.actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getPubDate() {
        return this.pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public float getUserRating() {
        return this.userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating/2;
    }

    public String getEtc(){return this.pubDate+ '\n' + this.director+'\n'+this.actor;}

}

