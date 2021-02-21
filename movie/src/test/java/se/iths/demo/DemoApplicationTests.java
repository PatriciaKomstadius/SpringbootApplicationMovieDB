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

    //För att kunna ställa frågor till springboottests behövs en httpClient eller TestRestTemplate.
    //End to end tester. Testar hela applikationen
    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate testClient;

    @Test
    void contextLoads() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/xml");
        //verifierar att urlinfo i movieController mappar rätt.
        var result = testClient.getForEntity("http://localhost:" +port+ "movies", MovieDto[].class);

assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
assertThat(result.getBody().length).isGreaterThan(0);
    }

}

//Frågor labb2:
// - vilka tester ska vi göra (anrop till end pointsen?)
//Inga tester för controllern? unittester?


