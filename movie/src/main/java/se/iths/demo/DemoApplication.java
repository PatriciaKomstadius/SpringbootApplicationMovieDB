package se.iths.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    //Main. Härifrån körs Spring applikationen med kommandot run
    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);
    }
}
