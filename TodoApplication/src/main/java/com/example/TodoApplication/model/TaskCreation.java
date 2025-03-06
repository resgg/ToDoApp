package com.example.TodoApplication.model;

import lombok.Data;

@Data
public class TaskCreation {
    private String assignto;
    private String taskname;
    private String status;
    private Long userid;
}
