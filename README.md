# Spring Boot Practice!
블로그 백엔드 서버 만들기 연습 저장소

## 🖥️ 스터디 저장소 소개
`Spring Framework`부터 시작하여 `MVC 디자인 패턴`, 그리고 최종적으로 `Spring Boot`를 활용한 회원가입, 로그인, 게시글 쓰기, 수정, 삭제, 조회 등 다양한 API를 구현해보는 연습 프로젝트 저장소 입니다. 프레임 워크를 사용하면서 코드가 변화하는 모습들을 살펴보고자 기록하고있습니다.
* 문제를 통한 요구사항 실습
* 코드 수정 및 기능 구현을 통해 부족한 부분을 체크하는 테스트
* 1주차 과제 리뷰 -> https://ohnyong.notion.site/Lv-1-cbd646bdc47a40ea90fe348ff2b8242f?pvs=4
* 2주차 과제 리뷰 -> https://ohnyong.notion.site/Lv-2-bb787094ac8141c9a88eaa15334ec7bf?pvs=4
* 추가중..

## 👋🏻 Contact
- **Email** : citefred@yzpocket.com
- **Blog** : https://www.citefred.com

## 🕰️ 학습 기간
* 23.08.29 ~ 23.09.14

## ⚙️ 개발 환경
- **MainLanguage** : `Java - JDK 17`
- **IDE** : `IntelliJ IDEA Ultimate`
- **Framework** : `SpringBoot`
- **Database** : `MySQL`
- **SERVER** : `Spring Inner Server(TOMCAT)` 
- **TEST** : `POSTMAN API Request`

## 📌 주요 기능
### 학습한 기능
* 회원 부분
    - 회원가입
    - 로그인

* 게시판 기능
    - 게시글 쓰기
    - 게시글 모두 조회, 선택 조회
    - 게시글 선택 수정
    - 게시글 선택 삭제

* Spring Security 프레임워크 기반의 JWT 토큰 방식 로그인으로 변경중..
* 상품 관련 관계형 데이터베이스 실습 추가 예정

## ⚠️ 주의
#### 추적 예외
* src/main/resources/application.properties 파일은 DB 접속 정보가 있어 추적이 제외되어 있습니다.
* 테스트를 진행 하시려면 위 경로와 파일(application.properties)을 생성해주세요.
- 다음과 코드를 입력해주세요 < ... > 부분을 작성해주셔야 합니다. "<", ">" 괄호도 제거되어야 합니다.
- ex) spring.datasource.username=roots
```
spring.datasource.url=jdbc:mysql://localhost:3306/blog
spring.datasource.username=<USERNAME>
spring.datasource.password=<PASSWORD>
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```
