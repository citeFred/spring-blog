package com.yzpocket.blog.repository;

import com.yzpocket.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 회원 가입 시 이름 중복 확인
    Optional<User> findByUsername(String username);

    Optional<User> findByUserId(String userId);
}
