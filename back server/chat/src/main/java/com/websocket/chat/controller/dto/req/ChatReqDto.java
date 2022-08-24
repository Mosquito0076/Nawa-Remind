package com.websocket.chat.controller.dto.req;

import com.websocket.chat.entity.Chat;
import com.websocket.chat.entity.Room;
import lombok.Getter;

import java.util.Date;

@Getter
public class ChatReqDto {

    private Long roomId;

    private String chatUserId;
    private String chatContent;

    public Chat saveChat(Room room) {

        return Chat.builder()
                .roomId(room)
                .chatUserId(this.chatUserId)
                .chatContent(this.chatContent)
                .chatDate(new Date())
                .isRead(room.getRoomCount())
                .build();
    }
}
