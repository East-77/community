package life.east.community.controller;

import life.east.community.dto.CommentDTO;
import life.east.community.dto.CommentResultDTO;
import life.east.community.exception.CustomizeErrorCode;
import life.east.community.model.Comment;
import life.east.community.model.User;
import life.east.community.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 7777777
 * @date 2019/11/26 19:37:07
 * @description
 * @RequestBody注解自动将接收的json数据反序列化为Java对象
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object comment(@RequestBody CommentDTO commentDTO,
                          HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return CommentResultDTO.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0);

        commentService.insert(comment);

        return CommentResultDTO.okOf();
    }
}
