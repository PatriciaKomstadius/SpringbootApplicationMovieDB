package se.iths.demo.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.iths.demo.entities.Movie;
import se.iths.demo.mappers.MovieMapper;
import se.iths.demo.repositories.MovieRepository;
import se.iths.demo.dtos.MovieDto;

import java.util.List;
import java.util.Optional;

//Service mellan Controllern och DB
// sköter validering av inskickad info
@Service
public class MovieService {

    private final MovieMapper movieMapper;
    private MovieRepository movieRepository;


    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    //hämta alla filmer, all();
    public List<MovieDto> getAllMovies() {
        return movieMapper.mapp(movieRepository.findAll());
    }

    //hämta en film, one(Long id);
    public Optional<MovieDto> getOne(Long id) {
        //map from Movie to MovieDto
        return movieMapper.mapp(movieRepository.findById(id));
    }

    //Skapa ny film, create(Movie movie);
    public MovieDto createMovie(MovieDto movie) {
        //valideringskod
        if (movie.getTitle().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "No movietitle entered."); //400 OK? skriver ej ut detta?
        //map from movieDto to Movie, får in Moviedto mappar det till ett movieobjekt
        // svaret från movierepo är en entitet som mappas till en moviedto hela.
        return movieMapper.mapp(movieRepository.save(movieMapper.mapp(movie)));
    }

    public List<MovieDto> getTitle(String title) {
        return movieMapper.mapp(movieRepository.findByTitle(title));
    }

    public List<MovieDto> getAllByGenre(String genre) {
        return movieMapper.mapp(movieRepository.findAllByGenre(genre));
    }

   /* public MovieDto updateTitle(MovieDto newMovie, Long id) {
        return movieRepository.findById(id)
                .map(movie -> {
                    movie.setTitle(newMovie.getTitle());
                    movie.setGenre(newMovie.getGenre());
                    movie.setYear(newMovie.getYear());
                    return movieRepository.save(movie);
                })
                .orElseGet(() -> {
                    newMovie.setId(id);
                    return movieRepository.save(newMovie);
                });
    }*/


    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public MovieDto replace(Long id, MovieDto movieDto) {
        Optional<Movie> movie = movieRepository.findById(id); //kopierar över info till movieobjektet
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
    //Ev skapa ny klass för att enbart uppdatera ex genre
    public MovieDto update(Long id, MovieDto movieDto) {
        Optional<Movie> movie = movieRepository.findById(id); //kopierar över info till movieobjektet
        if (movie.isPresent()) {

            Movie updateMovie = movie.get();

            if (movieDto.getTitle() != null)
                updateMovie.setTitle(movieDto.getTitle());
            if (movieDto.getGenre() != null)
                updateMovie.setGenre(movieDto.getGenre());
            if (movieDto.getYear() != null)
                updateMovie.setYear(movieDto.getYear());
            return movieMapper.mapp(movieRepository.save(updateMovie));
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id " + id + "not found.");
        }
    }
}



