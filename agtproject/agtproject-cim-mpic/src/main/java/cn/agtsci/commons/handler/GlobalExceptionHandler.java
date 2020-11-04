package cn.agtsci.commons.handler;

import cn.agtsci.commons.execption.FailedException;
import cn.agtsci.response.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局异常处理类
 * @author  caiyt
 * @date 2019-11-05
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 运行时异常捕获
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = 	Exception.class)
    @ResponseBody
    public ResponseResult globalErrorHandler(HttpServletRequest req, Exception e){
        logger.error(e.getMessage(), e);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("url", req.getRequestURL().toString());
        return ResponseResult.fail();
    }
    
    /**
     * 运行时异常捕获
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = FailedException.class)
    @ResponseBody
    public ResponseResult jsonErrorHandler(HttpServletRequest req, FailedException e){
        logger.error("捕获运行时异常");
        logger.error(e.getMessage(), e);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("url", req.getRequestURL().toString());
        return ResponseResult.fail(e.getCode(),e.getMessage(), e.getData());
    }

    /**
     * 处理所有接口请求参数验证异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseResult handleMethodArgumentNotValidException(HttpServletRequest req,
                                                                    MethodArgumentNotValidException e) {
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        ObjectError error = errors.get(0);
        logger.info(error.getDefaultMessage());
        return ResponseResult.fail(error.getCode(), error.getDefaultMessage());
    }

//    /**
//     * 捕获访问无权限的异常
//     * @param req
//     * @param e
//     * @return
//     */
//    @ExceptionHandler(UnauthorizedException.class)
//    @ResponseBody
//    public ResponseResult handleUnauthorizedException(HttpServletRequest req, UnauthorizedException e) {
//        return ResponseResult.fail(StatusCodeEnum.NO_AUTH.getValue(), StatusCodeEnum.NO_AUTH.getDesc());
//    }

}
