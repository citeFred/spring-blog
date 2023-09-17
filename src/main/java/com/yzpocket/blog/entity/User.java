package com.yzpocket.blog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Setter
@Table(name = "users") // 맵핑된 DB 테이블 : users
@NoArgsConstructor
public class User {
    @Id
    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private String userId;

    //@Id
    //@Column(nullable = false, unique = true)
    //private String username; // 회원이름 -> 회원번호로 인덱스 변경

    @Column(nullable = false)
    private String username; // 회원이름

    @Column(nullable = false)
    private String password; // 회원비밀번호

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING) //<- Enum 타입의 데이터를 저장 할때 필요, Enum의 이름자체를 저장(ex:"ADMIN", "USER") role 구현 참고
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Blog> memoList = new ArrayList<>();

    public User(String userId, String username, String password, UserRoleEnum role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
