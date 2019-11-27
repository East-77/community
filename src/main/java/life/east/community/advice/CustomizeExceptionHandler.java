package life.east.community.advice;

import com.alibaba.fastjson.JSON;
import life.east.community.dto.CommentResultDTO;
import life.east.community.exception.CustomizeErrorCode;
import life.east.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 7777777
 * @date 2019/11/26 15:09:10
 * @description
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            //暴力返回json
            CommentResultDTO commentResultDTO;
            if (e instanceof CustomizeException) {
                commentResultDTO = CommentResultDTO.errorOf((CustomizeException) e);
            } else {
                commentResultDTO = CommentResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(2000);
            try(PrintWriter writer = response.getWriter()) {
               writer.write(JSON.toJSONString(commentResultDTO));
            } catch (IOException ioEx) {
            }
            return null;
        } else {
            //跳转到错误页面
            if (e instanceof CustomizeException) {
                model.addAttribute("message", e.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
