package com.websocket.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class Users {

    // 유저 아이디
    @Id
    @Column(name = "userId", nullable = false, columnDefinition = "varchar(20)")
    private String userId;

    // 비밀번호
    @Column(name = "password", nullable = false, columnDefinition = "varchar(20)")
    private String password;

    @Column(name = "nickname", nullable = false, columnDefinition = "varchar(40)")
    private String nickname;

    @Column(name = "reportCount", nullable = false, columnDefinition = "int")
    private int reportCount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "endDate", columnDefinition = "timestamp")
    private Date endDate;
}
