package cn.agtsci;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 启动类
 */
@SpringBootApplication
@EnableDiscoveryClient //注册到nocos
@EnableFeignClients   //开启feign调用
public class AgtProjectCimFeignAppliaction {

    public static void main(String[] args) {
        SpringApplication.run(AgtProjectCimFeignAppliaction.class, args);
    }

}
