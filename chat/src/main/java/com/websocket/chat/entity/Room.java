package com.websocket.chat.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomId", nullable = false, columnDefinition = "int")
    private Long roomId;

    @NotNull
    private String roomName;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @OnDelete(action = OnDeleteAction.NO_ACTION)
//    @JoinColumn(name = "roomUserId1")
//    @NotNull
//    private Users users1;
//
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @OnDelete(action = OnDeleteAction.NO_ACTION)
//    @JoinColumn(name = "roomUserId2")
//    @NotNull
//    private Users users2;

}
