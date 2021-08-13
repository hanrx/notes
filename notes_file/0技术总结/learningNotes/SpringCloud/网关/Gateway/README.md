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

```






























