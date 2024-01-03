package com.example.crm.service;

import com.example.crm.model.Command;
import com.example.crm.repository.CommandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommandService {
    private final CommandRepository repository;
    public List<Command> getAllCommands() {
        return repository.findAll();
    }

    public Command getCommandById(Integer id) {
        return repository.findById(id).orElseThrow(()->new RuntimeException("Command not found for the id :"+id));
    }

    public Command addCommand(Command command) {
        return repository.save(command);
    }

    public Command updateCommand(Integer id, Command command) {
        Command command1 = repository.findById(id).orElseThrow(()-> new RuntimeException("command not found"));
        command1.setTotalAmount(command.getTotalAmount());
        command1.setDateCommand(command.getDateCommand());
        command1.setStatus(command.getStatus());
        command1.setProducts(command.getProducts());
        return repository.save(command1);
    }


    public void deleteCommand(Integer id) {
        repository.deleteById(id);
    }
}
