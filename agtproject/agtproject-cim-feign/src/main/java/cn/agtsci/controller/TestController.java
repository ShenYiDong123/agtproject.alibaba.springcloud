package cn.agtsci.controller;

import cn.agtsci.annotation.UserOperation;
import cn.agtsci.client.service.FeignTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.RedissonRxClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/*
 * Created by ShenYiDong on 2019/10/31
 */
@Api(value = "Fegin模块接口", description = "Fegin模块接口")
@RestController
@RequestMapping("/feign")
public class TestController {

    private static final String MODULE_NAME = "Fegin测试";

    @Value("${spring.redis.host}")
    private String host; // 地址

    @Value("${spring.redis.port}")
    private String port; // 端口

    @ApiOperation("feign测试接口")
    @GetMapping(value = "/addNumberTest/{number}")
    public String addNumberTest(@PathVariable("number") String number){
        return "agtproject-cim-feign 调用成功,数字为："+number;
    }


    @ApiOperation("Redisson测试")
    @GetMapping(value = "/redisson/{number}")
    public String testRedisson(@PathVariable("number") String number) {
        //1.创建redis对象
        Config config = new Config();
        config.useClusterServers().addNodeAddress(host + port);
        //2.创建Redisson对象(根据config)
        RedissonClient redisson = Redisson.create(config);

        //3.获取分布式锁
        RLock rLock = redisson.getLock("lockName");
        //如果获取锁成功
        if (rLock.tryLock()) {
            try {
                System.out.println("获取锁成功,执行业务逻辑");
            } finally {
                // 解锁
                rLock.unlock();
                System.out.println("解锁成功");
            }
        }
        return "获取分布式锁成功";
    }
}
