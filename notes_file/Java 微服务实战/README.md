
# [本书的组织结构](docs/本书的组织结构.md "本书的组织结构")

# [前言](docs/前言.md "前言")

## [第1章 微服务概述](docs/第1章微服务概述.md "第1章微服务概述")

* 什么是微服务
    * 微服务架构是将单体的应用开发拆解为一组“小”的服务，“小”是以业务边界来区分的。运行在一个单独的进程中;通过轻量级的机制进行通信：可通过全自动化的部署机制来独立部署；可以多种语言来编写;可使用不同数据存储技术。

* 为什么需要微服务
    * 单体架构图主要包括三部分：（请求都在一个进程中处理，水平扩展，多添加几台部署了该服务的机器，在这些服务器的前边部署一台负载均衡器就OK了）。
        * 展示层:为客户端人员提供一些交互页面。
        * 数据存储层：通常是一个数据库，用于存储一些持久化的数据。
        * 服务端的应用程序：主要用于处理请求、执行业务逻辑、操作数据库，将相关结果返回给前端等。
    * 单体架构问题：
        * 修改：业务逻辑都写在一个应用server中，任何修改需要编译打包部署整个应用。
        * 性能瓶颈：假设应用只有一个接口到达了瓶颈，要水平扩展该接口，只能通过水平扩展整个应用来达到目的。
        * 模块依赖：很难保证对一个模块的修改不会影响其他模块。
    * 微服务架构：（一个整体应用切分成多个小的服务，小的服务可独立部署并且每运行在自己的进程中）。
        * 修改：修改了一个服务的代码，只需要单独部署该服务就行。
        * 性能瓶颈：水平扩展一个服务的接口，只扩展该服务就可以了。

* 微服务架构的缺点
    * 冗余代码：应用拆解成了一系列的服务，不可避免地会在不同的服务中存在一些冗余代码。
    * 维护复杂：维护一个应用简单，但维护几十甚至几百个服务就不简单，需功底较深厚的运维人员，也要搭建完善的运维系统，如，计数监控系统、日志系统以及链路跟踪系统等。
    * 服务间调用复杂：服务间的调用由进程内调用变为进程间调用。需考虑》分布式事务处理，通信方式，网络延迟及服务容错等问题。
    * 需掌握组件技术较多：需要掌握的组件技术会比较多，开发的难度较大，开发周期会比较长。
    * 业务边界难以划分：将整体应用拆分成多个微服务，原则是根据业务拆分，但业务边界有时候是比较难划分的。

* 微服务中的组件与技术选型
    * 服务注册与服务发现：**服务注册**将服务ip和port注册到注册中心，可将注册中心理解为一个Map，key是服务唯一标识，而value是包含ipAndPort结构体集合。**服务发现**根据服务的唯一标识，从注册中心获取指定服务所在的服务器列表，根据上述Map中的key来获取value。 好处如下：
        * 服务之间的解耦。常用实现注册中心的技术有Consul、Zookeeper、Etcd和Eureka等。
    * 健康检查：(目的为在服务发现和服务路由时，可以将服务调用请求发送到处于健康状态的机器上。)
        * 服务所在服务器的运行状态；
        * 服务本身的运行状态。
    * 配置管理
        * 在一个地方将服务集中管理。
        * 实现服务的配置与代码分离。
        * 实现“热配置”。
    * 服务通信（服务之间的相互调用）
        * 服务之间的调用协议可以使用TCP协议，也可以使用HTTP协议。
    * 服务路由
        * 过程是: 当请求来时，通过服务发现和健康状态检查选出健康的服务器列表，之后采用负载均衡策略（路由策略）从这些服务器中选出一台，最后将请求发送到这台服务器上去。
    * 服务容错
        * 指的是：当服务集群中的一台机器宕机了，不会导致整个服务不可用，甚至不会因为级联失败导致多个服务不可用，形成雪崩。
    * 日志系统
        * 目的：用于收集散落在各台机器上的日志，提供高效的存储和查询方式，通过界面进行结果展示。也会提供方便的分析功能等。
    * 全链路追踪系统（以图形的形式展现出调用链信息）。
    * 计数监控系统（对服务的一系列指标进行记录监控，如：CPU使用率、内存占用率等）
    * 文档输出（将API接口进行文档化，就是通过编写代码来自动展现出API接口的各种信息）。
    * 持续集成与持续部署系统（管理代码，控制版本，打成ar包，启动jar包，打成镜像，镜像文件push到镜像仓库，镜像文件从仓库pull下来运行起来）。
    * 服务网关（=路由转发+过滤器）
        * 路由转发：指接收一切外界请求，将它们转发到后端的微服务上去。
        * 过滤器：指通过类似过滤器的方式完成一系列的横切功能。如权限校验、限流及监控等。
    * 服务编排：基于容器技术来实现一个服务的自动容错功能。



