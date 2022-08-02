package com.websocket.chat.controller;

import com.websocket.chat.controller.dto.req.RoomReqDto;
import com.websocket.chat.controller.dto.res.RoomResDto;
import com.websocket.chat.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<RoomResDto> room() {
        return roomService.findAllRoom();
    }

    @PostMapping("/room")
    public ResponseEntity<?> createRoom(@RequestParam String name) {
        RoomReqDto roomReqDto = new RoomReqDto();
        roomReqDto.setRoomName(name);
        return new ResponseEntity<RoomResDto>(roomService.createRoom(roomReqDto), HttpStatus.OK);
    }

    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable Long roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<?> roomInfo(@PathVariable Long roomId) {
        return new ResponseEntity<RoomResDto>(roomService.findByRoomId(roomId), HttpStatus.OK);
    }
}

