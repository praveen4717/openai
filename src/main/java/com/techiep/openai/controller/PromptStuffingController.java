package com.techiep.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PromptStuffingController {

    private final ChatClient chatClient;

    public PromptStuffingController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @Value("classpath:/promptTemplates/systemPromptTemplate.st")
    Resource systemPromptTemplate;


    @GetMapping("/prompt-stuff")
    public  String promptStuffing(@RequestParam("message") String message){
        ChatOptions chatOptions = ChatOptions.builder()
                .model("gpt-5.4-nano")
                .maxTokens(100)
                .temperature(0.8)
                .build();

        return chatClient
                .prompt()
                .system(systemPromptTemplate)
                .user(message)
                .call()
                .content();
    }

}
