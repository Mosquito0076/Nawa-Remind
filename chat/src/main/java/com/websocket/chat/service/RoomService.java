package com.websocket.chat.service;

import com.websocket.chat.controller.dto.req.RoomReqDto;
import com.websocket.chat.controller.dto.res.RoomResDto;
import com.websocket.chat.entity.Room;
import com.websocket.chat.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomResDto createRoom(RoomReqDto roomDto){
        Room room = roomRepository.save(roomDto.createRoom());
        RoomResDto roomResDto = new RoomResDto(room);
        return roomResDto;
    }

    public List<RoomResDto> findAllRoom() {
        List<Room> chatRooms = roomRepository.findAll();
        return chatRooms.stream().map(RoomResDto::new).collect(Collectors.toList());
    }


    public RoomResDto findByRoomId(Long roomId) {
        Room chatRooms = roomRepository.findById(roomId).get();
        return new RoomResDto(chatRooms);
    }
}
