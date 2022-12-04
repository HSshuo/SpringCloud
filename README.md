# SpringCloud
- 参考尚硅谷 SpringCloud 学习源码
- 依赖版本都是最新版

<br>
<br>

# 初步搭建微服务架构
- 多 Maven 架构
- 引入SpringCloud、SpringBoot、SpringCloudAlibaba
- [参考 相关版本配置问题](https://blog.nowcoder.net/n/1b1b98049786426da25d5169ea2f4d85)

<br>
<br>

# SpringCloud 服务注册与发现
#### 单机 Eureka 配置
- 主要通过 pom.xml 引入 spring-cloud-starter-netflix-eureka-client、 spring-cloud-starter-netflix-eureka-server。之后再 yml 添加相应配置，主启动类添加 @EnableEurekaClient、 @EnableEurekaServer
1. 引入 pom 依赖，分为 Eureka 的服务注册中心，和 Eureka 的客户端
``` java
        <!--引入 Eureka 组件作为服务注册中心  -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
        
        <!--引入 Eureka Client  -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
```
2. 写 application.yml  文件，添加 Eureka 的相关配置

``` java
# Eureka Server 添加的配置
eureka:
  instance:
    # eureka服务端的实例名称
    hostname: localhost
  client:
    # false表示不向注册中心注册自己。
    register-with-eureka: false
    # false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    fetch-registry: false
    service-url:
      # 设置与Eureka Server交互的地址查询服务和注册服务都需要依赖这个地址。
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/


# Eureka Client 添加的配置
eureka:
  client:
    # 表示是否将自己注册进EurekaServer默认为true。
    register-with-eureka: true
    # 是否从EurekaServer抓取已有的注册信息，默认为true。单节点无所谓，集群必须设置为true才能配合 ribbon 使用负载均衡
    fetchRegistry: true
    service-url:
      defaultZone: http://localhost:7001/eureka

```
3. 写启动类 EurekaMain.class, 添加 @EnableEurekaClient、 @EnableEurekaServer

``` java
# 客户端添加
@SpringBootApplication
@EnableEurekaClient
public class OrderMain {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain.class, args);
    }
}


# Eureka 服务端添加
@SpringBootApplication
@EnableEurekaServer
public class EurekaMain {
    public static void main(String[] args) {
        SpringApplication.run(EurekaMain.class, args);
    }
}
```

4. 启动服务测试连接
![alt](https://uploadfiles.nowcoder.com/images/20221204/630417200_1670133947510/D2B5CA33BD970F64A6301FA75AE2EB22)

<br>

#### 集群 Eureka 配置
- 在原有的基础上，新添加一个 Eureka Server
- 相互注册将 Eureka 配置的 defaultZone 添加另外的 Eureka 的 service-url。使用 Eureka 集群也只需要将配置的 defaultZone 添加注册的 Eureka service-url

1. Eureka Server 配置 彼此之间相互注册
```yml
eureka:
  instance:
    # eureka 服务端的实例名称
    hostname: eureka7002.com
  client:
    # false 表示不向注册中心注册自己。
    register-with-eureka: false
    # false 表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    fetch-registry: false
    service-url:
     defaultZone: http://eureka7001.com:7001/eureka/


eureka:
  instance:
    # eureka服务端的实例名称
    hostname: eureka7001.com
  client:
    # false表示不向注册中心注册自己。
    register-with-eureka: false
    # false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    fetch-registry: false
    service-url:
      # 设置与Eureka Server交互的地址查询服务和注册服务都需要依赖这个地址。
      #      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
      defaultZone: http://eureka7002.com:7002/eureka/
```
2. Eureka Client 使用只需要增加 defaultZone 里面的 server-url 即可
```yml
      # 集群版
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
```
3. 配置 host 文件 C:\Windows\System32\drivers\etc 配置后重启生效
![img.png](img.png)
4. 测试集群是否成功、访问 http://eureka7002.com:7002/、http://eureka7001.com:7001/
![img_1.png](img_1.png)