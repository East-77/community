package life.east.community.controller;

import life.east.community.dto.QuestionDTO;
import life.east.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 7777777
 * @date 2019/11/25 15:52:19
 * @description
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model){
        QuestionDTO questionDTO = questionService.findById(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
