package se.iths.movie.services;

import se.iths.movie.dtos.MovieDto;

import java.util.List;
import java.util.Optional;

//Interface för metoder i MovieController och TestService klass
public interface Service {

    //get one
    Optional<MovieDto> getOne(Long id);

    //get all
    List<MovieDto> getAllMovies();

    //get title
    List<MovieDto> getTitle(String title);

    //get all by genre
    List<MovieDto> getAllByGenre(String genre);

    //post create
    MovieDto createMovie(MovieDto movie);

    //put
    MovieDto replace(Long id, MovieDto movieDto);

    //patch
    MovieDto update(Long id, MovieDto movieDto);

    //delete
    void deleteMovie(Long id);
}
