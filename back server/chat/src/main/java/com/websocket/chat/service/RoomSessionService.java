package com.websocket.chat.service;

import com.websocket.chat.entity.RoomSession;
import com.websocket.chat.repository.RoomSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomSessionService {

    private final RoomSessionRepository roomSessionRepository;

    @Transactional
    public void saveSession(String simpSessionId, Long roomId) {
        roomSessionRepository.save(RoomSession.builder().simpSessionId(simpSessionId).roomId(roomId).build());
    }

    @Transactional
    public Long deleteSession(String simpSessionId) {
        RoomSession roomSession = roomSessionRepository.findBySimpSessionId(simpSessionId);
        if (roomSession != null) {
            roomSessionRepository.delete(roomSession);
            return roomSession.getRoomId();
        } else {
            return 0l;
        }
    }
}
