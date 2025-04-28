package com.jjang051.jpa.controller;

import com.jjang051.jpa.dto.CustomUserDetails;
import com.jjang051.jpa.dto.QuestionDto;
import com.jjang051.jpa.entity.Question;
import com.jjang051.jpa.repository.QuestionRepository;
import com.jjang051.jpa.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
@Slf4j
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/write")
    public String write() {
        return "question/write";
    }

    @PostMapping("/write")
    //@ResponseBody
    public String write(@ModelAttribute QuestionDto questionDto,
                        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        //SecurityContextHolder.getContext().setAuthentication()
        questionDto.setWriter(customUserDetails.getLoggedMember());
        questionService.write(questionDto);
        return "redirect:../question/list";
    }
    @GetMapping("/list")
    //@ResponseBody
    public String list(Model model) {
        List<Question> list = questionService.list();
        model.addAttribute("list",list);
        return "question/list";
    }
    @GetMapping("/view/{id}")
    //@ResponseBody
    public String view(@PathVariable("id") Integer id, Model model) {
        //front에서 넘어오는거는 dto로 받아서 service로 던져주면 entity로바꿔서 저장
        //db애서 조회된 entity는 dto로 바꿔서 front에 전달한다.
        Question question = questionService.view(id);
        QuestionDto questionDto = null;
        if(question != null) {
//            questionDto = QuestionDto.builder()
//                    .id(question.getId())
//                    .title(question.getTitle())
//                    .content(question.getContent())
//                    .regDate(question.getRegDate())
//                    .build();

            questionDto = Question.toDto(question);
        }
        model.addAttribute("questionDto",questionDto);
        //log.info(questionDto.getCommentList().get(0).getContent());

        return "question/view";
    }
    @GetMapping("/modify/{id}")
    public String modify(@PathVariable("id") Integer id, Model model) {
        Question question = questionService.view(id);
        QuestionDto questionDto = null;
        if(question != null) {
            questionDto = Question.toDto(question);
        }
        model.addAttribute("questionDto",questionDto);
        return "question/modify";
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute QuestionDto questionDto) {
        //id , title, content
        Question question = questionService.modify(questionDto);
        log.info("question==={}",question);
        if(question != null) {
            log.info("널이 아님");
            return "redirect:/question/list";
        }
        log.info("널임");
        return "redirect:/question/modify/"+questionDto.getId();
    }
}
