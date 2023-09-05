package com.yzpocket.blog;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordEncoderTest {

    @Autowired
    PasswordEncoder passwordEncoder; //Bean으로 등록된것을 주입

    @Test
    @DisplayName("수동 등록한 passwordEncoder를 주입 받아와 문자열 암호화")
    void test1() {
        String password = "Robbie's password"; //현재 패스워드

        // 암호화 encode()메소드에 paswword를 넣어봄
        String encodePassword = passwordEncoder.encode(password);
        System.out.println("encodePassword = " + encodePassword); //암호화 encode()실행 이후 password는 어떻게 변화했는지 확인

        String inputPassword = "Robbie"; //이런 원본값을 '평문'이라고 부름

        // 복호화를 통해 암호화된 비밀번호와 비교
        boolean matches = passwordEncoder.matches(inputPassword, encodePassword); // matches(평문, 암호화된암호)로 평문과 암호화작업이후 비밀번호와 같은지 비교해줌, true or false를 반환
        System.out.println("matches = " + matches); // 암호화할 때 사용된 값과 다른 문자열과 비교했기 때문에 false
    }
}