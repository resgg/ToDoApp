package com.example.TodoApplication.email;

import com.example.TodoApplication.model.TaskCreation;
import com.example.TodoApplication.model.UserDto;
import com.example.TodoApplication.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailCreation {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    UserRepo userRepo;
     public void sendInvitationMail(String email, TaskCreation taskCreation){
         SimpleMailMessage message = new SimpleMailMessage();
         Optional<UserDto> userDto = userRepo.findById(taskCreation.getUserid());
         UserDto userDto1 = null;
         if(userDto.isPresent()){
            userDto1 =  userDto.get();
         }
         message.setFrom("reshmabarvin40@gmail.com");
         message.setTo(email);
         message.setSubject("Task Assignment - Invitation to Join");
         message.setText(taskCreation.getTaskname() + "this task is assigned to you " + userDto1.getUsername() +", but it appears you are not registered in our system. "
                 + "Please sign up using this email address to manage your tasks." + "Click the following link to sign up: http://localhost:8080/createUser" + " (Use postman to hit the link) ");
         mailSender.send(message);
     }

}
