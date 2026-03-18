package com.spring.taskerbackend.service;

import com.spring.taskerbackend.model.Task;
import com.spring.taskerbackend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(long id) {
        return taskRepository.findById(id);
    }

    public List<Task> getTasksByStatus(String status) {
        return taskRepository.findByStatus(status);
    }

    public List<Task> getTasksByKeyword(String keyword) {
        return taskRepository.searchByKeyword(keyword);
    }

    public List<Task> searchByKeywordAndStatus(String keyword, String status) {
        return taskRepository.searchByKeywordAndStatus(keyword, status);
    }

    public Task createTask(Task task) {
        if (task.getStatus() == null || task.getStatus().isEmpty()){
            task.setStatus("TO_DO");
        }
        if (task.getCreatedAt() == null) {
            task.setCreatedAt(java.time.LocalDateTime.now());
        }
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskData) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found with id " + id));
        task.setTitle(taskData.getTitle());
        task.setDescription(taskData.getDescription());
        task.setStatus(taskData.getStatus());
        return taskRepository.save(task);
    }

    public void deleteTaskById(long id) {
        taskRepository.deleteById(id);
    }

}
