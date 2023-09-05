package com.yzpocket.blog.controller;

import com.yzpocket.blog.dto.BlogRequestDto;
import com.yzpocket.blog.dto.BlogResponseDto;
import com.yzpocket.blog.entity.Blog;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Controller
@RequestMapping("/api")
public class BlogController {
    private final JdbcTemplate jdbcTemplate;
    public BlogController(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //Create
    @PostMapping("/posts")
    @ResponseBody
    public BlogResponseDto createBlog(@RequestBody BlogRequestDto requestDto){
        Blog blog = new Blog(requestDto);

        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        //String sql DML문장이 실행됨
        String sql = "INSERT INTO blog (title, author, contents, password) VALUES (?,?,?,?)"; // <---  각각 동적으로 아래 setString으로 결정하게함
        jdbcTemplate.update( con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, blog.getTitle()); // <--- 이것이 title
                    preparedStatement.setString(2, blog.getAuthor()); // <--- 이것이 author
                    preparedStatement.setString(3, blog.getContents()); // <--- 이것이 contents
                    preparedStatement.setString(4, blog.getPassword()); // <--- 이것이 password
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 기본키 확인
        Long idx = keyHolder.getKey().longValue();
        blog.setIdx(idx);

        BlogResponseDto blogResponseDto = new BlogResponseDto(blog);
        return blogResponseDto;
    }
}
