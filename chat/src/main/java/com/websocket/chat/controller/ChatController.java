package com.websocket.chat.controller;

import com.websocket.chat.controller.dto.req.ChatReqDto;
import com.websocket.chat.controller.dto.res.ChatResDto;
import com.websocket.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class ChatController {

    public final ChatService chatService;
    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat/message")
    public void message(@RequestBody ChatReqDto message) {
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
        if (!message.getUserName().equals("system")) {
            chatService.saveChat(message);
        }
    }

    @GetMapping("/chat/message/{roomId}")
    public ResponseEntity<?> findAllMessage(@PathVariable Long roomId) {
        return new ResponseEntity<List<ChatResDto>>(chatService.findByRoomId(roomId), HttpStatus.OK);
    }
}
