package life.east.community.exception;

/**
 * @author 7777777
 * @date 2019/11/26 15:41:10
 * @description
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND(2001,"这个问题飘了，换个试试?"),
    TARGET_PARAM_NOT_FOUND(2002,"未选择任何目标，请换个姿势"),
    SYS_ERROR(2003,"服务器冒烟了，要不你稍后再试试？"),
    TYPE_PARAM_WRONG(2004,"此问题或评论已飘到外太空，换个试试？"),
    COMMENT_NOT_FOUND(2005,"该评论已飘到外太空，换个试试？"),
    COMMENT_IS_EMPTY(2006,"评论内容不能为空！"),
    NOT_LOGIN(3001,"你飘了~请先登录再重试");

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code,String message) {
        this.code = code;
        this.message = message;
    }
}
