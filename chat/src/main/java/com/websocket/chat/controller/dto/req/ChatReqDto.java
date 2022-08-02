package com.websocket.chat.controller.dto.req;

import com.websocket.chat.entity.Chat;
import com.websocket.chat.entity.Room;
import lombok.Getter;

import java.util.Date;

@Getter
public class ChatReqDto {

    private Long roomId;

    private String userName;
    private String chatContent;
    private Date chatDate;


    public Chat saveChat(Room room) {

        return Chat.builder()
                .roomId(room)
                .userName(this.userName)
                .chatContent(this.chatContent)
                .chatDate(this.chatDate)
                .build();
    }
}
