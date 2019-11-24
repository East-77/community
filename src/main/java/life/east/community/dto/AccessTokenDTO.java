package life.east.community.dto;

import lombok.Data;

/**
 * @author 7777777
 * @date 2019/11/23 14:44:58
 * @description 通过POST请求携带此信息返回给GitHub，获取access_token
 */
@Data
public class AccessTokenDTO {

    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
