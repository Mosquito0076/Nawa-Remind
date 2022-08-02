package com.websocket.chat.service;


import com.websocket.chat.controller.dto.req.ChatReqDto;
import com.websocket.chat.controller.dto.res.ChatResDto;
import com.websocket.chat.entity.Chat;
import com.websocket.chat.repository.ChatRepository;
import com.websocket.chat.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final RoomRepository roomRepository;

    public void saveChat(ChatReqDto message) {
        chatRepository.save(message.saveChat(roomRepository.findById(message.getRoomId()).get()));
    }

    public List<ChatResDto> findByRoomId(Long roomId) {
        List<Chat> chats = chatRepository.findByRoomId(roomRepository.findById(roomId).get());
        return chats.stream().map(ChatResDto::new).collect(Collectors.toList());
    }
}