## [第2章 微服务基础框架](docs/第2章微服务基础框架.md "第2章 微服务基础框架")

* Spring Boot的优势
    * 零配置，可直接以jar包运行。
    * starter引入使得jar包管理更加智能。
    * 自动配置使得整合一些框架非常简单。

* 注解@SpringBootApplication（复合注解，包含比较重要的注解是以下三个）
    * @SpringBootConfiguration：也是一个复合注解，其最重要的注解是@Configuration，**指明该类由Spring容器管理**。
    * @EnableAutoConfiguration：用于启动服务的自动配置功能。
    * @ComponentScan：用于扫描类，作用类似于Spring中的<context:component-scan>标签。

* 注解@RestController（复合注解，包含的比较重要的注解是）
    * @Controller：控制器类。
    * @ResponseBody：映射URI与方法。

* 运行Spring Boot项目(两种方式)
    * 用man install打成jar包，后使用java -jar运行该jar包（线上部署常使用该方式）。  
    * 用mvn spring-boot:run运行jar包（本地IDE调试常使用该方式）。

* Maven依赖树验证Spring Boot自动引包功能
    * 在终端执行【mvn dependency:tree】。




## [第3章 微服务文档输出](docs/第3章微服务文档输出.md "第3章 微服务文档输出")


* Swagger概述：是一款用于设计、构建、文档化并执行API的框架。

* 使用Swagger：首先需要引入Swagger的两个依赖：springfox-swaggwer2和springfox-swagger-ui。
    * @EnableSwagger2注解：使用该注解来启动Swagger。
    * @ApiModel：为POJO类做注释。
    * @ApiModelProperty：为POJO类中的属性做注释。
    * @Api：为controller类做注解，说明controller职能。
    * @ApiOperation：为接口做注释，说明接口职能。
    * @ApiImplicitParams：包含接口一组参数注解，可简单理解为参数注解的集合。
    * @ApiImplicitParam：用在@AplimplicitParams中，说明一个请求参数的各个方面。包含常用选项有如下：
        * paramType，**参数所放置的地方**，包含query、header、path、body及form，常用的是前4个。query域中的值需要使用@RequestHeader获取，path域中的值需要使用@PathVariable获取，body域中的值需要使用@RequestBody获取，否则可能出错。
        * name，参数名。
    	* dataType，参数类型。
    	* required，参数是否必传。
    	* value，参数的值。
    	* defaultValue，参数的默认值。
    * @ApiResponses：包含接口的一组响应注解，可理解为响应注解的集合。
    * @ApiResponse：用在@ApiResponses中，用于表达一个错误的响应信息。
        * code，即httpCode数字，例如400。
    	* message，信息，例如“请求参数没填好”。

* Lombok：主要用来消除POJO模板代码（例如，getter、setter等）的框架
    * 无论Eclipse还是IDEA使用Lombok，都要安装Lombok插件。
    * @Getter：生成getter方法，可用在类或属性上。
    * @Setter：生成setter方法，可用在类或属性上。
    * @AllArgsConstructor：生成全参构造器，用在类上。
    * @NoArgsConstructor：生成无参构造器，用在类上。
    * @Builder：将类改造成builder模式，用在类、方法或构造器上。
    * @Data：复合注解，生成默认无参构造器、所有属性getter、所有非final的属性setter方法，重写toString方法，重写equals方法，重写hashcode方法。




## [第4章 微服务数据库](docs/第4章微服务数据库.md "第4章 微服务数据库")

* 使用MyBatis-Generator生成数据访问层
    * MyBatis-Generator的配置文件：
        * 数据库连接四要素（driver、connectionurl、username、password）。
        * 生成的model类、mapper接口的位置及映射文件存储目录，指定表（tableName）对应哪一个model（domainObjectName）。

