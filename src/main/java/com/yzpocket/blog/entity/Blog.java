package com.yzpocket.blog.entity;

import com.yzpocket.blog.dto.BlogRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class Blog {
    private Long idx; // 글번호
    private String title; //글제목
    private String author; //작성자
    private String contents; //내용
    private Timestamp accesstime; //시간
    private String password; //비밀번호

    public Blog(BlogRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.author = requestDto.getAuthor();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();
        //this.accesstime = requestDto.getAccesstime();
        this.accesstime = new Timestamp(System.currentTimeMillis());
    }
    public void update(BlogRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.author = requestDto.getAuthor();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();
        this.accesstime = new Timestamp(System.currentTimeMillis());
    }
}
