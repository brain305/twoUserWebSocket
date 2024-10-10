package com.chat.twouserwebsocket.repository;

import com.chat.twouserwebsocket.model.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {

    public UserDTO login(UserDTO userDTO);
    public void addUser(UserDTO userDTO);

}
