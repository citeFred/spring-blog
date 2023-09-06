package com.yzpocket.blog.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDto {
    private String username;
    private String password;
    //private String email; //아직미구현
    //private boolean admin = false; //아직미구현
    //private String adminToken = ""; //아직미구현
}
