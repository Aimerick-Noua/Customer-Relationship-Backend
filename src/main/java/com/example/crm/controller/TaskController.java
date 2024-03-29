package com.example.crm.controller;


import com.example.crm.model.Task;
import com.example.crm.model.User;
import com.example.crm.service.TaskService;
import com.example.crm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/auth/users/tasks")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class TaskController {
    private final TaskService taskService ;
    private final UserService userService;


    @GetMapping("/{userId}")
    public List<Task> getAllTasksByUserId(@PathVariable Long userId) {
        return taskService.getAllTasksByUserId(userId);
    }

    @GetMapping()
    public List<User> getAllTasksWithAllUsers() {
        return taskService.getAllEmployeesWithTasks();
    }
    @PostMapping("/{id}")
    public ResponseEntity<String> addTasksToUser(@PathVariable Long id, @RequestBody Task tasks) {
        userService.addTaskToUser(id, tasks);
        return ResponseEntity.status(HttpStatus.CREATED).body("Tasks added to user successfully.");
    }
    @PutMapping("/task/{id}")
    public Task updateTask(@PathVariable Long id,@RequestBody Task task){
        return taskService.updateTask(id,task);
    }
    @GetMapping("/task/{id}")
    public Task getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id);
    }
    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long taskId){
        taskService.deleteTask(taskId);
    }
}