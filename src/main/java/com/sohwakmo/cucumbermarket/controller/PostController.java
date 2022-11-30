package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.domain.Post;
import com.sohwakmo.cucumbermarket.dto.PostReadDto;
import com.sohwakmo.cucumbermarket.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class    PostController {

    private final PostService postService;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 2, sort = "postNo", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(required = false,defaultValue = "")String searchText){
//        List<PostReadDto> list = postService.listAll();
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
    public String create(Model model, @RequestParam(required = false)Integer id){ // 새글 작성일경우 id가 필요 없으므로 필수 항복은 아니므로 false를 준다.
        if(id==null){
            model.addAttribute("board", new Post());
        }else{
            Post post = postService.findPostById(id);
            model.addAttribute("post", post);
        }
        return "/post/detail";
    }
}
