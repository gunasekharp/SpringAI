package com.example.ProjectAI.Tools;

import com.example.ProjectAI.entity.HelpDeskTicket;
import com.example.ProjectAI.model.TicketRequest;
import com.example.ProjectAI.service.HelpDeskService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HelpDeskTool {

    private static final Logger logger= LoggerFactory.getLogger(HelpDeskTool.class);

    private final HelpDeskService helpDeskService;

    @Tool(name="createTicket", description = "Create the Support Ticket")
    String createTicket(@ToolParam(description = "Details to create a Support ticket")
                        TicketRequest ticketRequest, ToolContext toolContext){
        String username=(String) toolContext.getContext().get("username");
        logger.info("Creating support ticket for user: {} with details: {}", username,ticketRequest);
        HelpDeskTicket savedTicket=helpDeskService.createTicket(ticketRequest,username);
        logger.info("Ticket created successfully. Ticket ID: {}, Username: {}", savedTicket.getId(), savedTicket.getUsername());
        return "Ticket created successfully";
    }

    @Tool(description = "Fetch the status of the tickets based on a given username")
    List<HelpDeskTicket> fetchTicket(ToolContext toolContext){
        String username = (String) toolContext.getContext().get("username");
        logger.info("Fetching tickets for user: {}", username);
        List<HelpDeskTicket> tickets=helpDeskService.getTicket(username);
        logger.info("Found {} tickets for user: {}", tickets.size(), username);
        return tickets;
    }
}
