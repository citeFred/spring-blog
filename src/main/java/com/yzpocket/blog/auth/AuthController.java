package com.yzpocket.blog.auth;

import com.yzpocket.blog.entity.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

// Util은 보통 다른 객체에 의존하지 않고 모듈처럼 사용해야하는 경우 (ex 계산된 금액에 단위인 KRW, USD등을 붙여야 한다던지 그런 부가적 기능들과 관련된) 어떤 기능의 추가적인 기능?적 단위의 모듈을 추가하는 경우 이런 이름을 붙인다.
@Component
public class AuthController {

    // 헤더에 직접 담는 방법 vs 쿠키를 생성하고 담아서 보내기
    // - 1) 헤더에 직접 담으면 그만큼 코드량이 줄어들고 간결해진다.
    // - 2) 쿠키(객체)를 생성해서 보내면 좋은점이 쿠키 자체의 설정(만료시간이나 명칭 등..)이 가능하다.
    // - 둘 중 어떤것이 좋다기 보다는 목적에 따라 다를 수 있고, 프론트엔드와의 어떤 것이 필요 할 지, 처리 방법에 대해서 협업 커뮤니케이션이 필요한 부분 중 하나다.

    // 이 중 아래 방법은 쿠키를 생성하고 담아서 보내는 방법을 보여준다.

    // ----------------------------------------------- [0] JWT 데이터 -----------------------------------------------

    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization"; //<- Cookie의 Name값
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth"; //<- 권한에 대한 정보
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer "; //<- 토큰의 식별자 이것은 토큰이다라는 컨벤션같은 규칙 Value앞에 붙여서 표시해주긴함 띄워쓰기 " " 꼭 넣어준다.
    // 토큰 만료시간
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분 <- ms기준이라 1000을 붙여준다.

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey <- application.properties에 선언해둔 값을 여기로 가져오게됨
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; // HS256 알고리즘 사용을 선택한것이다.

    // 로그 설정
    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그"); // <- 로깅한다는 의미, 시간순으로 동작 상태를 기록하는 것, Logger 프레임 워크를 사용해서 보여질 예정

    @PostConstruct //<- 요청을 새로 호출하는 실수를 방지하기 위해 사용. <- JWT 유틸 생성자 호출한 뒤에, 생성이 된 후, secretKey를 담게 되는데, 위 JwtUtil클래스의 iv 사용해줄 Key에 담아준다.
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }
    // -----------------------------------------------------------------------------------------------------------


    // ----------------------------------------------- [1] JWT 생성 -----------------------------------------------
    // 토큰 생성
    public String createToken(String username, UserRoleEnum role) { //<- 토큰 생성자 에서 username과 권한 매개변수를 필요로함, 호출 시 username, 권한명을 인자로 넘겨주면됨
        Date date = new Date();

        return BEARER_PREFIX + //아까 Value 앞에 붙게되는 식별자 컨벤션 "Bearer " 같은것이 붙게되고 이후 value값이 붙게됨
                Jwts.builder() //아래 Jwt 토큰을 만드는 메소드에 맞는 각 항목들을 생성하면서 암호화를 진행함. 필요한 정보는 아래 항목을 조절하면 되는것임
                        .setSubject(username) // 사용자 식별자값(ID)
                        .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }
    // -----------------------------------------------------------------------------------------------------------


    // ----------------------------------------------- [2] 생성된 JWT를 Cookie에 저장 -----------------------------------------------
    // JWT Cookie 에 저장
    public void addJwtToCookie(String token, HttpServletResponse res) {
        try {
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token); // Name-Value
            cookie.setPath("/");

            // Response 객체에 Cookie 추가
            res.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
    }
    // ---------------------------------------------------------------------------------------------------------------------------------


    // ----------------------------------------------- [3] Cookie에 들어있던 JWT 토큰을 Substring -----------------------------------------------
    // 토큰에 "Bearer " 같은 값이 함께 들어와있기 때문에 이런 문자열은 제거해줘야 읽을 수 있기 때문에 잘라내야함
    // JWT 토큰 substring
    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7); // <- "Bearer " 가 7자리라서 없에려고
        }
        logger.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }
    // ---------------------------------------------------------------------------------------------------------------------------------


    // ----------------------------------------------- [4] JWT 검증 -----------------------------------------------
    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token); //<- 토큰의 위변조, 파괴, 오류등을 감지하게 됨
            return true; //문제 없으면 true를 반환
        } catch (SecurityException | MalformedJwtException | SignatureException e) { //<- 각 위변조 등 오류 로그를 확인하기 위해서,
            logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false; //문제 발견되면 위 catch문에서 잡히고 false를 반환
    }
    // ---------------------------------------------------------------------------------------------------------------------------------


    // ----------------------------------------------- [5] JWT에서 사용자 정보 가져오기 -----------------------------------------------
    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
    // ---------------------------------------------------------------------------------------------------------------------------------

}


