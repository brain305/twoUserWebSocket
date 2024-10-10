package com.chat.twouserwebsocket.controller;

import com.chat.twouserwebsocket.model.ChatDTO;
import com.chat.twouserwebsocket.model.ChatMessage;
import com.chat.twouserwebsocket.repository.ChatDAO;
import com.chat.twouserwebsocket.service.ChatService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;



    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/chat/{roomId}")
    public ChatMessage sendMessage(@DestinationVariable Long roomId, ChatMessage chatMessage) {
        chatMessage.setChatRoomId(roomId);
        return chatService.saveChatMessage(chatMessage);
    }

    @GetMapping("/chat/{roomId}")
    public String getChatRoom(@PathVariable Long roomId, Model model) {
        List<ChatMessage> messages = chatService.getChatMessages(roomId);
        model.addAttribute("messages", messages);
        model.addAttribute("chatDTO", chatService.getChatRoom(roomId));
        return "index1";
    }

}
