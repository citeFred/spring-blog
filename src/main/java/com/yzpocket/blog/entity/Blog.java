package com.yzpocket.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yzpocket.blog.dto.BlogRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "password", nullable = false)
    private String password; //비밀번호

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Blog:Comment = 1:N
    // Blog를 삭제할 때 연관된 Comment들도 모두 삭제된다.
    @OneToMany(mappedBy = "blog", orphanRemoval = true) // Comment의 blog 참조변수에 맵핑, cascade 말고 orphanRemoval = true로 모 엔티티와 관계가 끊어진 자식 엔티티를 자동으로 삭제
    private List<Comment> commentList = new ArrayList<>();

    @Column(name = "like_cnt")
    private Integer likeCnt = 0;

    public Blog(BlogRequestDto requestDto, String tokenUsername) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.username = tokenUsername; // 사용자 이름 설정
    }

    public void update(BlogRequestDto requestDto, String username){
        this.username = username;
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public List<Comment> getComments() {
        return commentList;
    }

}
