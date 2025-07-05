package com.task_manager.task;


import com.task_manager.task.entity.Task;

import com.task_manager.task.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testFindTaskByUsername(){
        var task = new Task();
        task.setTitle("Task 1");
        task.setUsername("user1");
        task.setDescription("Description 1");
        task.setCompleted(false);
        taskRepository.save(task);

        /// When
        var found = taskRepository.findAllByUsername("user1");


        /// Then
        assertEquals(1,found.size());
        var foundTask = found.get(0);
        assertEquals("Task 1",foundTask.getTitle());
        assertEquals("user1",foundTask.getUsername());
        assertEquals("Description 1",foundTask.getDescription());
        assertFalse(foundTask.isCompleted());

    }

    @Test
    public void testFindTaskByUsername_NoTaskFound(){
        var found = taskRepository.findAllByUsername("Jimmy");

        assertTrue(found.isEmpty());
    }

    @Test
    public void deleteTask(){
        // Given
        var task = new Task();
        task.setTitle("Task 1");
        task.setUsername("user1");
        task.setDescription("Description 1");
        task.setCompleted(false);
        taskRepository.save(task);

        // When
        taskRepository.delete(task);

        // Then
        assertTrue(taskRepository.findAllByUsername("user1").isEmpty());
        assertFalse(taskRepository.findById(task.getId()).isPresent());
    }
    @Test
    public void getUserTask(){
        /// Given
        var task = new Task();
        task.setTitle("Test Task 1");
        task.setUsername("Jimmy");
        task.setDescription("Description 1");
        task.setCompleted(false);
        taskRepository.save(task);

        /// When
        var found = taskRepository.findById(task.getId());

        /// Then
        assertEquals("Test Task 1",found.get().getTitle());
        assertEquals("Jimmy",found.get().getUsername());
        assertEquals("Description 1",found.get().getDescription());
        assertFalse(found.get().isCompleted());

    }
    @Test
    void UpdateTask(){
        // Given
        var task = new Task();
        task.setTitle("Original Title");
        task.setUsername("user1");
        task.setDescription("Original Description");
        task.setCompleted(false);
        var savedTask = taskRepository.save(task);

        // When
        savedTask.setTitle("Updated Title");
        savedTask.setDescription("Updated Description");
        savedTask.setCompleted(true);
        savedTask.setUsername("user2");
        taskRepository.save(savedTask);

        // Then
        var updatedTask = taskRepository.findById(savedTask.getId()).orElseThrow();
        assertEquals("Updated Title", updatedTask.getTitle());
        assertEquals("Updated Description", updatedTask.getDescription());
        assertEquals("user2", updatedTask.getUsername());
        assertTrue(updatedTask.isCompleted());

    }


}
