package com.yzpocket.blog.dto;

import com.yzpocket.blog.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

// 클라이언트에 반환할 Dto
@Getter
public class CommentResponseDto {  // response Dto
    private Long id;
    private Long blogId;
    private String contents;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto() {}

    // 댓글 저장용 Dto 생성자
    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.blogId = comment.getBlogId();
        this.contents = comment.getContents();
        this.username = comment.getUsername();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }

    // 댓글 정보 중 필요한것만 줄 생성자
    public CommentResponseDto(Long id, Long blogId, String contents, String username, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.blogId = blogId; // postid 대신 blogId 사용
        this.contents = contents;
        this.username = username;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    // CommentResponseDto 형태로 변환
    public CommentResponseDto fromComment(Comment comment) {
        return new CommentResponseDto(comment.getId(), comment.getBlogId(), comment.getContents(), comment.getUsername(), comment.getCreatedAt(), comment.getModifiedAt());
    }
}