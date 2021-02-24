package se.iths.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import se.iths.demo.dtos.MovieDto;
import org.springframework.http.HttpHeaders;

import static org.assertj.core.api.Assertions.assertThat;

//Springboottest startar upp hela applikationen
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

    //public DemoApplicationTests() {
    //}

    //För att kunna ställa frågor till springboottests behövs en httpClient eller TestRestTemplate.
    //End to end tester. Testar hela applikationen
    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate testClient;



    //GET MOVIES /movies httpstatus ok
    @Test
   // void contextLoads() {
        void allShouldReturnAMovieList() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/xml");
        //Skickar en HTTP request till localhost och assertar responsen ex httpstatus OK

        //System.out.println(headers.getClass());
        var result = testClient.getForEntity("http://localhost:" + port + "/movies", MovieDto[].class);
        //  System.out.println(result.getStatusCode());
        //   System.out.println(result.getBody().length);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().length).isGreaterThan(0);
    }

    //GET ONE /{id} HTTP OK
    //check or else throw HTTP STATUS NOT FOUND

    void oneShouldReturnOneMovieTitle() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/xml");

        var result = testClient.getForEntity("http://localhost:" + port + "/movies/1", MovieDto[].class);
//Hur testa ett inskickat id mot id i urln?

    }
    //POST CHECK HTTPSTATUS CREATED
    //CHECK throw new response status exception bad request

    //PUT CHECK HTTPSTATUS OK OR HTTP 404 NOT FOUND

    //PATCH CHECK HTTPSTATUS OK OR HTTP 404 NOT FOUND

    //DELETE CHECK HTTP STATUS OK

    //GET TITLE HTTPSTATUS OK

    //GET GENRE HTTPSTATUS OK

}



