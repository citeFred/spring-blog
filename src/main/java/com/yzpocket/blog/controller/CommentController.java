package com.yzpocket.blog.controller;

import com.yzpocket.blog.dto.CommentRequestDto;
import com.yzpocket.blog.dto.CommentResponseDto;
import com.yzpocket.blog.entity.User;
import com.yzpocket.blog.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 댓글 관련 HTTP 요청 처리를 담당하는 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성 by User, blogID API
    @PostMapping("/comment")
    public CommentResponseDto createComment(
            @RequestBody CommentRequestDto requestDto,
            @RequestParam("blogId") Long blogId,
            HttpServletRequest req) {

        User user = (User) req.getAttribute("user"); // 사용자 정보를 추출
        String blogIdStr = (blogId != null) ? blogId.toString() : null;

        // 댓글 작성에 필요한 user와 blogId 정보를 commentService에 전달
        return commentService.createComment(requestDto, user.getUsername(), blogIdStr);
    }

    // 댓글 수정 by Id API // 댓글 수정, 삭제는 궂이 blogId가 필요하지 않을것 같음 댓글 작성자만 확인하도록.
    @PutMapping("/comment/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody Map<String,String> contents, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        return commentService.updateComment(id, contents.get("contents"), user.getUsername(), user.getRole());
    }

    // 댓글 삭제 by Id API // 댓글 수정, 삭제는 궂이 blogId가 필요하지 않을것 같음 댓글 작성자만 확인하도록.
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        return commentService.deleteComment(id, user.getUsername(), user.getRole());
    }
}