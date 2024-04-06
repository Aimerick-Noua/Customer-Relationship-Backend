package com.example.crm.service;

import com.example.crm.exceptions.UserNotFoundException;
import com.example.crm.model.*;
import com.example.crm.repository.CommandRepository;
import com.example.crm.repository.RoleRepository;
import com.example.crm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ImageService imageService;
    private final CommandRepository commandRepository;
    private final PasswordEncoder passwordEncoder;
        public void initRoleAndUser(){

            if (!roleRepository.existsByName((ERole.ROLE_ADMIN))) {
                Role adminRole = new Role();
                adminRole.setName(ERole.ROLE_ADMIN);
                roleRepository.save(adminRole);

                Role employeeRole = new Role();
                employeeRole.setName(ERole.ROLE_EMPLOYEE);
                roleRepository.save(employeeRole);

                Role userRole = new Role();
                userRole.setName(ERole.ROLE_USER);
                roleRepository.save(userRole);


                User admin = new User();
                admin.setPassword(passwordEncoder.encode("000000"));
                admin.setFirstname("admin");
                admin.setLastname("admin");
                admin.setEmail("admin@gmail.com");
                admin.setUsername("admin@gmail.com");
                Set<Role> adminRoles = new HashSet<>();
                adminRoles.add(adminRole);
                admin.setRoles(adminRoles);
                userRepository.save(admin);

                User user1 = new User();
                user1.setPassword(passwordEncoder.encode("00000000"));
                user1.setFirstname("Francis");
                user1.setLastname("Ngannou");
                user1.setEmail("francis@gmail.com");
                user1.setUsername("francis@gmail.com");
                Set<Role> userRoles = new HashSet<>();
                userRoles.add(userRole);
                user1.setRoles(userRoles);
                user1.setJoinedDate(LocalDate.of(2023,7,22));
                userRepository.save(user1);
            }
    }

    public List<User> getAllUsers() {
        return  userRepository.findAll();
    }


    public User getClientById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User not found"));
    }


    public void deleteUser(Long id) {
            userRepository.deleteById(id);
    }



public List<User> getAllEmployees(){
   List<User> user = userRepository.findByRoles(ERole.ROLE_EMPLOYEE);
   return user;
}




    public List<User> getAllClientsWithCommands() {
        List<User> clients = userRepository.findByRoles(ERole.ROLE_USER);
        return clients;
    }


    public User updateUser(Long userId, String firstname, String lastname, String phone, String address, MultipartFile profilePicture) {
        User existingUser = userRepository.findById(userId).orElse(null);

        if (existingUser != null) {
            existingUser.setFirstname(firstname);
            existingUser.setLastname(lastname);
            existingUser.setAddress(address);
            existingUser.setPhone(phone);

            // Profile image processing
            if (profilePicture != null && !profilePicture.isEmpty()) {
                try {
                    ImageModel newProfileImage = imageService.storeProfileImage(profilePicture);
                    existingUser.setProfilePicture(newProfileImage);

                    // Delete existing profile image if one exists (optional)
                    if (existingUser.getProfilePicture() != null) {
                        imageService.deleteImage(existingUser.getProfilePicture().getId());
                    }
                } catch (IOException e) {
                    // Handle potential IO exceptions during image processing
                    e.printStackTrace();
                    throw new RuntimeException("Error saving profile image: " + e.getMessage());
                }
            }

            return userRepository.save(existingUser);
        }

        return null;
    }

    public void addTasksToUser(Long userId, List<Task> tasks) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        user.getTasks().addAll(tasks);
        userRepository.save(user);
    }

    // Method to add a single task to a specific user by user ID
    public void addTaskToUser(Long userId, Task task) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        task.setStatus(TaskStatus.NOT_STARTED);
        task.setSent_date(LocalDate.now());
        task.setUser(user);
        addTasksToUser(userId, Collections.singletonList(task));
    }
    public void addCommandToUser(Long userId, List<Command> commands) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        user.getCommands().addAll(commands);
        userRepository.save(user);
    }

    // Method to add a single task to a specific user by user ID
    public void addCommandsByClientsId(Long userId,Command command) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        command.setStatus(Status.PENDING);
        command.setDateCommand(LocalDate.now());
        command.setAddress(command.getAddress());
        command.setDescription(command.getDescription());
        command.setPhone(command.getPhone());
        command.setTitle(command.getTitle());
        command.setUser(user);
        commandRepository.save(command);
        addCommandToUser(userId, Collections.singletonList(command));
    }

    public List<Command> getAllCommands() {
            return commandRepository.findAll();
    }
}
