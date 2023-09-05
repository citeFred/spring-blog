package com.yzpocket.blog;

import com.yzpocket.blog.entity.Blog;
import com.yzpocket.blog.repository.BlogRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransactionTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    BlogRepository blogRepository;

    @Test
    @Transactional
    @Rollback(value = false) // 테스트 코드에서 @Transactional 를 사용하면 테스트가 완료된 후 롤백하기 때문에 false 옵션 추가
    @DisplayName("글 생성 성공")
    void test1() {
        Blog blog = new Blog();
        blog.setName("Robbert");
        blog.setContents("@Transactional 테스트 중!");
        blog.setTitle("Title");
        blog.setPassword("pass");

        em.persist(blog);  // 영속성 컨텍스트에 글 Entity 객체를 저장합니다.
    }

    @Test
    //@Transactional //트랜잭셔널 환경이 아니면 entity가 persistence (MANAGED)상태가 될 수 없어서 에러, 이것 주석 해제하면 작동잘됨
    @Disabled //테스트 할 때 주석 (현재 미사용)
    @DisplayName("글 생성 실패")
    void test2() {
        Blog blog = new Blog();
        blog.setName("Robbert");
        blog.setContents("@Transactional 테스트 중!");
        blog.setTitle("Title");
        blog.setPassword("pass");

        em.persist(blog);  // 영속성 컨텍스트에 글 Entity 객체를 저장합니다.
    }

    @Test
    @Transactional //만약에 부모가 없는 경우에 테스트해보면, create(3)으로 전파안됨 /기본 Required 옵션, 부모메서드에 트랜잭션이 되있으면, 자손메소드도 트랜잭션도 이어진다(전파된다)
    @Disabled
    @Rollback(value = false)
    @DisplayName("트랜잭션 전파 테스트")
    void test3() {
        //blogRepository.createBlog(em);     //더이상 테스트안하니까 이것 미사용
        System.out.println("테스트 test3 메서드 종료");
    }
}