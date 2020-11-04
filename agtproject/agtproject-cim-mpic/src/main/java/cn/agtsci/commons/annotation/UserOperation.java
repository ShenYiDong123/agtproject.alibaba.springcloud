package cn.agtsci.commons.annotation;

import cn.agtsci.commons.enums.ModuleTypeEnum;
import cn.agtsci.commons.enums.UserOperationTypeEnum;

import java.lang.annotation.*;

/**
 * @author caiyt
 * @date 2019-11-05
 * 用户注解
 * 功能：作用于controller接口，用于aop记录日志使用
 */
@Retention(RetentionPolicy.RUNTIME)  //RetentionPolicy.RUNTIME 表示注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在
@Target({ElementType.METHOD})       //ElementType.METHOD 表示该注解是用来描述方法的
@Documented
public @interface UserOperation {

    /**
     * 日志模块类型
     */
    ModuleTypeEnum module();

    /**
     * 操作类型
     */
    UserOperationTypeEnum type();

    /**
     * 操作名称
     */
    String name();

    /**
     * 描述
     */
    String description() default "";

}
