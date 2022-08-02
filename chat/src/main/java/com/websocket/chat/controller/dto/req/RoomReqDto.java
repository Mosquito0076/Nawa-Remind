package com.websocket.chat.controller.dto.req;


import com.websocket.chat.entity.Room;
import lombok.Getter;

@Getter
public class RoomReqDto {

    private String roomName;

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Room createRoom() {
        return Room.builder()
                .roomName(this.roomName)
                .build();
    }
}
