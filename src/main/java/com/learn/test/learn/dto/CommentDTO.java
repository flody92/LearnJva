package com.learn.test.learn.dto;

import com.learn.test.learn.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commmentator;
    private Long gmtCreate;
    private Long gmtModified;
    private long likeCount;
    private String content;
    private User user;
}
