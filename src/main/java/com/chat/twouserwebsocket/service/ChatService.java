package com.chat.twouserwebsocket.service;

import com.chat.twouserwebsocket.model.ChatDTO;
import com.chat.twouserwebsocket.model.ChatMessage;
import com.chat.twouserwebsocket.repository.ChatDAO;
import com.chat.twouserwebsocket.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatDAO chatDAO;

    public void createChatRoom(ChatDTO chatDTO){
        chatDAO.createChatRoom(chatDTO);
    }
    public List<ChatDTO> getChatList(){
        return chatDAO.getChatList();
    }

    public ChatDTO getChatRoom(Long roomId) {
        return chatDAO.getChatRoom(roomId);
    }
    public ChatMessage saveChatMessage(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getChatMessages(Long roomId) {
        return chatMessageRepository.findByChatRoomId(roomId);
    }
}
