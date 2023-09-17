//package com.yzpocket.blog.filter;
//
//import com.yzpocket.blog.entity.User;
//import com.yzpocket.blog.jwt.JwtUtil;
//import com.yzpocket.blog.repository.UserRepository;
//import io.jsonwebtoken.Claims;
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import java.io.IOException;
//
//@Slf4j(topic = "AuthFilter")
//@Component
//@RequiredArgsConstructor
//@Order(2)
//public class AuthFilter implements Filter {
//
//    // 사용자 정보를 관리하는 저장소와 JWT 토큰 관련 기능을 제공하는 유틸리티 클래스를 멤버 변수로 선언
//    private final UserRepository userRepository;
//    private final JwtUtil jwtUtil;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, IOException {
//        // 현재의 요청 객체를 HttpServletRequest로 형변환하여 HTTP 관련 메서드를 사용
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        // 현재 요청의 URI를 가져옴
//        String url = httpServletRequest.getRequestURI();
//        String urlMethod = httpServletRequest.getMethod();
//
//        // 로그인 API 요청에 대해 인증 절차를 건너뜀 -> WebSecurityConfig로 인해 이부분들이 필요없어질것임
//        if (StringUtils.hasText(url) && (url.startsWith("/api/user")||"GET".equals(urlMethod))) { // 로그인 + 회원가입 필터 우회
//            chain.doFilter(request, response); // 인증없이 다음 Filter 또는 대상 Servlet/JSP로 요청 전달
//        } else {
//            log.info("auth");
//
//            // 그 외의 API 요청에 대해서는 인증 절차를 진행
//            // JWT 토큰을 요청 헤더에서 가져옴
//            String tokenValue = jwtUtil.getTokenFromRequest(httpServletRequest);
//
//            // 토큰이 존재하는 경우
//            if (StringUtils.hasText(tokenValue)) {
//                // Bearer 등의 접두사를 제거하고 순수 토큰만 추출
//                String token = jwtUtil.substringToken(tokenValue);
//
//                // 토큰의 유효성 검증
//                if (!jwtUtil.validateToken(token)) {
//                    throw new IllegalArgumentException("Token Error"); // 토큰 오류시 예외 발생
//                }
//
//                // 유효한 토큰인 경우 토큰에서 사용자 정보를 추출
//                Claims info = jwtUtil.getUserInfoFromToken(token);
//
//                // 추출한 사용자 정보를 바탕으로 데이터베이스에서 사용자 정보를 조회
//                User user = userRepository.findByUsername(info.getSubject()).orElseThrow(() ->
//                        new NullPointerException("Not Found User") // 사용자를 찾지 못할 경우 예외 발생
//                );
//
//                // 요청 객체에 사용자 정보를 속성으로 추가
//                request.setAttribute("user", user);
//                chain.doFilter(request, response); // 인증이 완료된 상태로 다음 Filter 또는 대상 Servlet/JSP로 요청 전달
//            } else {
//                throw new IllegalArgumentException("Not Found Token"); // 토큰이 없는 경우 예외 발생
//            }
//        }
//    }
//}
//
///*
//* @GetMapping("/products")
//    public String getProducts(HttpServletRequest req) {
//        System.out.println("ProductController.getProducts : 인증 완료");
//        User user = (User) req.getAttribute("user");
//        System.out.println("user.getUsername() = " + user.getUsername());
//
//        return "redirect:/";
//    }
//* */