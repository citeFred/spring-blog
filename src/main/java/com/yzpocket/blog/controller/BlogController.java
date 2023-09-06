package com.yzpocket.blog.controller;

import com.yzpocket.blog.dto.BlogRequestDto;
import com.yzpocket.blog.dto.BlogResponseDto;
import com.yzpocket.blog.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BlogController {
    private final BlogService blogService; //*** 느슨 결합 IoCcontainer->Bean->DI
    public BlogController(BlogService blogService){ //외부에서 주입해야 한다했는데 어디서 누가 주입해줘야 하는것이지?? 메인은 없는데.. -> IoC 컨테이너??? Bean Type이 없다? = Bean이 등록되어야 한다는 소리.
        this.blogService = blogService;
    }

    // 글 작성
    @PostMapping("/blog")
    public BlogResponseDto createBlog(@RequestBody BlogRequestDto requestDto) {
        return blogService.createBlog(requestDto);
    }

    // 글 목록 보기
    @GetMapping("/blogs")
    public List<BlogResponseDto> getBlogs() {
        return blogService.getBlogs();
    }

    // 아래부터는 타입의 다형성을 이용해보자
    // <- 성공한 경우 Dto 타입 json객체 반환, 실패한 경우 String jsonResponse 반환하기위해 타입<?> Generic 사용
    // 선택 글 보기
    @GetMapping("/blog/{id}")
    public ResponseEntity<?> getBlogById(@PathVariable Long id) {
        try {
            BlogResponseDto responseDto = blogService.getBlogById(id);
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            String jsonResponse = "{\"msg\": \"" + e.getMessage() + "\", \"statusCode\": 404}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonResponse);
        }
    }


    // 선택 글 수정
    @PutMapping("/blog/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
        try {
            blogService.updateBlog(id, requestDto);
            String jsonResponse = "{\"msg\": \"글 수정 성공\", \"statusCode\": 200}";
            return ResponseEntity.ok(jsonResponse);
        } catch (IllegalArgumentException e) {
            String jsonResponse = "{\"msg\": \"" + e.getMessage() + "\", \"statusCode\": 404}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonResponse);
        }
    }

    // 선택 글 삭제
    @DeleteMapping("/blog/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable Long id) {
        try {
            blogService.deleteBlog(id);
            String jsonResponse = "{\"msg\": \"글 삭제 성공\", \"statusCode\": 200}";
            return ResponseEntity.ok(jsonResponse);
        } catch (IllegalArgumentException e) {
            String jsonResponse = "{\"msg\": \"" + e.getMessage() + "\", \"statusCode\": 404}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonResponse);
        }
    }
}

