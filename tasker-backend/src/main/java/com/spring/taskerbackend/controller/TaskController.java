package com.spring.taskerbackend.controller;

import com.spring.taskerbackend.model.Task;
import com.spring.taskerbackend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Task> searchTasks(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status) {

        if (keyword != null && status != null) {
            return taskService.searchByKeywordAndStatus(keyword, status);
        } else if (keyword != null) {
            return taskService.getTasksByKeyword(keyword);
        } else if (status != null) {
            return taskService.getTasksByStatus(status);
        } else {
            return taskService.getAllTasks();
        }
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskData) {
        try {
            Task updatedTask = taskService.updateTask(id, taskData);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }

}
