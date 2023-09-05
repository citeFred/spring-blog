package com.yzpocket.blog.dto;

import com.yzpocket.blog.entity.Blog;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BlogResponseDto {
    private Long id;
    private String title;
    private String name;
    private String contents;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private String password;

    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.name = blog.getName();
        this.contents = blog.getContents();
        this.createAt = blog.getCreatedAt();
        this.modifiedAt = blog.getModifiedAt();
        this.password = blog.getPassword();
    }
}