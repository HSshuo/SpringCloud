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

# 背景知识
#### 什么是服务治理
- 在传统的 RPC 远程调用框架中，管理每个服务与服务之间依赖关系比较复杂、管理比较复杂，所以需要使用服务治理，管理服务与服务之间的依赖关系，可以实现服务调用、负载均衡、容错等，实现服务注册与发现

<br>

#### CAP 理论
- CAP：Consistency（强一致性）、Availability（可用性）、Partition tolerance（分区容错性）
- 一个分布式系统不可能同时很好的满足一致性、可用性和分区容错性这三个需求，最多只能同时较好的满足两个。CA一般是单点集群，在可拓展上不太强；CP一般性能不是特别高；AP一般对一致性要求低一些
- CAP 理论关注粒度是数据，而不是整体系统设计的策略
  ![alt](https://uploadfiles.nowcoder.com/images/20221218/630417200_1671355824769/D2B5CA33BD970F64A6301FA75AE2EB22)

<br>
<br>

# SpringCloud 服务注册与发现
# Eureka
#### 概念
- SpringCloud 封装了 Netfix 公司开发的 Eureka 模块来实现服务治理
- Eureka 采用了 CS 的设计架构，Eureka Server 作为服务注册功能的服务器，是服务注册中心。而系统中的其他微服务，使用 Eureka 的客户端连接到 Eureka Server 并维持心跳连接、这样系统的维护人员就可以通过 Eureka Server 来监控系统中各个微服务是否正常运行
- 在服务注册与发现中，有一个注册中心。当服务器启动的时候，会把当前自己服务器的信息 比如 服务地址通讯地址等以别名方式注册到注册中心上。另一方（消费者|服务提供者）以该别名的方式去注册中心上获取到实际的服务通讯地址，然后再实现本地 RPC 调用
- RPC 远程调用框架的思想：在于注册中心，因为使用注册中心管理每个服务与服务之间的一个依赖关系（服务治理概念）。在任何 RPC 远程框架中，都会有一个注册中心（存放服务地址相关信息（接口地址））
- [类似于 Dubbo 系统架构图](https://blog.nowcoder.net/hshuo/213162?page=1)  
  ![alt](https://uploadfiles.nowcoder.com/images/20221203/630417200_1670068031025/D2B5CA33BD970F64A6301FA75AE2EB22)

<br>

#### 组件
- Eureka Server 提供服务注册服务：各个微服务节点通过配置启动后，会在 Eureka Server 中进行注册，这样 Eureka Server 中的服务注册表中将会存储所有可用服务节点的信息，服务节点的信息可以在界面中直观看到
- Eureka Client 通过注册中心进行访问：是一个 Java 客户端，用于简化 Eureka Server 的交互，客户端同时也具备一个内置的、使用轮询（round-robin）负载算法的负载均衡器。在应用启动后，将会向 Eureka Server 发送心跳（默认周期为 30 秒）。如果 Eureka Server 在多个心跳周期内没有接收到某个节点的心跳，Eureka Server将会从服务注册表中把这个服务节点移除（默认 90 秒）

<br>

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

<br>

#### Eureka 自我保护
- 默认情况下，如果 Eureka Server 在一定时间内没有收到某个微服务实例的心跳，Eureka Server 将会注销该实例（默认 90 秒）。但是当发生网络分区故障（延时、卡顿、拥挤）时，微服务与 Eureka Sever 之间无法正常通信，由于服务本身是健康的，所以此时不应该注销这个微服务。Eureka 通过 自我保护模式 解决这个问题，当 Eureka Server 节点 在短时间内丢失过多客户端时（可能发生了网络分区故障），那么该节点会进入自我保护模式
- 某时刻某一个微服务不可用了， Eureka 不会立刻清理，依旧会对该微服务的信息进行保存
- 属于 CAP 理论的 AP 设计思想，保证高可用、分区容错性
- 标志字样：
  ![alt](https://uploadfiles.nowcoder.com/images/20221211/630417200_1670759531891/D2B5CA33BD970F64A6301FA75AE2EB22)

<br>

**取消自我保护机制**

``` yml
# Eureka Client 配置
eureka:
  instance:
    # Eureka 客户端向服务端发送心跳的时间间隔，单位为秒（默认30秒）
    lease-renewal-interval-in-seconds: 1
    # Eureka 服务端收到最后一次心跳后等待时间上限，单位为秒（默认90秒），超时将剔除服务
    # 如果 Eureka 服务端同时有此项配置，服务端优先级大于客户端
    lease-expiration-duration-in-seconds: 2
    
    
# Eureka Sever 配置   
eureka:
  server:
    # 关闭自我保护机制，保证不可用服务及时被剔除
    enable-self-preservation: false
    eviction-interval-timer-in-ms: 2000
```

<br>
<br>

# Zookeeper
- 服务注册的节点是临时节点，属于 CAP 理论的 CP 设计思想，保证数据一致性、分区容错性
- 引入配置与 Eureka 大体一致，都是使用 @EnableDiscoveryClient、@LoadBalanced 注解

<br>

#### 安装
- [Docker 中启动 ZooKeeper](https://cloud.tencent.com/developer/article/1707754)

名称 | 命令
:---: | :---:
使用 Docker 安装 | docker run --name zookeeper -p 2181:2181 --restart always -d  zookeeper
进入容器 | docker exec -it b3cd072581b2 bash
进入 zookeeper 客户端 | ./bin/zkCli.sh -server 127.0.0.1:2181
验证是否注册上节点 | ![alt](https://uploadfiles.nowcoder.com/images/20221214/630417200_1671026583595/D2B5CA33BD970F64A6301FA75AE2EB22)

<br>
<br>

# Consul
#### 概念
- Consul 是一套开源的分布式服务发现和配置管理系统，由 HashiCorp 公司用 Go 语言开发
- 提供了微服务系统中的服务治理、配置中心、控制总线等功能。这些功能中的每一个都可以根据需要单独使用，也可以一起使用以构建全方位的服务网格，总之 Consul 提供了一种完整的服务网格解决方案
- 具有很多有点。包括：基于 raft 协议，比较简洁；支持健康检查，同时支持 HTTP 和 DNS 协议；支持跨数据中心的 WAN 集群；提供图形界面；跨平台，支持 Linux、Mac、Windows; Key-Value 的存储方式
- [Consul 官网地址](https://developer.hashicorp.com/consul/docs/intro)
- [Spring Cloud Consul](https://www.springcloud.cc/spring-cloud-consul.html)

<br>

#### 安装
- [Docker 中启动 Consul](https://blog.csdn.net/weixin_44690195/article/details/124337028)

名称 | 命令
:---: | :---:
使用 Docker 安装 | docker run --name consul1 -d -p 8500:8500 -p 8300:8300 -p 8301:8301 -p 8302:8302 -p 8600:8600 consul agent -server -bootstrap-expect=1 -ui -bind=0.0.0.0 -client=0.0.0.0
访问客户端 | http://47.95.211.172:8500/

<br>

#### 缺点
- Consul 所在的 HashiCorp 公司宣布，不允许中国境内使用该公司旗下 Terraform、Consul、Vagrant 企业版


<br>
<br>

# Nacos
- 目前章节主要包含 SpringCloud 内容，后续会补充 SpringCloudAlibaba

<br>
<br>

# 总结
- 一般会选择 AP + 最终一致性的方案来完成
- 简单来讲：AP在系统数据不同步的时候访问会返回旧值、CP在系统数据不同步的时候访问会返回错误状态（因为要保证系统之间的一致性）

![alt](https://uploadfiles.nowcoder.com/images/20221218/630417200_1671355597916/D2B5CA33BD970F64A6301FA75AE2EB22)

<br>
<br>

# SpringCloud 服务负载与调用
# 背景知识
#### 负载均衡的概念
- 可以参考 [Dubbo 的负载均衡](https://blog.nowcoder.net/n/078d7469b60f4fc78066519d506a027c)
- 简单来说就是将用户的请求平摊的分配到多个服务上，从而达到系统的高可用。常见的负载均衡有软件 Nginx、LVS、硬件 F5 等

<br>

#### 负载均衡的分类
- 进程内的负载均衡：将负载均衡逻辑集成到消费方，消费方从服务注册中心获知有哪些地址可用，然后自己再从这些地址中选择出一个合适的服务器。它属于一个类库，集成于消费方进程，消费方通过它来获取服务提供方的地址。例如：Ribbon
- 集中式的负载均衡：在服务的消费方和提供方之间使用独立的负载均衡设施，由该设施负责把访问请求通过某种策略转发至服务的提供方。例如：F5、Nginx

<br>
<br>

# Ribbon
#### 概念
- SpringCloud Ribbon 是基于 Netflix Ribbon 实现的一套客户端负载均衡的工具
- Ribbon 是 Netflix 发布的开源项目，主要功能是提供客户端的软件负载均衡算法和服务调用。Ribbon 客户端组件提供一系列完善的配置项如连接超时，重试等。简单来说，就是在配置文件中列出 LoadBalancer 后面所有的机器，Ribbon 会自动的帮助你基于某种规则（简单轮询、随机连接等）去连接这些机器。同时我们也很容易使用 Ribbon 实现自定义的负载均衡算法

<br>

#### 使用
- Ribbon 是一个软负载均衡客户端组件，可以和其他客户端结合使用，例如：Eureka。
- 但是 spring-cloud-starter-netflix-eureka-client 3.0版本以上已经将 Ribbon 替换成了 LoadBalancer，所以需要我们手动添加依赖 spring-cloud-starter-netflix-ribbon
- [Maven 仓库地址](https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-netflix-ribbon)   
  ![alt](https://uploadfiles.nowcoder.com/images/20221219/630417200_1671426020370/D2B5CA33BD970F64A6301FA75AE2EB22)

<br>

#### 实质
- **负载均衡 + RestTemplate**
- RestTemplate 有很多方法：getForObject()、getForEntity()、postForObject()、postForEntity()
- Object 返回的是 JSON，Entity 返回的是 ResponseEntity 对象（响应头、状态码、响应体）等信息
- [RestTemplate 官网](https://docs.spring.io/spring-framework/docs/5.2.2.RELEASE/javadoc-api/org/springframework/web/client/RestTemplate.html)

<br>

#### 负载均衡算法
- RoundRobinRule：轮询
- RandomRule：随机
- RestryRule：先按照 RoundRobinRule 策略获取服务，如果获取服务失败则在指定时间内会进行重试，获取可用的服务
- WeightedResponseTimeRule：对 RoundRobinRule 的拓展，响应速度越快的实例选择权重越大，越容易被选择
- BestAvailableRule：会先过滤由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量最小的服务
- AvailabilityFilteringRule：会先过滤故障实例，再选择并发较小的实例
- ZoneAvoidanceRule：默认规则，复合判断 server 所在区域的性能和 server 的可用性选择服务器

<br>

#### Ribbon 与 Nginx 的区别
- Ribbon 是本地负载均衡客户端，在调用微服务接口时候，会在注册中心上获取注册信息服务列表之后缓存到 JVM 本地，从而在本地实现 RPC 远程服务调用技术
- Nginx 是服务器负载均衡，客户端所有请求都会交给 Nginx，然后由 Nginx 实现转发请求。也就是负载均衡是由服务端实现的

<br>

#### 缺点
- [ribbon 官网地址](https://github.com/Netflix/ribbon)
- 目前处于维护状态，后期致力于将 LoadBalancer 平替 Ribbon
  ![alt](https://uploadfiles.nowcoder.com/images/20221219/630417200_1671423839057/D2B5CA33BD970F64A6301FA75AE2EB22)

<br>

#### 遇到问题
- 使用 SpringCloud3.0 以上版本还仍然想用 ribbon 的时候，会出现我的服务已经成功注册到eureka server 中了，而 ribbon 服务器找不到已经注册的服务。猜测的因为 ribbon 服务器没有在eureka server中注册成功，所以不能识别主机名称。
- 翻阅解决的主要方式就是降低版本配置
- [eureka中显示有服务但是通过ribbon调用显示No instances available for service-hello的问题](https://blog.51cto.com/u_15707676/5857021)
- [Ribbon and Eureka - No instances available](https://stackoverflow.com/questions/46747246/ribbon-and-eureka-no-instances-available)

<br>
<br>


# OpenFeign
#### Feign 概念
- Feign 是一个声明式 WebService 客户端。使用 Feign 能让编写 WebService 客户端更加简单，更容易
- Feign 内置了 Ribbon，用来做客户端的负载均衡，去调用服务注册中心的服务。不同的是，Feign 进一步封装，只需要通过定义服务绑定接口且以声明式的方法，从而完成服务调用。有点类似于 Dubbo
- 前面在使用 Ribbon + RestTemplate 时，利用 RestTemplate 对 http 请求的封装处理，形成了一套模板化的调用方法。但是在实际开发中，由于对服务依赖的调用可能不止一处，往往一个接口会被多处调用，所以通常都会针对每个微服务自行封装一些客户端类来包装这些依赖服务的调用。所以，Feign 在此基础上做了进一步封装，有他来帮助我们定义和实现依赖服务接口。在 Feign 的实现下，我们只需要创建一个接口并使用注释的方式来配置它，即可完成对服务提供者的接口绑定，简化了使用 Ribbon 时，自动封装服务调用客户端的开发量

<br>

#### OpenFeign 概念
- 在 Feign 的基础上支持 SpringMVC 注解 @RequestMapping 等等。可以通过 @FeignClient 解析 @RequestMapping 注解下的接口，并通过动态代理的方式产生实现类，实现类中做负载均衡并调用其他服务。
- 新版本的 OpenFeign 已经不在内置 Ribbon

<br>

#### 使用
- Feign 提供了日志打印功能，我们可以通过配置来调整日志级别，从而了解 Feign 中 Http 请求的细节。也就是对 Feign 接口的调用情况进行监控和输出
- Feign 也可以对超时进行配置  
  ![alt](https://uploadfiles.nowcoder.com/images/20221223/630417200_1671780327715/D2B5CA33BD970F64A6301FA75AE2EB22)

<br>
<br>