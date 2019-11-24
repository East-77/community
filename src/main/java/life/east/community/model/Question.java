package life.east.community.model;

import lombok.Data;

/**
 * @author 7777777
 * @date 2019/11/24 13:21:36
 * @description
 */
@Data
public class Question {

    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private Integer creator;
}