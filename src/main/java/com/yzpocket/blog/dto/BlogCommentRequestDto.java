package com.yzpocket.blog.dto;

import com.yzpocket.blog.entity.Blog;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

// 댓글 Dto
@Getter
@Setter
public class BlogCommentRequestDto {

    private Long id;
    private String title;
    private String contents;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    List<CommentResponseDto> comments;

    public BlogCommentRequestDto(Blog blog, List<CommentResponseDto> comments) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.contents = blog.getContents();
        this.username = blog.getUsername();
        this.createdAt = blog.getCreatedAt();
        this.modifiedAt = blog.getModifiedAt();
        this.comments = comments;
    }
}
