package com.chat.twouserwebsocket.repository;

import com.chat.twouserwebsocket.model.ChatDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatDAO {

    public void createChatRoom(ChatDTO chatDTO);
    public List<ChatDTO> getChatList();
    public ChatDTO getChatRoom(Long roomId);
}
