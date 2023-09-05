package com.yzpocket.blog.dto;

import lombok.Getter;

@Getter
public class BlogRequestDto {
    private String title;
    private String name;
    private String contents;
    private String password;
}
