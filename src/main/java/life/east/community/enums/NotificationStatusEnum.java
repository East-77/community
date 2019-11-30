package life.east.community.enums;

/**
 * @author 7777777
 * @date 2019/11/29 22:45:06
 * @description
 */
public enum  NotificationStatusEnum {
    UNREAD(0),READED(1);

    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
