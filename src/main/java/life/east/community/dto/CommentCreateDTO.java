package life.east.community.dto;

import lombok.Data;

/**
 * @author 7777777
 * @date 2019/11/26 19:35:35
 * @description 记录用户回复时传入的comment（与前端对标）
 */
@Data
public class CommentCreateDTO {

    private Long parentId;
    private String content;
    private Integer type;
}
