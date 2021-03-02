package se.iths.demo;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import se.iths.demo.dtos.MovieDto;


import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieApplicationTests {

    //För att kunna ställa frågor till springboottests behövs en httpClient eller TestRestTemplate.
    //End to end tester. Testar hela applikationen
    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate testClient;

    //OK! - GET MOVIES /movies hela listan httpstatus ok
    @Test
    // void contextLoads() {
    void allShouldReturnAMovieList() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/xml");

        var result = testClient.getForEntity("http://localhost:" + port + "/movies", MovieDto[].class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().length).isGreaterThan(0);
    }


    //OK! - GET ONE /{id} HTTP OK
    @Test
    void oneShouldReturnOneMovieTitle() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/xml");

        String url = "http://localhost:" + port + "/movies/{id}";

        long id = 5;
        var result = testClient.getForEntity(url, MovieDto.class, id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getTitle()).isEqualTo("TestTitle4");
    }

    //check or else throw HTTP STATUS NOT FOUND
    @Test
    void oneShouldThrowResponseStatusException404() {
        String url = "http://localhost:" + port + "/movies/{id}";
        long id = 10;
        var exception = testClient.getForEntity(url, MovieDto.class, id);
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    //OK! - POST MOVIE CHECK HTTPSTATUS CREATED
    @Test
    void createShouldCreateANewMovie() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/xml");

        MovieDto movieDto = new MovieDto(1, "TestTitle", "TestTitle", "TestTitle");

        var result = testClient.postForEntity("http://localhost:" + port + "/movies", movieDto, MovieDto.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody().getTitle()).isEqualTo("TestTitle");
    }

    //Throws HttpStatus BAD_REQUEST 400 when empty title
    @Test
    void createEmptyTitleShouldThrowResponseStatusException400() {
        String url = "http://localhost:" + port + "/movies";
        MovieDto movieDto = new MovieDto();
        movieDto.setTitle("");
        long id = 9;
        var exception = testClient.postForEntity(url, movieDto, MovieDto.class, id);
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    //PUT CHECK HTTPSTATUS OK OR HTTP 404 NOT FOUND
    @Test
    void replaceShouldReplaceAllFieldsInMovie() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/xml");

        String url = "http://localhost:" + port + "/movies/{id}";
        MovieDto movieDto = new MovieDto(4, "Hello", "1998", "Comedy");

        long id = 4;

        testClient.put(url, movieDto, MovieDto.class, id);

        ResponseEntity<MovieDto> result = testClient.exchange(url, HttpMethod.PUT, new HttpEntity<>(movieDto), MovieDto.class, id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getTitle()).isEqualTo("Hello");
    }

    //exception throws
    @Test
    void replaceShouldThrowResponseStatusException404() {
        String url = "http://localhost:" + port + "/movies/{id}";
        long id = 10;
        //  var exception = testClient.put(url, MovieDto.class, id);
        // assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    //OK! - PATCH UPDATE
    // CHECK HTTPSTATUS OK OR HTTP 404 NOT FOUND
    @Test
    void updateShouldUpdateFieldsInMovie() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/xml");

        String url = "http://localhost:" + port + "/movies/{id}";

        MovieDto movieDto = new MovieDto(4, "TestTitle3", "TestYear3", "TestGenre3");

        movieDto.setYear("1998");
        long id = 4;
        testClient.patchForObject(url, movieDto, MovieDto.class, movieDto, id);

        ResponseEntity<MovieDto> result = testClient.exchange(url, HttpMethod.PATCH, new HttpEntity<>(movieDto), MovieDto.class, id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getYear()).isEqualTo("1998");

    }

    @Test
    void updateShouldThrowResponseStatusException404() {

    }

    //OK! - DELETE CHECK HTTP STATUS NOT FOUND - ok?
    @Test
    void deleteShouldDeleteMovie() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/xml");

        String url = "http://localhost:" + port + "/movies/delete/{id}";

        long id = 6;

        testClient.delete(url, id);

        ResponseEntity<MovieDto> result = testClient.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, MovieDto.class, id);

        assertThat(result.getBody().getId()).isEqualTo(0);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    //GET TITLE HTTPSTATUS OK

    //GET GENRE HTTPSTATUS OK
    @Test
    void getGenreShouldReturnAllMoviesInTheGenre() {
     //   HttpHeaders headers = new HttpHeaders();
      //  headers.add("Accept", "application/xml");

  //      URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:"+port+"/movies").path("/genre")
    //            .queryParam("genre", "Comedy").build().toUri();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost:"+port+"/movies/genre")
                .queryParam("genre", "Comedy");
        String uriBuilder = builder.build().encode().toUriString();

       var result2 = testClient.exchange(uriBuilder , HttpMethod.GET, HttpEntity.EMPTY,
                MovieDto.class);

//        var result = testClient.getForEntity(uri, MovieDto.class);

       // String url = "http://localhost:" + port + "/movies/genre?genre={genre}";
      //  String genre = "Comedy";
      //  var result = testClient.getForEntity(url, MovieDto.class, genre);

        // assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result2.getBody().getGenre()).isEqualTo("Comedy");

    }


}




