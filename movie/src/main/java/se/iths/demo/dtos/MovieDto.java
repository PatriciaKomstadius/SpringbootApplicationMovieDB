package se.iths.demo.dtos;

//Data transfer object
//Controller <-movieDto-> Service <-movie entity class-> DB/Repo
//Dto flyttar data mellan controller och service
//skickar info mellan våra lager kod

public class MovieDto {
    private long id;
    private String title;
    private String year;
    private String genre;

    public MovieDto(long id, String title, String year, String genre) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.genre = genre;
    }

    public MovieDto() {
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
