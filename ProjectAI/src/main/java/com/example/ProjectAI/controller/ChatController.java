package com.example.ProjectAI.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient){
        this.chatClient=chatClient;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("msg") String msg){
        return chatClient.prompt().user(msg)
                .call().content();
    }
}
