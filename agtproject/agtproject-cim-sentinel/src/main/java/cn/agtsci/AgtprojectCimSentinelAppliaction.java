package cn.agtsci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AgtprojectCimSentinelAppliaction {

    public static void main(String[] args) {
        SpringApplication.run(AgtprojectCimSentinelAppliaction.class, args);
    }

}
