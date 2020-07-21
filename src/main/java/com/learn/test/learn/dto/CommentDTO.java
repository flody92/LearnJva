package com.learn.test.learn.dto;

import com.learn.test.learn.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private int id;
    private int parentId;
    private Integer type;
    private int commmentator;
    private Long gmtCreate;
    private Long gmtModified;
    private long likeCount;
    private String content;
    private User user;
}
