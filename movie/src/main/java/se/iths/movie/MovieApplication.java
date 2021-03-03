package se.iths.movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieApplication {

    //Main. Härifrån körs Spring applikationen med kommandot run
    public static void main(String[] args) {

        SpringApplication.run(MovieApplication.class, args);
    }
}
