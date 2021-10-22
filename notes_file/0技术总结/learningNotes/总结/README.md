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
- Conditional：   /kənˈdɪʃənl/   adj. 有条件的，有前提的；（从句）条件的
- cluster：  /ˈklʌstə(r)/    n. 组，簇；星团；一连串同类事件；（聚集在同一地方的）一群人；（计算机磁盘上的）丛集，群集；辅音群，辅音连缀；基
- concurrent：  /kənˈkʌrənt/   adj. 并发的；一致的；同时发生的；并存的
- atomic：  /əˈtɒmɪk/    adj. 原子的，原子能的；微粒子的
- abstract： ['æbstrækt, æb'strækt]  adj. 抽象的，纯概念的；（艺术）抽象派的；理论上的，不切实际的
- queued：  [kjuːd]    v. 排队（queue的过去分词）
- monitored：   ['mɑnətər]   v. 检测；监控；监听（外国广播或电话）；监控（无线电或电视传输信号）（monitor 的过去式及过去分词）
- concurrent：  [kənˈkʌrənt]   adj. 并发的；一致的；同时发生的；并存的
- support：  [səˈpɔːt]   v. 支持，拥护，鼓励；帮助，援助；支撑，承受（人或建筑物等的重量）；供养，赡养；资助，赞助；追随（或支持）（某个运动队）；证实，确认；（计算机，操作系统）支持（程序，语言，装置的）运行 ；支持……的生存；（土地）维持，养活；忍受，容忍；（在流行音乐会上）当助演，担任演出嘉宾；支付……费用，（尤指）用钱维持（不良嗜好，如毒品）；胜任；安慰
- park：  [pɑːk]   vt. 停放；放置；寄存 
- wait：   [weit]    v. 等候；盼望，期待；推迟，延缓；（已备好）可处理，可取用；（用于鼓励或威胁）等着；（用于打断讲话）等一下，慢着；服侍，招待
- notify：  [ˈnəʊtɪfaɪ]    v. 通报，告知；申报
- condition：  [kənˈdɪʃn]    n. 状况，状态；条件，环境；疾病；条款；<旧>社会地位
- await：   [əˈweɪt]   vt. 等候，等待；期待
- signal：  [ˈsɪɡnəl]    vt. 标志；用信号通知；表示
- permit：    [pəˈmɪt]   vt. 许可，准许
- unsafe：   [,ʌn'seif]    adj. 不安全的；危险的；不安稳的
- atomic：   [ə'tɔmik]   adj. 原子的，原子能的；微粒子的
- transaction： [trænˈzækʃn]  n. 交易，买卖，业务；（学术团体会议的）议事录，公报（transactions）；（人与人之间的）交流，相互影响；事务（元），事项
- management： [ˈmænɪdʒmənt]   n. 经营，管理，安排；管理人员，管理层；（成功的）处理手段，（有效的）处理能力；治疗，监控；<古>欺骗，欺诈
- reentrant：[riːˈentrənt]   可重入 adj. 再进去的；凹角的 
- Monitor： [ˈmɒnɪtə(r)] n. 监视器；监听器；监控器；显示屏；班长
- Future：  /ˈfjuːtʃə(r)/    n. 未来，将来；前途，前景；期货；（动词的）将来时态
- Completable：    adj. [数] 可完备化的
- Current：  /ˈkɜːrənt/    adj. (current) 现在的；流通的，通用的；最近的；草写的
- Priority： /praɪˈɒrəti/    n. 优先事项，最重要的事；优先，优先权，重点；<英>优先通行权
- Daemon：   /ˈdiːmən/   n. 守护进程；后台程序
- fallback： [ˈfɔːlbæk]    服务降级。 n. 应急计划，应变计划；减少，削减；储备物，备用物；退路；可依靠之物
- break： [breɪk]  服务熔断。v. 打破；（人或动物）骨折；擦破（皮肤）；违犯；弄坏；（机器）失灵；打断（连续性）；（天气）突变；（新闻）突然传开；终止
- flowlimit：服务限流
- dashboard：[ˈdæʃbɔːd]  n. 汽车等的仪表板；马车等前部的挡泥板
- Blocking： /ˈblɒkɪŋ/   n. 阻塞；封闭；大块
- Synchronous：   /ˈsɪŋkrənəs/ adj. 同步的；同时的
- take：   /teɪk/  vt. 拿，取；带去；使达到；偷走；夺取；买下；订阅；吃，喝，服（药）；减去；写下，记录；拍照；量取；就（座）；以……为例；接受；接待；承受；遭受；（用某种方式）对待；理解，考虑；误认为；产生（某种感情或看法）；采取（措施）；采用（形式）；需要；使用；穿……尺码的鞋或衣服；容纳；学习；参加（考试）；乘坐（交通工具）；跨过，绕过；（体育比赛中）踢，掷；举行（投票），进行（调查）
- Executor：    /ɪɡˈzekjətə(r)/    n. 执行者；[法] 遗嘱执行人
- Fixed：  /fɪkst/   adj. 确定的；固执的；<美口>处境...的；准备好的
- Pool：   /puːl/    n. 游泳池；池塘，水坑；一小片（液体或光）；（河的）深水处，深潭；后备人员，备用物品；共用的资源（或资金）；（有相同职业或共同从事某项活动的）人群；赌球（或其他体育比赛），赌注；<英>足球普尔（the pools)；落袋台球；垄断性联营；预赛组
- Reject：    /rɪˈdʒekt/   vt. 拒绝；排斥；抵制；丢弃
- Cached：   /kæʃt/    v. 贮藏起来；高速缓存（cache 的过去式和过去分词）
- Scheduled:  /ˈʃedjuːld/   adj. 预先安排的，按时刻表的；（尤指航班）定期的；<英>（建筑，历史遗迹）列入文物保护单位的
- Command： [kəˈmɑːnd]  v. 命令，下令；统率，指挥；博得，赢得；控制，掌管；居高临下，俯瞰；值（高价）
- circuit：  ['sə:kit]   vt. 绕回…环行
- breaker：  ['breikə]   n. [电] 断路器；打破者；碎浪
- reactor：  [riˈæktə(r)]  n. [化工] 反应器；[核] 反应堆；起反应的人
- Predicate：[ˈprɛdɪˌkeɪt]   v. 使……基于；断言；暗示
- route：      [ruːt]    路由 n. 路线，航线；道路，公路；（交通工具的）固定路线；巡访；途径，渠道；<北美>递送路线；用于美国干线公路号码前
- locator：  [ləu'keitə, 'ləukeitə]  n. 定位器，探测器
- stack：   /stæk    n. （整齐的）一堆；（尤指工厂的）大烟囱；堆栈；（干草或谷物的）堆；竖着置放的高保真音响设备；定高分层盘旋（等待降落）的机群；（数支步枪支起的锥形）枪架；（车辆的）排气管；（浪蚀）岩柱；垛，堆（木材计量单位）
- Reference：  /ˈrefrəns/    n. 参考，参照；涉及，提及；参考书目；介绍信；证明书
- future：  /ˈfjuːtʃə(r)/    n. 未来，将来；前途，前景；期货；（动词的）将来时态
- bootstrap：['bu:tstræp]    系统级   n. （靴筒后的）靴襻；[计] 引导程序，辅助程序；自展
- binder： [ˈbaɪndə(r)]    n. [胶粘]粘合剂；活页夹；装订工；捆缚者；用以绑缚之物
- sink：[siŋk]   v. （在水或泥里）下沉；使（船只）沉没；倒下；渗透；完全被理解；使锋利物穿透表面；（物体）沉降；消失；降低；（声音）变小；挖掘；埋入；使消沉，压抑；接近死亡；隐藏；击球入洞；（非正式）猛喝
- Exchange：[iks'tʃeindʒ]  n. 交换，互换；短暂的交谈，争吵；交战，交火；兑换，汇兑；交流，互访；交易所；（商品的）调换；电话局，电话交换台；换子，兑子
- sleuth：  [sluːθ]    n. 侦探；警犬
- Stamped： /stæmpt/   adj. 铭刻的；盖上邮戳的；顿足的
- Markable：adj. 可屏蔽的













# 注解
- @ConfigurationProperties("my.service")：JavaBean 属性绑定。需启用 配置属性扫描。还可以在公共@Bean方法上使用它。当您想要将属性绑定到您无法控制的第三方组件时，这样做会特别有用。
- @ConstructorBinding：注释用于指示应使用构造函数绑定。这意味着绑定器将期望找到一个带有您希望绑定的参数的构造函数。如果您使用的是 Java 16 或更高版本，则构造函数绑定可以与记录一起使用。在这种情况下，除非您的记录有多个构造函数，否则不需要使用@ConstructorBinding.
- @DefaultValue：指定默认值
- @EnableConfigurationProperties：注释指定要处理的类型列表。这可以在任何@Configuration类上完成.@EnableConfigurationProperties(SomeProperties.class)
- @ConfigurationPropertiesScan：要使用配置属性扫描，请将@ConfigurationPropertiesScan注释添加到您的应用程序。
- @RefreshScope: 实现配置自动更新：




# 待补充






























