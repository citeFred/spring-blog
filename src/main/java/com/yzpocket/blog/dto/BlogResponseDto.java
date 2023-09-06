package com.yzpocket.blog.dto;

import com.yzpocket.blog.entity.Blog;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BlogResponseDto { // response Dto
    private Long id;
    private String username;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.username = blog.getUsername();
        this.title = blog.getTitle();
        this.contents = blog.getContents();
        this.createdAt = blog.getCreatedAt();
        this.modifiedAt = blog.getModifiedAt();
    }
    public BlogResponseDto(String username,String title, String contents, LocalDateTime createdAt) {
        this.username = username;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
    }
    public static BlogResponseDto fromBlog(Blog blog) { // 필요한 정보만 내보내기
        return new BlogResponseDto(blog.getUsername(), blog.getTitle(), blog.getContents(), blog.getCreatedAt());
    }
}