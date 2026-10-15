 
package com.codegnan.app.ecommercebackendmain.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AIServiceImpl implements AIService {

    private final ChatClient chatClient;

    public AIServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public String getAnswer(String prompt) {
        return chatClient.prompt()
                .system("You are ShopEase AI assistant. Help customers with shopping, products, orders, and general questions. Be friendly and concise.")
                .user(prompt)
                .call()
                .content();
    }
}
