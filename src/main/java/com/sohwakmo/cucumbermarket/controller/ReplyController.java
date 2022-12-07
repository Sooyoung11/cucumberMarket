package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.dto.ReplyReadDto;
import com.sohwakmo.cucumbermarket.dto.ReplyRegisterDto;
import com.sohwakmo.cucumbermarket.dto.ReplyUpdateDto;
import com.sohwakmo.cucumbermarket.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping // 댓글 create
    public ResponseEntity<Integer> registerReply(@RequestBody ReplyRegisterDto dto) {
        log.info("dto={}", dto);

        Integer replyNo = replyService.create(dto);
        return ResponseEntity.ok(replyNo);
    }

    @GetMapping("/all/{postNo}") // POST 게시물에 달린 모든 댓글 list 보기
    public ResponseEntity<List<ReplyReadDto>> readAllReplies(@PathVariable Integer postNo, boolean secret_reply) {
        log.info("readAllReplies(postNo={})", postNo);

        List<ReplyReadDto> list = replyService.selectByid(postNo);
        log.info("# of list = {} ", list.size());

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{replyNo}") // 수정하기 버튼 클릭 시 detail 화면 보기
    public ResponseEntity<ReplyReadDto> getReply(@PathVariable Integer replyNo) {
        log.info("getReply(replyNo={})", replyNo);

        ReplyReadDto dto = replyService.readReply(replyNo);

        return ResponseEntity.ok(dto);
    }


    @DeleteMapping("/{replyNo}") // 댓글 delete
    public ResponseEntity<Integer> deleteReply(@PathVariable Integer replyNo){
        log.info("deleteReply(replyNo={})",replyNo);

        Integer result = replyService.delete(replyNo);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{replyNo}") // 댓글 update
    public ResponseEntity<Integer> updateReply(@PathVariable Integer replyNo, @RequestBody ReplyUpdateDto dto){
        log.info("putReply=(replyNo={}, dto={})", replyNo, dto);

        dto.setReplyNo(replyNo);
        Integer result = replyService.update(dto);

        return ResponseEntity.ok(result);
    }
}