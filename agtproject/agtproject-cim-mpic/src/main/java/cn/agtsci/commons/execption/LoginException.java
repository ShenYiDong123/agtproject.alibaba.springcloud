package cn.agtsci.commons.execption;


import cn.agtsci.commons.enums.StatusCodeEnum;

/**
 * 登录异常
 */
public class LoginException extends RuntimeException {
    private String code = StatusCodeEnum.LOGINFAIL.getValue();
    private String message = StatusCodeEnum.LOGINFAIL.getDesc();

    public LoginException(){}

    public LoginException(String code,String message) {
        this.code = code;
        this.message = message;
    }
}

