package se.iths.ratedmovies.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import se.iths.ratedmovies.entities.Movie;
import se.iths.ratedmovies.entities.MovieWithRating;
import se.iths.ratedmovies.entities.Rating;

import java.util.List;

@RestController
public class RatedMoviesController {

    public RatedMoviesController() {
    }

    @Autowired
    RestTemplate restTemplate;


    @GetMapping("/ratedmovies")
    @ResponseBody
    public List<String> getTitlesAndRatings() {
        Movie[] movies = restTemplate.getForObject("http://movies-service/movies/", Movie[].class);
        Rating[] ratings = restTemplate.getForObject("http://ratings-service/ratings", Rating[].class);
        List<String> ratedMovies = MovieWithRating.getMoviesAndRatings(movies, ratings);
        return ratedMovies;
    }

    @GetMapping("/ratedmovies/{id}")
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

}




