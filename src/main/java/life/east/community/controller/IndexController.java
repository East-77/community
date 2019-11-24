package life.east.community.controller;

import life.east.community.mapper.UserMapper;
import life.east.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 7777777
 * @date 2019/11/23 13:43:33
 * @description
 */
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                User uesr = userMapper.findByToken(token);
                if(uesr != null){
                    request.getSession().setAttribute("user",uesr);
                }
                break;
            }
        }
        return "index";
    }
}
