package se.iths.demo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import se.iths.demo.entities.Movie;
import se.iths.demo.entities.MovieWithRating;
import se.iths.demo.entities.Rating;

@RestController
public class ConsumeWebServices {

    public ConsumeWebServices() {
    }

    @Autowired
    RestTemplate restTemplate;


    //GET MOVIES
    @GetMapping("/getMovies")
    public String testMovies() {
        return restTemplate.getForObject("http://movies-service/movies/", String.class);
    }


    @GetMapping("/getRatings")
    public String testRatings(){
        return restTemplate.getForObject("http://ratings-service/ratings", String.class);
    }


    //VISAR RATING FÖR ALLA FILMER
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

    //HÄMTAR TITEL OCH RATING
    @GetMapping("template/ratings/{id}")
    public String getTitleAndRating(@PathVariable Long id) {
        Movie[] movies = restTemplate.getForObject("http://localhost:5050/movies", Movie[].class);
        Rating[] ratings = restTemplate.getForObject("http://localhost:5060/ratings", Rating[].class);
        String movie = MovieWithRating.getTitleAndRating(id, movies, ratings);
        if (movie != null) return movie;
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

