package com.jjang051.jpa.controller;

import com.jjang051.jpa.dto.MemberDto;
import com.jjang051.jpa.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/signup")
    public String signin(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "member/signup";
    }
    @PostMapping("/signup")
    public String signin(@Valid @ModelAttribute MemberDto memberDto,
                         BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            System.out.println("========================111");
            return "member/signup";  //forward
        }
        int result = memberService.signUp(memberDto);
        log.info("result={}", result);
        if(result < 1) {
            return "member/signup";
        }
        return "redirect:/";
    }
    @GetMapping("/login")
    public String login() {
        return "member/login";
    }
}
