package life.east.community.controller;

import com.mysql.jdbc.StringUtils;
import life.east.community.dto.CommentCreateDTO;
import life.east.community.dto.CommentDTO;
import life.east.community.dto.CommentResultDTO;
import life.east.community.enums.CommentTypeEnum;
import life.east.community.exception.CustomizeErrorCode;
import life.east.community.model.Comment;
import life.east.community.model.User;
import life.east.community.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    /**
     * 获取问题的全部回复的请求
     * @param commentCreateDTO
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object comment(@RequestBody CommentCreateDTO commentCreateDTO,
                          HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return CommentResultDTO.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }
        if(commentCreateDTO == null || StringUtils.isNullOrEmpty(commentCreateDTO.getContent())){
            return CommentResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentCreateDTO, comment);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0);

        commentService.insert(comment);

        return CommentResultDTO.okOf();
    }

    /**
     * 获取某评论的全部回复的请求
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public CommentResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Long id){
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);

        return CommentResultDTO.okOf(commentDTOS);
    }
}
