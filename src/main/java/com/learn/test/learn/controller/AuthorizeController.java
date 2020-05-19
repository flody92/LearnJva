package com.learn.test.learn.controller;
import com.learn.test.learn.dto.AccessTokenDTO;
import com.learn.test.learn.dto.GithubUser;

import com.learn.test.learn.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


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



        @GetMapping("/callback")
        public String callback(@RequestParam(name = "code") String code,
                               @RequestParam(name = "state") String state,
                               HttpServletRequest request) {
            AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
            String accessToken = githubProvider.getAccessToken(accessTokenDTO);
            GithubUser githubUser = githubProvider.getUser(accessToken);
            System.out.println(githubUser);
            if(githubUser!=null){

                request.getSession().setAttribute("githubUser",githubUser);
                return "redirect:/";
                //登录成功
            }
            else{
                //登录失败
                return "redirect:/";

            }
        }


}
