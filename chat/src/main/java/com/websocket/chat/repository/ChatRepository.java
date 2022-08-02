package com.websocket.chat.repository;

import com.websocket.chat.entity.Chat;
import com.websocket.chat.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findByRoomId(Room room);
}
