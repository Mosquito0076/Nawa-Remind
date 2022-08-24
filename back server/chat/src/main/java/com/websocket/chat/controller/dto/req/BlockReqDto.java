package com.websocket.chat.controller.dto.req;

import com.websocket.chat.entity.Block;
import com.websocket.chat.entity.Users;
import lombok.Getter;

import java.util.Date;

@Getter
public class BlockReqDto {

    private String blockFrom;
    private String blockTo;
    private String blockMemo;

    public BlockReqDto(String blockFrom, String blockTo, String blockMemo) {
        this.blockFrom = blockFrom;
        this.blockTo = blockTo;
        this.blockMemo = blockMemo;
    }

    public Block addBlock(Users From, Users To) {
        return Block.builder()
                .blockFrom(From)
                .blockTo(To)
                .blockMemo(this.blockMemo)
                .blockDate(new Date())
                .build();
    }
}
