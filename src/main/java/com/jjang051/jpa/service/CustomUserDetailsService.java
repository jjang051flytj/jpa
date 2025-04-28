package com.jjang051.jpa.service;

import com.jjang051.jpa.dto.CustomUserDetails;
import com.jjang051.jpa.dto.CustomUserDetails02;
import com.jjang051.jpa.dto.MemberDto;
import com.jjang051.jpa.entity.Member;
import com.jjang051.jpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String userID) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername");
        Optional<Member> optionalMember = memberRepository.findByUserID(userID);
        if(optionalMember.isPresent()) {
            return new CustomUserDetails(optionalMember.get());
            //return new CustomUserDetails02(Member.toDto(optionalMember.get()));
        }
        throw new UsernameNotFoundException("아이디 패스워드 확인해주세요.");

    }
}
