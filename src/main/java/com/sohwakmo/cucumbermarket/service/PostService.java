package com.sohwakmo.cucumbermarket.service;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.domain.Post;
import com.sohwakmo.cucumbermarket.dto.PostReadDto;
import com.sohwakmo.cucumbermarket.dto.PostUpdateDto;
import com.sohwakmo.cucumbermarket.repository.MemberRepository;
import com.sohwakmo.cucumbermarket.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
            // TODO: getId 값 수정하기
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

    public Post createPost(Post post, MultipartFile files)throws Exception{
        String fileName = saveImage(files); // 이미지 생성,저장 메서드

        if(post.getImageUrl01()==null){
            post.setImageUrl01("/files/"+fileName);
            post.setImageName01(fileName);
        }else{
            post.setImageUrl02("/files/"+fileName);
            post.setImageName02(fileName);
        }
        return postRepository.save(post);
    }



    public void deletePost(Integer id) {
        postRepository.deleteById(id);
    }

    public Post findPostByPostNo(Integer postNO) {
        return postRepository.findById(postNO).orElse(null);
    }

    @Transactional
    public Integer modifyPost(PostUpdateDto dto) {
        Post post = postRepository.findById(dto.getPostNo()).get();
        Post newPost = post.update(dto.getTitle(), dto.getContent());
        log.info(newPost.toString());
        return post.getPostNo();
    }


    private  String saveImage(MultipartFile files) throws IOException {
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + files.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        files.transferTo(saveFile);
        return fileName;
    }
}


