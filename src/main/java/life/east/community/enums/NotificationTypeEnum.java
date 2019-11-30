package life.east.community.enums;

/**
 * @author 7777777
 * @date 2019/11/29 22:20:06
 * @description
 */
public enum NotificationTypeEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论");

    private int type;
    private String description;

    public int getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    NotificationTypeEnum(int type, String description) {
        this.type = type;
        this.description = description;
    }

    public static String descriptionOfType(int type){
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if (notificationTypeEnum.getType() == type){
                return notificationTypeEnum.getDescription();
            }
        }
        return "";
    }
}
