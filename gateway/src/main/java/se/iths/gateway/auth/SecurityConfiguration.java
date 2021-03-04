package se.iths.gateway.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import reactor.core.publisher.Mono;

//Autentifikation, tillåts att utföras
@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {

        // Disable default security.
        return http.httpBasic().disable() //stänger av alla httpbasics
                .formLogin().disable()
                .csrf().disable()
                .logout().disable() //behövs ej med tokens, existerar ej
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())

                //validerar token: authenticationManager validerar att tokenet är giltigt, om giltigt
                //skapas en springbootuser
                .authenticationManager(this.authenticationManager)
                //läser in headern: tillhandahåller användarens uppgifter, hämtar detta fr headern (ej db)
                .securityContextRepository(this.securityContextRepository)
                .exceptionHandling()
                .authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() ->
                        swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)
                )).accessDeniedHandler((swe, e) -> Mono.fromRunnable(() ->
                        swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN))).and()
                .authorizeExchange()
                .pathMatchers("/auth/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/movies/**").permitAll()
                .pathMatchers("/movies/**").hasRole("ADMIN")
                .pathMatchers(HttpMethod.GET,"/ratings/**").authenticated()
                .pathMatchers("/ratings/**").hasRole("ADMIN")
                .pathMatchers(HttpMethod.GET,"/ratedmovies/**").authenticated()
                .anyExchange().authenticated()
                .and().build();
    }
}