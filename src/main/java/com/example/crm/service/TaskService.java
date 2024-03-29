package com.example.crm.service;


import com.example.crm.exceptions.UserNotFoundException;
import com.example.crm.model.ERole;
import com.example.crm.model.Task;
import com.example.crm.model.TaskStatus;
import com.example.crm.model.User;
import com.example.crm.repository.TaskRepository;
import com.example.crm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    public List<Task> getAllTasksByUserId(Long userId) {
        return taskRepository.findAllByUserId(userId);
    }


    public Task saveTask(Long id,Task task) {
        Task task1 = new Task();
        User user = userRepository.findById(id).orElseThrow(()->new RuntimeException("failed to get user for this task"));
        if (user!=null) {
            task1.setStatus(TaskStatus.NOT_STARTED);
            task1.setDate_limit(task.getDate_limit());
            task1.setDescription(task.getDescription());
            task1.setSent_date(LocalDate.now());
            task1.setUser(user);
            taskRepository.save(task1);
            return task1;
        }
        return null;
    }

    public List<User> getAllEmployeesWithTasks() {
        List<User> employees = userRepository.findByRoles(ERole.ROLE_EMPLOYEE);
        employees.forEach(employee -> employee.setTasks(employee.getTasks())); // Ensure tasks are eagerly fetched
        return employees;
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public Task updateTask(Long id, Task task) {
        Task task1 = taskRepository.findById(id).orElseThrow(()->new RuntimeException("task with not found with id "+id));
        if(task1 !=null) {
            task1.setStatus(task.getStatus());
            return taskRepository.save(task1);
        }
        return null;

    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(()->new RuntimeException("task not found with id "+id));
    }
}
