package com.example.ProjectAI.config;

import com.example.ProjectAI.Advisors.TokenUsageAuditAdvisor;
import com.example.ProjectAI.rag.WebSearchDocumentRetriever;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
public class WebSearchRagChatClient {

    @Bean("WebChatClient")
    public ChatClient webChat(ChatClient.Builder chatClient, ChatMemory chatMemory,
                              RestClient.Builder restClient) {
        Advisor loggerAdvisor=new SimpleLoggerAdvisor();
        Advisor tokenAdvisor=new TokenUsageAuditAdvisor();
        Advisor memoryAdvisor= MessageChatMemoryAdvisor.builder(chatMemory).build();
        var webSearchAdvisor= RetrievalAugmentationAdvisor.builder()
                .documentRetriever(WebSearchDocumentRetriever.builder()
                        .maxResults(5).restClientBuilder(restClient).build()).build();
        return chatClient
                .defaultAdvisors(List.of(loggerAdvisor,tokenAdvisor,memoryAdvisor,webSearchAdvisor))
                .build();

    }
}
