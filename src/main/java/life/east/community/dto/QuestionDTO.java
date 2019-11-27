package life.east.community.dto;

import life.east.community.model.User;
import lombok.Data;

/**
 * @author 7777777
 * @date 2019/11/24 13:21:36
 * @description DTO为传输层模型
 */
@Data
public class QuestionDTO {

    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private Long creator;
    private User user;
}
