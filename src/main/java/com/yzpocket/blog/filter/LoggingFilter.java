package com.yzpocket.blog.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j(topic = "LoggingFilter")
@Component
@Order(1)
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, IOException {
        // ServletRequest를 HttpServletRequest로 다운캐스팅하여 HTTP 관련 메서드를 사용할 수 있게 함
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        // 현재 요청의 URI를 가져옴
        String url = httpServletRequest.getRequestURI();

        // 가져온 URI를 로그에 출력
        log.info(url);

        // 요청과 응답을 다음 필터로 전달하거나, 해당 필터가 마지막 필터라면 실제 서블릿이나 JSP로 전달
        chain.doFilter(request, response);

        // 요청 처리 후 "비즈니스 로직 완료" 라는 메시지를 로그에 출력
        log.info("비즈니스 로직 완료");
    }
}

//로그 출력을 위한 @Slf4j(topic = "LoggingFilter") 어노테이션을 사용하여 "LoggingFilter"라는 이름의 토픽으로 SLF4J 로깅을 진행
//@Component 어노테이션을 통해 Spring의 컴포넌트 스캐닝에 의해 해당 클래스를 빈으로 등록한다.
//필터의 실행 순서를 @Order(1)로 지정하여, 이 필터가 다른 필터들 중에서 가장 먼저 실행되도록 한다.
//구현체에서는 Filter 인터페이스를 구현하여 사용자 정의 필터를 생성한다.
//doFilter 메서드 내에서 ServletRequest 객체를 HttpServletRequest 객체로 다운캐스팅하여 HTTP 특화된 메서드에 접근한다.
//그 후 현재 HTTP 요청의 URI 정보를 가져와 로그로 출력한다.
//현재 요청을 다음 필터나 서블릿, JSP로 전달하고, 요청 처리가 완료된 후 "비즈니스 로직 완료"라는 메시지를 로그로 출력한다.

