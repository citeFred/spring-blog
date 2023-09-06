package com.yzpocket.blog.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BlogRequestDto {
    private String title;
    private String author;
    private String contents;
    private String password;
}
