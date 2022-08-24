package com.websocket.chat.repository;


import com.websocket.chat.entity.Block;
import com.websocket.chat.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block, Long> {

    Optional<Block> findByBlockFromAndBlockTo(Users blockFrom, Users blockTo);
    List<Block> findByBlockFrom(Users user);
}
