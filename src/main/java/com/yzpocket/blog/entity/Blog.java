package com.yzpocket.blog.entity;

import com.yzpocket.blog.dto.BlogRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Setter
@Table(name = "blog") // 맵핑된 DB 테이블 : blog
@NoArgsConstructor
public class Blog extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 글번호

    @Column(name = "title", nullable = false)
    private String title; //글제목

    @Column(name = "name", nullable = false)
    private String name; //작성자

    @Column(name = "contents", nullable = false)
    private String contents; //글내용

    @Column(name = "password", nullable = false)
    private String password; //글비밀번호

    public Blog(BlogRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.name = requestDto.getName();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();
    }
    public void update(BlogRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.name = requestDto.getName();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();
    }
}
