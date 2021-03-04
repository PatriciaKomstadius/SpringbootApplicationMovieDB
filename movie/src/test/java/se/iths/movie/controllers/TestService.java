package se.iths.movie.controllers;

import se.iths.movie.dtos.MovieDto;
import se.iths.movie.services.Service;

import java.util.List;
import java.util.Optional;

//Implementerar Service som alltid har dessa värden, en simulering av databas
public class TestService implements Service {

    @Override
    public List<MovieDto> getAllMovies() {
        return null;
    }

    @Override
    public Optional<MovieDto> getOne(Long id) {
        if (id == 1)
            return Optional.of(new MovieDto(1, "TestTitle", "TestYear", "TestGenre"));
        return Optional.empty();
    }

    @Override
    public MovieDto createMovie(MovieDto movie) {
        return null;
    }

    @Override
    public List<MovieDto> getTitle(String title) {
        return null;
    }

    @Override
    public List<MovieDto> getAllByGenre(String genre) {
        return null;
    }

    @Override
    public void deleteMovie(Long id) {

    }

    @Override
    public MovieDto replace(Long id, MovieDto movieDto) {
        return null;
    }

    @Override
    public MovieDto update(Long id, MovieDto movieDto) {
        return null;
    }

}
