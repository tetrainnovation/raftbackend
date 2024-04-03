
package dev.project.raftbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.project.raftbackend.Repo.UserdetailsRepo;
import dev.project.raftbackend.model.Userdetails;

@RestController
public class UserdetailsController {

    @Autowired
    UserdetailsRepo repo;

    @PostMapping("/createuser")
    public void createUser(@RequestBody Userdetails userdetails) {
        repo.save(userdetails);
    }
}