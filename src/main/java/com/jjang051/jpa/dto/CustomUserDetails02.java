package com.jjang051.jpa.dto;

import com.jjang051.jpa.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
//@RequiredArgsConstructor
public class CustomUserDetails02 implements UserDetails {
    // loggedMemberDto
    private final MemberDto loggedMemberDto;  //userID,userPW,userName,userEmail

    public CustomUserDetails02(MemberDto loggedMemberDto) {
        this.loggedMemberDto = loggedMemberDto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add((GrantedAuthority) () -> loggedMemberDto.getRoles());
        return authorities;
    }

    @Override
    public String getPassword() {
        return loggedMemberDto.getUserPW();
    }

    @Override
    public String getUsername() {
        return loggedMemberDto.getUserID();
    }
}
