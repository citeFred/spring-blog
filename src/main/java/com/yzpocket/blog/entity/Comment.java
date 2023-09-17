package com.yzpocket.blog.entity;

import com.yzpocket.blog.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comment") // 맵핑된 DB 테이블 : comment
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment(MySQL)
    private Long id; // 댓글 인덱스

    @Column(name = "username", nullable = false)
    private String username; // 작성자

    @Column(name = "contents", nullable = false,  columnDefinition = "TEXT") //<- TEXT 타입 in DB
    private String contents; // 댓글 내용

    // 댓글이 속한 블로그의 ID
    @Column(name = "blog_id", nullable = false)
    private Long blogId;

    //Comment:Blog 관계 N:1
    //Comment의 postid 컬럼과 Blog의 id 컬럼을 Join 조건으로 사용
    //Comment<->Blog 양방향
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Blog blog;

    //Comment:User 관계 N:1
    //Comment의 blogid 컬럼과 Blog의 id 컬럼을 Join 조건으로 사용
    //Comment<->User 양방향
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    // 댓글 저장 생성자
    public Comment(CommentRequestDto requestDto, String tokenUsername) { // <- 토큰에서 유저 이름 가져오기
        this.username = tokenUsername;
        this.blogId = requestDto.getBlogId(); // Comment와 관련된 블로그의 ID를 설정합니다.
        this.contents = requestDto.getContents();
    }

    // 댓글 수정 생성자
    public void update(String contents) {
        this.contents = contents;
    }
}
