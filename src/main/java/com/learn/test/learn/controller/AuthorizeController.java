package com.learn.test.learn.controller;
import com.learn.test.learn.dto.AccessTokenDTO;
import com.learn.test.learn.dto.GithubUser;

import com.learn.test.learn.mapper.UserMapper;
import com.learn.test.learn.model.User;
import com.learn.test.learn.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Controller
public class AuthorizeController {

        @Autowired
        private GithubProvider githubProvider;

        @Value("${github.client.id}")
        private String clientId;

        @Value("${github.client.secret}")
        private String clientSecret;

        @Value("${github.client.uri}")
        private String redirectUri;

        @Autowired
        private UserMapper userMapper;


        @GetMapping("/callback")
        public String callback(@RequestParam(name = "code") String code,
                               @RequestParam(name = "state") String state,
                               HttpServletRequest request,
                               HttpServletResponse response) {
            AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
            accessTokenDTO.setClient_id(clientId);
            accessTokenDTO.setClient_secret(clientSecret);
            accessTokenDTO.setCode(code);
            accessTokenDTO.setRedirect_uri(redirectUri);
            accessTokenDTO.setState(state);
            String accessToken = githubProvider.getAccessToken(accessTokenDTO);
            GithubUser githubUser = githubProvider.getUser(accessToken);
            if(githubUser!=null){
                User user = new User();
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                user.setName(githubUser.getName());
                user.setAccount_Id(String.valueOf(githubUser.getId()));
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                userMapper.insert(user);
                response.addCookie(new Cookie("token",token));
                return "redirect:/";
                //登录成功
            }
            else{
                //登录失败
                return "redirect:/";

            }
        }


}
