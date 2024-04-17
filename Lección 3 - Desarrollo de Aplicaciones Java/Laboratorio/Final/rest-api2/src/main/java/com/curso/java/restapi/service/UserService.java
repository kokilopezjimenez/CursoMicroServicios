
package com.curso.java.restapi.service;

import com.curso.java.restapi.exceptions.NotFoundException;
import com.curso.java.restapi.exceptions.ValidationException;
import com.curso.java.restapi.model.User;
import com.curso.java.restapi.model.UserStatus;
import com.curso.java.restapi.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    
    public List<User> getActiveUsers(){
        return this.userRepository.getByStatus(UserStatus.ACTIVE);
    }
    
    public User find(long userId){
        Optional<User> userOpt = this.userRepository.find(userId);
        return userOpt.orElseThrow(() -> {
            return new NotFoundException(String.format("User "+userId+" not found"));
        });
    }
    
    public User save(User user){
        this.validate(user);
        return this.userRepository.save(user);
    }
    
    public User update(User user){
        this.validate(user);
        this.find(user.getId());
        return this.userRepository.update(user);
    }
    
    public void delete(long userId){
        this.userRepository.delete(userId);
    }
    
    private void validate(User user){
    	
        if(user == null){
            throw new ValidationException("Not user indicated");
        }
                
        if(user.getName() == null || user.getName().isBlank()){
            throw new ValidationException("Name is required");
        }
        if(user.getLastName() == null || user.getLastName().isBlank()){
            throw new ValidationException("Last Name is required");
        }
    }
    
}
