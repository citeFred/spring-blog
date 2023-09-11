package com.yzpocket.blog.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // <- Spring Security 지원을 가능하게 함
public class WebSecurityConfig {

    @Bean //<- securityFilterChain 수동등록
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable());

        // AuthFilter가 하던 것을 아래 4줄로 처리하는 중
        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 경로 요청 접근 허용 설정
                        .requestMatchers("/api/user/**").permitAll() // 해당 경로의 모든 것들을 권한허용 | .hasRole() 특정 Role에따라 권한 허용 등 여러 옵션이 있다.
                        .anyRequest().authenticated() // 그 외 모든 요청 인증처리
        );

        // 로그인 사용
        http.formLogin(Customizer.withDefaults());

        return http.build();
    }
}