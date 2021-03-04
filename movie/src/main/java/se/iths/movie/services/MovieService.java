package se.iths.movie.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.iths.movie.entities.Movie;
import se.iths.movie.mappers.MovieMapper;
import se.iths.movie.repositories.MovieRepository;
import se.iths.movie.dtos.MovieDto;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService implements se.iths.movie.services.Service {

    private final MovieMapper movieMapper;
    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }


    @Override
    public Optional<MovieDto> getOne(Long id) {
        return movieMapper.mapp(movieRepository.findById(id));
    }

    @Override
    public List<MovieDto> getAllMovies() {
        return movieMapper.mapp(movieRepository.findAll());
    }

    @Override
    public List<MovieDto> getTitle(String title) {
        List<Movie> m = movieRepository.findByTitle(title);

        if (m.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with title " +title + " registered in the database.");
        }
        return movieMapper.mapp(movieRepository.findByTitle(title));
    }

    @Override
    public List<MovieDto> getAllByGenre(String genre) {
        List<Movie> m = movieRepository.findAllByGenre(genre);

        if (m.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No movies in requested genre " +genre + " registered in the database.");
        }
        return movieMapper.mapp(movieRepository.findAllByGenre(genre));
    }

    @Override
    public MovieDto createMovie(MovieDto movie) {
        if (movie.getTitle().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "No specified title. The movie was not saved to the database.");
        return movieMapper.mapp(movieRepository.save(movieMapper.mapp(movie)));
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

    @Override
    public void deleteMovie(Long id) {
        Optional<Movie> movieId = movieRepository.findById(id);
        if (movieId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id " + id + "not found.");
        }
        movieRepository.deleteById(id);
    }


}



