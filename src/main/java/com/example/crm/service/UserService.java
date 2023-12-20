package com.example.crm.service;

import com.example.crm.model.ERole;
import com.example.crm.model.Role;
import com.example.crm.model.User;
import com.example.crm.repository.RoleRepository;
import com.example.crm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
        public void initRoleAndUser(){

            if (!roleRepository.existsByName((ERole.ROLE_ADMIN))) {
                Role adminRole = new Role();
                adminRole.setName(ERole.ROLE_ADMIN);
                roleRepository.save(adminRole);

                Role employeeRole = new Role();
                employeeRole.setName(ERole.ROLE_Employee);
                roleRepository.save(employeeRole);

                Role userRole = new Role();
                userRole.setName(ERole.ROLE_USER);
                roleRepository.save(userRole);
            }

    }

    public List<User> getAllUsers() {
            return userRepository.findAll();
    }

    public User getUserById(Long id) {
            if(userRepository.existsById(id)){
                return userRepository.findUserById(id);
            }
            return null;
    }

    public void deleteUser(Long id) {
            userRepository.deleteById(id);
    }

    public User updateUser(Long userId, String firstname,String lastname, String email, String password, String phone, String address, MultipartFile profilePicture) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setEmail(email);
            user.setAddress(password);
            user.setPassword(getEncodedPassword(password));
            user.setPhone(phone);


            if (profilePicture != null && !profilePicture.isEmpty()) {
                String profilePicturePath = saveProfilePicture(profilePicture);
                user.setProfilePicture(profilePicturePath);
            }

            return userRepository.save(user);
        }

        return null;
    }
    private String saveProfilePicture(MultipartFile profilePicture) {
        String fileName = UUID.randomUUID().toString() + "_" + profilePicture.getOriginalFilename();
        String filePath = "E:/DigitalBox-backend/src/main/resources/profilePictures" + fileName;

        try {
            Files.copy(profilePicture.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            return filePath;
        } catch (IOException e) {
            return null;
        }
    }

    public String getEncodedPassword(String password){
        return passwordEncoder.encode(password);
    }

}
