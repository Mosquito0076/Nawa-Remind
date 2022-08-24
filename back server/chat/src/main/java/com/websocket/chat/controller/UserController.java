package com.websocket.chat.controller;


import com.websocket.chat.controller.dto.UserDto;
import com.websocket.chat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public void signup(@RequestBody UserDto userDto) {
        userService.signup(userDto);
    }
}
