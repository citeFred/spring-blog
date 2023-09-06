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

    @Column(name = "username", nullable = false)
    private String username; //사용자이름

    @Column(name = "title", nullable = false)
    private String title; //글제목

    @Column(name = "contents", nullable = false, columnDefinition = "TEXT") //<- text로!
    private String contents; //글내용



    public Blog(BlogRequestDto requestDto, String tokenUsername) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.username = tokenUsername; // 사용자 이름 설정
    }

    public void update(BlogRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void update(BlogRequestDto requestDto, String username){
        this.username = username;
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }
    //public String getPassword(BlogRequestDto requestDto){
    //    this.password = requestDto.getPassword();
    //    return password;
    //}
}
