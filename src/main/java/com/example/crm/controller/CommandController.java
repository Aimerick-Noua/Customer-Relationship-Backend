package com.example.crm.controller;

import com.example.crm.model.Command;
import com.example.crm.service.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/commands")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CommandController {
    private final CommandService commandService;

    @GetMapping
    public List<Command> getAllCommands(){
        return commandService.getAllCommands();
    }
    @GetMapping("/{id}")
    public Command getCommandById(@PathVariable Integer id){
        return commandService.getCommandById(id);
    }
    @PostMapping()
    public Command addCommand(@RequestBody Command command){
        return commandService.addCommand(command);
    }
    @PutMapping("/{id}")
    public Command updateCommand(@PathVariable Integer id, @RequestBody Command command){
        return commandService.updateCommand(id,command);
    }

    @DeleteMapping("/{id}")
    public void deleteCommand(@PathVariable Integer id){
        commandService.deleteCommand(id);
    }
}
