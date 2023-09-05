package com.yzpocket.blog.controller;

import com.yzpocket.blog.dto.BlogRequestDto;
import com.yzpocket.blog.dto.BlogResponseDto;
import com.yzpocket.blog.service.BlogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BlogController {
    private final BlogService blogService; //*** 약한 결합으로 바꿔준다.
    public BlogController(BlogService blogService){ //외부에서 주입해야 한다했는데 어디서 누가 주입해줘야 하는것이지?? 메인은 없는데.. -> IoC 컨테이너??? Bean Type이 없다? = Bean이 등록되어야 한다는 소리.
        this.blogService = blogService;
    }

    //Create
    @PostMapping("/blog")
    public BlogResponseDto createBlog(@RequestBody BlogRequestDto requestDto) {
        return blogService.createBlog(requestDto);
    }

    //Read
    @GetMapping("/blogs")
    public List<BlogResponseDto> getBlogs() {
        return blogService.getBlogs();
    }

    @GetMapping("/blog/contents")
    public List<BlogResponseDto> getBlogsByKeyword(String keyword) {
        return blogService.getBlogsByKeyword(keyword);
    }

    //Update
    @PutMapping("/blog/{id}")
    public Long updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
        return blogService.updateBlog(id, requestDto);
    }

    //Delete
    @DeleteMapping("/blog/{id}")
    public Long deleteBlog(@PathVariable Long id) {
        return blogService.deleteBlog(id);
    }
}

