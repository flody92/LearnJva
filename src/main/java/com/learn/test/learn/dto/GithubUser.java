package com.learn.test.learn.dto;

public class GithubUser {
    private String user;
    private long id;
    private String bio;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getUser() {
        return user;
    }

    public long getId() {
        return id;
    }

    public String getBio() {
        return bio;
    }

    @Override
    public String toString() {
        return "GithubUser{" +
                "id=" + id +
                ", bio='" + bio + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
