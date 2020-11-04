package cn.agtsci.client.service;

import cn.agtsci.client.ServiceList;


import cn.agtsci.client.sentinel.FeginTestServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign公共远程接口
 *
 * @Author shenyidong
 * @Date 2019-10-30
 */
@FeignClient(value = ServiceList.AGTPROJECT_CIM_FEIGN,fallbackFactory = FeginTestServiceFallbackFactory.class)
public interface FeignTestService {

    /**
     * 测试接口
     * @param number
     * @return
     */
    @GetMapping("/feign/addNumberTest/{number}")
    public String addNumberTest(@PathVariable("number") String number);

}
