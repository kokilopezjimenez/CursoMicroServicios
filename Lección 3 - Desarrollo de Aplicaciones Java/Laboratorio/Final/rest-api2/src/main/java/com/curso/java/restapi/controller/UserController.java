package com.curso.java.restapi.controller;

import com.curso.java.restapi.model.User;
import com.curso.java.restapi.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    
    @GetMapping
    public List<User> getUsers(){
        var users = userService.getActiveUsers();
        return users;
    }
    
    @GetMapping("/{userId}")
    public User findUser(@PathVariable("userId") long userId){
        return userService.find(userId);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody User user){
        return this.userService.save(user);
    }
    
    @PutMapping("/{userId}")
    public User update(@RequestBody User user, @PathVariable("userId") long userId){
        if(userId != user.getId()){
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "User ids in Path and body are not the same");
        }
        return this.userService.update(user);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long userId){
        this.userService.delete(userId);
    }
}
