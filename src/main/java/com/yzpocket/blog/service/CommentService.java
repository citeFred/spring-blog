package com.yzpocket.blog.service;

import com.yzpocket.blog.dto.CommentRequestDto;
import com.yzpocket.blog.dto.CommentResponseDto;
import com.yzpocket.blog.entity.Comment;
import com.yzpocket.blog.entity.UserRoleEnum;
import com.yzpocket.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    // 댓글 작성
    public CommentResponseDto createComment(CommentRequestDto requestDto, String username, String blogId) {
        // RequestDto -> Entity
        Comment comment = new Comment(requestDto, username);
        comment.setBlogId(Long.parseLong(blogId)); // 클라이언트에서 받아온 blogId를 Comment 엔터티에 설정

        // DB 저장
        Comment saveComment = commentRepository.save(comment);
        // Entity -> ResponseDto
        CommentResponseDto commentResponseDto = new CommentResponseDto(saveComment);
        return commentResponseDto;
    }

    // 댓글의 작성자만 댓글을 수정하는 기능
    @Transactional
    public CommentResponseDto updateComment(Long id, String contents, String username, UserRoleEnum role) {
        Comment comment = findComment(id);

        // 댓글 작성자와 현재 사용자를 비교하여 권한을 확인
        if (role.getAuthority().equals("ROLE_ADMIN") || comment.getUsername().equals(username)) {
            comment.update(contents);
        } else {
            throw new IllegalArgumentException("당신에겐 글을 수정할 권한이 없습니다.");
        }

        return new CommentResponseDto().fromComment(comment);
    }

    // 작성자가 댓글 삭제
    public ResponseEntity<String> deleteComment(Long id, String username, UserRoleEnum role) {
        Comment comment = findComment(id);
        if(role.getAuthority().equals("ROLE_ADMIN")|| comment.getUsername().equals(username))
            commentRepository.delete(comment);
        else
            return ResponseEntity.ok("{\"msg\": \"댓글 삭제 실패\", \"statusCode\": 444}");
        return ResponseEntity.ok("{\"msg\": \"댓글 삭제 성공\", \"statusCode\": 200}");

    }

    /**
     * 댓글 ID 를 바탕으로 댓글을 조회
     * @param id 클라이언트가 요청한 댓글의 ID
     * @return 조회된 댓글
     */
    private Comment findComment(Long id) { // 메모 찾기
        return commentRepository.findById(id).orElseThrow(() ->  // null 시 오류 메시지 출력
                new IllegalArgumentException("선택한 댓글은 존재하지 않습니다.")
        );

    }



}