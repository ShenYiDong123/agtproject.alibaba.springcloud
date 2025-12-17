# RocketMQ 消息队列使用指南

[![RocketMQ](https://img.shields.io/badge/RocketMQ-4.7.0+-orange.svg)](https://rocketmq.apache.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.3.0+-green.svg)](https://spring.io/projects/spring-boot)

## 📋 目录

- [RocketMQ 简介](#rocketmq-简介)
- [核心概念](#核心概念)
- [环境搭建](#环境搭建)
- [Spring Boot 集成](#spring-boot-集成)
- [消息发送](#消息发送)
- [消息消费](#消息消费)
- [高级特性](#高级特性)
- [最佳实践](#最佳实践)
- [监控运维](#监控运维)
- [常见问题](#常见问题)

## 🎯 RocketMQ 简介

**Apache RocketMQ** 是一个统一消息引擎、轻量级数据处理平台。它是阿里巴巴在2012年开源的分布式消息中间件，具有高性能、高可靠、高实时、分布式特点。

### 核心特性
- **高性能** - 单机支持万级QPS
- **高可靠** - 99.95%的超高可用性
- **高实时** - 毫秒级的消息投递
- **分布式** - 支持集群部署，水平扩展
- **顺序消息** - 支持严格的消息顺序
- **事务消息** - 支持分布式事务消息
- **定时消息** - 支持延迟消息投递

## 📚 核心概念

### 1. 基础概念

| 概念 | 说明 |
|------|------|
| **Producer** | 消息生产者，负责发送消息 |
| **Consumer** | 消息消费者，负责接收和处理消息 |
| **Topic** | 消息主题，消息的逻辑分类 |
| **Tag** | 消息标签，用于消息过滤 |
| **Queue** | 消息队列，Topic的物理存储单元 |
| **Message** | 消息，数据传输的载体 |

### 2. 架构组件

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Producer      │    │   NameServer    │    │   Consumer      │
│   (生产者)      │    │   (路由中心)    │    │   (消费者)      │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         └───────────────────────┼───────────────────────┘
                                 │
                    ┌─────────────────┐
                    │     Broker      │
                    │   (消息服务器)  │
                    └─────────────────┘
```

#### NameServer
- 路由信息管理中心
- 为Producer和Consumer提供路由信息
- 无状态，集群部署

#### Broker
- 消息存储和转发
- 处理消息的读写请求
- 支持主从部署

## 🚀 环境搭建

### 1. 下载安装

#### 下载 RocketMQ
```bash
# 下载
wget https://archive.apache.org/dist/rocketmq/4.7.1/rocketmq-all-4.7.1-bin-release.zip

# 解压
unzip rocketmq-all-4.7.1-bin-release.zip
cd rocketmq-all-4.7.1-bin-release
```

### 2. 启动服务

#### 启动 NameServer
```bash
# Linux/Mac
nohup sh mqnamesrv &

# Windows
mqnamesrv.cmd
```

#### 启动 Broker
```bash
# Linux/Mac
nohup sh mqbroker -n localhost:9876 &

# Windows
mqbroker.cmd -n localhost:9876 autoCreateTopicEnable=true
```

### 3. 验证安装
```bash
# 发送消息测试
sh mqadmin sendMessage -n localhost:9876 -t TestTopic -p "Hello RocketMQ"

# 消费消息测试
sh mqadmin consumeMessage -n localhost:9876 -t TestTopic
```

### 4. Docker 部署

#### docker-compose.yml
```yaml
version: '3.8'
services:
  namesrv:
    image: apache/rocketmq:4.7.1
    container_name: rocketmq-namesrv
    ports:
      - "9876:9876"
    command: ["sh", "mqnamesrv"]
    
  broker:
    image: apache/rocketmq:4.7.1
    container_name: rocketmq-broker
    ports:
      - "10909:10909"
      - "10911:10911"
    depends_on:
      - namesrv
    environment:
      - NAMESRV_ADDR=namesrv:9876
    command: ["sh", "mqbroker", "-c", "/opt/rocketmq-4.7.1/conf/broker.conf"]
```

## ⚙️ Spring Boot 集成

### 1. 添加依赖

```xml
<dependency>
    <groupId>org.apache.rocketmq</groupId>
    <artifactId>rocketmq-spring-boot-starter</artifactId>
    <version>2.1.1</version>
</dependency>
```

### 2. 配置文件

#### application.properties
```properties
# RocketMQ 配置
rocketmq.name-server=192.168.17.128:9876
rocketmq.producer.group=agtproject-producer-group
rocketmq.producer.send-message-timeout=3000
rocketmq.producer.retry-times-when-send-failed=3

# 消费者配置
rocketmq.consumer.group=agtproject-consumer-group
rocketmq.consumer.consume-thread-min=5
rocketmq.consumer.consume-thread-max=32
```

### 3. 配置类

```java
@Configuration
@EnableConfigurationProperties(RocketMQProperties.class)
public class RocketMQConfig {
    
    @Bean
    public RocketMQTemplate rocketMQTemplate() {
        return new RocketMQTemplate();
    }
}
```

## 📤 消息发送

### 1. 同步发送

```java
@Service
public class MessageProducer {
    
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    
    /**
     * 同步发送消息
     */
    public void sendSyncMessage(String topic, Object message) {
        SendResult sendResult = rocketMQTemplate.syncSend(topic, message);
        System.out.println("发送结果: " + sendResult.getSendStatus());
    }
    
    /**
     * 发送带Tag的消息
     */
    public void sendMessageWithTag(String topic, String tag, Object message) {
        String destination = topic + ":" + tag;
        SendResult sendResult = rocketMQTemplate.syncSend(destination, message);
        System.out.println("发送结果: " + sendResult.getSendStatus());
    }
}
```

### 2. 异步发送

```java
@Service
public class AsyncMessageProducer {
    
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    
    /**
     * 异步发送消息
     */
    public void sendAsyncMessage(String topic, Object message) {
        rocketMQTemplate.asyncSend(topic, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("异步发送成功: " + sendResult.getMsgId());
            }
            
            @Override
            public void onException(Throwable e) {
                System.err.println("异步发送失败: " + e.getMessage());
            }
        });
    }
}
```

### 3. 单向发送

```java
@Service
public class OnewayMessageProducer {
    
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    
    /**
     * 单向发送消息（不关心发送结果）
     */
    public void sendOnewayMessage(String topic, Object message) {
        rocketMQTemplate.sendOneWay(topic, message);
        System.out.println("单向消息已发送");
    }
}
```

### 4. 延迟消息

```java
@Service
public class DelayMessageProducer {
    
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    
    /**
     * 发送延迟消息
     * 延迟级别: 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     */
    public void sendDelayMessage(String topic, Object message, int delayLevel) {
        Message<Object> msg = MessageBuilder.withPayload(message).build();
        SendResult sendResult = rocketMQTemplate.syncSend(topic, msg, 3000, delayLevel);
        System.out.println("延迟消息发送结果: " + sendResult.getSendStatus());
    }
}
```

## 📥 消息消费

### 1. 普通消费

```java
@Component
@RocketMQMessageListener(
    topic = "test-topic",
    consumerGroup = "test-consumer-group"
)
public class SimpleMessageConsumer implements RocketMQListener<String> {
    
    @Override
    public void onMessage(String message) {
        System.out.println("接收到消息: " + message);
        // 处理业务逻辑
    }
}
```

### 2. 带Tag过滤的消费

```java
@Component
@RocketMQMessageListener(
    topic = "order-topic",
    consumerGroup = "order-consumer-group",
    selectorExpression = "create || update"  // 只消费create和update标签的消息
)
public class OrderMessageConsumer implements RocketMQListener<OrderMessage> {
    
    @Override
    public void onMessage(OrderMessage orderMessage) {
        System.out.println("处理订单消息: " + orderMessage);
        // 处理订单业务逻辑
    }
}
```

### 3. 顺序消息消费

```java
@Component
@RocketMQMessageListener(
    topic = "order-topic",
    consumerGroup = "order-sequential-consumer",
    consumeMode = ConsumeMode.ORDERLY  // 顺序消费
)
public class OrderSequentialConsumer implements RocketMQListener<OrderMessage> {
    
    @Override
    public void onMessage(OrderMessage orderMessage) {
        System.out.println("顺序处理订单: " + orderMessage.getOrderId());
        // 按顺序处理订单
    }
}
```

### 4. 批量消费

```java
@Component
@RocketMQMessageListener(
    topic = "batch-topic",
    consumerGroup = "batch-consumer-group",
    consumeMode = ConsumeMode.CONCURRENTLY,
    messageModel = MessageModel.CLUSTERING
)
public class BatchMessageConsumer implements RocketMQListener<List<String>> {
    
    @Override
    public void onMessage(List<String> messages) {
        System.out.println("批量处理消息，数量: " + messages.size());
        for (String message : messages) {
            // 批量处理逻辑
            System.out.println("处理消息: " + message);
        }
    }
}
```

## 🔥 高级特性

### 1. 事务消息

#### 事务消息生产者
```java
@Service
public class TransactionMessageProducer {
    
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    
    /**
     * 发送事务消息
     */
    public void sendTransactionMessage(String topic, Object message, Object arg) {
        TransactionSendResult result = rocketMQTemplate.sendMessageInTransaction(
            topic, 
            MessageBuilder.withPayload(message).build(), 
            arg
        );
        System.out.println("事务消息发送结果: " + result.getLocalTransactionState());
    }
}
```

#### 事务监听器
```java
@Component
@RocketMQTransactionListener
public class OrderTransactionListener implements RocketMQLocalTransactionListener {
    
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        try {
            // 执行本地事务
            System.out.println("执行本地事务: " + new String((byte[]) msg.getPayload()));
            
            // 模拟业务处理
            if (processLocalTransaction(arg)) {
                return RocketMQLocalTransactionState.COMMIT;
            } else {
                return RocketMQLocalTransactionState.ROLLBACK;
            }
        } catch (Exception e) {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }
    
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        // 检查本地事务状态
        System.out.println("检查本地事务状态: " + new String((byte[]) msg.getPayload()));
        
        // 根据业务逻辑检查事务状态
        if (checkTransactionStatus(msg)) {
            return RocketMQLocalTransactionState.COMMIT;
        } else {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }
    
    private boolean processLocalTransaction(Object arg) {
        // 实际的本地事务处理逻辑
        return true;
    }
    
    private boolean checkTransactionStatus(Message msg) {
        // 检查事务状态的逻辑
        return true;
    }
}
```

### 2. 消息过滤

#### SQL过滤
```java
@Component
@RocketMQMessageListener(
    topic = "user-topic",
    consumerGroup = "user-consumer-group",
    selectorType = SelectorType.SQL92,
    selectorExpression = "age > 18 AND region = 'beijing'"
)
public class UserMessageConsumer implements RocketMQListener<UserMessage> {
    
    @Override
    public void onMessage(UserMessage userMessage) {
        System.out.println("处理用户消息: " + userMessage);
    }
}
```

#### 发送带属性的消息
```java
@Service
public class UserMessageProducer {
    
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    
    public void sendUserMessage(UserMessage userMessage) {
        Message<UserMessage> message = MessageBuilder
            .withPayload(userMessage)
            .setHeader("age", userMessage.getAge())
            .setHeader("region", userMessage.getRegion())
            .build();
            
        rocketMQTemplate.syncSend("user-topic", message);
    }
}
```

### 3. 消息重试和死信队列

#### 消费重试配置
```java
@Component
@RocketMQMessageListener(
    topic = "retry-topic",
    consumerGroup = "retry-consumer-group",
    maxReconsumeTimes = 3  // 最大重试次数
)
public class RetryMessageConsumer implements RocketMQListener<String> {
    
    @Override
    public void onMessage(String message) {
        try {
            // 可能失败的业务逻辑
            processMessage(message);
        } catch (Exception e) {
            System.err.println("消息处理失败，将进行重试: " + e.getMessage());
            throw new RuntimeException("处理失败", e);
        }
    }
    
    private void processMessage(String message) {
        // 业务处理逻辑
        if (message.contains("error")) {
            throw new RuntimeException("模拟处理失败");
        }
        System.out.println("成功处理消息: " + message);
    }
}
```

## 🎯 最佳实践

### 1. Topic设计原则

#### 命名规范
```java
// 推荐的Topic命名
public class TopicConstants {
    // 业务模块_消息类型_版本
    public static final String ORDER_CREATE_V1 = "order_create_v1";
    public static final String USER_REGISTER_V1 = "user_register_v1";
    public static final String PAYMENT_SUCCESS_V1 = "payment_success_v1";
}
```

#### Tag使用
```java
// 使用Tag进行消息分类
public class MessageTagConstants {
    public static final String CREATE = "create";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final String CANCEL = "cancel";
}
```

### 2. 生产者最佳实践

```java
@Configuration
public class ProducerConfig {
    
    @Bean
    public DefaultMQProducer defaultMQProducer() {
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setNamesrvAddr("192.168.17.128:9876");
        producer.setProducerGroup("agtproject-producer-group");
        
        // 性能优化配置
        producer.setSendMsgTimeout(3000);  // 发送超时时间
        producer.setRetryTimesWhenSendFailed(3);  // 同步发送失败重试次数
        producer.setRetryTimesWhenSendAsyncFailed(3);  // 异步发送失败重试次数
        producer.setMaxMessageSize(4 * 1024 * 1024);  // 最大消息大小4MB
        
        return producer;
    }
}
```

### 3. 消费者最佳实践

```java
@Component
@RocketMQMessageListener(
    topic = "order-topic",
    consumerGroup = "order-consumer-group",
    consumeThreadMin = 5,      // 最小消费线程数
    consumeThreadMax = 32,     // 最大消费线程数
    consumeTimeout = 15000L,   // 消费超时时间
    maxReconsumeTimes = 3      // 最大重试次数
)
public class OrderConsumerBestPractice implements RocketMQListener<OrderMessage> {
    
    private static final Logger logger = LoggerFactory.getLogger(OrderConsumerBestPractice.class);
    
    @Override
    public void onMessage(OrderMessage orderMessage) {
        String msgId = orderMessage.getMsgId();
        
        try {
            // 1. 幂等性检查
            if (isMessageProcessed(msgId)) {
                logger.info("消息已处理，跳过: {}", msgId);
                return;
            }
            
            // 2. 业务处理
            processOrder(orderMessage);
            
            // 3. 标记消息已处理
            markMessageProcessed(msgId);
            
            logger.info("订单消息处理成功: {}", orderMessage.getOrderId());
            
        } catch (Exception e) {
            logger.error("订单消息处理失败: {}, 错误: {}", orderMessage.getOrderId(), e.getMessage());
            throw e;  // 抛出异常触发重试
        }
    }
    
    private boolean isMessageProcessed(String msgId) {
        // 检查Redis或数据库中是否已处理
        return false;
    }
    
    private void processOrder(OrderMessage orderMessage) {
        // 实际业务处理逻辑
    }
    
    private void markMessageProcessed(String msgId) {
        // 在Redis或数据库中标记已处理
    }
}
```

### 4. 消息幂等性处理

```java
@Service
public class IdempotentMessageProcessor {
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    private static final String PROCESSED_MSG_KEY = "processed_msg:";
    private static final int EXPIRE_TIME = 24 * 60 * 60; // 24小时
    
    /**
     * 检查消息是否已处理
     */
    public boolean isMessageProcessed(String msgId) {
        String key = PROCESSED_MSG_KEY + msgId;
        return redisTemplate.hasKey(key);
    }
    
    /**
     * 标记消息已处理
     */
    public void markMessageProcessed(String msgId) {
        String key = PROCESSED_MSG_KEY + msgId;
        redisTemplate.opsForValue().set(key, "1", EXPIRE_TIME, TimeUnit.SECONDS);
    }
    
    /**
     * 处理消息（带幂等性）
     */
    @Transactional
    public void processMessageIdempotent(String msgId, Runnable businessLogic) {
        if (isMessageProcessed(msgId)) {
            return;
        }
        
        try {
            businessLogic.run();
            markMessageProcessed(msgId);
        } catch (Exception e) {
            // 处理失败，不标记为已处理
            throw e;
        }
    }
}
```

## 📊 监控运维

### 1. RocketMQ Console

#### 部署控制台
```bash
# 下载控制台
git clone https://github.com/apache/rocketmq-externals.git
cd rocketmq-externals/rocketmq-console

# 修改配置
vim src/main/resources/application.properties

# 编译打包
mvn clean package -Dmaven.test.skip=true

# 启动控制台
java -jar target/rocketmq-console-ng-1.0.1.jar
```

#### 控制台配置
```properties
# application.properties
server.port=8080
rocketmq.config.namesrvAddr=192.168.17.128:9876
rocketmq.config.dataPath=/tmp/rocketmq-console/data
```

### 2. 监控指标

#### JMX监控
```java
@Component
public class RocketMQMonitor {
    
    private MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
    
    /**
     * 获取生产者监控指标
     */
    public void getProducerMetrics() {
        try {
            ObjectName objectName = new ObjectName("org.apache.rocketmq:type=Producer,name=*");
            Set<ObjectInstance> instances = mBeanServer.queryMBeans(objectName, null);
            
            for (ObjectInstance instance : instances) {
                ObjectName name = instance.getObjectName();
                Long sendTPS = (Long) mBeanServer.getAttribute(name, "SendTPS");
                Long sendRT = (Long) mBeanServer.getAttribute(name, "SendRT");
                
                System.out.println("Producer TPS: " + sendTPS + ", RT: " + sendRT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### 3. 日志配置

#### logback-spring.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!-- RocketMQ 日志配置 -->
    <logger name="RocketmqClient" level="WARN" additivity="false">
        <appender-ref ref="STDOUT"/>
        <appender-ref ref="FILE"/>
    </logger>
    
    <logger name="org.apache.rocketmq" level="INFO" additivity="false">
        <appender-ref ref="STDOUT"/>
        <appender-ref ref="FILE"/>
    </logger>
    
    <!-- 应用日志 -->
    <appender name="ROCKETMQ_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>logs/rocketmq.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>logs/rocketmq.%d{yyyy-MM-dd}.log</fileNamePattern>
            <maxHistory>30</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
        </encoder>
    </appender>
</configuration>
```

## ❓ 常见问题

### 1. 消息发送失败

**问题**: 消息发送返回SEND_OK但消费者收不到
**解决方案**:
- 检查Topic是否存在
- 确认消费者订阅的Topic和Tag是否正确
- 检查消费者组是否正常启动
- 查看Broker日志

### 2. 消息重复消费

**问题**: 同一条消息被重复消费
**解决方案**:
- 实现消息幂等性处理
- 使用唯一消息ID进行去重
- 检查消费者的消费模式配置

### 3. 消息堆积

**问题**: 消息在队列中堆积，消费速度跟不上生产速度
**解决方案**:
- 增加消费者实例数量
- 调整消费线程数配置
- 优化消费者业务逻辑
- 考虑消息批量处理

### 4. 顺序消息乱序

**问题**: 顺序消息出现乱序
**解决方案**:
- 确保使用顺序消息发送
- 检查MessageQueueSelector实现
- 确认消费者使用ORDERLY模式
- 避免消费者异常导致的重新负载均衡

### 5. 事务消息状态异常

**问题**: 事务消息长时间处于UNKNOWN状态
**解决方案**:
- 实现本地事务状态检查逻辑
- 确保事务监听器正常工作
- 检查本地事务执行结果
- 调整事务消息检查间隔

## 🔧 性能调优

### 1. 生产者调优

```properties
# 生产者性能配置
rocketmq.producer.send-message-timeout=3000
rocketmq.producer.compress-message-body-threshold=4096
rocketmq.producer.max-message-size=4194304
rocketmq.producer.retry-times-when-send-failed=2
rocketmq.producer.retry-another-broker-when-not-store-ok=false
```

### 2. 消费者调优

```properties
# 消费者性能配置
rocketmq.consumer.consume-thread-min=20
rocketmq.consumer.consume-thread-max=64
rocketmq.consumer.consume-concurrently-max-span=2000
rocketmq.consumer.pull-threshold-for-queue=1000
rocketmq.consumer.pull-batch-size=32
```

### 3. Broker调优

```properties
# broker.conf
# 存储配置
storePathRootDir=/opt/rocketmq/store
storePathCommitLog=/opt/rocketmq/store/commitlog
mapedFileSizeCommitLog=1073741824
mapedFileSizeConsumeQueue=300000

# 性能配置
flushDiskType=ASYNC_FLUSH
brokerRole=ASYNC_MASTER
deleteWhen=04
fileReservedTime=72
maxMessageSize=65536
```

## 📚 参考资料

- [Apache RocketMQ 官方文档](https://rocketmq.apache.org/docs/quick-start/)
- [RocketMQ Spring Boot Starter](https://github.com/apache/rocketmq-spring)
- [RocketMQ 最佳实践](https://rocketmq.apache.org/docs/bestPractice/01bestpractice)
- [RocketMQ 运维指南](https://rocketmq.apache.org/docs/deploymentOperations/01deploy)

---

📝 **更新日志**
- v1.0 - 初始版本，包含基础使用和配置
- v1.1 - 添加事务消息和高级特性
- v1.2 - 完善监控运维和性能调优
- v1.3 - 增加最佳实践和常见问题解决方案