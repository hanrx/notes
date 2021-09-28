技术体系整体总结：该处只记录关键点

# 微服务

## Spring BOOT
- github： https://github.com/spring-projects/spring-boot
- releases释放版本:https://github.com/spring-projects/spring-boot/releases
### 官网
参考：https://docs.spring.io/spring-boot/docs/2.6.0-M2/reference/htmlsingle/#features

## Spring Cloud
- 官网：https://spring.io/projects/spring-cloud#overview
- 中文网站：https://www.bookstack.cn/read/spring-cloud-docs/docs-index.md

## Spring Cloud技术选型
https://start.spring.io/actuator/info

## 服务注册中心

### Eureka
```markdown
停更
```


### Zookeeper

### Consul
```markdown
go语言实现的
```


### Nacos
```markdown
推荐
经过阿里百万级测试
```


## 服务调用

### Ribbon
```markdown
停更
```

### LoadBalancer
```markdown
Spring Cloud内部孵化，发芽，未发布
```

### Fenign
```markdown
停更
```

### OpenFeign
```markdown
推荐使用。
Spring Cloud开源社区实现。

```

## 服务降级

### Hystrix
```markdown
停更
```

### resilience4j
```markdown
国外在大规模使用
```

### sentienl
```markdown
推荐
国内推荐，用的比较多
```

## 服务网关

### Zuul
```markdown
自己作死
```

### Zuul2
```markdown
核心开发跳槽，不停跳票
```

### gateway
```markdown
推荐
```

## 服务配置

### Config
```markdown
停更
```

### Nacos
```markdown
推荐

```

## 服务总线

### Bus
```markdown
停更
```

### Nacos
```markdown
推荐
```


# 需要强记单词
- FailureAnalyzer：故障分析器。Spring Boot概念，参见：https://docs.spring.io/spring-boot/docs/2.6.0-M2/reference/htmlsingle/#howto.application.failure-analyzer
  - Failure：  /ˈfeɪljə(r)/  n. 失败；失败的人（或企业、事物）；未做到；缺乏，不足；故障，衰退；公司倒闭；歉收；突然失去
  - Analyzer：/'ænəlaɪzə/ n. [计] 分析器；分析者；检偏镜
- diagnostics：/ˌdaɪəɡˈnɒstɪks/  n. 诊断学（用作单数）
- lazyInitialization：延迟初始化。Spring Boot概念，参见：7.1.2. 延迟初始化。
  - lazy： /ˈleɪzi/  adj. 懒惰的；懒洋洋的；怠惰的；慢吞吞的
  - Initialization： /ɪˌnɪʃəlaɪˈzeɪʃn/   n. [计] 初始化；赋初值
- Availability： /əˌveɪləˈbɪləti/    n. 可用性；有效性；实用性
- factories：  /'fæktriz/    工厂，车厂（factory复数形式）
- Aware：  /əˈweə(r)/    adj. 意识到的；知道的；有…方面知识的；懂世故的
- Command：   /kəˈmɑːnd/   n. 命令，指示；
- location： /ləʊˈkeɪʃn/   n. 位置；地点；外景拍摄场地
- optional： /ˈɒpʃənl/   adj. 可选择的，随意的  
- discovery：  /dɪˈskʌvəri/  n. 发现，被发现的事物；（法律）强制性透露 
- exclusion：  /ɪkˈskluːʒn/  n. 排斥，排除在外；被排除在外的人（或事物）；认为不可能；（合约中的）除外事项；<英>开除学籍
- Constructor：  /kənˈstrʌktər/  n. 构造器；构造方法；构造函数
- Configuration：  /kənˌfɪɡəˈreɪʃn/  n. 布局，构造；配置
- Optional： /ˈɒpʃənl/   adj. 可选择的，随意的
- Validated：   /ˈvælɪˌdeɪtid/ adj. 经过验证的
- discovery：   /dɪˈskʌvəri/   n. 发现，被发现的事物；（法律）强制性透露
- prefix：   /ˈpriːfɪks/   n. 前缀
- cluster：   /ˈklʌstə(r)/   n. 组，簇；星团；一连串同类事件；（聚集在同一地方的）一群人；（计算机磁盘上的）丛集，群集；辅音群，辅音连缀；基
- prometheus：  /pro'miθɪəs/   n. 普罗米修斯（希腊神话中人名，为人类盗火种甘受罚）














# 注解
- @ConfigurationProperties("my.service")：JavaBean 属性绑定。需启用 配置属性扫描。还可以在公共@Bean方法上使用它。当您想要将属性绑定到您无法控制的第三方组件时，这样做会特别有用。
- @ConstructorBinding：注释用于指示应使用构造函数绑定。这意味着绑定器将期望找到一个带有您希望绑定的参数的构造函数。如果您使用的是 Java 16 或更高版本，则构造函数绑定可以与记录一起使用。在这种情况下，除非您的记录有多个构造函数，否则不需要使用@ConstructorBinding.
- @DefaultValue：指定默认值
- @EnableConfigurationProperties：注释指定要处理的类型列表。这可以在任何@Configuration类上完成.@EnableConfigurationProperties(SomeProperties.class)
- @ConfigurationPropertiesScan：要使用配置属性扫描，请将@ConfigurationPropertiesScan注释添加到您的应用程序。
- @RefreshScope: 实现配置自动更新：




# 待补充






























