package com.websocket.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Controller
public class ChatRoomController {


    @GetMapping
    public String index(Model model) {
        return "/chat/main";
    }

    @GetMapping("/chat/room")
    public String rooms(Model model) {
        return "/chat/room";
    }

    @GetMapping("/chat/room/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }

}
