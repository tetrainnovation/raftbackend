
package dev.project.raftbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.mail.javamail.JavaMailSender;

import dev.project.raftbackend.Entity.Emaildetails;
import dev.project.raftbackend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.project.raftbackend.Repo.UserdetailsRepo;
import dev.project.raftbackend.model.Userdetails;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserdetailsController {

    @Autowired
    UserdetailsRepo repo;
    @Autowired private EmailService emailService;

    @PostMapping("/createuser")
    public String createUser(@RequestBody Userdetails userdetails) {
        System.out.println(userdetails.getEmailid());
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, "expensetrackerapikeyiniim12gjdfghgfvghjhbjnbnjnnkmjnkmfgbn")
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + 3600000))
                .claim("email", userdetails.getEmailid())
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        Emaildetails details = new Emaildetails();
        details.setRecipient(userdetails.getEmailid());
        details.setMsgBody("<li>"+token+"</li>");
        String status = emailService.sendSimpleMail(details);
        System.out.println(status);

        return map.toString();

    }
}