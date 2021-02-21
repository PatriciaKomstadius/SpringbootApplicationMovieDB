package se.iths.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieWithRating {

    public MovieWithRating() {
    }


    //Kan man ha två resttample & autowired fr samma bean?
    @Autowired
    RestTemplate restTemplate;

    Movie[] movies = restTemplate.getForObject("http://localhost:5050/movies", Movie[].class);
    Rating[] ratings = restTemplate.getForObject("http://localhost:5080/ratings", Rating[].class);


    //OK FUNKAR
    static String getTitleAndRating(Long id, Movie[] movies, Rating[] ratings) {
        for (Movie m : movies) {
            for (Rating r : ratings) {
                if (id.equals(m.getId()) && id.equals(r.getId())) {
                    String movie = "{title:" + m.getTitle() + " rating:" + r.getRating() + "}";
                    return movie;
                }
            }
        }
        return "No rating on this title yet";
    }


/*
    static double getTitlesAndRatings(Movie[] movies, Rating[] ratings) {
        Map<String, Double> list = new HashMap<>();
        String titl = "";
        double rate = 0;
        for (Movie m : movies) {
            for (Rating r : ratings) {
                if (m.getId() == r.getId()) {
                    list.put(m.getTitle(), r.getRating());
                }
            }
        }
        for (String i: list.keySet()) {
            return list.get(i);
        }
        return 0;
    }
 */
        /*
    public static double getMovieWithRating(Movie[] movies, Rating[] ratings) {
        for (Movie m : movies) {
            for (Rating r : ratings) {
                if (m.getId()  == r.getId()) {
                    return r.getRating();
                }
            }
        }
        return 0;
    }
    */

    /* STREAM FÖR LISTA AV FILMER M RATINGS?
          //ström av data. varje movie mappas och läggs i en ny lista
    private List<MovieDto> mapp(List<Movie> all) {
       return  all
                .stream()//returnerar objekt av typen stream m massa metoder
                .map(this::mapp) //anger namnet på metoden (mapp) som ska appliceras på varje movie-objekt
               // .map(movie -> mapp(movie))//får in ett movie-objekt, mappar det till vår movieDto
                .collect(Collectors.toList()); //lägger allt i en ny lista
    }
     */

}
