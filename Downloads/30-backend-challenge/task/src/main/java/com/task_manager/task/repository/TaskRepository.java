package com.task_manager.task.repository;


import com.task_manager.task.dto.ResponseDto;
import com.task_manager.task.entity.Task;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findAllByUsername(String username);

    boolean existsByTitleAndUsername(@NotBlank(message= "title is required") String title, @NotBlank(message= "username is required") String username);
}
