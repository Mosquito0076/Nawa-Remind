package com.websocket.chat.controller.dto;

import com.websocket.chat.entity.Users;
import lombok.Getter;

@Getter
public class UserDto {

    private String userId;
    private String password;
    private String nickname;

    public Users saveUser() {
        return Users.builder()
                .userId(this.userId)
                .password(this.password)
                .nickname(this.nickname)
                .reportCount(0)
                .build();
    }
}
