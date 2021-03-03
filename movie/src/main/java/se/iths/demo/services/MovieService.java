package se.iths.demo.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.iths.demo.entities.Movie;
import se.iths.demo.mappers.MovieMapper;
import se.iths.demo.repositories.MovieRepository;
import se.iths.demo.dtos.MovieDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService implements se.iths.demo.services.Service {

    private final MovieMapper movieMapper;
    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    //GET ALL
    @Override
    public List<MovieDto> getAllMovies() {
        return movieMapper.mapp(movieRepository.findAll());
    }

    //GET ONE
    @Override
    public Optional<MovieDto> getOne(Long id) {
        //map from Movie to MovieDto
        return movieMapper.mapp(movieRepository.findById(id));
    }

    //POST MOVIE - 400 OK ELLER 404 NOT FOUND? skriver ej ut detta?
    @Override
    public MovieDto createMovie(MovieDto movie) {
        if (movie.getTitle().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "No movietitle entered.");
        //mappar från movieDto till Movieobjekt
        return movieMapper.mapp(movieRepository.save(movieMapper.mapp(movie)));
    }

    @Override
    public List<MovieDto> getTitle(String title) {
        return movieMapper.mapp(movieRepository.findByTitle(title));
    }

    @Override
    public List<MovieDto> getAllByGenre(String genre) {
        return movieMapper.mapp(movieRepository.findAllByGenre(genre));
    }

    @Override
    public void deleteMovie(Long id) {
        Optional<Movie> movieId = movieRepository.findById(id);
        if (movieId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id " + id + "not found.");
        }
        movieRepository.deleteById(id);
    }

    @Override
    public MovieDto update(Long id, MovieDto movieDto) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {

            Movie updateMovie = movie.get();

            if (movieDto.getTitle() != null)
                updateMovie.setTitle(movieDto.getTitle());
            if (movieDto.getGenre() != null)
                updateMovie.setGenre(movieDto.getGenre());
            if (movieDto.getYear() != null)
                updateMovie.setYear(movieDto.getYear());
            return movieMapper.mapp(movieRepository.save(updateMovie));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id " + id + "not found.");
        }
    }

    @Override
    public MovieDto replace(Long id, MovieDto movieDto) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            Movie updateMovie = movie.get();
            updateMovie.setTitle(movieDto.getTitle());
            updateMovie.setGenre(movieDto.getGenre());
            updateMovie.setYear(movieDto.getYear());
            return movieMapper.mapp(movieRepository.save(updateMovie));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id " + id + "not found.");
        }
    }


}



