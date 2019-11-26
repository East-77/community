package life.east.community.exception;

/**
 * @author 7777777
 * @date 2019/11/26 15:41:10
 * @description
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND("这个问题飘了，换个试试?");

    @Override
    public String getMessage() {
        return message;
    }

    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }
}
