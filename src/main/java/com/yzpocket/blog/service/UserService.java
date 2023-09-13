package com.yzpocket.blog.service;

import com.yzpocket.blog.dto.LoginRequestDto;
import com.yzpocket.blog.dto.SignupRequestDto;
import com.yzpocket.blog.entity.User;
import com.yzpocket.blog.entity.UserRoleEnum;
import com.yzpocket.blog.jwt.JwtUtil;
import com.yzpocket.blog.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository; // Bean DI
    private final PasswordEncoder passwordEncoder; // Bean DI
    private final JwtUtil jwtUtil; // Bean DI

    // ADMIN_TOKEN -> ADMIN가입시 필요한것
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC"; // 가상의 관리자키 -> 더복잡한데 여기선 임시값으로 썼음 -> 실제론 어떤 과정을쓸까?
    //아이디는 최소 4자 이상, 10자 이하이며 알파벳 소문자(az)와 숫자(09)로만 구성(대문자X)
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-z0-9]{4,10}$");
    //비밀번호 최소 8자 이상, 15자 이하, 영문 대문자, 영문 소문자, 숫자, 특수문자(예: !@#$%^&*()) 로 구성
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@#$%^&*()]{8,15}$");

    // 회원가입 기능
    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        Boolean isAdmin = requestDto.isAdmin();
        String adminToken = requestDto.getAdminToken();

        // 회원 중복 확인
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 아이디(회원이름) 유효성검사
        if (!isValidUsername(username)) {
            throw new IllegalArgumentException("ID 형태가 부적절합니다.");
        }

        // 비밀번호 기초 유효성검사, 암호화
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("PW 형태가 부적절합니다.");
        }
        // 비밀번호 암호화
        password = passwordEncoder.encode(password);


        // 회원유형(Role) 설정
        UserRoleEnum role = UserRoleEnum.USER; // default = USER
        if(isAdmin == true){
            if(!ADMIN_TOKEN.equals(adminToken)){
                throw new IllegalArgumentException("관리자 코드가 잘못 입력되어 등록이 불가능 합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, password, role);
        userRepository.save(user);
    }


    // 로그인 기능
    public void login(LoginRequestDto requestDto, HttpServletResponse res) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        //사용자 존재 확인
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));

        //비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 인증 성공 시 JWT 생성 및 쿠키에 저장
        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
        jwtUtil.addJwtToCookie(token, res);
    }


    // 유효성검사 메소드 - 이름
    public boolean isValidUsername(String username) {
        return USERNAME_PATTERN.matcher(username).matches();
    }


    // 유효성검사 메소드 - 비밀번호
    public boolean isValidPassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }
}