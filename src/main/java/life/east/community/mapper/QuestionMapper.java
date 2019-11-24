package life.east.community.mapper;

import life.east.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 7777777
 * @date 2019/11/24 13:25:21
 * @description
 */
@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void createQuestion(Question question);

    @Select("select * from question limit #{offset},#{pageSize}")
    List<Question> listQuestions(Integer offset, Integer pageSize);

    @Select("select count(1) from question")
    Integer count();
}
