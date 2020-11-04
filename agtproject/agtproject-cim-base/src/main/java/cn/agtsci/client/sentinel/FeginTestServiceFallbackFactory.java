package cn.agtsci.client.sentinel;

import cn.agtsci.client.sentinel.impl.FeginTestServiceFallback;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * Feigin模块测试接口降级处理类
 *
 * 定义接口降级处理类，并集成
 * @Author shenyidong
 * @Date 2019-10-30
 */
@Component
public class FeginTestServiceFallbackFactory implements FallbackFactory<FeginTestServiceFallback> {

    @Override
    public FeginTestServiceFallback create(Throwable throwable) {
        return new FeginTestServiceFallback(throwable);
    }
}
