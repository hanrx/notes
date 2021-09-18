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





# 待补充






























