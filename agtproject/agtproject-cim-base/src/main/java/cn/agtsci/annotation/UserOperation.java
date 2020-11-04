package cn.agtsci.annotation;

import java.lang.annotation.*;

/**
 * 用户注解
 * 功能：作用于controller接口，用于aop记录日志使用
 */
@Retention(RetentionPolicy.RUNTIME)  //RetentionPolicy.RUNTIME 表示注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在
@Target({ElementType.METHOD})       //ElementType.METHOD 表示该注解是用来描述方法的
@Documented
public @interface UserOperation {

    //模块名
    String moduleName() default "";

    //操作记录
    String operationRecord() default "";

}
