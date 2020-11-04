package cn.agtsci.channel;

import cn.agtsci.api.Constant;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 生产者发送消息
 */
@Service
public class ProducerRunner implements CommandLineRunner {
    @Autowired
    private MessageChannel messageChannel; // 获取name为output的binding

    @Override
    public void run(String... args) throws Exception {
        Map<String,Object> headers = new HashMap<>();
        headers.put(MessageConst.PROPERTY_TAGS, "TagA");
        Message message = (Message) MessageBuilder.createMessage("你好，沈毅栋",new MessageHeaders(headers));
        messageChannel.send(message);
    }
}
