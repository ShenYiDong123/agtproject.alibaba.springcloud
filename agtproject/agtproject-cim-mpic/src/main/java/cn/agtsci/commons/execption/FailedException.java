package cn.agtsci.commons.execption;
/**
 * 自定义运行时异常
 * @author: caiyt
 * @date 2019-11-05
 */
public class FailedException extends RuntimeException {
    private String msg;
    private String code = "500";
    private Object data;

    public FailedException(String message) {
        super(message);
        this.msg = message;
    }

    public FailedException(String code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public FailedException(String code, String message, Object data) {
        super(message);
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public FailedException(String message, String code, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.msg = message;
    }

    public FailedException(String message, Throwable cause) {
        super(cause);
        this.msg = message;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
