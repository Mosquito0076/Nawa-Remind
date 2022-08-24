package com.websocket.chat.service;


import com.websocket.chat.controller.dto.UserDto;
import com.websocket.chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;


    @Transactional
    public void signup(UserDto userDto) {
        userRepository.save(userDto.saveUser());
    }
}
