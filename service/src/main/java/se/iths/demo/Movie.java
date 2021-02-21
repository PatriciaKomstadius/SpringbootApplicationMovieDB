package se.iths.demo;


import org.springframework.context.annotation.Bean;

public class Movie {

    //välj fält från movie
    private long id;
    private String title;

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
}
