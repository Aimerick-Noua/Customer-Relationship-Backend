package com.example.crm.service;

import com.example.crm.model.*;
import com.example.crm.payload.Request.UserRequest;
import com.example.crm.repository.CommandRepository;
import com.example.crm.repository.RoleRepository;
import com.example.crm.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProductService productService;
    private final CommandRepository commandRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
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
            }
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToClientDto)
                .collect(Collectors.toList());
    }


    public User getUserByIdWithCommandsAndProducts(Long id) {
        return userRepository.findById(id)
                .map(this::mapToClientDtoForUserId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    public User mapToClientDtoForUserId(User client) {
        List<Command> commandDtos = client.getCommands().stream()
                .map(this::mapToCommandDto)
                .collect(Collectors.toList());
        client.setCommands(commandDtos);
        return client;
    }


    public void deleteUser(Long id) {
            userRepository.deleteById(id);
    }

    public User updateUser(Long userId, UserRequest userRequest) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            user.setFirstname(userRequest.getFirstname());
            user.setLastname(userRequest.getLastname());
            user.setAddress(userRequest.getAddress());
            user.setPhone(userRequest.getPhone());
            return userRepository.save(user);
        }

        return null;
    }


public List<User> getAllEmployees(){

    List<User> employees =  userRepository.findByRoles(ERole.ROLE_EMPLOYEE);
    return employees.stream()
            .map(this::mapToClientDto)
            .collect(Collectors.toList());
}
    public List<User> getAllClientsWithCommandsAndProducts() {
        List<User> clients =  userRepository.findByRoles(ERole.ROLE_USER);
        return clients.stream()
                .map(this::mapToClientDto)
                .collect(Collectors.toList());
    }
    private User mapToClientDto(User client) {
        User clientDto = new User();
        clientDto.setId(client.getId());
        clientDto.setFirstname(client.getFirstname());
        clientDto.setLastname(client.getLastname());
        clientDto.setEmail(client.getEmail());
        clientDto.setPhone(client.getPhone());
        clientDto.setRoles(client.getRoles());
        clientDto.setAddress(client.getAddress());
        clientDto.setJoinedDate(client.getJoinedDate());
        List<Command> commandDtos = client.getCommands().stream()
                .map(this::mapToCommandDto)
                .collect(Collectors.toList());
        clientDto.setCommands(commandDtos);
        return clientDto;
    }
    private Command mapToCommandDto(Command command) {
        Command commandDto = new Command();
        commandDto.setId(command.getId());
        commandDto.setStatus(command.getStatus());
        commandDto.setTotalAmount(command.getTotalAmount());
        commandDto.setDateCommand(command.getDateCommand());
        List<Product> productDtos = command.getProducts().stream()
                .map(this::mapToProductDto)
                .collect(Collectors.toList());
        commandDto.setProducts(productDtos);
        return commandDto;
    }

    private Product mapToProductDto(Product product) {
        Product productDto = new Product();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setQuantity(product.getQuantity());
        return productDto;
    }

    // Inside the ClientService class
    @Transactional
    public void saveCommandWithProducts(Long clientId, Command command) {
        List<User> userList = userRepository.findByRoles(ERole.ROLE_USER);
        User user = userList.stream()
                .filter(usr -> usr.getId().equals(clientId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientId));

        if (user != null ){
             float calculateTotalAmount =0.00f;
            List<Product> products = command.getProducts();
            for (Product product : products) {
                calculateTotalAmount+=product.getPrice();
                product.setCommand(command);
                productService.addProduct(product);
            }
            command.setUser(user);
            command.setTotalAmount(calculateTotalAmount);
            command.setStatus(Status.PENDING);
            command.setDateCommand(LocalDate.now());
            commandRepository.save(command);
        } else {
            throw new RuntimeException("User with id " + clientId + " is not a client");
        }
    }

}
