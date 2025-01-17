package se.iths.ratedmovies.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovieWithRating {

    @Autowired
    RestTemplate restTemplate;

    public static String getTitleAndRating(Long id, Movie[] movies, Rating[] ratings) {

        for (Movie m : movies) {
            for (Rating r : ratings) {
                if (id.equals(m.getId()) && id.equals(r.getId())) {
                    String movie = "{title:" + m.getTitle() + " rating:" + r.getRating() + "}";
                    return movie;
                }
            }
        }
            for (Rating r : ratings) {
                if (id != r.getId()) {
                    return null;
                }
            }
        return null;
    }

    public static List<String> getMoviesAndRatings(Movie[] movies, Rating[] ratings) {
        List<String> list = new ArrayList<>();
        String object = "";
        for (Movie m : movies) {
            for (Rating r : ratings) {
                if (m.getId() == r.getId()) {
                    object = m.getTitle() + " " + r.getRating();
                    list.add(object);
                    Collections.sort(list);
                }
            }
        }
            return list;
    }


}


