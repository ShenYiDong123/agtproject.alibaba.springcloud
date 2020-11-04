package cn.agtsci.client.sentinel.impl;

import cn.agtsci.client.service.FeignTestService;

/**
 * Feigin模块测试接口降级处理具体实现
 *
 * @Author shenyidong
 * @Date 2019-10-30
 */
public class FeginTestServiceFallback implements FeignTestService {


    private Throwable throwable;

    public FeginTestServiceFallback(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public String addNumberTest(String number) {
        return "服务调用失败，降级处理。异常信息：" + throwable.getMessage();
    }
}
