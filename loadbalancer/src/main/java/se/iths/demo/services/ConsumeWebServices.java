package se.iths.demo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
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

    //HÄMTAR TITEL OCH RATING
    @GetMapping("/getTitleAndRating/{id}")
    public String getTitleAndRating(@PathVariable Long id) {
        Movie[] movies = restTemplate.getForObject("http://movies-service/movies/", Movie[].class);
        Rating[] ratings = restTemplate.getForObject("http://ratings-service/ratings", Rating[].class);
        String movie = MovieWithRating.getTitleAndRating(id, movies, ratings);
        if (movie != null) return movie;
        return "Couldn't find movie with id " + id;
    }

    //VISAR FILMER OCH RATINGS
    @GetMapping("/getAllTitlesWithRatings")
    @ResponseBody
    public List<String> getTitlesAndRatings() {
        Movie[] movies = restTemplate.getForObject("http://movies-service/movies/", Movie[].class);
        Rating[] ratings = restTemplate.getForObject("http://ratings-service/ratings", Rating[].class);
        List<String> ratedMovies = MovieWithRating.getMoviesAndRatings(movies, ratings);
        return ratedMovies;
    }





/*
    @GetMapping("/getItAll")
    public List<MovieWithRating> getItAll() {
        Movie[] movies = restTemplate.getForObject("http://movies-service/movies/", Movie[].class);
        Rating[] ratings = restTemplate.getForObject("http://ratings-service/ratings", Rating[].class);
        // MovieWithRating mrw = new MovieWithRating();
       List<MovieWithRating> arr =

        for (Movie m : movies) {
            for (Rating r : ratings) {
                if (m.getId() == r.getId()) {
                    String titl = m.getTitle();
                    double rate = r.getRating();
                    MovieWithRating mrw = new MovieWithRating(titl, rate);
                    arr.add(mrw);
                    arr.stream().toArray();
                }
            }
        }
        return arr;
    }
    */





       /*
         @GetMapping("/getTitleAndRating/{id}")
    public String getAll(@PathVariable Long id) {
    return movie;
    }*/


    //VISAR RATING FÖR ALLA FILMER OK ATT RADERA!
    @GetMapping("template/getRatings")
    public String getFromUrl() throws JsonProcessingException {
        return restTemplate.getForObject("http://localhost:5060/ratings", String.class);
    }

    //VISAR FILMER OCH RATINGS I JSON, EJ PATCHAT
    @GetMapping("template/movies/info")
    public String allInfo() {
        return restTemplate.getForObject("http://localhost:5050/movies", String.class)
                + restTemplate.getForObject("http://localhost:5060/ratings", String.class);
    }

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


/*
    //TEST ALL OFULLSTÄNDIG KOD
    @GetMapping("template/ratings/allTitles")
    public String getAllTitlesAndRatings() {
        Movie[] movies = restTemplate.getForObject("http://localhost:5050/movies", Movie[].class);
        Rating[] ratings = restTemplate.getForObject("http://localhost:5080/ratings", Rating[].class);
        String titl = "";
        double rate = 0;
        for (Movie m : movies) {
            for (Rating r : ratings) {
                if (m.getId() == r.getId()) {
                    titl += m.getTitle();
                    rate += r.getRating();
                    return titl + rate;
                }
            }
        }
    }
    */


}


//     restTemplate.getForObject("http://localhost:5050/movies", Movie[].class)//returnerar array av movieobjekt
//   + restTemplate.getForObject("http://localhost:5080/ratings", String.class); //hämtar info från klassen/ratings



    /* FUNKAR
    @RequestMapping(value = "/template/movies")
        public String getMovies(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        return restTemplate.exchange("http://localhost:5050/movies", HttpMethod.GET, entity, String.class).getBody()
                + restTemplate.exchange("http://localhost:5080/ratings", HttpMethod.GET, entity, String.class).getBody();
    }
    */
    /*EJ HUNNIT TESTA ÄN....
    @RequestMapping(value = "/template/products", method = RequestMethod.POST)
    public String createProducts(@RequestBody Movie movie) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Movie> entity = new HttpEntity<Movie>(movie,headers);

        return restTemplate.exchange(
                "http://localhost:8080/products", HttpMethod.POST, entity, String.class).getBody();
    }
    */

