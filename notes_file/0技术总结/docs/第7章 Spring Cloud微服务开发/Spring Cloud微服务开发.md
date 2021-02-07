# 概述
微服务架构是一种架构模式，它提倡将单一应用程序划分成一组小的服务，服务之间互相协调、互相配合，为用户提供最终价值。每个服务运行在其独立的进程中，服务
与服务之间采用轻量级的通信机制互相协作（通常是基于HTTP协议的RESTful API）。每个服务都围绕着具体业务进行构建，并且能够被独立的部署到生成环境、类生成
环境等。另外，应当尽量避免统一的、集中式的服务管理机制，对于一个服务而言，应根据业务上下文，学长合适的语言、工具对其进行构建。
![img.png](img.png)

![img_1.png](img_1.png)

微服务目前主流体系:
![img_2.png](img_2.png)


## Boot和Cloud选型
参见官网说明：https://spring.io/projects/spring-cloud
技术选型：https://start.spring.io/actuator/info








https://www.bilibili.com/video/BV18E411x7eT?p=4



# SpringCloud微服务架构框架
## 目标：
![img_3.png](img_3.png)

## 微服务技术演变
![img_4.png](img_4.png)
* 单体应用：
    * Model1模式：jsp + java
    * Model2模式（MVC）模式：Model View Controller(web service dao)
    * 当网站流量很小时，只需一个应用，将所有功能都部署在一起，以减少部署节点和成本。
    * 此时，用于简化增删改查工作量的数据访问框架（ORM）是关键。
    * 缺点：随着应用功能的增多，代码量越来越大，越来越难维护，那怎么解决代码一体化的问题？
* 垂直应用：
    * 加统一验证validator：入用户，返回ticker。服务间调用通过ticker。
* RPC分布式应用：
* SOA流动计算架构：
  * 资源调度 负载均衡 动态服务创建... 服务治理  
* 微服务:
    微：单一职责。

### Dubbo VS Spring Cloud
* Spring全家桶
*Dubbo
  * 可以支持RESTful风格的API,调用远程API像调用本地API一样，同时基于接口的方式增加了服务间的耦合。
![img_5.png](img_5.png)


https://www.bilibili.com/video/BV1fK4y1p7N9?p=1



















