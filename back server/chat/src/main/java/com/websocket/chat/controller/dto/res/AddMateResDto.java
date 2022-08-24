package com.websocket.chat.controller.dto.res;

import com.websocket.chat.entity.AddMate;
import lombok.Getter;

@Getter
public class AddMateResDto {
    private Long addMateId;
    private String addMateFrom;
    private String nickname;

    public AddMateResDto(AddMate addMate) {
        this.addMateId = addMate.getAddMateId();
        this.addMateFrom = addMate.getAddMateFrom().getUserId();
        this.nickname = addMate.getAddMateFrom().getNickname();
        // 유저 사진 필요
    }
}
