package life.east.community.dto;

import lombok.Data;

/**
 * @author 7777777
 * @date 2019/11/23 15:18:56
 * @description 接收GitHub返回的用户信息
 */
@Data
public class GithubUserDTO {

    private String name;
    private Long id;
    private String bio;
    //fastjson与驼峰命名规则兼容
    private String avatarUrl;
}
