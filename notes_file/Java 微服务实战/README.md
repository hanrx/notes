
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













