package se.iths.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.iths.demo.dtos.MovieDto;
import se.iths.demo.services.Service;

import java.util.List;

//Controller klass
@RestController
public class MovieController {

    //Skapar ett fält för Service, så man sedan kan göra dependency injections
    private Service service;

    //Skapar en konstruktor med annotation Autowired, gör att alla metoder blir tillgängliga i klassen
    @Autowired
    public MovieController(Service service) {
        this.service = service;
    }

    //GET ONE - RETURNERAR 404 OM EJ FINNS
    //PathVariable, visar att värdet på variabel Long id finns i url:n
    @GetMapping("/movies/{id}")
    public MovieDto one(@PathVariable Long id) {
        return service.getOne(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Id " + id + " not found."));
    }

    //GET ALL
    @GetMapping("/movies")
    public List<MovieDto> all() {
        return service.getAllMovies();
        //Här ställs frågan att hitta alla när klienten ställer frågan /movies
        // konverteras automatisk till json av springboot
    }

    //POST CREATE NEW MOVIE 201
    //med postmapping kan vi skicka data i insomnia via bodyn
    @PostMapping("/movies")
    @ResponseStatus(HttpStatus.CREATED) //ändrar till http status 201 created
    public MovieDto create(@RequestBody MovieDto movie) { //@RequestBody hämtar sin info från inkommande body i json
        return service.createMovie(movie); //returnerar objektet med det autogenererade id:t
    }

    //SEARCH FOR TITLES
    @GetMapping("/movies/titles")
    @ResponseBody
    public List<MovieDto> getMovieByTitle(@RequestParam String title) {
        return service.getTitle(title);
    }

    //SEARCH BY GENRE
    @GetMapping("/movies/genre")
    @ResponseBody
    public List<MovieDto> findAllByGenre(@RequestParam String genre) {
        return service.getAllByGenre(genre);
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
        service.deleteMovie(id);
    }

    //DELETE ID - TA BORT?
    @DeleteMapping("/movies/delete/{id}")
    public void deleteMovieById(@PathVariable Long id) {
        //personService.deleteById(id);
        //->Skapa public void delete(Long id) i ny klass PersonService
        //404 not found
        service.deleteMovie(id);
        //.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
        //      "Id " + id + " not found."));
    }


    //PUT använd 404 not found
    @PutMapping("/movies/{id}")
    public MovieDto replace(@RequestBody MovieDto movieDto, @PathVariable Long id) {
        return service.replace(id, movieDto);
    }

    //PATCH använd 404 not found
    @PatchMapping("/movies/{id}")
    public MovieDto update(@RequestBody MovieDto movieDto, @PathVariable Long id) {
        return service.update(id, movieDto);
    }


//FILMEN SPARAS EJ I TABELLEN, FYLLT I PARAMETRAR
   /* @GetMapping("/movies/{id}")
    public Movie create(@PathVariable Long id){
        return movieRepository.findById(id)
                .orElse(new Movie());
    }
    */



}

