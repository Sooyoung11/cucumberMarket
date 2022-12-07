package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.domain.Post;
import com.sohwakmo.cucumbermarket.dto.PostCreateDto;
import com.sohwakmo.cucumbermarket.dto.PostReadDto;
import com.sohwakmo.cucumbermarket.dto.PostUpdateDto;
import com.sohwakmo.cucumbermarket.service.MemberService;
import com.sohwakmo.cucumbermarket.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class    PostController {

    private final PostService postService;
    private final MemberService memberService;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 10, sort = "postNo", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(required = false,defaultValue = "")String searchText){
        List<PostReadDto> list = postService.searchPost(searchText);
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        final Page<PostReadDto> page = new PageImpl<>(list.subList(start, end), pageable, list.size());
        int startPage = Math.max(1, page.getPageable().getPageNumber() - 9);
        int endPage = Math.min(page.getTotalPages(), page.getPageable().getPageNumber() + 9);
        int a = page.getPageable().getPageNumber();
        model.addAttribute("list", page);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "/post/list";
    }

    @GetMapping("/detail")
    public String detail(Model model, @RequestParam Integer postNo,@RequestParam(required = false, defaultValue = "-1")Integer clickCount){
        if(clickCount==-1){ // modify 에서 넘어올경우 파라미터 초기화
            clickCount = postService.findPostByPostNo(postNo).getClickCount()-1;
        }
        Post post = postService.findPostByIdandUpdateClickCount(postNo,clickCount); // detail page 로 올경우 조회수도 같이 증가.
        String nickname = post.getMember().getNickname();
        log.info("컨트롤러 Post={}",post);
        model.addAttribute("post", post);
        model.addAttribute("nickname", nickname);
        return "/post/detail";
    }

    @GetMapping("/create")
    public String create(Model model, @RequestParam(required = false)Integer id){ // 새글 작성일경우 id가 필요 없으므로 필수 항복은 아니므로 false를 준다.
        if(id==null){
            model.addAttribute("post", new Post());
            return "/post/create";
        }else{
            Post post = postService.findPostByPostNo(id);
            model.addAttribute("post", post);
            return "/post/detail";
        }
    }


    @PostMapping("/create")
    public String create(PostCreateDto dto, Integer memberNo, @RequestParam(value = "files", required = false) List<MultipartFile> files) throws Exception {

        Member member = memberService.findMemberByMemberNo(memberNo);
        log.info(member.toString());
        Post post = PostCreateDto.builder()
                .title(dto.getTitle()).content(dto.getContent()).clickCount(dto.getClickCount()).member(member).build().toEntity();
        for (MultipartFile multipartFile : files) {
            log.info("files={}", files);
            Post newPost=postService.createPost(post,multipartFile);
        }

        return "redirect:/post/list";
    }

    @PostMapping("/delete")
    public String delete(Integer id){
        postService.deletePost(id);
        return "redirect:/post/list";
    }

    @GetMapping("/modify")
    public String modify(Model model, Integer id){
        Post post = postService.findPostByPostNo(id);
        model.addAttribute("post", post);
        return "/post/modify";
    }

    @PostMapping("/modify")
    public String modify(PostUpdateDto dto,RedirectAttributes attrs, @RequestParam("files") List<MultipartFile> files){
        Integer postNo = postService.modifyPost(dto);
        log.info("postNo={}",postNo);
        attrs.addAttribute("postNo",postNo);
        return "redirect:/post/detail";
    }
}