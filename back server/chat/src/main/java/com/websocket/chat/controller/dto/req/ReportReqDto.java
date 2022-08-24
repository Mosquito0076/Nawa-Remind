package com.websocket.chat.controller.dto.req;

import com.websocket.chat.entity.Report;
import lombok.Getter;

import java.util.Date;

@Getter
public class ReportReqDto {

    private String reportFrom;
    private String reportTo;
    private String reportContent;

    public Report reported() {
        return Report.builder()
                .reportFrom(this.reportFrom)
                .reportTo(this.reportTo)
                .reportContent(this.reportContent)
                .reportDate(new Date())
                .build();
    }
}
