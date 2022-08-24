package com.websocket.chat.controller.dto.res;

import com.websocket.chat.entity.Calendar;
import lombok.Getter;

import java.util.Date;

@Getter
public class CalResDto {
    private Long calId;
    private String userId;
    private String calContent;
    private Date calDate;

    public CalResDto(Calendar calendar) {
        this.calId = calendar.getCalId();
        this.userId = calendar.getUsers().getUserId();
        this.calContent = calendar.getCalContent();
        this.calDate = calendar.getCalDate();
    }
}
