package se.iths.movie;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;
import se.iths.movie.dtos.MovieDto;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieApplicationTests {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate testClient;

    @BeforeEach
    void beforeEachTestLoadDatabaseWithMovies(){
        MovieDto movie1 = new MovieDto(1, "TestTitle1", "TestYear1", "TestGenre1");
        MovieDto movie2 = new MovieDto(2, "TestTitle2", "TestYear2", "TestGenre2");
        MovieDto movie3 = new MovieDto(3, "TestTitle3", "TestYear3", "TestGenre3");
        MovieDto movie4 = new MovieDto(4, "TestTitle4", "TestYear4", "TestGenre4");
        MovieDto movie5 = new MovieDto(5, "TestTitle5", "TestYear5", "TestGenre5");

       testClient.postForEntity("http://localhost:" + port + "/movies", movie1, MovieDto.class);
       testClient.postForEntity("http://localhost:" + port + "/movies", movie2, MovieDto.class);
       testClient.postForEntity("http://localhost:" + port + "/movies", movie3, MovieDto.class);
       testClient.postForEntity("http://localhost:" + port + "/movies", movie4, MovieDto.class);
       testClient.postForEntity("http://localhost:" + port + "/movies", movie5, MovieDto.class);

    }

    @Test
    void allShouldReturnAMovieList() {
        String url = "http://localhost:" + port + "/movies";

        var result = testClient.getForEntity(url, MovieDto[].class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().length).isGreaterThan(0);
    }

    @Test
    void oneShouldReturnOneMovieTitle() {
        String url = "http://localhost:" + port + "/movies/{id}";

        long id = 4;
        var result = testClient.getForEntity(url, MovieDto.class, id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getTitle()).isEqualTo("TestTitle4");
    }

    @Test
    void oneWithInvalidIdShouldThrowResponseStatusException404() {
        String url = "http://localhost:" + port + "/movies/{id}";

        long id = 200;
        var exception = testClient.getForEntity(url, MovieDto.class, id);

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void findTitleShouldReturnAllMoviesInTheGenre() {
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/movies").path("/titles")
                .queryParam("title", "TestTitle1").build().toUri();

        var result = testClient.getForEntity(uri, MovieDto[].class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()[0].getTitle()).isEqualTo("TestTitle1");
    }

    @Test
    void findTitleNotInDatabaseShouldThrowResponseStatusException404() {
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/movies").path("/titles")
                .queryParam("title", "Game").build().toUri();

        var result = testClient.getForEntity(uri, MovieDto.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void findGenreShouldReturnAllMoviesInTheGenre() {
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/movies").path("/genre")
                .queryParam("genre", "TestGenre1").build().toUri();

        var result = testClient.getForEntity(uri, MovieDto[].class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()[0].getGenre()).isEqualTo("TestGenre1");
    }

    @Test
    void findGenreNotInDatabaseShouldThrowResponseStatusException404() {
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/movies").path("/genre")
                .queryParam("genre", "StandUp").build().toUri();

        var result = testClient.getForEntity(uri, MovieDto.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void postShouldCreateANewMovie() {
        MovieDto movieDto = new MovieDto(6, "NewMovie", "NewMovie", "NewMovie");

        var result = testClient.postForEntity("http://localhost:" + port + "/movies", movieDto, MovieDto.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody().getTitle()).isEqualTo("NewMovie");
    }

    @Test
    void postWithEmptyTitleShouldThrowResponseStatusException400() {
        String url = "http://localhost:" + port + "/movies";

        MovieDto movieDto = new MovieDto();
        movieDto.setTitle("");

        long id = 7;
        var exception = testClient.postForEntity(url, movieDto, MovieDto.class, id);

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void putShouldReplaceAllFieldsInMovie() {
        String url = "http://localhost:" + port + "/movies/{id}";

        MovieDto movieDto = new MovieDto(5, "Hello you", "1998", "Comedy");

        long id = 5;

        testClient.put(url, movieDto, MovieDto.class, id);
        ResponseEntity<MovieDto> result = testClient.exchange(url, HttpMethod.PUT, new HttpEntity<>(movieDto), MovieDto.class, id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getTitle()).isEqualTo("Hello you");
    }

    @Test
    void putWithInvalidIdShouldThrowResponseStatusException404() {
        String url = "http://localhost:" + port + "/movies/{id}";

        long id = 100;

        testClient.put(url, MovieDto.class, id);
        var exception = testClient.getForEntity(url, MovieDto.class, id);

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void patchShouldUpdateFieldsInMovie() {
        String url = "http://localhost:" + port + "/movies/{id}";

        MovieDto movieDto = new MovieDto(5, "TestTitle5", "TestYear5", "TestGenre5");

        movieDto.setYear("NewTestYear5");
        long id = 5;
        testClient.patchForObject(url, movieDto, MovieDto.class, movieDto, id);

        ResponseEntity<MovieDto> result = testClient.exchange(url, HttpMethod.PATCH, new HttpEntity<>(movieDto), MovieDto.class, id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getYear()).isEqualTo("NewTestYear5");
    }

    @Test
    void patchWithInvalidIdShouldThrowResponseStatusException404() {
        String url = "http://localhost:" + port + "/movies/{id}";

        MovieDto movieDto = new MovieDto();
        long id = 100;
        testClient.patchForObject(url, movieDto, MovieDto.class, movieDto, id);
        var exception = testClient.getForEntity(url, MovieDto.class, id);

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteShouldDeleteMovie() {
        String url = "http://localhost:" + port + "/movies/delete/{id}";

        long id = 6;

        testClient.delete(url, id);
        ResponseEntity<MovieDto> result = testClient
                .exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, MovieDto.class, id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(result.getBody().getId()).isEqualTo(0);
    }

}




