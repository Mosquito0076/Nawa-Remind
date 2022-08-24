package com.websocket.chat.controller;

import com.websocket.chat.controller.dto.req.ChatReqDto;
import com.websocket.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;


@RequiredArgsConstructor
@Controller
public class ChatController {

    public final ChatService chatService;


    @MessageMapping("/chat/message")
    public void message(@RequestBody ChatReqDto message) {
        chatService.saveChat(message);
    }

    @GetMapping("/chat/message/user/{userId}")
    public ResponseEntity<?> findAllUserMessage(@PathVariable String userId) {
        Map<String, ?> response = chatService.findByUserId(userId);
        if (response.get("result").equals(false)) {
            return new ResponseEntity<>(false, HttpStatus.valueOf(401));
        } else {
            return new ResponseEntity<>(response.get("result"), HttpStatus.OK);
        }
    }

}
