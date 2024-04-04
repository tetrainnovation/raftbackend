package dev.project.raftbackend.Controller;


import dev.project.raftbackend.Service.UserService;
import dev.project.raftbackend.model.Userdetails;
import dev.project.raftbackend.Repo.UserdetailsRepo;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;


@RestController
public class Step0 {
    //@Autowired
    private Userdetails userDetails;
    private UserService userService;

    public Step0(UserService userService) {
        this.userService = userService;
    }
    private static final String SECRET_KEY = "expensetrackerapikeyiniim12gjdfghgfvghjhbjnbnjnnkmjnkmfgbn";

    @GetMapping("/login/{token}")
    public ResponseEntity<?> firstserve(@PathVariable("token") String token){
        String[] chunks = token.split("\\.");
        if(isTokenExpired(token)){
            ErrorResponse errorResponse = new ErrorResponse("Token has Expired", HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String((decoder.decode(chunks[1])));
        JSONParser parser = new JSONParser();
        try{
            JSONObject json = (JSONObject) parser.parse(payload);
            String email = (String) json.get("email");

            System.out.println(getUserByEmail(email));
            return getUserByEmail(email);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }
    public static boolean isTokenExpired(String token) {
        try {

            // Parse the token
            SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            // Get the expiration time from the token
            Date expiration = claimsJws.getBody().getExpiration();

            // Compare with the current time
            Instant now = Instant.now();
            Date current = Date.from(now);
            return expiration.before(current); // Token is expired if expiration time is before current time
        } catch (Exception e) {
            // Token parsing or verification failed
            return true; // Consider token as expired
        }

    }



    public ResponseEntity<Userdetails> getUserByEmail(String emailid) {
        System.out.println("This email ID has reached Entity Function "+emailid);
        Userdetails userDetails = userService.findUserByEmail(emailid);
        if (userDetails != null) {
            return ResponseEntity.ok().body(userDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
class ErrorResponse {
    private final String message;
    private final HttpStatus status;

    public ErrorResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}