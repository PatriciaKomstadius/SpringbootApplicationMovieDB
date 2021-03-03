package se.iths.gateway.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

//Autenticationmanagern validerar tokenet, men inte password osv.
//Ej kopplat till en db här. Behövert endast tillgång till secretkeyn för valideringen
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private JWTUtil jwtUtil;

    //autenticatemetod som
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        //en autenticatemetod som får in objektet auth med token i
        String authToken = authentication.getCredentials().toString(); //läser ut token
        try {
            //Check if signed with our secret key
            var claims = jwtUtil.getAllClaimsFromToken(authToken); //gör en validering av token
            if (claims == null) {
                return Mono.empty();
            }
            //Check so it hasn't expired om det är giltigt
            Date expires = claims.getBody().getExpiration();
            if (expires.before(new Date(System.currentTimeMillis())))
                return Mono.empty();

            //Get list of roles for this user om det är giltigt, kan plocka ut authorities och roller
            ArrayList<String> perms = (ArrayList<String>) claims.getBody().get("authorities");
            var authorities = perms.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            //returnerar och skapar sedan ett nytt userpasswordautenticationtoken

            //Skapar en springbootuser om tokenet är giltigt:
            // hämtar då användarnamn och roller från tokenet
            return Mono.just(new UsernamePasswordAuthenticationToken(claims.getBody().getSubject(), null, authorities));
        } catch (Exception e) {
            return Mono.empty();
        }
    }
}