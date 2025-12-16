package com.korit.post_mini_project_back.service;

import com.korit.post_mini_project_back.entity.User;
import com.korit.post_mini_project_back.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public User createUser() {
        User user = null;
//        userMapper.findByOauth2Id()
        userMapper.insert(user);
        return user;
    }

    public String createNickname() {
        String newNickname = null;
        while (true) {
            newNickname = userMapper.createNickname();
            if (userMapper.findByNickname(newNickname) == null) {
                break;
            }
        }
        return newNickname;
    }
}
