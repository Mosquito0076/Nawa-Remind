package com.websocket.chat.repository;

import com.websocket.chat.entity.RoomSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomSessionRepository extends JpaRepository<RoomSession, Long> {

    RoomSession findBySimpSessionId(String simpSessionId);
}
