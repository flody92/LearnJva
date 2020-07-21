package com.learn.test.learn.advice;

import com.alibaba.fastjson.JSON;
import com.learn.test.learn.dto.ResultDTO;
import com.learn.test.learn.exception.CustomizeErrorCode;
import com.learn.test.learn.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {

        String contentType = request.getContentType();
        if ("application/json".equals(contentType)){
            ResultDTO resultDTO;
            if (e instanceof CustomizeException){
                resultDTO = ResultDTO.errorOf((CustomizeException) e);
            }
            else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }
        try{
            response.setContentType("application/json");
            response.setStatus(200);
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(resultDTO));
        }catch (IOException ioe){
        }
            return null;
    }
        else {

            if (e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());
            }else{
                model.addAttribute("message",CustomizeErrorCode.SYSTEM_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
