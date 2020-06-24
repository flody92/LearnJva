package com.learn.test.learn.controller;


import com.learn.test.learn.dto.QuestionDTO;

import com.learn.test.learn.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Integer id,
                           Model model){
        QuestionDTO questionDTO = questionService.getById(id);
//        System.out.println(questionDTO);
        model.addAttribute("question",  questionDTO);
        return "question";
    }
}
