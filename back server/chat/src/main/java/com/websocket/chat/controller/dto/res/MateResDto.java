package com.websocket.chat.controller.dto.res;


import com.websocket.chat.entity.Mate;
import com.websocket.chat.entity.Users;
import lombok.Getter;

@Getter
public class MateResDto {

    private Long mateId;
    private String userid;
    private String nickname;

    public MateResDto(Long mateId, Users user) {
        this.mateId = mateId;
        this.userid = user.getUserId();
        this.nickname = user.getNickname();
        // 이 부분도 유저 사진 추가
    }
}
