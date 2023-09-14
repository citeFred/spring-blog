package com.yzpocket.blog.dto;

import lombok.Getter;
import lombok.Setter;

// 게시글과 댓글 담은 Dto
@Getter
@Setter
public class BlogCommentResponseAllDto {
    BlogCommentRequestDto postList;
    public BlogCommentResponseAllDto(BlogCommentRequestDto postList){
        this.postList = postList;
    }

}
