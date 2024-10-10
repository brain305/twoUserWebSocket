package com.chat.twouserwebsocket.repository;


import com.chat.twouserwebsocket.model.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByChatRoomId(Long chatRoomId);
}
