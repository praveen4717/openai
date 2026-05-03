package com.techiep.openai;

import com.techiep.openai.controller.TokenUsageAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder){
        ChatOptions chatOptions = ChatOptions.builder()
                .model("gpt-5.4-nano")
                .maxTokens(100)
                .temperature(0.8)
                .build();

        return chatClientBuilder
                .defaultOptions(chatOptions)
                .defaultAdvisors(new TokenUsageAdvisor())
                .build();
    }
}
