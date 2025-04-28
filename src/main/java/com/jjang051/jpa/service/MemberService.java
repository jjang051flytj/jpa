package com.jjang051.jpa.service;

import com.jjang051.jpa.dto.MemberDto;
import com.jjang051.jpa.entity.Member;
import com.jjang051.jpa.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    //field 주입 injection
    //@Autowired
    //MemberDao memberDao;
    //생성자 주입방식  불변객체로 만들어 쓴다.

    @Value("${file.upload}")
    private String upload;

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public int signUp(MemberDto memberDto) {
        String rawUserPW = memberDto.getUserPW(); //1234
        String encodedUserPW = bCryptPasswordEncoder.encode(rawUserPW);
        memberDto.setUserPW(encodedUserPW);
        memberDto.setRoles("ROLE_MEMBER");
        Member member = MemberDto.toEntity(memberDto);
        Member returnMEmber = memberRepository.save(member);
        if (returnMEmber != null) {
            return 1;
        }
        return 0;
    }
}
