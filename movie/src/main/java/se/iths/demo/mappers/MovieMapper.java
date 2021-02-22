package se.iths.demo.mappers;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import se.iths.demo.dtos.MovieDto;
import se.iths.demo.entities.Movie;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//mappmetoderna kopierar över moviedto till movie och tvärtom, mappar ihop
@Component
public class MovieMapper {

    public MovieMapper() {
    }

    //metod som kan kopiera ett movieDtoobjekt från ett movieobjekt
    public MovieDto mapp(Movie movie) {
        return new MovieDto(movie.getId(), movie.getTitle(), movie.getYear(), movie.getGenre());
    }
    //metod som kan kopiera ett movieobjekt från ett movieDtoobjekt
    public Movie mapp(MovieDto movieDto) {
        return new Movie(movieDto.getId(), movieDto.getTitle(), movieDto.getYear(), movieDto.getGenre());
    }

    public Optional<MovieDto> mapp(Optional<Movie> optionalMovie) {
        if (optionalMovie.isEmpty()) //om den är tom
            return Optional.empty(); //returneras optional movieDto som är tom
        return Optional.of(mapp(optionalMovie.get())); //annars returnera optional,
// mappar inkommande optional movie till en movieDto
    }//ström av data. varje movie mappas och läggs i en ny lista

    public List<MovieDto> mapp(List<Movie> all) {
        return all
                .stream()
                .map(this::mapp) //anger namnet på metoden (mapp) som ska appliceras på varje movie-objekt
                .collect(Collectors.toList()); //lägger allt i en ny lista
    }
}