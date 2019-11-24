package life.east.community.controller;

import life.east.community.Provider.GithubProvider;
import life.east.community.dto.AccessTokenDTO;
import life.east.community.dto.GithubUserDTO;
import life.east.community.mapper.UserMapper;
import life.east.community.model.User;
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
 * @description 登录GitHub
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
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
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
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insertUser(user);
//            //登录成功，写cookie和session
//            request.getSession().setAttribute("user",githubUser);
            //数据库模拟cokie
            response.addCookie(new Cookie("token",token));
            return "redirect:/";

        }else {
            //登录失败，重新登录
            return "redirect:/";
        }
    }
}
