package com.yzpocket.blog.service;

import com.yzpocket.blog.dto.BlogRequestDto;
import com.yzpocket.blog.dto.BlogResponseDto;
import com.yzpocket.blog.entity.Blog;
import com.yzpocket.blog.repository.BlogRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BlogService {
    private final BlogRepository blogRepository; // <- Blog 타입으로 SimpleRepository 구현체 객체가 들어옴

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public BlogResponseDto createBlog(BlogRequestDto requestDto, String username) {
        // RequestDto -> Entity
        Blog blog = new Blog(requestDto, username);

        // DB 저장
        Blog saveBlog = blogRepository.save(blog);

        // Entity -> ResponseDto
        BlogResponseDto blogResponseDto = new BlogResponseDto(saveBlog);

        return blogResponseDto;
    }

    // 게시글 조회
    public List<BlogResponseDto> getBlogs() {
        return blogRepository.findAll().stream().map(BlogResponseDto::new).toList(); //전체 목록 가져오는것
    }

    // 선택 게시글 조회 by 글번호
    public BlogResponseDto getOneBlog(Long id) {
        Blog blog = findBlog(id); // 글 존재 확인 검증 메소드
        return new BlogResponseDto(blog);
    }

    // 선택 게시글 수정 by 글번호
    @Transactional
    public Blog updateBlog(Long id, BlogRequestDto requestDto, String username) {
        try {
            Blog blog = findBlog(id); // 글 존재 확인 검증 메소드
            if (blog.getUsername().equals(username)) {
                blog.update(requestDto, username); // 변경 감지가 적용됨
                return blog; // 수정된 블로그 반환
            } else {
                throw new IllegalAccessException("수정할 권한이 없습니다."); // 다른 사용자가 수정하지 못하게 방지
            }
        } catch (IllegalAccessException e) {
            // 수정 실패 시 예외 처리
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    // 선택 게시글 삭제 by 글번호
    @Transactional //변경감지->삭제도 마찬가지 Transaction 환경필요
    public ResponseEntity<String> deleteBlog(Long id, String username) {
        try {
            Blog blog = findBlog(id); // 글 존재 확인 검증 메소드
            if (blog.getUsername().equals(username)) {
                blogRepository.delete(blog); // 변경 감지가 적용됨
                return ResponseEntity.ok("{\"msg\": \"삭제 성공\", \"statusCode\": 200}");
            } else {
                return ResponseEntity.ok("{\"msg\": \"삭제 실패\", \"statusCode\": 444}");
            }
        } catch (IllegalArgumentException e) {
            // 삭제 실패 시 예외 처리
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // 글 존재 확인 메소드(중복제거용)
    private Blog findBlog(Long id){
        return blogRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 정보는 존재하지 않습니다.")
        );
    }
}