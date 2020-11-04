package cn.agtsci;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 */
@SpringBootApplication
@MapperScan(basePackages = "cn.agtsci.mapper")
@DubboComponentScan(basePackages = "cn.agtsci.dubbo")
@ComponentScan(basePackages={"cn.agtsci"})
public class AgtProjectCimSeataAccountAppliaction {

    public static void main(String[] args) {
        SpringApplication.run(AgtProjectCimSeataAccountAppliaction.class, args);
    }

}
