package life.east.community.dto;

import life.east.community.model.User;
import lombok.Data;

/**
 * @author 7777777
 * @date 2019/11/29 23:21:23
 * @description
 */
@Data
public class NotificationDTO {

    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private Long outerid;
    private String outerTitle;
    private String description;
    private Integer type;
}
