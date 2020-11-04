package cn.agtsci.controller;

import cn.agtsci.annotation.UserOperation;
import cn.agtsci.client.service.FeignTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ShenYiDong on 2019/10/31
 */
@Api(value = "Sentinel模块接口", description = "Sentinel模块接口")
@RestController
@RequestMapping("/test")
@CrossOrigin //解决跨域
public class SentinelTestController {

    private static final String MODULE_NAME = "Sentinel测试";

    @Autowired
    FeignTestService feignTestService;

    @ApiOperation("Sentinel测试接口")
    @GetMapping(value = "/addNumberTest/{number}")
    public String addNumberTest(@PathVariable("number") String number){
        //调用feign模块接口
        System.out.println("进入");
        return feignTestService.addNumberTest("2");
    }
}
