package com.example.TodoApplication.model;

import lombok.Data;

@Data
public class ResetPassword {
    private String email;
    private String password;
    private String confirmPassword;

}
