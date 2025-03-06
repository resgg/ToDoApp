package com.example.TodoApplication.service;

import com.example.TodoApplication.model.ResetPassword;
import com.example.TodoApplication.model.UserDto;
import com.example.TodoApplication.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public String login(){
        return "login successful";
    }
    public void createUser(UserDto user){
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(passwordEncoder.encode(user.getPassword()));
        userDto.setRole(user.getRole());
        userDto.setUserid(user.getUserid());
        userRepo.save(userDto);
    }
    public void resetPassword(ResetPassword resetPassword){

        UserDto userDto = userRepo.findByEmail(resetPassword.getEmail());

        if (userDto == null) {
            throw new RuntimeException("User not found for email: " + resetPassword.getEmail());
        }

        if (!resetPassword.getPassword().equals(resetPassword.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match!");
        }

        userDto.setPassword(passwordEncoder.encode(resetPassword.getConfirmPassword()));
        userRepo.save(userDto);
    }

}
