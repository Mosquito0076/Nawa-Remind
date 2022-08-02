package com.websocket.chat.service;

import com.websocket.chat.controller.dto.req.AddMateReqDto;
import com.websocket.chat.controller.dto.res.AddMateResDto;
import com.websocket.chat.entity.AddMate;
import com.websocket.chat.entity.Users;
import com.websocket.chat.repository.AddMateRepository;
import com.websocket.chat.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AddMateService {

    private final AddMateRepository addMateRepository;
    private final UsersRepository usersRepository;

    public void addMate(AddMateReqDto addMateReqDto) {
        Users addMateFrom = usersRepository.findById(addMateReqDto.getAddMateFrom()).get();
        Users addMateTo = usersRepository.findById(addMateReqDto.getAddMateTo()).get();
        addMateRepository.save(addMateReqDto.addMate(addMateFrom, addMateTo));
    }

    public List<AddMateResDto> findAddMateList(String userId) {
        List<AddMate> AddMateList = addMateRepository.findByAddMateTo(usersRepository.findById(userId).get());
        return AddMateList.stream().map(AddMateResDto::new).collect(Collectors.toList());
    }
}
