package com.chat.twouserwebsocket.service;

import com.chat.twouserwebsocket.model.ChatDTO;
import com.chat.twouserwebsocket.model.UserDTO;
import com.chat.twouserwebsocket.repository.UserDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDAO userDAO;



    @Override
    public boolean login(UserDTO userDTO, HttpServletRequest request) throws IOException {
        UserDTO checkExistsId = userDAO.login(userDTO);
        if (checkExistsId != null && passwordEncoder.matches(userDTO.getUserPw(), checkExistsId.getUserPw())) {
            return true;
            }
            return false;
        }


    @Override
    public void addUser(UserDTO userDTO) {
        userDTO.setUserPw(passwordEncoder.encode(userDTO.getUserPw()));
        userDAO.addUser(userDTO);
    }
}
