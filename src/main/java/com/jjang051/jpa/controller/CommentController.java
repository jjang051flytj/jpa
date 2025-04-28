package com.jjang051.jpa.controller;

import com.jjang051.jpa.dto.CommentDto;
import com.jjang051.jpa.dto.CustomUserDetails;
import com.jjang051.jpa.entity.Comment;
import com.jjang051.jpa.entity.Question;
import com.jjang051.jpa.repository.CommentRepository;
import com.jjang051.jpa.service.CommentService;
import com.jjang051.jpa.service.QuestionService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final QuestionService questionService;

    //comment/write/10
    @PostMapping("/write/{id}")
    public String write(@PathVariable("id") int id,
                        @RequestParam String content,
                        @AuthenticationPrincipal CustomUserDetails customUserDetails
                        ) {
        Question question = questionService.view(id);
        commentService.write(content, question,customUserDetails.getLoggedMember());
        return "redirect:/question/view/" + id;
    }

    @PostMapping("/write-ajax/{id}")
    @ResponseBody
    public Map<String, Object> writeAjax(@PathVariable("id") int id,
                                         @RequestBody CommentDto commentDto,
                                         @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        Question question = questionService.view(id);
        Comment comment =
                commentService.write(commentDto.getContent(), question,
                        customUserDetails.getLoggedMember());
        Map<String, Object> result = new HashMap<>();
        //되도록 entity는 front에 넘기지 마라....
        //front랑 데이터 주고 받을때는 dto써라... entity 넘기면 참조하는 것 때문에
        //오류날 수 있다...
        CommentDto responseCommentDto = CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .writer(comment.getWriter().getUserName())
                .regDate(comment.getRegDate())
                .build();

        result.put("comment", responseCommentDto);
        //
        return result;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Map<String,Object> delete(@PathVariable("id") Integer id) {
        commentService.delete(id);
        //만들어야 한다. respository에 deleteById(아이디 번호 던지면 된다.)
        Map<String, Object> result = new HashMap<>();
        result.put("isCommentDelete", true);
        return result;
    }
}
