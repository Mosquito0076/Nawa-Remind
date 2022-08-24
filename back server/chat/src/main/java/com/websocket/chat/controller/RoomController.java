package com.websocket.chat.controller;

import com.websocket.chat.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class RoomController {

    private final RoomService roomService;


    @GetMapping("/user/{userId}")
    @ResponseBody
    public ResponseEntity<?> findAllRooms(@PathVariable String userId) {
        Map<String, ?> allRooms = roomService.findAllRooms(userId);
        if (allRooms.get("result").equals(false)) {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(allRooms.get("result"), HttpStatus.OK);
        }
    }

}

