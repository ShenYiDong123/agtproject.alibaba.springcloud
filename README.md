# agtproject.alibaba.springcloud
### 项目介绍 ###：
使用springcloud阿里巴巴版本搭建的基础架构

### 基本介绍 ###
1. 采用nacos作为注册中心与配置中心，用于服务发现和服务健康监测，动态配置服务。
2. feign作为服务间远程调用，Feign是Netflix开发的声明式、模板化的HTTP客户端， Feign可以帮助我们更快捷、优雅地调用HTTP API。
3. gateway作为服务网关，简化前端的调用逻辑，同时也简化内部服务之间互相调用的复杂度；具体作用就是转发服务，接收并转发所有内外
   部的客户端调用；其他常见的功能还有权限认证，限流控制等等。
4. account和order两个服务作为业务模块进行调用。
5. sentinel进行管理服务限流和降级，Sentinel核心点在于流量控制多样化，熔断降级服务，系统负载保护，实时监控和控制台；
6. 使用skywalking进行分布式追踪、性能指标分析、应用和服务依赖分析等。
7. 另外还使用到security作为认证授权，rocketmq作为异步队列处理，
8. 使用redis中的redisson作为分布式锁，seata作为分布式事务进行demo开发

### 版本号 ###
version=1.0-20191020-001
