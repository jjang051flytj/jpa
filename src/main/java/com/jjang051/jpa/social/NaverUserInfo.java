package com.jjang051.jpa.social;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class NaverUserInfo implements SocialUserInfo {
    private final Map<String, Object> oAuth2UserInfo;

    //이거가지고 db에 회원가입 userID
    @Override
    public String getProviderId() {
        Map<String, Object> response = (Map)oAuth2UserInfo.get("response");
        return getProvider()+"_"+response.get("id").toString();
    }
    @Override
    public String getProvider() {
        return "naver";
    }
    @Override
    public String getEmail() {
        Map<String, Object> response = (Map)oAuth2UserInfo.get("response");
        return response.get("email").toString();
    }

    @Override
    public String getName() {
        Map<String, Object> response = (Map)oAuth2UserInfo.get("response");
        return response.get("name").toString();
    }
}
