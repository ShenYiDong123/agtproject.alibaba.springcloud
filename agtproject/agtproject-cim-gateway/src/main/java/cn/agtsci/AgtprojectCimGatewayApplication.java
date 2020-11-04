package cn.agtsci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by ShenYiDong on 2019/11/1
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AgtprojectCimGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(AgtprojectCimGatewayApplication.class, args);
    }

}
