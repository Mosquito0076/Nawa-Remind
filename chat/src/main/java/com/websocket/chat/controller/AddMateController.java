package com.websocket.chat.controller;

import com.websocket.chat.controller.dto.req.AddMateReqDto;
import com.websocket.chat.controller.dto.res.AddMateResDto;
import com.websocket.chat.service.AddMateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/add-mate")
public class AddMateController {

    private final AddMateService addMateService;


    @PostMapping
    public ResponseEntity<?> addMate(AddMateReqDto addMateReqDto) {
        addMateService.addMate(addMateReqDto);
        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> addMateList(String userId) {
        List<AddMateResDto> addMateList =  addMateService.findAddMateList(userId);
        return new ResponseEntity<List<AddMateResDto>>(addMateList, HttpStatus.OK);
    }

//    @PutMapping
//    public ResponseEntity<?> submitMate(Long addMateId) {
//
//    }


}
