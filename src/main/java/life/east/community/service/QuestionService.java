package life.east.community.service;

import life.east.community.dto.PaginationDTO;
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

    public PaginationDTO listQuestions(Integer pageNumber, Integer pageSize) {

        //size*(page-1)
        Integer totalCount = questionMapper.count();
        Integer totalPage;
        if (totalCount % pageSize == 0) {
            totalPage = totalCount / pageSize;
        } else {
            totalPage = totalCount / pageSize + 1;
        }

        if(pageNumber < 1){
            pageNumber = 1;
        }
        if(pageNumber > totalPage){
            pageNumber = totalPage;
        }
        Integer offset = pageSize * ((pageNumber - 1) >= 0 ? (pageNumber - 1): 0);
        List<Question> questionList = questionMapper.listQuestions(offset,pageSize);

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questionList){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //通过BeanUtils快速注入questionDTO中与question相同的属性
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        //获取总记录数
//        Integer totalCount = questionMapper.count();
        //根据分页大小等得到分页的相关信息
        paginationDTO.setPagination(totalPage,pageNumber,pageSize);
        return paginationDTO;
    }
}
