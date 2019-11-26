package life.east.community.service;

import life.east.community.dto.PaginationDTO;
import life.east.community.dto.QuestionDTO;
import life.east.community.exception.CustomizeErrorCode;
import life.east.community.exception.CustomizeException;
import life.east.community.mapper.QuestionMapper;
import life.east.community.mapper.UserMapper;
import life.east.community.model.Question;
import life.east.community.model.QuestionExample;
import life.east.community.model.User;
import org.apache.ibatis.session.RowBounds;
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

        long totalCount = questionMapper.countByExample(new QuestionExample());
//        Integer totalCount = questionMapper.count();
        Integer totalPage;
        if (totalCount % pageSize == 0) {
            totalPage = Math.toIntExact(totalCount) / pageSize;
        } else {
            totalPage = Math.toIntExact(totalCount) / pageSize+ 1;
        }

        if(pageNumber < 1){
            pageNumber = 1;
        }
        if(pageNumber > totalPage){
            pageNumber = totalPage;
        }
        Integer offset = pageSize * ((pageNumber - 1) >= 0 ? (pageNumber - 1): 0);

        //List<Question> questionList = questionMapper.listQuestions(offset,pageSize);

        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, pageSize));

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questionList){

            //根据主键查找
            User user = userMapper.selectByPrimaryKey(question.getCreator());
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
        paginationDTO.setPagination(totalPage,pageNumber);
        return paginationDTO;
    }

    public PaginationDTO listQuestions(Integer userId, Integer pageNumber, Integer pageSize) {
//        Integer totalCountOfCreator = questionMapper.countOfCreator(userId);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);

        Integer totalCountOfCreator = Math.toIntExact(questionMapper.countByExample(questionExample));

        Integer totalPage;
        if (totalCountOfCreator % pageSize == 0) {
            totalPage = totalCountOfCreator / pageSize;
        } else {
            totalPage = totalCountOfCreator / pageSize + 1;
        }

        if(pageNumber < 1){
            pageNumber = 1;
        }
        if(pageNumber > totalPage){
            pageNumber = totalPage;
        }
        Integer offset = pageSize * ((pageNumber - 1) >= 0 ? (pageNumber - 1): 0);
//        List<Question> questionList = questionMapper.listQuestionsByCreator(userId,offset,pageSize);
//        questionExample.clear();
//        questionExample.createCriteria().andCreatorEqualTo(userId);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, pageSize));

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questionList){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
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
        paginationDTO.setPagination(totalPage,pageNumber);
        return paginationDTO;
    }

    public QuestionDTO findById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else {
            //更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());

            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, questionExample);
            if(updated < 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }
}
