package life.east.community.controller;

import life.east.community.dto.CommentDTO;
import life.east.community.dto.QuestionDTO;
import life.east.community.enums.CommentTypeEnum;
import life.east.community.service.CommentService;
import life.east.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author 7777777
 * @date 2019/11/25 15:52:19
 * @description
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){
        QuestionDTO questionDTO = questionService.findById(id);
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        //阅读数累加
        questionService.incViewCount(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("commentDTOS",commentDTOS);
        return "question";
    }
}
