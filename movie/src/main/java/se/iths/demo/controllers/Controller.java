package se.iths.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.iths.demo.dtos.MovieDto;
import se.iths.demo.services.MovieService;

import java.util.List;

//Controller klass
@RestController
public class Controller {
    //Skapar ett fält för MovieRepository, så man sedan kan göra dependency injections

    private MovieService movieService;

    //Skapar en konstruktor med annotation Autowired,
    //gör att alla metoder blir tillgängliga i klassen
    @Autowired
    public Controller(MovieService movieService) {
        this.movieService = movieService;
    }


    //TEST
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello World";
    }

    //GET SHOW ALL MOVIES
    @GetMapping("/movies")
    public List<MovieDto> all() {
        return movieService.getAllMovies();
        //Här ställs frågan att hitta alla när klienten ställer frågan /movies
        // konverteras automatisk till json av springboot
    }

    //GET TITLE BY ID - RETURNERAR 404 OM EJ FINNS
    //PathVariable, visar att värdet på variabel Long id finns i url:n
    @GetMapping("/movies/{id}")
    public MovieDto one(@PathVariable Long id) {
        return movieService.getOne(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Id " + id + " not found."));
    }

    //POST CREATE NEW MOVIE 201
    //med postmapping kan vi skicka data i insomnia via bodyn
    @PostMapping("/movies")
    @ResponseStatus(HttpStatus.CREATED) //ändrar till http status 201 created
    public MovieDto create(@RequestBody MovieDto movie) { //@RequestBody hämtar sin info från inkommande body i json
        return movieService.createMovie(movie); //returnerar objektet med det autogenererade id:t
    }

    //SEARCH FOR TITLES
    @GetMapping("/movies/titles")
    @ResponseBody
    public List<MovieDto> getMovieByTitle(@RequestParam String title) {
        return movieService.getTitle(title);
    }

    //SEARCH BY GENRE
    @GetMapping("/movies/genre")
    @ResponseBody
    public List<MovieDto> findAllByGenre (@RequestParam String genre) {
        return movieService.getAllByGenre(genre);
    }

    /*
    //PUT UPDATE A MOVIE
    @PutMapping("/movies/{id}")
    MovieDto replaceTitle(@RequestBody MovieDto newMovie, @PathVariable Long id) {
        return movieService.updateTitle(newMovie, id);
    }*/

    //DELETE ID
    @DeleteMapping("/movies/{id}")
    void deleteMovie(@PathVariable Long id) {
        //personService.deleteById(id);
        //->Skapa public void delete(Long id) i ny klass PersonService
        //
        movieService.deleteMovie(id);
    }

    //DELETE ID
    @DeleteMapping("/movies/delete/{id}")
   public void deleteMovieById(@PathVariable Long id) {
        //personService.deleteById(id);
        //->Skapa public void delete(Long id) i ny klass PersonService
        //404 not found
        movieService.deleteMovie(id);
        //.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
          //      "Id " + id + " not found."));
    }


    //PUT använd 404 not found
    @PutMapping("/movies/{id}")
    public MovieDto replace(@RequestBody MovieDto movieDto, @PathVariable Long id){
        return movieService.replace(id, movieDto);
    }

    //PATCH använd 404 not found
    @PatchMapping("/movies/{id}")
    public MovieDto update(@RequestBody MovieDto movieDto, @PathVariable Long id){
        return movieService.update(id, movieDto);
    }





//FILMEN SPARAS EJ I TABELLEN, FYLLT I PARAMETRAR
   /* @GetMapping("/movies/{id}")
    public Movie create(@PathVariable Long id){
        return movieRepository.findById(id)
                .orElse(new Movie());
    }
    */

    /* //VARFÖR FUNKAR DET INTE ATT ANGE TITEL? BLIR 505 ERROR
    @GetMapping("/titles/{title}")
    public List<Movie> search(@PathVariable String title){
        return movieRepository.findByTitle(title);
    }
     */

}

