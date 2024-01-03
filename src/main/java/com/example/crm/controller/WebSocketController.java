package com.example.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(origins = "http://localhost:4200") // Allow requests from Angular app

public class WebSocketController {

    private final SimpMessagingTemplate template;
    @Autowired
    WebSocketController(SimpMessagingTemplate template){
        this.template = template;
    }
//    for group chart implementation
    @MessageMapping("/send/message")
    public void sendMessage(String message){
        System.out.println(message);
        this.template.convertAndSend("/message",  message);
    }

//    @MessageMapping("/send/message")
//    @SendToUser("/queue/message") // Send the message to the user's specific destination
//    public String sendMessage(String message) {
//        System.out.println(message);
//        return message;
//    }


}