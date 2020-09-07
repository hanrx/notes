
# [README](../README.md "回到 README")


# 第3章 Spring Cloud的核心之Spring Boot

**Spring Cloud是基于Spring Boot提供的一套微服务解决方案，配置中心、服务注册和负载均衡等，都是在Spring Boot这个框架上来构建的，所以Spring Boot其实是构建Spring Cloud生态的基石**，那么到底什么是Spring Boot呢？简单来说，**Spring Boot是帮助开发者快速构建一个基于Spring Framework及Spring生态体系的应用解决方案，也是Spring Framework对于“约定优于配置（Convention over Configuration）”理念的最佳实践**。如果想更加深入地了解Spring Boot，有必要花一点时间了解一下Spring的起源。


## 3.1 重新认识Spring Boot

Spring Framework的起源需要追溯到2002年，当时很多知名公司都用Java EE标准及EJB容器作为主要的软件解决方案，其中EJB是J2EE规范的核心内容，也与我们要说的Spring Framework的诞生密切相关。
    
EJB提供了一种组件模式，使得开发人员只需要关注业务开发，不需要关心底层的实现机制，比如远程调用、事务管理等，所以很多大公司都纷纷采用EJB来部署自己的系统，但是在使用**EJB之后各种问题接踵而至，比如配置太过烦琐、臃肿、低效等**。

2002年，Rod Johnson撰写了一本名为Expert one on one J2EE design and development的书，在书中指出了Java EE和EJB容器中存在的一些缺陷，并提出了更加简捷的实现方案。之后该书的作者基于书中的理念推出了最初版本的Spring，经过10多年的发展，目前Spring的版本已经升级到了5.0。

**Spring是一个轻量级框架，它的主要目的是简化JavaEE的企业级应用开发，而达到这个目的的两个关键手段是IoC/DI和AOP。除此之外，Spring就像一个万能胶，对Java开发中的常用技术进行合理的封装和设计，使之能够快速方便地集成和开发，比如Spring集成MyBatis、Spring集成Struts等**。Spring的出现给Java EE规范统治下的黑暗时代带来了春天，很快人们就抛弃了繁重的EJB标准，Spring逐步成了现实中的Java EE开发的标准。

本书不是专门介绍Spring的，就不过多展示Spring的内容，但是Spring中的IoC与Spring Boot的核心有比较大的关系，所以有必要再多说一下。


### 3.1.1 Spring IoC/DI











# [README](../README.md "回到 README")