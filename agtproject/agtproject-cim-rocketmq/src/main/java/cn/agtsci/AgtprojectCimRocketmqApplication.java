package cn.agtsci;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
/**
 * Created by ShenYiDong on 2020/04/21
 */
@SpringBootApplication
@EnableBinding({Sink.class, Source.class})
public class AgtprojectCimRocketmqApplication {
    public static void main(String[] args) {
        SpringApplication.run(AgtprojectCimRocketmqApplication.class, args);
    }
}
