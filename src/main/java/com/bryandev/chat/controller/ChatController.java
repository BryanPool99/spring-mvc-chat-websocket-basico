package com.bryandev.chat.controller;

import com.bryandev.chat.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChatController {
    @MessageMapping("/sendMessage")//cliente envia aqui-canal de entrada
    @SendTo("/topic/messages")//servidor reenvia a todos-canal de salida
    public Message send(Message message) {
        message.setTimestamp(LocalDateTime.now());
        return message;
    }
}
