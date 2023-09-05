package com.yzpocket.blog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzpocket.blog.entity.Blog;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Setter
@Getter
public class BlogResponseDto {
    private Long idx;
    private String title;
    private String author;
    private String contents;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+9")
    private Timestamp accesstime;
    private String password;

    public BlogResponseDto(Blog blog) {
        this.idx = blog.getIdx();
        this.title = blog.getTitle();
        this.author = blog.getAuthor();
        this.contents = blog.getContents();
        this.accesstime = blog.getAccesstime();
        this.password = blog.getPassword();
    }

    public BlogResponseDto(Long idx, String title, String author, String contents, Timestamp accesstime, String password) {
        this.idx = idx;
        this.title = title;
        this.author = author;
        this.contents = contents;
        this.accesstime = accesstime;
        this.password = password;
    }
}