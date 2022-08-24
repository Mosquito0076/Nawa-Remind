package com.websocket.chat.service;


import com.websocket.chat.controller.dto.req.CalReqDto;
import com.websocket.chat.controller.dto.res.CalResDto;
import com.websocket.chat.entity.Calendar;
import com.websocket.chat.entity.Users;
import com.websocket.chat.repository.CalenderRepository;
import com.websocket.chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalendarService {

    private final CalenderRepository calenderRepository;
    private final UserRepository userRepository;

    @Transactional
    public Map<String, ?> createTodo(CalReqDto calReqDto) {
        Users user = userRepository.findByUserId(calReqDto.getUserId());
        Calendar calendar = calenderRepository.findByCalDateAndUsers(calReqDto.getCalDate(), user);
        if (user == null) {
            Map<String, Integer> response = new HashMap<>();
            response.put("result", 401);
            return response;
        } else if (calendar == null) {
            calenderRepository.save(calReqDto.saveTodo(user));
            Map<String, Map> response = new HashMap<>();
            Map<String, List> calendars = new HashMap<>();
            calendars.put("calendars", calenderRepository.findByUsers(user).stream().map(CalResDto::new).collect(Collectors.toList()));
            response.put("result", calendars);
            return response;
        } else {
            Map<String, Integer> response = new HashMap<>();
            response.put("result", 403);
            return response;
        }
    }

    public Map<String, ?> findTodo(String userId) {
        Optional<Users> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            Map<String, Boolean> response = new HashMap<>();
            response.put("result", false);
            return response;
        } else {
            Map<String, Map> response = new HashMap<>();
            Map<String, List> calendars = new HashMap<>();
            calendars.put("calendars", calenderRepository.findByUsers(user.get()).stream().map(CalResDto::new).collect(Collectors.toList()));
            response.put("result", calendars);
            return response;
        }
    }

    @Transactional
    public Map<String, ?> updateTodo(CalReqDto calReqDto) {
        Optional<Users> user = userRepository.findById(calReqDto.getUserId());
        Calendar calendar = calenderRepository.findByCalDateAndUsers(calReqDto.getCalDate(), user.get());
        if (user.isEmpty()) {
            Map<String, Integer> response = new HashMap<>();
            response.put("result", 401);
            return response;
        } else if (calendar != null && calendar.getCalId().equals(calReqDto.getCalId())) {
            calReqDto.updateTodo(user.get());
            Map<String, Map> response = new HashMap<>();
            Map<String, List> calendars = new HashMap<>();
            calendars.put("calendars", calenderRepository.findByUsers(user.get()).stream().map(CalResDto::new).collect(Collectors.toList()));
            response.put("result", calendars);
            return response;
        } else {
            Map<String, Integer> response = new HashMap<>();
            response.put("result", 403);
            return response;
        }
    }

    @Transactional
    public Map<String, ?> deleteTodo(Long calId) {
        Optional<Calendar> Todo = calenderRepository.findById(calId);
        if (!Todo.isPresent()) {
            Map<String, Boolean> response = new HashMap<>();
            response.put("result", false);
            return response;
        } else {
            calenderRepository.delete(Todo.get());
            Map<String, Map> response = new HashMap<>();
            Map<String, List> calendars = new HashMap<>();
            calendars.put("calendars", calenderRepository.findByUsers(Todo.get().getUsers()).stream().map(CalResDto::new).collect(Collectors.toList()));
            response.put("result", calendars);
            return response;
        }
    }
}
