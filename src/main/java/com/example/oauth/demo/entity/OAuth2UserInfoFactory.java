package com.example.oauth.demo.entity;

import com.example.oauth.demo.exception.OAuth2AuthenticationProcessingException;
import com.example.oauth.demo.type.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (AuthProvider.kakao.toString().equalsIgnoreCase(registrationId)) {
            return new KakaoOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException(registrationId + "로그인은 지원하지 않습니다.");
        }
    }

}
