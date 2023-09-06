//package com.yzpocket.blog.entity;
//
//public enum UserRoleEnum { // 클래스 생성 시 enum으로 선택할것, 또는 enum으로 수정
//    USER(Authority.USER),  // 사용자 권한
//    ADMIN(Authority.ADMIN);  // 관리자 권한
//
//    private final String authority;
//
//    UserRoleEnum(String authority) { // authority를 인자로 입력하면 해당 생성자를 통해 위 권한 변수 중 하나인 객체(유저)가 생성되게 된다.
//        this.authority = authority;
//    }
//
//    public String getAuthority() { // 나중에 권한 값을 가져오기 위한 메소드
//        return this.authority;
//    }
//
//    public static class Authority {
//        public static final String USER = "ROLE_USER";
//        public static final String ADMIN = "ROLE_ADMIN";
//    }
//}