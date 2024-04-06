package com.example.crm.controller;

import com.example.crm.model.Command;
import com.example.crm.model.User;
import com.example.crm.payload.Request.UserInformation;
import com.example.crm.payload.Request.UserRequest;
import com.example.crm.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        return userService.getClientById(userId);
    }
    @GetMapping("/clients/{userId}")
    public User getClientsById(@PathVariable Long userId){
        return userService.getClientById(userId);
    }

    @PutMapping(path = "/{userId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public User updateUserProfile( @PathVariable Long userId,
                                   @RequestPart("firstname") String firstname,
                                   @RequestPart("lastname") String lastname,
                                   @RequestPart("phone") String phone,
                                   @RequestPart("address") String address,
                                  @RequestPart(value="profilePicture", required = false) MultipartFile profilePicture) throws IOException {

        User updatedUser = userService.updateUser(userId, firstname,lastname,phone,address, profilePicture);
    return  updatedUser;
    }
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId){
         userService.deleteUser(userId);
    }

    @GetMapping("/clients")
    public List<User> getAllClientsWithCommands(){
        return userService.getAllClientsWithCommands();
    }
    @GetMapping("/clients/commands")
    public List<Command> getAllCommands(){
        return  userService.getAllCommands();
    }
    @GetMapping("/employees")
    public List<User> getAllEmployees(){
        return userService.getAllEmployees();
    }

    @PostMapping("/clients/{id}")
    public void addCommandsByClientsId(@PathVariable Long id,@RequestBody Command command){
        userService.addCommandsByClientsId(id,command);
    }
}
