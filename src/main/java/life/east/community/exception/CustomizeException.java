package life.east.community.exception;

/**
 * @author 7777777
 * @date 2019/11/26 15:26:16
 * @description
 */
public class CustomizeException extends RuntimeException {
    private String message;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }

    public CustomizeException(String message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
