package se.iths.demo.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

//Unit testerna ska vara isolerade, inga beroenden (av t ex db)
//privata metoder kan man ej skriva tester till, de privata testas automatiskt när man kör de publika metoderna

class MovieControllerTest {

    //testar att giltigt id returnerar en person
    //verifierar att man får rätt personobjekt tillbaka vid test av id
    @Test
    void callingOneWithValidIdReturnsOnePerson() {
        MovieController movieController = new MovieController(new TestService());

        var movie = movieController.one(1L);

        assertThat(movie.getId()).isEqualTo(1);
        assertThat(movie.getTitle()).isEqualTo("TestTitle");
        assertThat(movie.getYear()).isEqualTo("TestYear");
        assertThat(movie.getGenre()).isEqualTo("TestGenre");

    }

    //testar throws exception med status 404
    @Test
    void callingOneWithInvalidIdThrowsExceptionWithResponseStatus4040() {
        MovieController movieController = new MovieController(new TestService());

        //var movie = movieController.one(2L); skriver med lambdauttryck under
        var exception = assertThrows(ResponseStatusException.class, () -> movieController.one(2L));
        //vi vill ha typen responsstatusexception.class,
        // fångar exceptionen i variabel exception,
        // returneras då hit och hämtas nedan
        //verifierar att statusen är samma som not found statusen

        assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}