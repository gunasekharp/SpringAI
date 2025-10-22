package com.example.ProjectAI.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatMemoryChatClient {

    @Bean
    ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository){
       return MessageWindowChatMemory.builder().maxMessages(10)
                .chatMemoryRepository(jdbcChatMemoryRepository).build();
    }

    @Bean(name="chatMemoryClient")
    public ChatClient chatClient(ChatClient.Builder chatClient, ChatMemory chatMemory
           // , RetrievalAugmentationAdvisor retrievalAugmentationAdvisor
    ){
        Advisor loggerAdvisor=new SimpleLoggerAdvisor();
        Advisor memoryAdvisor= MessageChatMemoryAdvisor.builder(chatMemory).build();
        return chatClient.defaultAdvisors(List.of(loggerAdvisor,memoryAdvisor
                       // ,retrievalAugmentationAdvisor
                ))
                .build();
    }

//    @Bean
//    RetrievalAugmentationAdvisor retrievalAugmentationAdvisor(VectorStore vectorStore){
//        return RetrievalAugmentationAdvisor.builder()
//                .documentRetriever(VectorStoreDocumentRetriever.builder()
//                        .vectorStore(vectorStore).topK(3).similarityThreshold(0.5).build())
//                .build();
//    }
}
