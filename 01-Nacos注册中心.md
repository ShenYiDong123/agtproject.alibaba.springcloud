# Nacos 注册中心使用指南

[![Nacos](https://img.shields.io/badge/Nacos-1.3.0+-blue.svg)](https://nacos.io/)
[![Spring Cloud Alibaba](https://img.shields.io/badge/Spring%20Cloud%20Alibaba-2.2.1.RELEASE-green.svg)](https://github.com/alibaba/spring-cloud-alibaba)

## 📋 目录

- [Nacos 简介](#nacos-简介)
- [核心功能](#核心功能)
- [环境搭建](#环境搭建)
- [配置说明](#配置说明)
- [服务注册与发现](#服务注册与发现)
- [配置管理](#配置管理)
- [最佳实践](#最佳实践)
- [常见问题](#常见问题)

## 🎯 Nacos 简介

**Nacos** (Dynamic Naming and Configuration Service) 是阿里巴巴开源的一个更易于构建云原生应用的动态服务发现、配置管理和服务管理平台。

### 核心特性
- **服务发现和服务健康监测**
- **动态配置服务**
- **动态DNS服务**
- **服务及其元数据管理**

## ⚡ 核心功能

### 1. 服务注册中心
- 支持基于DNS和基于RPC的服务发现
- 支持对服务的实时健康检查
- 支持动态路由及流量管理

### 2. 配置管理中心
- 动态配置服务，让您能够以中心化、外部化和动态化的方式管理所有环境的应用配置和服务配置
- 配置变更推送，配置的变更推送给订阅者
- 历史版本管理，配置变更的历史版本管理

### 3. 服务管理
- 服务及其元数据管理
- 动态服务权重调整
- 动态服务优雅上下线

## 🚀 环境搭建

### 1. 下载安装

#### 方式一：直接下载
```bash
# 下载 Nacos Server
wget https://github.com/alibaba/nacos/releases/download/1.4.2/nacos-server-1.4.2.tar.gz

# 解压
tar -xvf nacos-server-1.4.2.tar.gz
cd nacos/bin
```

#### 方式二：Docker 部署
```bash
# 拉取镜像
docker pull nacos/nacos-server:1.4.2

# 启动容器
docker run --name nacos-standalone -e MODE=standalone -p 8848:8848 -d nacos/nacos-server:1.4.2
```

### 2. 启动服务

#### Linux/Mac
```bash
sh startup.sh -m standalone
```

#### Windows
```cmd
startup.cmd -m standalone
```

### 3. 访问控制台
- **访问地址**: http://localhost:8848/nacos
- **默认账号**: nacos
- **默认密码**: nacos

## ⚙️ 配置说明

### 1. Spring Boot 集成

#### 添加依赖
```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
</dependency>
```

#### 配置文件 (bootstrap.properties)
```properties
# 应用名称
spring.application.name=agtproject-service

# Nacos 服务发现配置
spring.cloud.nacos.discovery.server-addr=192.168.17.128:8848
spring.cloud.nacos.discovery.namespace=de0b84ff-8c90-4489-8cf2-26f7610f00d4
spring.cloud.nacos.discovery.group=DEFAULT_GROUP

# Nacos 配置中心配置
spring.cloud.nacos.config.server-addr=192.168.17.128:8848
spring.cloud.nacos.config.namespace=de0b84ff-8c90-4489-8cf2-26f7610f00d4
spring.cloud.nacos.config.group=DEFAULT_GROUP
spring.cloud.nacos.config.file-extension=properties

# 配置文件名称规则: ${spring.application.name}-${spring.profiles.active}.${file-extension}
spring.profiles.active=dev
```

### 2. 命名空间配置

#### 创建命名空间
1. 登录 Nacos 控制台
2. 进入 **命名空间** 管理
3. 新建命名空间
   - **命名空间ID**: `de0b84ff-8c90-4489-8cf2-26f7610f00d4`
   - **命名空间名**: `agtproject-dev`
   - **描述**: 开发环境命名空间

#### 命名空间隔离
- **开发环境**: `dev`
- **测试环境**: `test`
- **生产环境**: `prod`

## 🔧 服务注册与发现

### 1. 服务注册

#### 启用服务注册
```java
@SpringBootApplication
@EnableDiscoveryClient
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

#### 服务元数据配置
```properties
# 服务实例元数据
spring.cloud.nacos.discovery.metadata.version=1.0
spring.cloud.nacos.discovery.metadata.author=agtproject
spring.cloud.nacos.discovery.cluster-name=DEFAULT

# 服务权重 (0.1 - 1.0)
spring.cloud.nacos.discovery.weight=1.0

# 网络接口配置
spring.cloud.nacos.discovery.network-interface=eth0
spring.cloud.nacos.discovery.ip=192.168.17.128
spring.cloud.nacos.discovery.port=8001
```

### 2. 服务发现

#### 使用 RestTemplate
```java
@Configuration
public class RestTemplateConfig {
    
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

@Service
public class UserService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    public String getUserInfo(String userId) {
        return restTemplate.getForObject(
            "http://user-service/user/" + userId, 
            String.class
        );
    }
}
```

#### 使用 OpenFeign
```java
@FeignClient(name = "user-service")
public interface UserServiceClient {
    
    @GetMapping("/user/{userId}")
    String getUserInfo(@PathVariable("userId") String userId);
}
```

## 📝 配置管理

### 1. 配置发布

#### 在 Nacos 控制台发布配置
1. 进入 **配置管理** → **配置列表**
2. 点击 **+** 新建配置
3. 填写配置信息：
   - **Data ID**: `agtproject-service-dev.properties`
   - **Group**: `DEFAULT_GROUP`
   - **配置格式**: `Properties`
   - **配置内容**: 
     ```properties
     # 数据库配置
     spring.datasource.url=jdbc:mysql://localhost:3306/agtproject
     spring.datasource.username=root
     spring.datasource.password=123456
     
     # Redis配置
     spring.redis.host=192.168.17.128
     spring.redis.port=6379
     spring.redis.database=0
     ```

### 2. 配置监听

#### 自动刷新配置
```java
@RestController
@RefreshScope
public class ConfigController {
    
    @Value("${spring.datasource.url:}")
    private String datasourceUrl;
    
    @GetMapping("/config")
    public String getConfig() {
        return "Current datasource url: " + datasourceUrl;
    }
}
```

#### 配置变更监听
```java
@Component
public class ConfigListener {
    
    @NacosConfigListener(dataId = "agtproject-service-dev.properties")
    public void onConfigChange(String configInfo) {
        System.out.println("Config changed: " + configInfo);
    }
}
```

### 3. 配置共享

#### 共享配置
```properties
# 共享配置文件
spring.cloud.nacos.config.shared-configs[0].data-id=common-config.properties
spring.cloud.nacos.config.shared-configs[0].group=DEFAULT_GROUP
spring.cloud.nacos.config.shared-configs[0].refresh=true

# 扩展配置文件
spring.cloud.nacos.config.extension-configs[0].data-id=redis-config.properties
spring.cloud.nacos.config.extension-configs[0].group=DEFAULT_GROUP
spring.cloud.nacos.config.extension-configs[0].refresh=true
```

## 🎯 最佳实践

### 1. 命名规范

#### 服务命名
- 使用小写字母和连字符
- 体现业务含义
- 例如: `user-service`, `order-service`, `payment-service`

#### 配置文件命名
- 格式: `${服务名}-${环境}.${扩展名}`
- 例如: `user-service-dev.properties`

### 2. 环境隔离

#### 使用命名空间隔离环境
```properties
# 开发环境
spring.cloud.nacos.discovery.namespace=dev-namespace-id
spring.cloud.nacos.config.namespace=dev-namespace-id

# 测试环境
spring.cloud.nacos.discovery.namespace=test-namespace-id
spring.cloud.nacos.config.namespace=test-namespace-id

# 生产环境
spring.cloud.nacos.discovery.namespace=prod-namespace-id
spring.cloud.nacos.config.namespace=prod-namespace-id
```

### 3. 健康检查配置

```properties
# 健康检查配置
spring.cloud.nacos.discovery.heart-beat-interval=5000
spring.cloud.nacos.discovery.heart-beat-timeout=15000
spring.cloud.nacos.discovery.ip-delete-timeout=30000
```

### 4. 集群部署

#### Nacos 集群配置
```properties
# cluster.conf 文件
192.168.17.128:8848
192.168.17.129:8848
192.168.17.130:8848
```

#### 数据库配置 (application.properties)
```properties
spring.datasource.platform=mysql
db.num=1
db.url.0=jdbc:mysql://localhost:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
db.user=nacos
db.password=nacos
```

## ❓ 常见问题

### 1. 服务注册失败

**问题**: 服务无法注册到 Nacos
**解决方案**:
- 检查 Nacos 服务器是否正常运行
- 确认网络连接是否正常
- 检查命名空间和分组配置是否正确
- 查看应用日志中的错误信息

### 2. 配置无法获取

**问题**: 应用无法从 Nacos 获取配置
**解决方案**:
- 确认配置文件的 Data ID 和 Group 是否正确
- 检查命名空间配置
- 确认配置文件格式是否正确
- 检查 bootstrap.properties 配置

### 3. 服务发现异常

**问题**: 服务间调用失败
**解决方案**:
- 检查服务是否正常注册
- 确认服务名称是否正确
- 检查负载均衡配置
- 查看服务健康状态

### 4. 配置不生效

**问题**: 配置修改后不生效
**解决方案**:
- 确认是否添加了 `@RefreshScope` 注解
- 检查配置监听是否正常
- 重启应用服务
- 检查配置的优先级

## 📚 参考资料

- [Nacos 官方文档](https://nacos.io/zh-cn/docs/what-is-nacos.html)
- [Spring Cloud Alibaba 官方文档](https://github.com/alibaba/spring-cloud-alibaba/wiki)
- [Nacos Spring Boot Starter](https://github.com/nacos-group/nacos-spring-boot-project)

---

📝 **更新日志**
- v1.0 - 初始版本，包含基础配置和使用说明
- v1.1 - 添加集群部署和最佳实践
- v1.2 - 完善常见问题和解决方案