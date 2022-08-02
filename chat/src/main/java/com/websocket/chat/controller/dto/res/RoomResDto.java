package com.websocket.chat.controller.dto.res;


import com.websocket.chat.entity.Room;
import lombok.Getter;

@Getter
public class RoomResDto {

    private Long roomId;
    private String roomName;

    public RoomResDto(Room room) {
        this.roomId = room.getRoomId();
        this.roomName = room.getRoomName();
    }
}
