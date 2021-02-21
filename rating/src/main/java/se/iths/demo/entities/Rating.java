package se.iths.demo.entities;

import javax.persistence.*;

@Entity
@Table(name = "Ratings")
public class Rating {

    public Rating() {
    }

    @Id
    private long id;
    private double rating;

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
