# 简要说明
SpringCloud自研的
- zull的升级一直跳票，SpringCloud最后自己研发了一个网关替代Zuul。

官网：
- 上一代zull 1.X：https://github.com/NetFlix/zull/wiki
- gateWay：https://docs.spring.io/spring-cloud-gateway/docs/2.2.9.RELEASE/reference/html/

说明：
```bash
Gateway是在Spring生态系统之上构建的API网关服务 基于Spring 5 Spring Boot 2和Project Reactor等技术 

Gateway旨在提供一种简单而有效的方式来对API进行路由 以及提供一些强大的过滤器功能 例如 **熔断 限流 重试**等


```

Gateway替换Zull的原因：
```bash
SpringCloud Gateway是Spring Cloud的一个全新项目 基于Spring 5.0+Spring Boot 2.0和Project Reactor等技术开发的网关，它旨在为微服务架构
提供一种简单有效的统一API路由管理方式。

SpringCloud Gateway作为Spring Cloud生态系统中的网关 目标是替代 Zuul 在Spring Cloud 2.0以上版本中 没有对新版本的Zull 2.0以上最新高性能版本
进行集成 仍然使用的是Zull 1.x非Reactor模式的老版本 而为了提升网关的性能 SpringCloud Gateway是基于WebFlux框架实现的 而 WebFlux框架底层则使用了高
性能的Reactor模式通信框架Netty

Spring Cloud Gateway的目标提供统一的路由方式且基于Filter链的方式提供了网关基本的功能 例如 安全 监控/指标 和限流

Gateway是基于 异步非阻塞模型上进行开发的

SpringCloud Finchley正式版之前 SpringCloud推荐网关是Netflix提供的Zuul
Zuul 1.x是一个基于阻塞I/O的API Gateway  基于Servlet 2.5使用的阻塞结构 它不支持任何长链接 如WebSocket Zull的设计模式和Nginx较像 每次IO
操作都是从工作线程中选择一个执行 请求线程被阻塞到工作线程完成 但是差别是Nginx是用C++实现 ZUll用Java实现 而JVM本身会有第一次加载较慢的情况 使Zuul得性能相对较差

在 Servlet3.1之后有了异步非阻塞的支持 而WebFlux是一个典型的非阻塞异步的框架 它的核心是基于Reactor的相关API实现的 相对于传统的web框架来说
它可以运行在诸如Netty Undertow及支持Servlet3.1的容器上 

```

# Gateway三大核心概念
Route路由：
- 是构建网关的基本模块，由ID 目标URI 一系列的断言和过滤器组成 如果断言为true则匹配该路由

Predicate断言：
- 参考的是Java8的java.util.function.Predicate 开发人员可以匹配HTTP请求中的所有内容 例如请求头或请求参数 如果请求与断言相匹配则进行路由

Filter过滤器：
- 指的是Spring框架中GatewayFilter的实例，使用过滤器，可以在请求被路由前或者之后对请求进行修改。

执行流程：
```markdown
客户端向 Spring CLoud Gateway发出请求。然后在Gateway Handler Mapping中找到与请求相匹配的路由，将其发送到Gateway Web Handler。

Handler再通过指定的过滤器链来将请求发送到我们实际的服务执行业务逻辑，然后返回

过滤器之间用虚线分开是因为过滤器可能会在发送代理请求之前pre或之后post执行业务逻辑。

Filter在pre类型的过滤器可以做参数校验、权限校验、流量监控、日志输出、协议转换等。
在post类型的过滤器中可以做相应内容、响应头的修改、日志的输出，流量监控等有着非常重要的作用。


```
# 入门配置
示例：
```yaml
spring:
  application:
    name: cloud-gateway
  cloud:
    gateway:
      routes:
        - id: payment_routh #payment_route #路由的ID,没有固定规则但要求唯一，建议配合服务名
        uri：http://localhost:8001 #匹配后提供服务的路由地址
        predicates：
        - Path=/payment/get/**  # 断言，路径相匹配的进行路由
```




















