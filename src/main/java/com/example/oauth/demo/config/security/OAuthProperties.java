package com.example.oauth.demo.config.security;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@ConfigurationProperties(prefix = "app")
public class OAuthProperties {
    private final Auth auth = new Auth();
    private final OAuth2 oAuth2 = new OAuth2();

    @Setter
    @Getter
    @RequiredArgsConstructor
    public static class Auth {
        private String tokenSecret;
        private long tokenExpirationMsec;
        private long refreshTokenExpirationMsec;

        @Builder
        public Auth(String tokenSecret, long tokenExpirationMsec, long refreshTokenExpirationMsec) {
            this.tokenSecret = tokenSecret;
            this.tokenExpirationMsec = tokenExpirationMsec;
            this.refreshTokenExpirationMsec = refreshTokenExpirationMsec;
        }
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class OAuth2 {
        private List<String> authorizedRedirectUris = new ArrayList<>();

        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
    }
}
