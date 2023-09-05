package com.yzpocket.blog.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BlogRequestDto {
    private Long id;
    private String title;
    private String name;
    private String contents;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private String password;
}
