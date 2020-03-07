package com.personalweb.demo.advice;

import com.alibaba.fastjson.JSON;
import com.personalweb.demo.dto.ResultDTO;
import com.personalweb.demo.exception.CustomizeErrorCode;
import com.personalweb.demo.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//对于service层的错误
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    Object handle(Throwable e, Model model, HttpServletRequest request/*, HttpServletResponse response*/) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            //返回json
            /*ResultDTO resultDTO;*/
            if (e instanceof CustomizeException) {
                return /*resultDTO=*/ResultDTO.errorOf((CustomizeException) e);
            } else {
                return /*resultDTO=*/ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
           /* try{
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer =response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ex) {
            }
            return null;*/

        } else {
            //错误页面
            if (e instanceof CustomizeException) {
                model.addAttribute("message", e.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView(("error"));
        }
    }
}
