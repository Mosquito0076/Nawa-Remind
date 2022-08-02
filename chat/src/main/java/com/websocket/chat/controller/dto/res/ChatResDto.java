package com.websocket.chat.controller.dto.res;

import com.websocket.chat.entity.Chat;
import lombok.Getter;

import java.util.Date;

@Getter
public class ChatResDto {

    private Long chatId;
    private String userName;
    private String chatContent;
    private Date chatDate;

    public ChatResDto(Chat chat) {
        this.chatId = chat.getChatId();
        this.userName = chat.getUserName();
        this.chatContent = chat.getChatContent();
        this.chatDate = chat.getChatDate();
    }
}
