package com.learn.test.learn.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String user;
    private long id;
    private String bio;
    private String name;
    private String avatarUrl;

}
