package life.east.community.dto;

import life.east.community.model.User;
import lombok.Data;

/**
 * @author 7777777
 * @date 2019/11/27 20:45:25
 * @description 向前端列出某个问题的全部回复时传输的comment（与数据库对标）
 */
@Data
public class CommentDTO {

    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer likeCount;
    private String content;
    private User user;
}
