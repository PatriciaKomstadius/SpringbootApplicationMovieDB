package se.iths.ratedmovies.entities;

import org.springframework.context.annotation.Bean;

public class Movie {

    private long id;
    private String title;
    private String genre;

    public Movie() {
    }

    public Movie(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
