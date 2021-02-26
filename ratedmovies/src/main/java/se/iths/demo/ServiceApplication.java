package se.iths.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
@EnableRetry
public class ServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }


    /*
    //Ev lägg i egen config klass
    @Bean
    @LoadBalanced //kör runt i listan av services för lastbalansering
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
        //RestTemplate för att göra anrop och hämta info
        // från andra applikationer.
    }*/

    @Bean
    @LoadBalanced
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.setConnectTimeout(Duration.ofSeconds(3)).setReadTimeout(Duration.ofSeconds(20)).build();
    }



}
