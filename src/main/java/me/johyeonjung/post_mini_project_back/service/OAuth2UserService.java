package me.johyeonjung.post_mini_project_back.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User =  super.loadUser(userRequest);
        String clientName = userRequest.getClientRegistration().getClientName();


        Map<String, Object> attribute = new LinkedHashMap<>();
        if ("NAVER".equalsIgnoreCase(clientName)) {
        Map<String, Object> response = (Map<String, Object>) oAuth2User

        }
        return new DefaultOAuth2User(author);
    }
}
