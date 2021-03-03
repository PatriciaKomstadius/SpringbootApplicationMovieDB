package se.iths.ratedmovies.entities;

public class Rating {

    private long id;
    private double rating;

    public Rating() {
    }

    public Rating(long id, double rating) {
        this.id = id;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
