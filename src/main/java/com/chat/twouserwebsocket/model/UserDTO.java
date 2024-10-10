package com.chat.twouserwebsocket.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private long userId;
    private String userNm;
    private String userPw;
    private Date createdAt;
}
