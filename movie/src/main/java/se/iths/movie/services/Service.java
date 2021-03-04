package se.iths.movie.services;

import se.iths.movie.dtos.MovieDto;

import java.util.List;
import java.util.Optional;

public interface Service {

    Optional<MovieDto> getOne(Long id);

    List<MovieDto> getAllMovies();

    List<MovieDto> getTitle(String title);

    List<MovieDto> getAllByGenre(String genre);

    MovieDto createMovie(MovieDto movie);

    MovieDto replace(Long id, MovieDto movieDto);

    MovieDto update(Long id, MovieDto movieDto);

    void deleteMovie(Long id);
}
