package com.websocket.chat.service;

import com.websocket.chat.controller.dto.req.BlockReqDto;
import com.websocket.chat.controller.dto.res.BlockResDto;
import com.websocket.chat.entity.Block;
import com.websocket.chat.entity.Users;
import com.websocket.chat.repository.AddMateRepository;
import com.websocket.chat.repository.BlockRepository;
import com.websocket.chat.repository.MateRepository;
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
public class BlockService {

    private final BlockRepository blockRepository;
    private final UserRepository userRepository;
    private final MateRepository mateRepository;
    private final AddMateRepository addMateRepository;

    private final MateService mateService;

    @Transactional
    public Map<String, Integer> addBlock(BlockReqDto blockReqDto) {
        Users From = userRepository.findByUserId(blockReqDto.getBlockFrom());
        Users To = userRepository.findByUserId(blockReqDto.getBlockTo());
        Map<String, Integer> response = new HashMap<>();

        // 차단은 한 번만 가능합니다!
        if (From == null) {
            response.put("result", 401);
        } else if (To == null) {
            response.put("result", 400);
        } else if (blockRepository.findByBlockFromAndBlockTo(From, To).isPresent()) {
            response.put("result", 403);
        } else if (blockReqDto.getBlockTo().equals(blockReqDto.getBlockFrom())) {
            response.put("result", 410);
        } else {
            blockRepository.save(blockReqDto.addBlock(From, To));
            response.put("result", 200);

            // 메이트 관계일 경우, 손절까지 도와드립니다.
            mateRepository.findByMateUserId1AndMateUserId2(From, To).ifPresent(mate -> mateService.deleteMate(mate.getMateId(), blockReqDto.getBlockFrom()));
            mateRepository.findByMateUserId1AndMateUserId2(To, From).ifPresent(mate -> mateService.deleteMate(mate.getMateId(), blockReqDto.getBlockFrom()));

            // 신청도 와있을 경우, 삭제해버리기
            addMateRepository.findByAddMateFromAndAddMateTo(From, To).ifPresent(addMateRepository::delete);
            addMateRepository.findByAddMateFromAndAddMateTo(To, From).ifPresent(addMateRepository::delete);

        }
        return response;

    }

    public Map<String, ?> findAllBlockList(String userId) {

        Optional<Users> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            Map<String, Boolean> response = new HashMap<>();
            response.put("result", false);
            return response;
        } else {
            List<Block> allBlockList = blockRepository.findByBlockFrom(user.get());
            Map<String, Map> response = new HashMap<>();
            Map<String, List> blockers = new HashMap<>();
            blockers.put("blockers", allBlockList.stream().map(BlockResDto::new).collect(Collectors.toList()));
            response.put("result", blockers);
            return response;
        }

    }

    @Transactional
    public Map<String, Boolean> deleteBlock(Long blockId) {
        Optional<Block> block = blockRepository.findById(blockId);
        Map<String, Boolean> response = new HashMap<>();
        if (block.isPresent()) {
            blockRepository.delete(block.get());
            response.put("result", true);
        } else {
            response.put("result", false);
        }
        return response;
    }
}