* 集成MyBatis主要步骤：
    * 引包：
        * spring-boot-starter-jdbc。
        * druid：阿里巴巴的Druid数据源。
        * mysql-connector-java：scope是runtime。
        * mybatis。
        * mybatis-spring。
    * 单数据源集成MyBatis：（即配置数据源，配置MyBatis）
        * @MapperScan：指定扫描的mapper接口所在的包。
        * 创建单例DataSource：根据数据库配置。
        * 创建SqlSessionFactory单例：根据DataSource实例和SqlSessionFactionBean。
        * SqlSession: SqlSessionFactory创建出SqlSession。
        * 操作数据：使用SqlSession获取相应的Mapper实例，通过Mapper实例操作数据库。
        * 补充SqlSessionFactoryBean说明：
            * TypeAliasesPackage：指定domain类的基包，指定后在xxxMapper.xml文件中可使用简名来代替全类名。
            * MapperLocations：指定xxxMapper.xml文件所在的位置。
    * 多数据源集成MyBatis:(步骤)
        * 1.DatabaseType列出所有的数据源key作为第5步中所说的key。
        * 2.DatabaseContextHolder是一个线程安全的DatabaseType容器，并提供了向其中设置和获取DatabaseType的方法。
        * 3.DynamicDataSource继承AbstractRoutingDataSource并重写其中的方法determineCurrentLookupKey(),在该方法中使用DatabaseContextHolder获取当前线程的DatabaseType。
        * 4.在MyBatisCOnfig中生成两个数据源DataSource的bean作为第5步中所说的value。
        * 5.在MyBatisConfig中将第1步中的key和第4步中的value组成的key-value对写入DynamicDataSource动态数据源的targetDataSources属性中（当然，同时也会设置两个数据源其中的一个到DynamicDataSource的defalutTargetDataSource属性中）。
        * 6.将DynamicDataSource作为primary数据源注入SqlSessionFactory的dataSource属性中。
        * 7.使用的时候，在dao层或service层先使用DatabaseContextHolder设置将要使用的数据源key(当然也可以使用Spring AOP去做)，然后再调用mapper层进行相应的操作。在mapper层进行操作的时候，会先调用determineCurrentLookupKey()方法获取一个数据源（获取数据源的方法：先根据设置去targetDataSources中找，若没有，则选择defaultTargetDataSource），之后再进行数据库操作。
         
* @Primary注解：作用是“指定在同一个接口有多个实现类可以注入的时候，默认选择哪一个。

* MyBatis-Generator基本用法:
    * 1.生成model类、mapper接口、mapper对应的xml文件。
    * 2.数据库微小的变动时可以直接使用MyBatis-Generator重写生成以上文件。
    * 3.避免手工操作错误、jdbcType和javaType的避免对应错误。    



## [第5章微服务缓存系统](docs/第5章微服务缓存系统.md "第5章 微服务缓存系统")

* 本地缓存与分布式缓存:
    * 本地缓存：
        优点：数据存储在本机内存中，操作数据速度快。
        缺点：缓存数量与大小受限于本机内存。所有应用服务器都要维护一份缓存。
    * 分布式缓存：
        * 优点：数据存储另外机器上，理论上可添加缓存机器无限增加缓存。应用服务器不需维护缓存。
        * 缺点：由于是远程操作，操作缓存数据速度较本地缓存慢很多。

* 用得较多分布式缓存是Memcached和Redis：
    * Memcached：
        * 使用经典一致性hash算法是缓存软件算法的标准。
        * 基于slab内存模型可防止内部碎片产生，前提是设置好启动参数，否则浪费很多内存。
    * Redis：
        * 支持5种数据结构：string、hash、list、set、sorted set。
        * 提供RDB和AOF支持数据持久化。
        * 支持事件调度、发布订阅等，还可以充当一下队列。

*Redis客户端分片OR集群:
    * 客户端分片：不会共享数据，容易造成单点缓存丢失。
    * 集群会：自动在多个Redis节点之间共享数据，不会造成单点问题。
    * 优缺点：使用Redis2.x客户端分片方式，搭建Redis服务器方便，但编写代码费劲一些；使用Redis3.x集群方式，搭建Redis集群麻烦，但编写代码会简单。
            
