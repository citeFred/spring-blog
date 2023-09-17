package com.yzpocket.blog.repository;

import com.yzpocket.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Comment 테이블을 관리하는 Repository
 * JpaRepository를 상속하면 기본적인 CRUD 메서드를 자동으로 생성한다.
 * JpaRepository를 상속할 때는 다음과 같이 두 개의 제네릭을 명시해야 한다.
 * 첫 번째 제네릭은 Entity 클래스 타입
 * 두 번째 제네릭은 Id의 타입
 * JpaRepository를 상속하면 기본적인 CRUD 메서드를 자동으로 생성한다.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> { // JpaRepository 덕분에 @Repository 선언 안해도 자동으로 Bean으로 취급
    /**
     * 게시글 번호로 댓글 목록을 조회하는 메서드
     * @param blogId 게시글 번호
     * @return 댓글 목록
     */
    //List<Comment> findAllByBlogIdOrderById(Long blogId); // 필요한 정보만 가져오도록 함
    void delete(Comment comment);

}