package com.example.TodoApplication.controller;

import com.example.TodoApplication.model.AuthenticationRequest;
import com.example.TodoApplication.model.ResetPassword;
import com.example.TodoApplication.model.UserDto;
import com.example.TodoApplication.repo.PendingRepo;
import com.example.TodoApplication.repo.UserRepo;
import com.example.TodoApplication.service.AuthService;
import com.example.TodoApplication.service.ToDoTaskService;
import com.example.TodoApplication.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PendingRepo pendingRepo;
    @Autowired
    private ToDoTaskService toDoTaskService;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/authenticate")
    public Map<String,String> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        UserDto userDto = userRepo.findByUsername(userDetails.getUsername());
        if(!jwt.isEmpty()){
            toDoTaskService.assignPendingTask(userDto);
        }
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        map.put("userId", String.valueOf(userDto.getUserid()));
        map.put("name", userDto.getUsername());
        map.put("email", userDto.getEmail());
        return map ;
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody ResetPassword resetpassword){
        authService.resetPassword(resetpassword);
        return "It is updated";
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto){
        authService.createUser(userDto);
        return new ResponseEntity<>("User created successfully", HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<String> create(){
        return  new ResponseEntity<>("Hello", HttpStatus.OK);
    }
}
