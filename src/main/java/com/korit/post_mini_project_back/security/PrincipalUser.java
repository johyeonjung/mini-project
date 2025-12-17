package com.korit.post_mini_project_back.security;

import com.korit.post_mini_project_back.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import java.util.Collection;
import java.util.Map;

@Getter
public class PrincipalUser extends DefaultOAuth2User {
        private User user;

    public PrincipalUser(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey, User user) {
        super(authorities, attributes, nameAttributeKey);
        this.user = user;
    }
}

