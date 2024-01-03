package com.example.crm.controller;

import com.example.crm.model.Command;
import com.example.crm.model.User;
import com.example.crm.payload.Request.UserRequest;
import com.example.crm.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class UserController {
private final UserService userService;

    @PostConstruct
    public void initRoleAndUsers(){
        userService.initRoleAndUser();
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId){
        return userService.getUserByIdWithCommandsAndProducts(userId);
    }

    @PutMapping("/{userId}")

    public ResponseEntity<User> updateUser(
            @PathVariable Long userId,
            @RequestBody UserRequest userRequest
//            @RequestParam(required = false) MultipartFile profilePicture
    ) {
        User updatedUser = userService.updateUser(userId,userRequest);

        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId){
         userService.deleteUser(userId);
    }

    @GetMapping("/clients")
    public List<User> getAllClientsWithCommandsAndProducts(){
        return userService.getAllClientsWithCommandsAndProducts();
    }

    @PostMapping("/clients/{id}")
    public void addCommandsByClientsId(@PathVariable Long id,@RequestBody Command command){
        userService.saveCommandWithProducts(id,command);
    }
}
