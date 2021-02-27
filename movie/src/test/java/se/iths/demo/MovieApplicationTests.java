package se.iths.demo;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import se.iths.demo.dtos.MovieDto;


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
    //check or else throw HTTP STATUS NOT FOUND
    @Test
    void oneShouldReturnOneMovieTitle() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/xml");

        String url = "http://localhost:" + port + "/movies/{id}";

        long id = 5;
        var result = testClient.getForEntity(url, MovieDto.class, id);

        assertThat(result.getBody().getTitle()).isEqualTo("TestTitle4");
    }

    //OK! - POST MOVIE CHECK HTTPSTATUS CREATED
    @Test
    void postMovie() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/xml");

        MovieDto movieDto = new MovieDto(1, "TestTitle", "TestTitle", "TestTitle");

        var result = testClient.postForEntity("http://localhost:" + port + "/movies", movieDto, MovieDto.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody().getTitle()).isEqualTo("TestTitle");

    }

    //PUT CHECK HTTPSTATUS OK OR HTTP 404 NOT FOUND
    @Test
    void putMovie() {
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

    //OK! - PATCH UPDATE
    // CHECK HTTPSTATUS OK OR HTTP 404 NOT FOUND
    @Test
    void patchMovie() {
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

    //OK! - DELETE CHECK HTTP STATUS NOT FOUND - ok?
    @Test
    void deleteMovie() {
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

}




