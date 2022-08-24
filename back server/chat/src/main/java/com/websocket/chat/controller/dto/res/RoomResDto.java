package com.websocket.chat.controller.dto.res;


import com.websocket.chat.entity.Room;
import com.websocket.chat.entity.Users;
import lombok.Getter;

@Getter
public class RoomResDto {

    private Long roomId;
    private String roomUserId = null;
    private String roomNickName = null;


    public RoomResDto(Room room, Users user) {
        this.roomId = room.getRoomId();
        if (user != null) {
            this.roomUserId = user.getUserId();
            this.roomNickName = user.getNickname();
        }
        // 유저 사진
    }
}
