package cn.agtsci.api;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

class ProducerTest {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException,
            MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer(Constant.PRODUCE_GROUP);
        producer.setNamesrvAddr(Constant.NAME_SERVER_ADDRESS);
        producer.start();
        //1 创建消息
        Message message = new Message(Constant.TOPIC,     //主题
                "TagA",                                       //标签
//                "key" + i,                                     //自定义唯一key
                ("你好").getBytes());                 //消息实体(byte[])
        SendResult sendResult = producer.send(message);
        System.out.println(sendResult);

        producer.shutdown();
    }
}
