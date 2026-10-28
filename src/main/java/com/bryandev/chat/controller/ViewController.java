package com.bryandev.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/chat")
    public String chatPage() {
        return "chat"; // Renderiza chat.html
    }
}
