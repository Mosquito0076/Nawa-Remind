package com.websocket.chat.service;


import com.websocket.chat.controller.dto.req.ReportReqDto;
import com.websocket.chat.entity.Users;
import com.websocket.chat.repository.ReportRepository;
import com.websocket.chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReportService {

    private final ReportRepository reportRepository;
    private final BlockService blockService;
    private final UserRepository userRepository;
    private final SimpMessageSendingOperations messaging;

    @Transactional
    public Map<String, Integer> reported(ReportReqDto reportReqDto) {
        Users user = userRepository.findById(reportReqDto.getReportTo()).get();
        Optional<Users> me = userRepository.findById(reportReqDto.getReportFrom());

        Map<String, Integer> response = new HashMap<>();

        if (user == null) {
            response.put("result", 400);
        } else if (me.isEmpty()) {
            response.put("result", 401);
        } else if (reportReqDto.getReportTo().equals(reportReqDto.getReportFrom())) {
            response.put("result", 410);
        } else if (reportRepository.findByReportFromAndReportTo(reportReqDto.getReportFrom(), reportReqDto.getReportTo()).isPresent()) {
            response.put("result", 403);
        } else {
            reportRepository.save(reportReqDto.reported());

            // 유저 정보 업데이트
            int reportCount = user.getReportCount() + 1;
            Calendar date = Calendar.getInstance();

            // 신고 누적이 5회면 일주일 정지, 10회면 20년(영구) 정지
            if (reportCount == 5) {
                date.add(Calendar.DATE, 7);
            } else if (reportCount == 10) {
                date.add(Calendar.YEAR, 10);
            }

            // 유저 정보 업데이트

            Map<String, String> message = new HashMap<>();
            message.put("chatUserId", "reported");
            message.put("detail", "신고 회수 누적으로 임시 사용 정지 되었습니다.");
            Map<String, Map> data = new HashMap<>();
            data.put("data", message);
            messaging.convertAndSend("/sub/chat/user/" + user.getUserId(), data);


            response.put("result", 200);
        }
        return response;
    }
}
