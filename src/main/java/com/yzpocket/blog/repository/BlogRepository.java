package com.yzpocket.blog.repository;

import com.yzpocket.blog.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAllByOrderByModifiedAtDesc();
    List<Blog> findAllByContentsContainsOrderByModifiedAtDesc(String keyword);

    //JpaRepository<"@Entity 클래스", "@Id 의 데이터 타입">를 상속받는 interface 로 선언하면
    //지금 삭제된 소스코드들 모두 기본적인 틀이 작성되어있는 프레임워크를 사용만 하면 될 정도로 간편해진다.
    //따라서 현재 이 MemoRepository가 클래스에서 추상화 인터페이스로 변경되면서 삭제된 코드들은
    //해당 인터페이스의 실제 구현체는 Spring이 자동으로 만들어두었으며, ***** SimpleJpaRepository.java *****에서
    //추상인터페이스를 받는 구현체의 실질 메소드들을 확인 할 수 있다.  <---- 이전 코드들은
    //그것들을 기준으로 비슷한 형태로 실습 했던 것이기 때문에 비교하면서 구현체에서 필요한 기능들을 확인하고 사용하면 되는 것이다.
    //제네릭으로 변할 수 있는 타입들을 대명사로 구현해두었기 때문에, 구현체 메소드 사용 시 전달되는 타입도 커버되는 상황이다.
    //그럼 정확한 목적에 따라 구현체에 있는 어떤 기능들을 사용해야 하는지 생각하는 것이 이제 개발자의 할 일이다.
}