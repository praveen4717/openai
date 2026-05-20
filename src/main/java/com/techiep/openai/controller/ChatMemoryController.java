package com.techiep.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ai.chat.memory.ChatMemory;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/ai")
public class ChatMemoryController {

    private final ChatClient chatClient;

    public ChatMemoryController(@Qualifier("chatMemoryChatClient") ChatClient chatBuilder) {
        this.chatClient = chatBuilder;
    }

    @GetMapping("/chat-memory")
    public ResponseEntity<String> chatMmory(@RequestHeader("username") String username,
            @RequestParam("message") String message){
        return ResponseEntity
                .ok(chatClient.prompt().user(message).advisors(
                        advisorSpec -> advisorSpec.param(CONVERSATION_ID,username)
                        )
                        .call().content());
    }


}
