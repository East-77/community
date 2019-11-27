package life.east.community.controller;

import com.mysql.jdbc.StringUtils;
import life.east.community.dto.QuestionDTO;
import life.east.community.model.Question;
import life.east.community.model.User;
import life.east.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 7777777
 * @date 2019/11/23 13:43:33
 * @description
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model){
        QuestionDTO question = questionService.findById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());
        return "publish";
    }

    /**
     * GET请求跳转页面
     *
     * @return
     */
    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    /**
     * POST请求发布问题
     *
     * @return
     */
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(name = "title",required = false) String title,
            @RequestParam(name = "description",required = false) String description,
            @RequestParam(name = "tag",required = false) String tag,
            @RequestParam(name = "id",required = false) Long id,
            HttpServletRequest request,
            Model model) {


        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        if (StringUtils.isNullOrEmpty(title)) {
            model.addAttribute("error", "标题不能为空！");
            return "/publish";
        }
        if (StringUtils.isNullOrEmpty(description)) {
            model.addAttribute("error", "问题描述不能为空！");
            return "/publish";
        }
        if (StringUtils.isNullOrEmpty(tag)) {
            model.addAttribute("error", "请至少添加一个标签！");
            return "/publish";
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录！");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        //数据库default值不生效？
        question.setViewCount(0);
        question.setCommentCount(0);
        question.setLikeCount(0);

        questionService.createOrUpdate(question);

        return "redirect:/";
    }
}
