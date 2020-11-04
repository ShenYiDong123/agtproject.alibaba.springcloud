package cn.agtsci.response;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 *
 * 结果返回对象
 * Created by ShenYiDong on 2019/7/23
 */
@Component
@Data
public class ResponseResult implements Serializable {

    /**
     * 状态码，是否成功
     */
    String code = StatusCodeEnum.SUCCESS.getValue();

    /**
     * 返回的数据
     */
    Object data;

    /**
     * 提示信息
     */
    String message = StatusCodeEnum.SUCCESS.getDesc();


    public ResponseResult() {

    }

    public ResponseResult(Object data){
        this.data = data;
    }

    public ResponseResult(String code, String message){
        this.code = code;
        this.message = message;
    }

    public ResponseResult(String code, String message, Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 构建结果
     * @param code
     * @param message
     */
    public void buildResult(String code,String message){
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * 构建结果
     * @param data
     */
    public void buildResult(String code,String message,String data){
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static ResponseResult success(){
        return new ResponseResult(StatusCodeEnum.SUCCESS.getValue(), StatusCodeEnum.SUCCESS.getDesc(), null);
    }

    public static ResponseResult success(Object data){
        return new ResponseResult(StatusCodeEnum.SUCCESS.getValue(), StatusCodeEnum.SUCCESS.getDesc(), data);
    }

    public static ResponseResult fail(String message){
        return new ResponseResult(StatusCodeEnum.FAIL.getValue(), message);
    }

    public static ResponseResult fail(String code, String message){
        return new ResponseResult(code, message);
    }

    public static ResponseResult fail(String code, String message, Object data){
        return new ResponseResult(code, message, data);
    }

    public static ResponseResult fail(){
        return new ResponseResult(StatusCodeEnum.FAIL.getValue(), StatusCodeEnum.FAIL.getDesc(), null);
    }

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
