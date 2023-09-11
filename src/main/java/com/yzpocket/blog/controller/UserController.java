package com.yzpocket.blog.controller;

import com.yzpocket.blog.dto.LoginRequestDto;
import com.yzpocket.blog.dto.SignupRequestDto;
import com.yzpocket.blog.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    //private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 API
    @PostMapping("/user/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            String password = requestDto.getPassword();

            // 아이디와 비밀번호의 유효성 검사 호출 계층을 (Service->Controller로 옮김)
            if (!userService.isValidUsername(username)) { // <- UserService의 유효성검사 중복제거용 메소드를 호출
                String jsonResponse = "{\"msg\": \"ID 형태가 부적절합니다.\", \"statusCode\": 400}";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
            }

            if (!userService.isValidPassword(password)) { // <- UserService의 유효성검사 중복제거용 메소드를 호출
                String jsonResponse = "{\"msg\": \"PW 형태가 부적절합니다.\", \"statusCode\": 400}";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
            }

            userService.signup(requestDto);
            String jsonResponse = "{\"msg\": \"회원가입 성공\", \"statusCode\": 200}";
            return ResponseEntity.ok(jsonResponse);

        } catch (Exception e) {
            e.printStackTrace();
            String jsonResponse = "{\"msg\": \"회원가입 실패\", \"statusCode\": 400}";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }
    }

    // 로그인 API
    @PostMapping("/user/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse res) {
        try {
            String username = requestDto.getUsername();
            String password = requestDto.getPassword();

            // 아이디와 비밀번호의 유효성 검사 호출 계층을 (Service->Controller로 옮김)
            if (!userService.isValidUsername(username)) { // "
                String jsonResponse = "{\"msg\": \"ID 형태가 부적절합니다.\", \"statusCode\": 400}";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
            }

            // 아이디와 비밀번호의 유효성 검사 호출 계층을 (Service->Controller로 옮김)
            if (!userService.isValidPassword(password)) { // "
                String jsonResponse = "{\"msg\": \"PW 형태가 부적절합니다.\", \"statusCode\": 400}";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
            }

            userService.login(requestDto, res);
            String jsonResponse = "{\"msg\": \"로그인 성공\", \"statusCode\": 200}";
            return ResponseEntity.ok(jsonResponse);

        } catch (Exception e) {
            e.printStackTrace();
            String jsonResponse = "{\"msg\": \"로그인 실패\", \"statusCode\": 400}";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }
    }
}

/* 예외 처리 계층 선택에 대한 정리
* Controller와 Service에서 예외 처리를 어떻게 다룰지에 대한 선택은 프로젝트의 구조와 설계에 따라 다를 수 있습니다.
* 일반적으로 다음과 같은 원칙을 고려하면서 결정할 수 있습니다

Controller에서 예외 처리:
* Controller에서 예외를 처리하면 클라이언트에게 빠르게 오류 응답을 전달할 수 있습니다.
* 이것은 예외가 컨트롤러 메서드 내에서 발생하고 클라이언트에게 오류 메시지를 반환하는데 유용합니다.
* 예를 들어, 회원가입과 같은 입력 유효성 검사와 관련된 예외를 처리하는 데 사용할 수 있습니다.

Service에서 예외 처리:
* Service 레벨에서 예외 처리를 하는 것은 비즈니스 로직과 관련된 예외를 처리하는데 유용합니다.
* 예를 들어, 데이터베이스 조회 중에 발생한 예외나 비즈니스 규칙 위반과 관련된 예외를 Service에서 처리할 수 있습니다.
* 그런 다음 Service는 Controller에 처리된 결과를 반환하고,
* Controller에서는 클라이언트에게 적절한 응답을 제공할 수 있습니다.

* 위의 코드에서 보면 회원가입과 관련된 예외는 Controller에서 처리되고,
* 게시글 조회와 관련된 예외는 Service에서 처리됩니다.
* 이것은 예외가 발생한 위치와 예외의 종류에 따라 적절한 처리 방법을 선택한 것으로 보입니다.

* 이러한 방식은 일반적으로 권장되는 방법입니다.
* Controller에서는 클라이언트와의 인터페이스 역할을 하며,
* Service에서는 비즈니스 로직을 처리하고 예외를 적절하게 처리합니다.
* 이를 통해 코드의 가독성과 유지 보수성을 향상시킬 수 있습니다.
* */
