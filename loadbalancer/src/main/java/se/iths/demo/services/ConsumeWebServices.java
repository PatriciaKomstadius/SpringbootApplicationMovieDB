package se.iths.demo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import se.iths.demo.entities.Movie;
import se.iths.demo.entities.MovieWithRating;
import se.iths.demo.entities.Rating;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController

public class ConsumeWebServices {
    
    public ConsumeWebServices() {
    }

    @Autowired
    RestTemplate restTemplate;


    //GET MOVIES - ändra titel
    @GetMapping("/getMovies")
    public String testMovies() {
        return restTemplate.getForObject("http://movies-service/movies/", String.class);
    }

    //GET RATINGS
    @GetMapping("/getRatings")
    public String testRatings() {
        return restTemplate.getForObject("http://ratings-service/ratings", String.class);
    }

    //HÄMTAR EN TITEL OCH RATING
    @GetMapping("/getTitleAndRating/{id}")
    @ResponseBody
    public String getTitleAndRating(@PathVariable Long id) {
        Movie[] movies = restTemplate.getForObject("http://movies-service/movies/", Movie[].class);
        Rating[] ratings = restTemplate.getForObject("http://ratings-service/ratings", Rating[].class);
        String movie = MovieWithRating.getTitleAndRating(id, movies, ratings);
        if (movie != null) {
            return movie;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Requested title not available with rating and/or doesn't exist in database.");
        }
    }

    //VISAR FILMER MED RATINGS
    @GetMapping("/getAllTitlesWithRatings")
    @ResponseBody
    public List<String> getTitlesAndRatings() {
        Movie[] movies = restTemplate.getForObject("http://movies-service/movies/", Movie[].class);
        Rating[] ratings = restTemplate.getForObject("http://ratings-service/ratings", Rating[].class);
        List<String> ratedMovies = MovieWithRating.getMoviesAndRatings(movies, ratings);
        return ratedMovies;
    }

}
    /*
    //HÄMTAR TITEL OCH RATING OK ATT RADERA!
    @GetMapping("template/ratings/{id}")
    public String getTitleAndRating2(@PathVariable Long id) {
        Movie[] movies = restTemplate.getForObject("http://localhost:5050/movies", Movie[].class);
        Rating[] ratings = restTemplate.getForObject("http://localhost:5060/ratings", Rating[].class);
        String movie = MovieWithRating.getTitleAndRating(id, movies, ratings);
        if (movie != null) return movie;
        return "Couldn't find movie with id " + id;
    }

    //RETURNERAR TITEL FRÅN ARRAY PÅ SÖKT ID
    @GetMapping("template/movies/{id}")
    public String getTitle(@PathVariable Long id) {
        Movie[] movies = restTemplate.getForObject("http://localhost:5050/movies", Movie[].class);
        for (Movie m : movies) {
            if (id.equals(m.getId())) {
                return m.getTitle();
            }
        }
        return "Couldn't find movie with id " + id;
    }
     */

//     restTemplate.getForObject("http://localhost:5050/movies", Movie[].class)//returnerar array av movieobjekt
//   + restTemplate.getForObject("http://localhost:5080/ratings", String.class); //hämtar info från klassen/ratings



