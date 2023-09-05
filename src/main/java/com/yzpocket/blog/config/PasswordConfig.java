package com.yzpocket.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //<-- 스프링서버가 IoC Container에 등록 해줌
public class PasswordConfig { //passwordConfig

    @Bean
    public PasswordEncoder passwordEncoder() { //<- 인터페이스라 구현체 넣어줘야함
        return new BCryptPasswordEncoder(); //<- 인터페이스의 구현체는 BCryptPasswordEncoder()라는 메소드를 사용하는 것으로 추상클래스의 추상메소드를 구체화했다.
        //  Bcrypt란 비밀번호 암호화해주는 해시함수
    }
}