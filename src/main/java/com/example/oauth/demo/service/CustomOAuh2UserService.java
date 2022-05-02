package com.example.oauth.demo.service;

import com.example.oauth.demo.config.security.UserPrincipal;
import com.example.oauth.demo.domain.entity.User;
import com.example.oauth.demo.domain.repository.UserRepository;
import com.example.oauth.demo.entity.OAuth2UserInfo;
import com.example.oauth.demo.entity.OAuth2UserInfoFactory;
import com.example.oauth.demo.exception.OAuth2AuthenticationProcessingException;
import com.example.oauth.demo.type.AuthProvider;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuh2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(userRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

        if (StringUtils.isBlank(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("OAuth2 공급자에서 이메일을 찾을 수 없습니다.");
        }

        Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (!AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId()).equals(user.getProvider())) {
                throw new OAuth2AuthenticationProcessingException(user.getProvider() + "계정을 사용하기 위해서 로그인을 해야합니다.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registrationNewUser(userRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private User registrationNewUser(OAuth2UserRequest userRequest, OAuth2UserInfo oAuth2UserInfo) {
        return userRepository.save(User.builder()
                .username(oAuth2UserInfo.getName())
                .email(oAuth2UserInfo.getEmail())
                .emailVerified(true)
                .provider(AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId()))
                .providerId(oAuth2UserInfo.getId())
                .build());
    }

    private User updateExistingUser(User user, OAuth2UserInfo oAuth2UserInfo) {
        return userRepository.save(user.update(oAuth2UserInfo.getName()));
    }
}
