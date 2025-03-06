package com.example.TodoApplication.repo;
import com.example.TodoApplication.model.PendingTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PendingRepo extends JpaRepository<PendingTask,Long> {

    List<PendingTask> findByEmail(String email);
}
