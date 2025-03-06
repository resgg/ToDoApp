package com.example.TodoApplication.model;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.catalina.User;


@Data
@Entity
public class ToDoTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskid;

    @ManyToOne
    @JoinColumn(name = "user_created", nullable = false)
    private UserDto usercreated;

    private String assignto;

    private String taskname;

    private String status;

    @ManyToOne
    @JoinColumn(name = "assigned_user")
    private UserDto assigneduser;

    private String email;

}
