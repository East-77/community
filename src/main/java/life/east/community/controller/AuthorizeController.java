package life.east.community.controller;

import life.east.community.Provider.GithubProvider;
import life.east.community.dto.AccessTokenDTO;
import life.east.community.dto.GithubUserDTO;
import life.east.community.model.User;
import life.east.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author 7777777
 * @date 2019/11/23 14:29:03
 * @description 登录GitHub/退出登录
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    //获取配置文件的相关值
    @Value("${github.client.id}")
    private String clientId;
    
    @Value("${github.client.secret}")
    private String clientSecret;
    
    @Value("${github.redirect.uri}")
    private String redirectURI;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response,
                           HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectURI);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUserDTO githubUser = githubProvider.getUser(accessToken);

        if(githubUser != null && githubUser.getId() != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setAvatarUrl(githubUser.getAvatarUrl());

            userService.insertOrUpdate(user);

            //写入session、cookie
            request.getSession().setAttribute("user",user);
            request.getSession().setAttribute("test","我没有消失");
            //数据库模拟cokie
            response.addCookie(new Cookie("token",token));
            return "redirect:/";

        }else {
            //登录失败，重新登录
            return "redirect:/";
        }
    }

    /**
     * 退出登录
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        //清楚session的user
        request.getSession().removeAttribute("user");

        //创建新的同名cookie，替换旧的token
        Cookie cookie = new Cookie("token",null);
        //立即删除型
        cookie.setMaxAge(0);
        //所有路径下均有效，确保删除
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/";
    }
}
