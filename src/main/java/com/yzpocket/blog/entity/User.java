package com.yzpocket.blog.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Setter
@Table(name = "users") // 맵핑된 DB 테이블 : users
@NoArgsConstructor
public class User {
    @Id
    private String username; // 회원이름
    @Column(nullable = false)
    private String password; // 회원비밀번호

    //@Coluem(nullable = false)
    //@Enumberated(value = EnumType.STRING) //<- Enum 타입의 데이터를 저장 할때 필요, Enum의 이름자체를 저장(ex:"ADMIN", "USER") role 구현 참고
    //private UserRoleEnum role;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
