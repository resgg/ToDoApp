package com.example.TodoApplication.repo;

import com.example.TodoApplication.model.ToDoTask;
import com.example.TodoApplication.model.UserDto;
import com.example.TodoApplication.service.ToDoTaskService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepo extends JpaRepository<com.example.TodoApplication.model.ToDoTask,Long>{

    List<ToDoTask> findByAssignto(String email);
    List<ToDoTask> findByAssigneduser(UserDto userCreated);

    List<ToDoTask> findByUsercreated(UserDto userCreated);
}
