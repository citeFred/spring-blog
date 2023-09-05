package com.yzpocket.blog.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Setter
@Getter
public class BlogRequestDto {
    private String title;
    private String author;
    private String contents;
    private Timestamp accessTime;
    private String password;
}
