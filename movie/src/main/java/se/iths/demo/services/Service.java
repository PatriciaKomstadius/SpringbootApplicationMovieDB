package se.iths.demo.services;

import se.iths.demo.dtos.MovieDto;

import java.util.List;
import java.util.Optional;

//Interface för metoder i MovieController och TestService klass

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
    MovieDto update(Long id, MovieDto movieDto);

}
