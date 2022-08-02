package com.websocket.chat.repository;

import com.websocket.chat.entity.AddMate;
import com.websocket.chat.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddMateRepository extends JpaRepository<AddMate, Long> {

    List<AddMate> findByAddMateTo(Users user);
}