* Spring Boot集成ShardJedis（客户端分片）：
    * 依赖：
        * Jedis。
        * 引入commons-lang3：有一系列的工具类 方便开发。
        * 引入fastjson：该JSON工具类较Jackson方便顺手。       
    * 配置Redis信息：
        * Redis server：IP 、端口。
        * maxTotal：同时建立最大连接个数（最多可分配ShardJedis实例数），默认8个，设置为-1，表示不限制。
    * 集成分片版的Jedis：
        * ShardedJedisPool：每台服务器封装成一个JedisShardInfo，通过这些JedisShardInfo组成的服务器列表以及Redis的配置信息JedisPoolConfig创建了一个ShardedJedisPool。
        * maxTotal：指定同时获取多少个ShardJedis连接实例。
    * 创建RedisUtil：操作缓存的工具类。
        * 创建相应方法：set和get。set和get的逻辑：
            * 获取操作Redis的连接：首先从ShardedJedisPool中获取shardedJedis，可理解为一个连接。
            * 数据库的操作：之后使用该连接进行数据库的操作。
            * 资源关闭：将shardedJedis资源关闭。
    * 使用：编写一个缓存前缀指定类：
        * 目的：防止缓存key冲突和增强语义。

* 搭建Redis集群（集群）：至少需要3个master实例，同时，为了使集群高可用，需为每个master实例至少分配一个slave实例。
* Spring Boot集成JedisCluster（通常使用Jedis的JedisCluster操作集群版的Redis）：
    * 依赖：
        * Jedis。
        * 引入commons-lang3：有一系列的工具类 方便开发。
        * 引入fastjson：该JSON工具类较Jackson方便顺手。 
    * 配置：
        * IP、端口、超时时长。

* GuavaCache：实现本地缓存。




## [第6章Spring Boot启动源码解析](docs/第6章Spring Boot启动源码解析.md  "第6章 Spring Boot启动源码解析")


* Spring Boot启动源码解析：
    * 关键字：定制点、初始化器（ApplicationContextInitializer接口的实现类）和监听器（ApplicationListener接口的实现类。
    * initialize方法具体做了以下几件事：
      * 将传入的“com.microservice.myserviceA.Application”放入Set<Object>sources集合。
      * 判断是否是Web环境。
        * 方法：通过在classpath中查看是否存在WEB_ENVIRONMENT_CLASSES这个数组中所包含的所有类（就2个类：javax.servlet.Servlet和org.springframework.web.context.ConfigurableWebApplicationContext），如果两个类都存在即是一个Web应用程序，反之则不然。
      * 创建并初始化ApplicationInitializer列表。
        * 方法：获取要加载的initializer的名字。反射创建对象。
      * 创建并初始化ApplicationListener列表。
        * 方法：获取要加载的initializer的名字。反射创建对象。
      * 初始化主类mainApplicationClass。
      * 启动核心run方法:
        * 创建计时器StopWatch。
        * 配置awt系统属性。
        * 获取SpringApplicationRunListeners。
        * 启动SpringApplicationRunListener。
        * 创建ApplicationArguments。
        * 创建并初始化ConfigurableEnvironment。
        * 打印Banner。
        * 创建ConfigurableApplicationContext。
        * 准备ConfigurableApplicationContext。
        * 刷新ConfigurableApplicationContext。
        * 容器刷新后动作。
        * SpringApplicationRunListeners发布finish事件。
        * 计时器停止计时。
    * 自定义监听器：就是向之前的listener列表中添加一个自己的监听器。
    * 创建启动停止计时器。
    * 配置awt系统属性。
    * 获取SpringApplicationRunListeners。
    * 启动SpringApplicationRunListener。
    * 创建ApplicationArguments。
    * 创建并初始化ConfigurableEnvironment。
    * 打印Banner。
    * 创建ConfigurableApplicationContext。
    * 准备ConfigurableApplicationContext。
    * 刷新ConfigurableApplicationContext。
    * 容器刷新后动作。
    * SpringApplicationRunListeners发布finish事件。
    * 计时器停止计时。
    * 获取属性的4种方法:
        * @Value。
        * Environment。
        * 整体代换：构建一个新类，专门用于读取配置。
        * 动态获取:主要是使用第三方的工具包提供的获取属性方法，如Archaius。




## [第7章 微服务注册与发现](docs/第7章微服务注册与发现.md "第7章 微服务注册与发现")

