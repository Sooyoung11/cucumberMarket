package com.sohwakmo.cucumbermarket.service;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.domain.Post;
import com.sohwakmo.cucumbermarket.dto.PostReadDto;
import com.sohwakmo.cucumbermarket.repository.MemberRepository;
import com.sohwakmo.cucumbermarket.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    /**
     * 내용, 제목으로 검색해서 결과를 페이지로 가져오기
     * @param searchText 검색 내용
     * @return 결과 페이지들을 리턴
     */
    public List<PostReadDto> searchPost(String searchText){
        List<Post> postList =  postRepository.findByTitleIgnoreCaseContainingOrContentIgnoreCaseContainingOrderByPostNoDesc(searchText,searchText);
        List<PostReadDto> list = new ArrayList<>();
        for(Post p : postList){
            Member member = memberRepository.findById(p.getMember().getMemberNo()).get();
            PostReadDto dto = PostReadDto.builder()
                    .postNo(p.getPostNo()).title(p.getTitle()).writer(member.getNickname()).createdTime(p.getCreatedTime()).clickCount(p.getClickCount())
                    .build();
            list.add(dto);
        }
        return list;
    }

    public Post findPostById(Integer id) {
        return postRepository.findById(id).orElse(null);
    }
}
