package com.websocket.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reportId", columnDefinition = "int")
    private Long reportId;

    @Column(name = "reportFrom", nullable = false, columnDefinition = "varchar(20)")
    private String reportFrom;

    @Column(name = "reportTo", nullable = false, columnDefinition = "varchar(20)")
    private String reportTo;

    @Column(name = "reportContent", nullable = false, columnDefinition = "varchar(600)")
    private String reportContent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reportDate", nullable = false, columnDefinition = "timestamp")
    private Date reportDate;

}
