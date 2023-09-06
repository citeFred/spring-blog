package com.yzpocket.blog.service;

import com.yzpocket.blog.dto.BlogRequestDto;
import com.yzpocket.blog.dto.BlogResponseDto;
import com.yzpocket.blog.entity.Blog;
import com.yzpocket.blog.repository.BlogRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    private final BlogRepository blogRepository; // <- Blog 타입으로 SimpleRepository 구현체 객체가 들어옴

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public BlogResponseDto createBlog(BlogRequestDto requestDto) {
        // RequestDto -> Entity
        Blog blog = new Blog(requestDto);

        // DB 저장
        Blog saveBlog = blogRepository.save(blog);

        // Entity -> ResponseDto
        BlogResponseDto blogResponseDto = new BlogResponseDto(saveBlog);

        return blogResponseDto;
    }

    // 게시글 조회
    public List<BlogResponseDto> getBlogs() {
        return blogRepository.findAll().stream().map(BlogResponseDto::new).toList(); //전체 목록 가져오는것
        // <Stream 부분 해석, 글이 하나씩 추출되고 map()의해 변환이 되는데,
        // BlogResponseDto의 생성자 중에서 Blog를 파라미터로 가지고 있는 생성자가 호출되고 하나씩 변환되고,
        // toList()에 의해 리스트로 변환된다.
    }

    // 선택 게시글 조회 by 글번호
    public BlogResponseDto getBlogById(Long id) {
        Blog blog = findBlog(id); // 글 존재 확인 검증 메소드
        return new BlogResponseDto(blog);
    }

    // 선택 게시글 수정 by 글번호
    @Transactional //변경감지->업데이트하려면 꼭 Transaction 환경으로 객체가 영속성을 가지도록 (MANAGED) 상태가 되도록 꼭 붙여줘야함 -> 없엔 상태로 테스트했더니 업데이트가 되지 않음.
    public void updateBlog(Long id, BlogRequestDto requestDto) {
        Blog blog = findBlog(id); // 글 존재 확인 검증 메소드
        blog.update(requestDto); // 변경 감지가 적용됨
    }

    // 선택 게시글 삭제 by 글번호
    @Transactional
    public void deleteBlog(Long id) {
        Blog blog = findBlog(id); // 글 존재 확인 검증 메소드
        blogRepository.delete(blog); // 지울 객체 넣어준다.
    }

    // 글 존재 확인 메소드(중복제거용)
    private Blog findBlog(Long id){
        return blogRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 정보는 존재하지 않습니다.")
        );
    }
}