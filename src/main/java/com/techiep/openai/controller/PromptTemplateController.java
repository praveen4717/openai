package com.techiep.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prompt")
public class PromptTemplateController {

    @Value("classpath:/promptTemplates/userPromptTemplate.st")
    Resource userPromptTemplate;

    private final ChatClient chatClient;

    public PromptTemplateController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }



    @GetMapping("/email")
    public String specialChat(@RequestParam("customerName") String customerName,
                              @RequestParam("customerMessage") String customerMessage){
        return chatClient
                .prompt()
                .system("you are an professional customer service assistant which helps drafting email" +
                        "responses to improve the productive of the customer support team")
                .user(promptUserSpec -> promptUserSpec
                        .text(userPromptTemplate).
                        param("customerName",customerName)
                        .param("customerMessage", customerName)
                )
                .call()
                .content();
    }
}
