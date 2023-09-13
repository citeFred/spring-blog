package com.yzpocket.blog.controller;

import com.yzpocket.blog.dto.LoginRequestDto;
import com.yzpocket.blog.dto.SignupRequestDto;
import com.yzpocket.blog.exception.RestApiException;
import com.yzpocket.blog.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    // 회원가입 API - RestApiException 클래스로 반환 내용 통일시키기 적용
    @PostMapping("/user/signup")
    public ResponseEntity<RestApiException> signup(@RequestBody SignupRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            String password = requestDto.getPassword();

            // 아이디와 비밀번호의 유효성 검사 호출 계층을 (Service->Controller로 옮김) -> 유효성 검사는 사용자 입력을 받은 후, 해당 입력이 처리되기 전에 수행해야 합니다. 따라서 UserController 내에서 아이디와 비밀번호의 유효성 검사를 수행하는 것은 적절한 위치
            if (!userService.isValidUsername(username)) { // <- UserService의 유효성검사 중복제거용 메소드를 호출
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new RestApiException("ID 형태가 부적절합니다.", HttpStatus.BAD_REQUEST.value()));
            }

            if (!userService.isValidPassword(password)) { // <- UserService의 유효성검사 중복제거용 메소드를 호출
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new RestApiException("PW 형태가 부적절합니다.", HttpStatus.BAD_REQUEST.value()));
            }

            userService.signup(requestDto);
            return ResponseEntity.ok(new RestApiException("회원가입 성공", HttpStatus.OK.value()));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new RestApiException("회원가입 실패", HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 로그인 API
    @PostMapping("/user/login")
    public ResponseEntity<RestApiException> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse res) {
        try {
            String username = requestDto.getUsername();
            String password = requestDto.getPassword();

            // 아이디와 비밀번호의 유효성 검사 호출 계층을 (Service->Controller로 옮김)
            if (!userService.isValidUsername(username)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new RestApiException("ID 형태가 부적절합니다.", HttpStatus.BAD_REQUEST.value()));
            }

            if (!userService.isValidPassword(password)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new RestApiException("PW 형태가 부적절합니다.", HttpStatus.BAD_REQUEST.value()));
            }

            userService.login(requestDto, res);
            return ResponseEntity.ok(new RestApiException("로그인 성공", HttpStatus.OK.value()));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new RestApiException("로그인 실패", HttpStatus.BAD_REQUEST.value()));
        }
    }
}
