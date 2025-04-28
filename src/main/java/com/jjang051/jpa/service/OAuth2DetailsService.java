package com.jjang051.jpa.service;

import com.jjang051.jpa.dto.CustomUserDetails;
import com.jjang051.jpa.entity.Member;
import com.jjang051.jpa.repository.MemberRepository;
import com.jjang051.jpa.social.GoogleUserInfo;
import com.jjang051.jpa.social.KakaoUserInfo;
import com.jjang051.jpa.social.NaverUserInfo;
import com.jjang051.jpa.social.SocialUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2DetailsService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        //요기서 로그인한 정보 얻을 수 있는 곳
        log.info("userRequest : {}", userRequest);
        Map<String, Object> oAuth2UserInfo = (Map)oAuth2User.getAttributes();
        log.info("oAuth2UserInfo : {}", oAuth2UserInfo);

        log.info("userRequest.getClientRegistration().getRegistrationId()==={}",userRequest.getClientRegistration().getRegistrationId()); //google  //kakao
        String provider = userRequest.getClientRegistration().getRegistrationId();
        SocialUserInfo socialUserInfo = null;
        if(provider.equals("kakao")) {
            socialUserInfo = new KakaoUserInfo(oAuth2UserInfo);
        } else if(provider.equals("google")) {
            socialUserInfo = new GoogleUserInfo(oAuth2UserInfo);
        } else if(provider.equals("naver")) {
            socialUserInfo = new NaverUserInfo(oAuth2UserInfo);
        }

        Member returnMember = null;
        Optional<Member> findedMember = memberRepository.findByUserID(socialUserInfo.getProviderId());
        log.info("findedMember : {}", socialUserInfo.getProviderId());
        if (findedMember.isPresent()) {
            returnMember = findedMember.get(); //db에 저장되어 있는 사람...
        } else {
            Member member = Member.builder()
                    .userID(socialUserInfo.getProviderId())
                    .userName(socialUserInfo.getName())
                    .userEmail(socialUserInfo.getEmail())
                    .roles("ROLE_MEMBER")
                    .userPW(bCryptPasswordEncoder.encode(UUID.randomUUID().toString()))
                    .build();
            returnMember = memberRepository.save(member);
        }
        return new CustomUserDetails(returnMember,oAuth2UserInfo);
    }
}
