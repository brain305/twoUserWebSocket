package com.chat.twouserwebsocket.service;

import com.chat.twouserwebsocket.model.ChatDTO;
import com.chat.twouserwebsocket.model.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface UserService {
    public boolean login(UserDTO userDTO, HttpServletRequest request) throws IOException;
    public void addUser(UserDTO userDTO);
    }
