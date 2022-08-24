package com.websocket.chat.repository;

import com.websocket.chat.entity.AddMate;
import com.websocket.chat.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddMateRepository extends JpaRepository<AddMate, Long> {

    Optional<AddMate> findByAddMateFromAndAddMateTo(Users user1, Users user2);
    List<AddMate> findAllByAddMateTo(Users user);
}