* 初始Consul：分布式、高可用、支持多数据中心的软件，主要用于服务注册、服务发现、key/value存储及健康检。
* 搭建Consul集群。
* 使用Consul实现服务注册与服务发现：
    * 搭建项目框架。
    * 配置服务注册信息。
    * 实现服务启动注册：实现org.springframework.context.ApplicationListener<T>，并且将泛型T指定为ContextRefreshedEvent，这样该类就会监听容器刷新事件。
    * 实现服务发现。
    * 使用Consul与Actuator实现健康检查。



## [第8章 微服务配置管理](docs/第8章微服务配置管理.md "第8章 微服务配置管理")

* 初始Archaius：是Netflix开源技术栈中的一员，提供一系列与配置管理相关的API。
* 为什么要使用Archaius：当我们修改了配置文件后，可以使修改后的配置直接生效。
* 使用Consul-KV实现配置集中管理：Consul有一个可用于管理配置文件的功能，叫做key/value store。
* 使用Archaius实现动态获取配置：
    * 创建配置信息读取源。
    * 实现服务启动时读取配置信息。
    * 动态获取配置信息。
    * 将配置信息动态加入Spring属性源的思路。
    * Archaius关键源码解析:
        * 构造属性源。【指定时间间隔去拉去配置信息】
        * 动态获取属性。



## [第9章 微服务进程间通信](docs/第9章微服务进程间通信.md "第9章 微服务进程间通信")

* 常见的三种服务通信技术：
    * OkHttp：是一种http客户端，使用连接池技术减少请求延迟，会对响应进行缓存以减少重复的网络请求，无缝支持GZIP以减少数据流量。请求和响应API都是采用流式的builder模式来设计的；OkHttp同时支持同步阻塞调用和异步调用（使用callback实现回调）。使用Okhttp至少需要JDK1.7。
    * AsyncHttpClient：目的是为让Java应用可以很方便地执行http请求并且异步处理http相应。AsyncHttpClient底层是Netty框架（一个非常优秀的rpc框架），这就注定了它的效率是很高的，优于OkHttp。使用最新版本的AsyncHttpClient需要JDK 1.8
    * Retrofit：Retrofit和OkHttp师出同门，都是Square公司开源的项目，Retrofit实际上对OkHttp进行了封装。与OkHttp不同的是，Retrofit将url（或者更确切地说是rest API）封装成了Java接口，在该接口上可以使用注解来指定一系列的相关信息，比如请求方式、请求参数等。我们不需要写该接口的实现类，Retrofit会帮我们搞定。此外，Retrofit还自己封装了Gson，实现了将返回的json串自动转换为POJO的功能。
* 总结：虽然OkHttp使用起来比较简单，因为论性能，AsyncHttpClient优于OkHttp，论设计，Retrofit优于OkHttp，。但是当引入服务路由之后，AsyncHttpClient使用起来依然简单，但是Retrofit就要使用自定义的retrofit.client.Client的实现类了，当然编写实现类也很简单，完全可以模仿Retrofit源码中给出的一些样例。

* Retrofit源码解析。



## [第10章 微服务降级容错](docs/第10章微服务降级容错.md "第10章 微服务降级容错")

* Hystrix：Netflix开源技术栈，用于在微服务中实现容错和降级。
* 为什么要使用Hystrix：
    * 解决同一服务器不同服务间资源抢占。（为每一个服务提供一个线程池，舱壁模式的一种实现）。
    * 级联失败，可能造成雪崩。（提供了一种fallback模式，快速失败。请求结果缓存。熔断、休眠窗口。）。
    * 恢复。(半开状态)。



## [第11章 微服务日志系统](docs/第11章微服务日志系统.md "第11章 微服务日志系统")

* ELK：是一个技术栈，包括Elasticsearch、Logstash和Kibana，ELK是三种技术首字母缩写。ELK主要用于日志收集、存储和查询。
    * 目的：定位问题、分析性能、数据挖掘。
    * ELK最常用的两种架构:
        * 最简架构：使用Logstash收集日志，将日志直接存储到ELasticsearch中，通过Kibana查询日志并展示。
        * 缓冲架构：将原本的Logstash按职能分为Logstash-Shipper（简称Shipper）和Logstash-Indexer（简称Indexer），其中Shipper用于收集日志，Indexer用于指定索引；第二件，在Shipper和Indexer之间添加了Redis来做缓冲，减少Indexer将数据索引到ElasticSearch的压力。



## [第12章 微服务全链路追踪系统](docs/第12章微服务全链路追踪系统.md "第12章 微服务全链路追踪系统")
