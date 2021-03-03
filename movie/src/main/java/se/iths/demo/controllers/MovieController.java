package se.iths.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.iths.demo.dtos.MovieDto;
import se.iths.demo.services.Service;

import java.util.List;

@RestController
public class MovieController {

    private Service service;

    @Autowired
    public MovieController(Service service) {
        this.service = service;
    }

    //GET ONE - två tester KLARA
    @GetMapping("/movies/{id}")
    public MovieDto one(@PathVariable Long id) {
        return service.getOne(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Id " + id + " not found."));
    }

    //GET ALL - ett test KLAR
    @GetMapping("/movies")
    public List<MovieDto> all() {
        return service.getAllMovies();
    }

    //POST MOVIE - två tester KLARA
    @PostMapping("/movies")
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDto create(@RequestBody MovieDto movie) {
        return service.createMovie(movie);
    }

    //PUT använd 404 not found - 1-2 tester KLARA
    @PutMapping("/movies/{id}")
    public MovieDto replace(@RequestBody MovieDto movieDto, @PathVariable Long id) {
        return service.replace(id, movieDto);
    }

    //PATCH använd 404 not found - 1-2 tester KLARA
    @PatchMapping("/movies/{id}")
    public MovieDto update(@RequestBody MovieDto movieDto, @PathVariable Long id) {
        return service.update(id, movieDto);
    }

    //DELETE ID - 1 tester KLAR
    @DeleteMapping("/movies/delete/{id}")
    public void deleteMovieById(@PathVariable Long id) {
        service.deleteMovie(id);
    }

    //SEARCH FOR TITLES PARAM - ett test
    @GetMapping("/movies/titles")
    @ResponseBody
    public List<MovieDto> getMovieByTitle(@RequestParam String title) {
        return service.getTitle(title);
    }

    //SEARCH BY GENRE PARAM - ett test
    @GetMapping("/movies/genre")
    @ResponseBody
    public List<MovieDto> findAllByGenre(@RequestParam String genre) {
        return service.getAllByGenre(genre);
    }


}

