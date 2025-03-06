package com.example.TodoApplication.controller;

import com.example.TodoApplication.email.EmailCreation;
import com.example.TodoApplication.model.TaskCreation;
import com.example.TodoApplication.model.ToDoTask;
import com.example.TodoApplication.model.UserDto;
import com.example.TodoApplication.repo.ToDoRepo;
import com.example.TodoApplication.repo.UserRepo;
import com.example.TodoApplication.service.ToDoTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "http://localhost:4200")
public class ToDoController {

    @Autowired
    ToDoTaskService toDoTaskService;

    @Autowired
    UserRepo userRepo;

    @Autowired
    EmailCreation emailCreation;

    @PostMapping("/create")
    public String createTask(@RequestBody TaskCreation taskCreation){
        if(userRepo.existsByEmail(taskCreation.getAssignto())){
            return toDoTaskService.createTask(taskCreation);
        }else{
            emailCreation.sendInvitationMail(taskCreation.getAssignto(),taskCreation);
            toDoTaskService.createTask(taskCreation);
            toDoTaskService.createPendingTask(taskCreation);
            return "user not found,Invitation sent";
        }
    }

    @GetMapping("/getTasks")
    public List<ToDoTask> getTasks(@RequestParam String userId){
        Long userIdLong = Long.parseLong(userId);
        Optional<UserDto> userDto = userRepo.findById(userIdLong);
        if(userDto.isPresent()){
            UserDto userDto1 = userDto.get();
            return toDoTaskService.getAllTaskByUserId(userDto1);
        }
        return  new ArrayList<>();
    }
    @PutMapping("/update")
    public String updateTask(@RequestBody ToDoTask toDoTask){
        return toDoTaskService.updateTask(toDoTask);
    }

    @DeleteMapping("/delete")
    public String deleteById(@RequestParam Long taskId){
        return toDoTaskService.deleteTaskById(taskId);
    }

    @GetMapping("/getTask")
    public Optional<ToDoTask> getTask(@RequestParam Long taskId){
        return toDoTaskService.getTaskById(taskId);
    }

    @PostMapping("/changeStatus/{taskId}")
    public ResponseEntity<Map<String, String>> changeTaskStatus(@PathVariable Long taskId, @RequestBody Map<String,Object> status){
       String result = toDoTaskService.changeTaskStatus(taskId,status);
        Map<String, String> response = new HashMap<>();
        response.put("message",result);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/isitworking")
    public String getTask(){
        return "happy vandhuruchu";
    }


}
