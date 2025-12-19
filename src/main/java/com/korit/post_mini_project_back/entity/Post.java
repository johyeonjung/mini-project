package com.korit.post_mini_project_back.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private int postId;
    private String content;
    private String visibility;
    private int userId;
    private LocalDateTime createdAt;

    // 책상을 조금만 치워볼까??

    private User user;
    private List<ImageFile> imageFiles;
}
