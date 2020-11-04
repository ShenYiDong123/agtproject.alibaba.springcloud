package cn.agtsci;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author cyt
 * @date 2019-11-05
 *
 * **/
@SpringBootApplication
@ServletComponentScan
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableAsync
@MapperScan("cn.agtsci.modules.**.mapper")
@EnableDiscoveryClient
public class AgtProjectMpicApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgtProjectMpicApplication.class, args);
    }
}
