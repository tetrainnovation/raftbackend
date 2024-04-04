
package dev.project.raftbackend.Controller;

import dev.project.raftbackend.Service.MailService;
import dev.project.raftbackend.Repo.UserdetailsRepo;
import dev.project.raftbackend.model.Userdetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserdetailsController {

    @Autowired
    UserdetailsRepo repo;
    @Autowired
    private MailService mailService;

    @PostMapping("/createuser")
    public String createUser(@RequestBody Userdetails userdetails) {
        System.out.println(userdetails.getEmailid());
        repo.save(userdetails);


        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, "expensetrackerapikeyiniim12gjdfghgfvghjhbjnbnjnnkmjnkmfgbn")
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + 86400000))
                .claim("email", userdetails.getEmailid())
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        try {
            String clickableLink = "http://localhost:8080/login" + token;

            mailService.sendMail(userdetails.getEmailid(), "Your Verification Link", clickableLink);
            System.out.println("Mail Sent Successfully!");

        }
        catch (Exception e){
            System.out.println("Something happened");
        }
        return map.toString();

    }


}