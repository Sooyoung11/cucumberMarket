package com.sohwakmo.cucumbermarket.service;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.domain.Post;
import com.sohwakmo.cucumbermarket.domain.Reply;
import com.sohwakmo.cucumbermarket.dto.PostReadDto;
import com.sohwakmo.cucumbermarket.dto.PostUpdateDto;
import com.sohwakmo.cucumbermarket.repository.MemberRepository;
import com.sohwakmo.cucumbermarket.repository.PostRepository;

import com.sohwakmo.cucumbermarket.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    private  final ReplyRepository replyRepository;

    public List<Post> readByClickCountDesc(){
        log.info("readByClickCountDesc()");
        return postRepository.findByOrderByClickCountDescPostNoDesc();
    }

    @Transactional(readOnly = true)
    /**
     * 내용, 제목으로 검색해서 결과를 페이지로 가져오기
     * @param searchText 검색 내용
     * @return 결과 페이지들을 리턴
     */
    public List<PostReadDto> searchPost(String searchText,String address){
        List<Post> postList =  postRepository.findByTitleIgnoreCaseContainingOrContentIgnoreCaseContainingOrMemberNicknameIgnoreCaseContainingOrderByPostNoDesc(searchText,searchText,searchText);
        List<PostReadDto> list = new ArrayList<>();
        for(Post p : postList){
            if(address.equals("전국")){
                Member member = memberRepository.findById(p.getMember().getMemberNo()).get();
                PostReadDto dto = PostReadDto.builder()
                        .postNo(p.getPostNo()).title(p.getTitle()).writer(member.getNickname()).createdTime(p.getCreatedTime()).clickCount(p.getClickCount())
                        .build();
                list.add(dto);
            }else {
                String memberAddress = p.getMember().getAddress();
                String memberDetailAddress[] = memberAddress.split(" ");
                if (memberDetailAddress[0].equals(address)) {
                    Member member = memberRepository.findById(p.getMember().getMemberNo()).get();
                    PostReadDto dto = PostReadDto.builder()
                            .postNo(p.getPostNo()).title(p.getTitle()).writer(member.getNickname()).createdTime(p.getCreatedTime()).clickCount(p.getClickCount())
                            .build();
                    list.add(dto);
                }
            }
        }
        return list;
    }

    @Transactional
    public Post findPostByIdandUpdateClickCount(Integer id, Integer clickCount) {
         Post post = postRepository.findById(id).orElse(null);
         post = post.plusClickCount(clickCount + 1);
         return post;
    }

    /**
     * 사진을 넣고 작성을한경우
     * @param post 제목, 내용
     * @param files 사진
     * @return 변경된 객체
     * @throws Exception 사진이 있냐 없냐 에따라 exception 발생
     */
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

    /**
     * 사진을 넣지않는 일반적인 게시물
     * @param post 제목,내용
     * @return 변경된 게시물 객체
     */
    public Post createPost(Post post){
        return postRepository.save(post);
    }



    public void deletePost(Integer id) {
        List<Reply>list = replyRepository.findByPostPostNoOrderByReplyNoDesc(id).stream().toList();
        for(Reply r : list){
            replyRepository.delete(r);
        }
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

        String fileName = files.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        files.transferTo(saveFile);
        return fileName;
    }

    /**
     * 사진 삭제를 누를경우 사진 삭제를 바로바로 해준다.
     * @param imageSrc
     * @return 몇번 사진을 삭제했는지 알려준다.
     * @throws Exception
     */
    @Transactional
    public String  chekImageNumandDeleteImage(String imageSrc) throws Exception{
        Post post =  postRepository.findByImageName01(imageSrc);
        if(post == null){
            Post post2 = postRepository.findByImageName02(imageSrc);
            post2.setImageUrl02("");
            post2.setImageName02("");
            extractImage(imageSrc);
            return "2번사진 삭제완료";
        }else{
            post.setImageName01("");
            post.setImageUrl01("");
            extractImage(imageSrc);
            return "1번사진 삭제완료";
        }
    }

    /**
     * static  폴더 안에 있는 사진 경로를 찾아내서 삭제
     * @param imageSrc 전달받은 이미지 경로
     * @throws IOException
     */
    private void extractImage(String imageSrc) throws IOException {
        // 경로는 능동적으로 변경
        Path filePath = Paths.get(System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files\\" + imageSrc);
        Files.delete(filePath);
    }

    @Transactional()
    public String modifyImage01(Post post, MultipartFile data)throws Exception {
        String fileName = saveImage(data);
//        extractImage(post.getImageName01());
        log.info(fileName);
        post.setImageName01(fileName);
        post.setImageUrl01("/files/"+fileName);
        return "files/"+fileName;
    }

    @Transactional()
    public String modifyImage02(Post post, MultipartFile data)throws Exception {
        String fileName = saveImage(data);
//        extractImage(post.getImageName02());
        log.info(fileName);
        post.setImageUrl02("/files/"+fileName);
        post.setImageName02(fileName);
        return "files/"+fileName;
    }


    @Transactional
    public String insertImage(Post post, MultipartFile data)throws Exception {
        String fileName = saveImage(data);
        if(post.getImageUrl01()==null) {
            log.info(fileName);
            post.setImageUrl01("/files/"+fileName);
            post.setImageName01(fileName);
            return "1번이미지 삽입 완료";
        }else {
            post.setImageUrl02("/files/"+fileName);
            post.setImageName02(fileName);
            return "2번이미지 삽입 완료";
        }

    }
}


