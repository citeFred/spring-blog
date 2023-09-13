package com.yzpocket.blog.service;

import com.yzpocket.blog.dto.LoginRequestDto;
import com.yzpocket.blog.dto.SignupRequestDto;
import com.yzpocket.blog.entity.User;
import com.yzpocket.blog.jwt.JwtUtil;
import com.yzpocket.blog.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository; // Bean DI
    private final PasswordEncoder passwordEncoder; // Bean DI
    private final JwtUtil jwtUtil; // Bean DI

    // ADMIN_TOKEN -> ADMIN가입시 필요한것
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC"; // 가상의 관리자키 -> 더복잡한데 여기선 임시값으로 썼음 -> 어떤 과정을쓸까?
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-z0-9]{4,10}$"); //아이디는 소문자 알파벳 및 숫자로 구성되어 있어야 하며, 길이는 4자 이상 10자 이하로 설정
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9]{8,15}$"); //비밀번호는 대문자 및 소문자 알파벳, 숫자로 구성되어 있어야 하며, 길이는 8자 이상 15자 이하로 설정

    // 유효성검사 메소드 - 이름
    public boolean isValidUsername(String username) {
        return USERNAME_PATTERN.matcher(username).matches();
    }

    // 유효성검사 메소드 - 비밀번호
    public boolean isValidPassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    // 회원가입 기능
    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword(); //암호화는 아래 유효성체크 이후 할당되도록 변경

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 비밀번호 기초 유효성검사, 암호화
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("PW 형태가 부적절합니다.");
        } //불필요한 else 제거
        password = passwordEncoder.encode(password); //인코더로 암호화해서 할당 정보통신보호법에따라 꼭해줘야함

        // 아이디(회원이름) 기초 유효성검사
        if (!isValidUsername(username)) {
            throw new IllegalArgumentException("ID 형태가 부적절합니다.");
        }
        /* email 중복확인, 사용자 유형 role 확인 추가 시
         * 해당 추가 기능은 한번 구현해보자 회원 중복 확인과 유사한 원리다. 햇갈리면 1-7 회원가입구현 22분부터 참고 해볼 것.*/

        // 사용자 등록
        User user = new User(username, password); //db row는 entity객체다
        userRepository.save(user);
    }


    // 로그인 기능
    public void login(LoginRequestDto requestDto, HttpServletResponse res) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow( // 회원 중복 확인과 다르게 없는경우를 체크해야 함
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 비밀번호 확인matches(request들어온 평문, repo의user객체 저장된 암호문) - 단방향이라 matches가 평문A를 암호화해서 암호vs암호 비교함 true or false
        if (!passwordEncoder.matches(password,user.getPassword())){ // 일치(matches)하지 않을경우라 !연산자
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        // -- 인증완료 --

        // JWT 생성 및 쿠키에 저장 후 Response객체에 추가
        String token = jwtUtil.createToken(user.getUsername()); //사용자 확인된 이름, 나중에 권한 등 추가하면 인자 추가할 것
        jwtUtil.addJwtToCookie(token, res);
    }
}