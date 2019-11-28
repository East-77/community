package life.east.community.dto;

import life.east.community.exception.CustomizeErrorCode;
import life.east.community.exception.CustomizeException;
import lombok.Data;

/**
 * @author 7777777
 * @date 2019/11/26 22:42:20
 * @description 记录回复结果（成功或失败）
 */
@Data
public class CommentResultDTO<T> {
    //自定义错误码
    private Integer code;
    private String message;
    private T data;

    public static CommentResultDTO errorOf(Integer code,String message){
        CommentResultDTO commentResultDTO = new CommentResultDTO();
        commentResultDTO.setCode(code);
        commentResultDTO.setMessage(message);
        return commentResultDTO;
    }

    public static CommentResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static CommentResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }

    public static CommentResultDTO okOf(){
        CommentResultDTO commentResultDTO = new CommentResultDTO();
        commentResultDTO.setCode(200);
        commentResultDTO.setMessage("请求成功");
        return commentResultDTO;
    }

    public static <T> CommentResultDTO okOf(T t){
        CommentResultDTO commentResultDTO = new CommentResultDTO();
        commentResultDTO.setCode(200);
        commentResultDTO.setMessage("请求成功");
        commentResultDTO.setData(t);
        return commentResultDTO;
    }
}
