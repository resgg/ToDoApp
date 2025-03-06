package com.example.TodoApplication.repo;

import com.example.TodoApplication.model.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<UserDto,Long> {
    UserDto findByUsername(String username);
    Boolean existsByEmail(String email);

    UserDto findByEmail(String email);

}
