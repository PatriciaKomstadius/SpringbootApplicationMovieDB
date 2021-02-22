package se.iths.demo.services;

import se.iths.demo.dtos.MovieDto;

import java.util.List;
import java.util.Optional;

//Interface Service skapas för att kunna skapa en TestService klass
// med tester som implementerar dessa metoder för att se att
// de fungerar som de ska skriver då alltså tester för exakt
// samma klasser som finns i moviecontroller

public interface Service {

    //get all
    List<MovieDto> getAllMovies();

    //get one
    Optional<MovieDto> getOne(Long id);

    //post create
    MovieDto createMovie(MovieDto movie);

    //get title
    List<MovieDto> getTitle(String title);

    //get all by genre
    List<MovieDto> getAllByGenre(String genre);

    //delete
    void deleteMovie(Long id);

    //put
    MovieDto replace(Long id, MovieDto movieDto);

    //patch
    //Ev skapa ny klass för att enbart uppdatera ex genre
    MovieDto update(Long id, MovieDto movieDto);
}
