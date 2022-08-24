package com.websocket.chat.controller.dto.res;


import com.websocket.chat.entity.Block;
import lombok.Getter;

import java.util.Date;

@Getter
public class BlockResDto {
    private Long blockId;
    private String blockTo;
    private String blockNickname;
    private String blockMemo;
    private Date blockDate;

    public BlockResDto(Block block) {
        this.blockId = block.getBlockId();
        this.blockTo = block.getBlockTo().getUserId();
        this.blockNickname = block.getBlockTo().getNickname();
        this.blockMemo = block.getBlockMemo();
        this.blockDate = block.getBlockDate();
        // 프사 추가
    }

}
