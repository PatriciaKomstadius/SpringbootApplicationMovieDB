package se.iths.ratedmovies.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
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
        } //ändra till throw new
        for (Movie m : movies) {
            for (Rating r : ratings) {
                if (id != r.getId()) {
                    //   throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    //         "Requested title not available with rating and/or doesn't exist in database.");
                    String empty = "Requested title not available with rating and/or doesn't exist in database.";
                    return empty;

                }
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
                    object = m.getTitle() + r.getRating();
                    list.add(object);
                }
            }
        }
        for (String o : list) {
            return list;
        }
        return null;
    }


}


