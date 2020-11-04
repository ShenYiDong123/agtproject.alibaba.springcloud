package cn.agtsci.commons.execption;


import cn.agtsci.commons.enums.StatusCodeEnum;

/**
 *  判断参数是否为空异常
 */
public class ParameterIsNullException extends RuntimeException{

    private String code = StatusCodeEnum.PARAMETERISNULL.getValue();
    private String message = StatusCodeEnum.PARAMETERISNULL.getDesc();

    public ParameterIsNullException(){}



}
