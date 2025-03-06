package com.example.TodoApplication.service;

import com.example.TodoApplication.model.PendingTask;
import com.example.TodoApplication.model.TaskCreation;
import com.example.TodoApplication.model.ToDoTask;
import com.example.TodoApplication.model.UserDto;
import com.example.TodoApplication.repo.PendingRepo;
import com.example.TodoApplication.repo.ToDoRepo;
import com.example.TodoApplication.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ToDoTaskService {
    @Autowired
    ToDoRepo toDoRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    private PendingRepo pendingRepo;

    public String createTask(TaskCreation taskCreation){
        UserDto taskAssignedUser= userRepo.findByEmail(taskCreation.getAssignto());
        Optional<UserDto> taskCreatedUser= userRepo.findById(taskCreation.getUserid());
        if(taskAssignedUser != null && taskCreatedUser.get().getEmail().equals(taskAssignedUser.getEmail())){
            ToDoTask toDoTask = new ToDoTask();
            toDoTask.setStatus(taskCreation.getStatus());
            toDoTask.setAssignto(taskCreation.getAssignto());
            toDoTask.setTaskname(taskCreation.getTaskname());
            toDoTask.setUsercreated(taskCreatedUser.get());
            toDoTask.setAssigneduser(taskAssignedUser);
            toDoRepo.save(toDoTask);
            return "Task created successfully";
        }else if(taskAssignedUser == null){
            ToDoTask toDoTask = new ToDoTask();
            toDoTask.setStatus(taskCreation.getStatus());
            toDoTask.setAssignto(taskCreation.getAssignto());
            toDoTask.setTaskname(taskCreation.getTaskname());
            toDoTask.setUsercreated(taskCreatedUser.get());
            toDoTask.setAssigneduser(null);
            toDoRepo.save(toDoTask);
            return "Task created successfully with out user";
        }else{
            ToDoTask toDoTask = new ToDoTask();
            toDoTask.setStatus(taskCreation.getStatus());
            toDoTask.setAssignto(taskCreation.getAssignto());
            toDoTask.setTaskname(taskCreation.getTaskname());
            toDoTask.setUsercreated(taskCreatedUser.get());
            toDoTask.setAssigneduser(taskAssignedUser);
            toDoRepo.save(toDoTask);
            return "Task created successfully for another user";
        }

    }


    public List<ToDoTask> getAllTaskByUserId(UserDto userDto){
        List<ToDoTask> assignedTasks = toDoRepo.findByAssigneduser(userDto);
        List<ToDoTask> createdTasks = toDoRepo.findByUsercreated(userDto);

        if (assignedTasks == null) {
            assignedTasks = new ArrayList<>();
        }
        if (createdTasks == null) {
            createdTasks = new ArrayList<>();
        }

        // Combine both lists
        assignedTasks.addAll(createdTasks);
        return assignedTasks;
    }

    public String deleteTaskById(Long taskid){
        toDoRepo.deleteById(taskid);
        return "deleted successfully";
    }

    public String updateTask(com.example.TodoApplication.model.ToDoTask toDoTask){
        if(toDoRepo.existsById(toDoTask.getTaskid())){
            toDoRepo.save(toDoTask);
            return "update successfully";
        }else{
            return "User id not found";
        }

    }

    public Optional<ToDoTask> getTaskById(Long taskId){
        return toDoRepo.findById(taskId);
    }

    public void createPendingTask(TaskCreation taskCreation){
        PendingTask pendingTask = new PendingTask();
        pendingTask.setTaskName(taskCreation.getTaskname());
        pendingTask.setEmail(taskCreation.getAssignto());
        pendingTask.setUserCreated(false);
        pendingRepo.save(pendingTask);
    }

    public void assignPendingTask(UserDto userDto){
        if(pendingRepo.findByEmail(userDto.getEmail())!=null){
            List<PendingTask> pendingTaskList = pendingRepo.findByEmail(userDto.getEmail());
            if( pendingTaskList != null &&
                    pendingTaskList.stream().anyMatch(x->x.getEmail().equalsIgnoreCase(userDto.getEmail()))){
                List<PendingTask> pendingTasks1 = pendingTaskList.stream().filter(x->x.getEmail().equalsIgnoreCase(userDto.getEmail())).collect(Collectors.toList());
                for(int i=0;i< pendingTasks1.size();i++){
                    pendingTasks1.get(i).setUserCreated(true);
                }
                pendingRepo.saveAll(pendingTasks1);
                changeTaskPendingAccept(pendingTasks1,userDto);
            }
        }


    }
    public void changeTaskPendingAccept(List<PendingTask> pendingTasks,UserDto userDto){
        for(PendingTask pendingTask: pendingTasks){
           List<ToDoTask> listOfTask = toDoRepo.findByAssignto(pendingTask.getEmail());
           for(ToDoTask task : listOfTask){
               //will produce one websocket message here
               // to notify the user is created and assigned
               task.setAssigneduser(userDto);
               toDoRepo.save(task);
           }
        }
    }

    public String changeTaskStatus(Long taskId, Map<String,Object> status){
        Optional<ToDoTask> toDoTask1 =  toDoRepo.findById(taskId);
        if(toDoTask1.isPresent()){
            ToDoTask task = toDoTask1.get();
            task.setStatus((String) status.get("status"));
            toDoRepo.save(task);
            return (String) status.get("status");
        }
        return "not updated";
    }


}
