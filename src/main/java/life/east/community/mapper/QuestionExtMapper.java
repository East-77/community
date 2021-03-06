package life.east.community.mapper;

import life.east.community.model.Question;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 使用自定义的Mapper，用于扩展
 */
@Mapper
public interface QuestionExtMapper {

    int incViewCount(Question record);

    int incCommentCount(Question record);

    List<Question> selectByTagRelated(Question question);
}