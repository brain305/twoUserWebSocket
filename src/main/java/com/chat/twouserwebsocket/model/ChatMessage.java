package com.chat.twouserwebsocket.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "chatMessages")
@Data
public class ChatMessage {

    @Id
    private String id;
    private Long chatRoomId;
    private String sender;
    private String receiver;
    private String content;
    private String timestamp;

}
