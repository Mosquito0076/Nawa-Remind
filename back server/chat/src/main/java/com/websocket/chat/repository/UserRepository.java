package com.websocket.chat.repository;

import com.websocket.chat.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, String> {

    Users findByUserId(String userId);

}
