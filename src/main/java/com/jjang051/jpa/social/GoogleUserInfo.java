package com.jjang051.jpa.social;


import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class GoogleUserInfo implements SocialUserInfo {

    private final Map<String, Object> oAuth2UserInfo;

    @Override
    public String getProviderId() {
        return getProvider()+"_"+oAuth2UserInfo.get("sub").toString();
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getEmail() {
        return oAuth2UserInfo.get("email").toString();
    }

    @Override
    public String getName() {
        return oAuth2UserInfo.get("name").toString();
    }
}
