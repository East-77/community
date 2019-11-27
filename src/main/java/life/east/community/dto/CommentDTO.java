package life.east.community.dto;

import lombok.Data;

/**
 * @author 7777777
 * @date 2019/11/26 19:35:35
 * @description
 */
@Data
public class CommentDTO {

    private Long parentId;
    private String content;
    private Integer type;
}
