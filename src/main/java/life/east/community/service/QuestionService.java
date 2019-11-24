package life.east.community.service;

import life.east.community.dto.QuestionDTO;
import life.east.community.mapper.QuestionMapper;
import life.east.community.mapper.UserMapper;
import life.east.community.model.Question;
import life.east.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 7777777
 * @date 2019/11/24 18:02:50
 * @description ‘一般把组装实体的层称为service层’。QuestionService组装Question跟User成传输的QuestionDTO
 */
@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public List<QuestionDTO> listQuestions() {
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        List<Question> questionList = questionMapper.listQuestions();

        for (Question question : questionList){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //通过BeanUtils快速注入questionDTO中与question相同的属性
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
