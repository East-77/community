package life.east.community.model;

import lombok.Data;

/**
 * @author 7777777
 * @date 2019/11/23 21:40:53
 * @description
 */
@Data
public class User {

    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    //用户头像地址
    private String avatarUrl;
}
