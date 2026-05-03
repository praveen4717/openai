package com.techiep.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/ai")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatBuilder) {
         this.chatClient = chatBuilder.build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message){
        return chatClient
                .prompt(message)
                .options(OpenAiChatOptions.builder()
                        .model("gpt-4o-mini")
                        .temperature(0.8)
                        .maxTokens(100)
                        .build())
                .advisors(Arrays.asList(
                        new SimpleLoggerAdvisor()
                        ,new TokenUsageAdvisor()))
                .call()
                .content();
    }



    @GetMapping("/special-chat")
    public String specialChat(@RequestParam("message") String message){
        return chatClient
                .prompt()
                .system("you are an internal HR assistant. Your role is to help \s" +
                        "employees with questions related to HR Policies. such as \s" +
                        "leave policies, working hours, benefits and code of conduct." +
                        "If a user asks for help with anything outside of these topics.\s " +
                        "Kindly inform them that you can only assist with queries related to HR policies.")
                .user(message)
                .call()
                .content();
    }
}
