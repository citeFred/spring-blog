package com.yzpocket.blog.dto;

import lombok.Getter;


@Getter
public class BlogRequestDto { // 정보주는 Dto
    private String title;
    private String username;
    private String contents;
}
