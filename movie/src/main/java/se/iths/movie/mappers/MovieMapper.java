package se.iths.movie.mappers;

import org.springframework.stereotype.Component;
import se.iths.movie.dtos.MovieDto;
import se.iths.movie.entities.Movie;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MovieMapper {

    public MovieMapper() {
    }

    public MovieDto mapp(Movie movie) {
        return new MovieDto(movie.getId(), movie.getTitle(), movie.getYear(), movie.getGenre());
    }

    public Movie mapp(MovieDto movieDto) {
        return new Movie(movieDto.getId(), movieDto.getTitle(), movieDto.getYear(), movieDto.getGenre());
    }

    public Optional<MovieDto> mapp(Optional<Movie> optionalMovie) {
        if (optionalMovie.isEmpty())
            return Optional.empty();
        return Optional.of(mapp(optionalMovie.get()));
    }

    public List<MovieDto> mapp(List<Movie> all) {
        return all
                .stream()
                .map(this::mapp)
                .collect(Collectors.toList());
    }
}