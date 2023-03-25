package com.plantcontroller.server.controllers;

import com.plantcontroller.server.entities.User;
import com.plantcontroller.server.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    public List<User> all() {
        return repository.findAll();
    }

    @PostMapping("/users")
    public User create(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @GetMapping("/users/{id}")
    public Optional<User> one(@PathVariable int id) {
        return repository.findById(id);
    }

    @PutMapping("/users/{id}")
    public User update(@RequestBody User newUser, @PathVariable int id) {

        return repository.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    user.setPassword(newUser.getPassword());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable int id) {
        repository.deleteById(id);
    }
}