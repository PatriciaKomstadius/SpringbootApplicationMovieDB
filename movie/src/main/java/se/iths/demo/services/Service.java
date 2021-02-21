package se.iths.demo.services;

import se.iths.demo.dtos.MovieDto;

import java.util.List;
import java.util.Optional;

//Interface Service skapas för att kunna skapa en TestService klass
// med tester som implementerar dessa metoder för att se att
// de fungerar som de ska skriver då alltså tester för exakt
// samma klasser som finns i moviecontroller

public interface Service {
    //hämta alla filmer, all();
    List<MovieDto> getAllMovies();

    //hämta en film, one(Long id);
    Optional<MovieDto> getOne(Long id);

    //Skapa ny film, create(Movie movie);
    MovieDto createMovie(MovieDto movie);

    List<MovieDto> getTitle(String title);

    List<MovieDto> getAllByGenre(String genre);

    void deleteMovie(Long id);

    MovieDto replace(Long id, MovieDto movieDto);

    //Ev skapa ny klass för att enbart uppdatera ex genre
    MovieDto update(Long id, MovieDto movieDto);
}
