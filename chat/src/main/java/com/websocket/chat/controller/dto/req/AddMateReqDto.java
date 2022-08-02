package com.websocket.chat.controller.dto.req;

import com.websocket.chat.entity.AddMate;
import com.websocket.chat.entity.Users;
import lombok.Getter;


@Getter
public class AddMateReqDto {

    private String addMateFrom;
    private String addMateTo;


    public AddMate addMate(Users addMateFrom, Users addMateTo) {
        return AddMate.builder()
                .addMateFrom(addMateFrom)
                .addMateTo(addMateTo)
                .build();
    }
}
