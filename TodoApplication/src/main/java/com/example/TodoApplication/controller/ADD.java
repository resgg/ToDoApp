package com.example.TodoApplication.controller;

import com.example.TodoApplication.model.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ADD {

    @GetMapping("/getuser")
    public String getUser(){
        return "helllo";
    }
}
