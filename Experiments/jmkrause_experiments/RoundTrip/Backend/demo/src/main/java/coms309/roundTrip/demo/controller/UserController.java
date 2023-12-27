package coms309.roundTrip.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import coms309.roundTrip.demo.model.User;
import coms309.roundTrip.demo.repository.UserRepository;


@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("user/all")
    List<User> GetAllUser() { return userRepository.findAll(); }

    @PostMapping("user/post/{f}{l}")
    User PostUserByPath(@PathVariable String f, @PathVariable String l) {
        User newUser = new User();
        newUser.setFirstName(f);
        newUser.setLastName(l);
        userRepository.save(newUser);
        return newUser;
    }

    @PostMapping("user/post")
    User PostUserByPath(@RequestBody User newUser) {
        userRepository.save(newUser);
        return newUser;
    }



}
