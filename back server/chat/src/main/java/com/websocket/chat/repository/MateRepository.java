package com.websocket.chat.repository;

import com.websocket.chat.entity.Mate;
import com.websocket.chat.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MateRepository extends JpaRepository<Mate, Long> {

    Optional<Mate> findByMateUserId1AndMateUserId2(Users user1, Users user2);
    List<Mate> findAllByMateUserId1OrMateUserId2(Users user1, Users user2);
}
