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

    public List<BlogResponseDto> getBlogs() {
        // DB 조회
        return blogRepository.findAll().stream().map(BlogResponseDto::new).toList(); //전체 목록 가져오는것
        // <Stream 부분 해석, 글이 하나씩 추출되고 map()의해 변환이 되는데,
        // BlogResponseDto의 생성자 중에서 Blog를 파라미터로 가지고 있는 생성자가 호출되고 하나씩 변환되고,
        // toList()에 의해 리스트로 변환된다.
    }

    public List<BlogResponseDto> getBlogsByKeyword(String keyword) {
        // DB 조회 by 키워드
        return blogRepository.findAllByContentsContainsOrderByModifiedAtDesc(keyword).stream().map(BlogResponseDto::new).toList();
    }

    @Transactional //변경감지->업데이트하려면 꼭 Transaction 환경으로 객체가 영속성을 가지도록 (MANAGED) 상태가 되도록 꼭 붙여줘야함 -> 없엔 상태로 테스트했더니 업데이트가 되지 않음.
    public Long updateBlog(Long id, BlogRequestDto requestDto) {
        // 해당 글이 DB에 존재하는지 확인
        Blog blog = findBlog(id);

        // 내용 수정
        blog.update(requestDto); //변경 감지가 적용됨

        return id;
    }

    public Long deleteBlog(Long id) {
        // 해당 글이 DB에 존재하는지 확인
        Blog blog = findBlog(id);

        // 글 삭제
        blogRepository.delete(blog); //지울 객체 넣어준다.

        return id;
    }
    //update, delete에서 중복되기 때문에 별도 메소드로 구성

    private Blog findBlog(Long id){
        return  blogRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("선택한 정보는 존재하지 않습니다.")
        );
    }
}