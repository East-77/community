package life.east.community.controller;

import life.east.community.dto.PaginationDTO;
import life.east.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 7777777
 * @date 2019/11/23 13:43:33
 * @description
 */
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
                        @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
                        Model model) {

        PaginationDTO pagination = questionService.listQuestionsByUserId(pageNumber, pageSize);
        model.addAttribute("pagination", pagination);
        return "index";
    }
}
