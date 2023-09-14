# Spring Boot Practice!
[Spring] 블로그 백엔드 서버 만들기

## 🖥️ 저장소 소개
Spring Boot 를 활용한 회원가입, 로그인, 게시글 쓰기, 수정, 삭제, 조회, 댓글 등 API를 구현해보는 연습 프로젝트 저장소 입니다.
## 🕰️ 학습 기간
* 23.08.29 ~ 23.09.14
* 1주차 https://ohnyong.notion.site/Lv-1-cbd646bdc47a40ea90fe348ff2b8242f?pvs=4
* 2주차 https://ohnyong.notion.site/Lv-2-bb787094ac8141c9a88eaa15334ec7bf?pvs=4
* 3주차 https://ohnyong.notion.site/Lv-3-94fd777a224f4854b38550dabde79082?pvs=4
* 

### ⚙️ 개발 환경
- **MainLanguage** : `Java - JDK 17`
- **IDE** : `IntelliJ IDEA Ultimate`
- **Framework** : `SpringBoot`
- **Database** : `MySQL`
- **SERVER** : `Spring Inner Server(TOMCAT)` 
- **TEST** : `POSTMAN API Request` 

## 👋🏻 Contact
- **Email** : citefred@yzpocket.com
- **Blog** : https://www.citefred.com

## 📌 주요 기능
#### 학습한 기능
* 회원 부분
    - 회원가입
    - 로그인

* 게시판 기능
    - 게시글 쓰기
    - 게시글 모두 조회, 선택 조회
    - 게시글 선택 수정
    - 게시글 선택 삭제

* 댓글 기능
    - 댓글 작성
    - 댓글 수정
    - 댓글 삭제

* 추가중…

## ⚠️ 주의
#### 추적 예외
* src/main/resources/application.properties 파일은 DB 접속 정보가 있어 추적이 제외되어 있습니다.
* MySQL을 연결 한 뒤 'blog' 이름의 DATABASE를 생성해 주셔야 합니다.
```
create database blog;
```
* 테스트를 진행 하시려면 위 경로 src/main/resources/ 에 파일(application.properties)을 생성해주세요.
  - 다음과 코드를 입력해주세요 < ... > 부분을 작성해주셔야 합니다. "<", ">" 괄호 제거해주세요.
  - ex) spring.datasource.username=roots
  - <Secret Key> 는 로컬 테스트용으로 임시 적용하고 있습니다. 원하는 난수를 입력해주세요 ex) adsfa19aaAd91
```
#JDBC
spring.datasource.url=jdbc:mysql://localhost:3306/blog
spring.datasource.username=<username>
spring.datasource.password=<password>
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

#JPA
spring.jpa.hibernate.ddl-auto=update
## Options : create, create-drop, validate, none
spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true

#JWT
jwt.secret.key=<Secret Key>
```