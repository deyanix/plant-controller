package com.plantcontroller.serwer.controllers;

import com.plantcontroller.serwer.entities.User;
import com.plantcontroller.serwer.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    public List<User> all() {
        return repository.findAll();
    }

    @PostMapping("/users")
    public User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @GetMapping("/users/{id}")
    public Optional<User> one(@PathVariable int id) {
        return repository.findById(id);
    }

    @PutMapping("/users/{id}")
    public User replaceUser(@RequestBody User newUser, @PathVariable int id) {

        return repository.findById(id)
                .map(user -> {
                    user.setUserName(newUser.getUserName());
                    user.setUserEmail(newUser.getUserPassword());
                    user.setUserPassword(newUser.getUserPassword());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setUserId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        repository.deleteById(id);
    }
}