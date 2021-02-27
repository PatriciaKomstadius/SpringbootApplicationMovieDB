package se.iths.demo.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.xml.MarshallingView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MovieWithRating {

    //Kan man ha två resttample & autowired fr samma bean?
    @Autowired
    RestTemplate restTemplate;

    //RETURNERAR EN FILM MED RATING
    public static String getTitleAndRating(Long id, Movie[] movies, Rating[] ratings) {

        for (Movie m : movies) {
            for (Rating r : ratings) {
                if (id.equals(m.getId()) && id.equals(r.getId())) {
                    String  movie = "{title:" + m.getTitle() + " rating:" + r.getRating() + "}";
                    return movie;
                }
            }
        }
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

    //RETURNERAR ALLA FILMER MED RATINGS
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


