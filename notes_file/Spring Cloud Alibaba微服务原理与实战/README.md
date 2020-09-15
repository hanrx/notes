

## [第0章 概览简介](docs/第0章%20概览简介.md "第0章 概览简介")

* 涉及的技术组件包括分：
    * 分布式服务治理Dubbo。
    * 服务配置和服务注册中心Nacos。
    * 分布式限流与熔断Sentinel。
    * 分布式消息通信RocketMQ。
    * 分布式事务Seata及微服务网关Spring Cloud Gateway。
    
 
## [第1章 微服务的发展史](docs/第1章%20微服务的发展史.md "第1章 微服务的发展史")   

* SOA和微服务有什么区别：可以把SOA看出微服务的超集，也就是多个微服务可以组成一个SOA服务。
    * 从单体架构到分布式架构的演进：
        * 单体架构：一个war包或者jar包里面包含一个应用的所有功能，则我们称这种架构为单体架构。
        * 集群及垂直化：横向增加服务器/业务的垂直领域进行拆分。
    * SOA（Service-Oriented Architecture）：也就是面向服务的架构。
        * SOA主要解决的问题是：
            * 信息孤岛。
            * 共享业务的重用。
    * 微服务架构：
    * 两者有非常大的区别：
        * SOA关注的是服务的重用性及解决信息孤岛问题。
        * 微服务关注的是解耦，虽然解耦和可重用性从特定角度来看是一样的，但本质上是有区别的，解耦是降低业务之间的耦合度，而重用性关注的是服务的复用。
        * 微服务会更多地关注在DevOps的持续交付上，因为微服务粒度细化之后使得开发运维变得更加重要，因此微服务与容器化技术的结合更加紧密。
* 微服务架构的优点：
    * 复杂度可控：通过对共享业务更细粒度的拆分，一个服务只需要关注一个特定的业务领域，并通过定义良好的接口清晰表述服务边界。
    * 技术选型更灵活：每个微服务逗游不同的团队来维护，所有可以结合业务特性自由选择技术栈。
    * 可扩展性更强：可以根据每个微服务的性能要求和业务特点来对服务进行灵活扩展，比如通过增加单个服务的集群规模，提升部署了该服务的节点的硬件配置。
    * 独立部署：由于每个微服务都是一个独立运行的进程，所以可以实现独立部署。当某个微服务发送变更时不需要重新编译部署整个应用，并且单个微服务的代码量比较小，使得发布更加高效。
    * 容错性：在微服务架构中，如果某一个微服务发生故障，我们可以使故障隔离在单个服务中。其他服务可以通过重试、降级等机制实现应用层面的容错。
* 微服务架构面临的挑战：
    * 故障排查：一次请求可能会经历多个不同的微服务的多次交互，交互的链路可能会比较长，每个微服务会产生自己的日志，在这种情况下如果出现一个故障，开发人员定位问题的根源会比较困难。
    * 服务监控：在一个单体架构中很容易实现服务的监控，因为所有的功能都在一个服务中。在微服务架构中，服务监控开销会非常大，可以想象一下，在几百个微服务组成的架构中，我们不仅要对整个链路进行监控，还要对每个微服务都实现一套类似单体架构的监控。
    * 分布式架构的复杂性：微服务本身构建的是一个分布式系统，分布式系统涉及服务之间的远程通信，而网络通信中网络的延迟和网络故障时无法避免的，从而增加了应用程序的复杂度。
    * 服务依赖：微服务数量增加之后，各个服务之间会存在更多的依赖关系，使得系统整体更为复杂。假设你在完成一个案例，需要修改服务A、B、C，而A依赖B，B依赖C。在单体式应用中，你只需要改变相关模块，整合变化，再部署就好了。对比之下，微服务架构模式就需要考虑相关改变对不同服务的影响。比如，你需要更新服务C，然后是B，最后才是A，幸运的是，许多改变一般只影响一个服务，需要协调多个服务的改变很少。
    * 运维成本：在微服务中，需要保证几百个微服务的正常运行，对于运维的挑战是巨大的。比如单个服务流量激增时如何快速扩展、服务拆分之后导致故障点增多如何处理、如何快速部署和统一管理众多的服务等。
* 架构的本质是对系统进行有序化重构，使系统不断进化。



## [第2章 微服务解决方案之Spring Cloud](docs/第2章%20微服务解决方案之Spring%20Cloud.md "第2章 微服务解决方案之Spring Cloud")   
* 什么是Spring Cloud：Spring Cloud提供了一些可以让开发者快速构建微服务应用的工具，比如配置管理、服务发现、熔断、智能路由等，这些服务可以在任何分布式环境下很好地工作。Spring Cloud主要致力于解决如下问题：
* Spring Cloud版本简介：
    * 根据字母表的顺序结合对应版本的时间顺序来定义一个大版本：Spring Cloud项目的发布内容积累到一个临界点或者解决一些严重的Bug后，会发布一个Service Release的版本，简称SRX，其中X是一个递增的数字。
    * Spring Cloud中所有子项目都依赖Spring Boot框架：Spring Boot框架的版本号和Spring Cloud的版本号之间也存在依赖及兼容的关系。
* Spring Cloud规范下的实现：
    * Spring Cloud Netflix：包括以下组件：
        * Eureka，服务注册与发现。
        * Zuul，服务网关。
        * Ribbon，负载均衡。
        * Feign，远程服务的客户端代理。
        * Hystrix，断路器，提供服务熔断和限流功能。
        * Hystrix Dashboard，监控面板。
        * Turbine，将各个服务实例上的Hystrix监控信息进行统一聚合。                   
    * Spring Cloud Alibaba：
        * Sentinel，流量控制和服务降级。
        * Nacos，服务注册与发现。
        * Nacos，分布式配置中心。
        * RocketMQ，消息驱动。
        * Seate，分布式事务。
        * Dubbo，RPC通信。
        * OSS，阿里云对象存储（收费的云服务）。           
    * Spring Cloud Alibaba的优势:大规模应用过、不仅完全覆盖了Spring Cloud Netflix原生特性，而且还提供了更加稳定和成熟的实现。
    * Spring Cloud Alibaba的版本：在2018年10月31号发布了第一个预览版本，0.2.0.RELEASE和0.1.0.RELEASE，其中0.1.0.RELEASE与Spring Boot 1.5.x兼容，0.2.0.RELEASE与Spring Boot 2.0.x兼容。



## [第3章 Spring Cloud的核心之Spring Boot](docs/第3章%20Spring%20Cloud的核心之Spring%20Boot.md "第3章 Spring Cloud的核心之Spring Boot")

* 重新认识Spring Boot：Spring是一个轻量级框架，它的主要目的是简化JavaEE的企业级应用开发，而达到这个目的的两个关键手段是IoC/DI和AOP。
    * Spring IoC/DI：分别是控制反转和依赖注入。
        * IoC（控制反转）：实际上就是把对象的生命周期托管到Spring容器中，而反转是指对象的获取方式被反转了。
        * DI（Dependency Inject）：也就是依赖注入，简单理解就是IoC容器在运行期间，动态地把某种依赖关系注入组件中。实现依赖注入的方法有三种，分别是接口注入、构造方法注入和setter方法注入。
    * Bean装配方式的升级：
        * XML配置的方式：随着项目规模不断扩大，XML的配置也逐渐增多，使得配置文件难以管理。另一方面，项目中依赖关系越来越复杂，配置文件变得难以理解。
        * 注解的方式：随着JDK 1.5带来的注解支持，Spring从2.x开始，可以使用注解的方式来对Bean进行声明和注入，大大减少了XML的配置量。
        * JavaConfig：Spring升级到3.x后，提供了JavaConfig的能力，它可以完全取代XML，通过Java代码的方式来完成Bean的注入。
    * Spring Boot解决：
        * 依赖过多。Spring可以整合几乎所有常用的技术框架，比如JSON、MyBatis、Redis、Log等，不同依赖包的版本很容易导致版本兼容的问题。
        * 配置太多。以Spring使用JavaConfig方式整合MyBatis为例，需要配置注解驱动、配置数据源、配置MyBatis、配置事务管理器等，这些只是集成一个技术组件需要的基础配置，在一个项目中这类配置很多，开发者需要做很多类似的重复工作。
        * 运行和部署很烦琐。需要先把项目打包，再部署到容器上。
* Spring Boot的价值：在Spring框架中支持无容器Web应用程序体系结构。
    * 主要作用：简化Spring应用的开发，开发者只需要通过少量的代码就可以创建一个产品级的Spring应用，而达到这一目的最核心的思想就是“约定优于配置（Convention over Configuration）”。  
    * 约定优于配置（Convention Over Configuration）：是一种软件设计范式，目的在于减少配置的数量或者降低理解难度，从而提升开发效率。
        * 在Spring Boot中，约定优于配置的思想主要体现在以下方面（包括但不限于）：
            * Maven目录结构的约定。
            * Spring Boot默认的配置文件及配置文件中配置属性的约定。
            * 对于Spring MVC的依赖，自动依赖内置的Tomcat容器。
            * 对于Starter组件自动完成装配。
    * Spring Boot的核心
        * Starter组件，提供开箱即用的组件。
        * 自动装配，自动根据上下文完成Bean的装配。
        * Actuator，Spring Boot应用的监控。
        * Spring Boot CLI，基于命令行工具快速构建Spring Boot应用。
* Spring Boot自动装配的原理：简单来说，就是自动将Bean装配到IoC容器。基于某种约定或者规范，只要Starter组件符合Spring Boot中自动装配约定的规范，就能实现自动装配。
    * 自动装配的实现：
        * 自动装配在Spring Boot中是通过@EnableAutoConfiguration注解来开启的，这个注解的声明在启动类注解@SpringBootApplication内。
            * @EnableAutoConfiguration：注解里，可以看到除@Import注解之外，还多了一个@AutoConfigurationPackage注解（它的作用是把使用了该注解的类所在的包及子包下所有组件扫描到Spring IoC容器中）。实现配置类的导入。
                * @AutoConfigurationPackage：AutoConfigurationImportSelector实现了ImportSelector，它只有一个SelectImports抽象方法，并且返回一个String数组，在这个数组中可以指定需要装配到IoC容器的类，当在@Import中导入一个ImportSelector的实现类之后，会把该实现类中返回的Class名称都装载到IoC容器中。
    * 自动装配原理分析：自动装配的核心是扫描约定目录下的文件进行解析，解析完成之后把得到的Configuration配置类通过ImportSelector进行导入，从而完成Bean的自动装配过程。
        * 定位到AutoConfigurationImportSelector中的selectImports方法，它是ImportSelector接口的实现，这个方法中主要有两个功能：
            * AutoConfigurationMetadataLoader.loadMetadata从META-INF/spring-autoconfigure-metadata.properties中加载自动装配的条件元数据，简单来说就是只有满足条件的Bean才能够进行装配。
            * 收集所有符合条件的配置类autoConfigurationEntry.getConfigurations()，完成自动装配。
        * 用到了SpringFactoriesLoader：它是Spring内部提供的一种约定俗成的加载方式，类似于Java中的SPI。简单来说，它会扫描classpath下的META-INF/spring.factories。
* 自动装配的核心过程：
    * 通过@Import（AutoConfigurationImportSelector）实现配置类的导入，但是这里并不是传统意义上的单个配置类装配。
    * AutoConfigurationImportSelectory类实现了ImportSelector接口，重写了方法selectImports，它用于实现选择性批量配置类的装配。
    * 通过Spring提供的SpringFactoriesLoader机制，扫描classpath路径下的META-INF/spring.factories，读取需要实现自动装配的配置类。
    * 通过条件筛选的方式，把不符合条件的配置类移除，最终完成自动装配。
* @Conditional条件装配：注解的作用是提供自动装配的条件约束。
* Spring Boot中的@Conditional：
    * ConditionalOnBean/ConditionalOnMissingBean：容器中存在某个类或者不存在某个Bean时进行Bean装载。
    * ConditionalOnClass/ConditionalOnMissingClass：classpath下存在指定类或者不存在指定类时进行Bean装载。
    * ConditionalOnCloudPlatform：只有运行在指定的云平台上才加载指定的Bean。
    * ConditionalOnExpression：基于SpEl表达式的条件判断。
    * ConditionalOnJava：只有运行指定版本的Java才会加载Bean。
    * ConditionalOnJndi：只有指定的资源通过JNDI加载后才加载Bean。
    * ConditionalOnWebApplication/ConditionalOnNotWebApplication：如果是Web应用或者不是Web应用，才加载指定的Bean。
    * ConditionalOnProperty：系统中指定的对应的属性是否有对应的值。
    * ConditionalOnResource：要加载的Bean依赖指定资源是否存在于classpath中。
    * ConditionalOnSingleCandidate：只有在确定了给定Bean类的单个候选项时才会加载Bean。
* Spring-autoconfigure-metadata：除了@Conditional注解类，在Spring Boot中还提供了spring-autoconfigure-metadata.properties文件来实现批量自动装配条件配置。通过这种配置化的方式来实现条件过滤必须要遵循两个条件：
    * 配置文件的路径和名称必须是/META-INF/spring-autoconfigure-metadata.properties。
    * 配置文件中key的配置格式：自动配置类的全路径名.条件=值。
* 手写实现一个Starter：
    * Staeter组件主要有三个功能：
        * 涉及相关组件的Jar包依赖。
        * 自动实现Bean的装配。
        * 自动声明并加载application.properties文件中的属性配置。
    * Starter的命名规范：
        * 官方命名的格式为：spring-boot-starter-模块名称，比如spring-boot-starter-web。
        * 自定义命名格式为：模块名称-spring-boot-starter，比如mybatis-spring-boot-starter。
    * 实现基于Redis的Starter：



## [第4章 微服务架构下的服务治理](docs/第4章%20微服务架构下的服务治理.md "第4章 微服务架构下的服务治理")
* 微服务间通信问题比如：
    * 如何协调线上运行的服务，以及保障服务的高可用性。
    * 如何根据不同服务的访问情况来合理地调控服务器资源，提高机器的利用率。
    * 线上出现故障时，如何动态地对故障业务做降级、流量控制等。
    * 如何动态地更新服务中的配置信息，比如限流阈值、降级开关等。
    * 如何实现大规模服务机器所带来的服务地址的管理和服务上下线的动态感知。
* 如何理解Apache Dubbo：Apache Dubbo是一个分布式服务框架，主要实现多个系统之间的高性能、透明化调用，简单来说它就是一个RPC框架，但是和普通RPC框架不同的是，它提供了服务治理功能，比如服务注册、监控、路由、容错等。
* Spring Boot集成Apache Dubbo：
    * 服务提供者开发流程：
        * 创建接口(@Service注解发布服务)，实现接口。
        * 引入以下依赖，其中dubbo-spring-boot-starter是Apche Dubbo官方提供的开箱即用的组件。
        * 添加配置。
        * Spring Boot启动方法上添加@DubboComponentScan注解，它的作用和Spring Framework提供的@ComponetScan一样，只不过这里扫描的是Dubbo中提供的@Service注解。
    * 服务调用者的开发流程：
        * 添加Jar包依赖。
        * 使用Dubbo提供的@Reference注解来获得一个远程代理对象。
* 快速上手ZooKeeper：是一个高性能的分布式协调中间件。
    * ZooKeeper的安装。
    * ZooKeeper的数据结构：是一种层次化的属性结构，ZooKeeper的数据是结构化存储的，并没有在物理上体系出文件和目录。
    * ZooKeeper的特性：
        * 节点类型分为：持久化节点，临时节点，有序节点，容器节点，TTL节点。
    * Watcher机制：也就是当Znode节点状态发生变化时或者ZooKeeper客户端连接状态发生变化时，会触发事件通知。
        * getData()、getChildren()、exists()。
    * 常见应用场景分析：分布式锁、Master选举
* Apache Dubbo集成ZooKeeper实现服务注册：
    * 在远程RPC通信过程中，会遇到两个比较尖锐的问题：
        * 服务动态上下线感知。
        * 负载均衡。
* Apache Dubbo集成ZooKeeper实现服务注册的步骤：
    * 添加依赖。
    * 修改配置文件。
* ZooKeeper注册中心的实现原理：
    * Dubbo还可以针对不同的情况来实现以下功能**。
        * 基于临时节点的特性，当服务提供者宕机或者下线时，注册中心会自动删除该服务提供者的信息。
        * 注册中心重启时，Dubbo能够自动恢复注册数据及订阅请求。
        * 为了保证节点操作的安全性，ZooKeeper提供了ACL权限控制，在Dubbo中可以通过dubbo.registry.username/dubbo.registry.password设置节点的验证信息。
        * 注册中心默认的根节点是/dubbo，如果需要针对不同环境设置不同的根节点，可以使用dubbo.registry.group修改跟节点名称。
* 实战Dubbo Spring Cloud：Dubbo Spring Cloud是Spring Cloud Alibaba的核心组件，它构建在原生的Spring Cloud标准之上，不仅覆盖了Spring Cloud原生特性，还提供了更加稳定和成熟的实现。
    * 实现Dubbo服务提供方：
    * 实现Dubbo服务调用方：
    
    
* Apache Dubbo的高级应用：
    * Apache Dubbo更像一个生态，它提供了很多比较主流框架的集成，比如：
        * 支持多种协议的服务发布，默认是dubbo://，还可以支持rest://、webservice://、thrift://等。
        * 支持多种不同的注册中心，如Nacos、ZooKeeper、Redis，未来还将会支持Consul、Eureka、Etcd等。
        * 支持多种序列化技术，如avro、fst、fastjson、hessian2、kryo等。
        * *除此之外，Apache Dubbo在服务治理方面的功能非常完善，比如集群容错、服务路由、负载均衡、服务降级、服务限流、服务监控、安全验证等。
        
    * 集群容错：Dubbo默认提供了6种容错模式，默认为Failover Cluster。
        * Failover Cluster，失败自动切换。当服务调用失败后，会切换到集群中的其他机器进行重试，默认重试次数为2，通过属性retries=2可以修改次数，但是重试次数增加会带来更长的响应延迟。这种容错模式通常用于读操作，因为事务型操作会带来数据重复问题。
        * Failfast Cluster，快速失败，当服务调用失败后，立即报错，也就是只发起一次调用。通常用于一些幂等的写操作，比如新增数据，因为当服务调用失败时，很可能这个请求已经在服务器端处理成功，只是因为网络延迟导致响应失败，为了避免在结果不确定的情况下导致数据重复插入的问题，可以使用这种容错机制。
        * Failsafe Cluster，失败安全。也就是出现异常时，直接忽略异常。
        * Failback Cluster，失败后自动回复。服务调用出现异常时，在后台记录这条失败的请求定时重发。这种模式适合用于消息通知操作，保证这个请求一定发送成功。
        * forking Cluster，并行调用集群中的多个服务，只要其中一个成功就返回。可以通过forks=2来设置最大并行数。
        * Broadcast Cluster，广播调用所有的服务提供者，任意一个服务报错则表示服务调用失败。这种机制通常用于通知所有的服务提供者更新缓存或者本地资源信息。
    * 集群容错配置方式：只需要在指定服务的@Service注解上增加一个参数即可。
    
    * 负载均衡：提供了4中负载均衡策略，默认负载均衡策略是random。
        * Random LoadBalance，随机算法。可以针对性能较好的服务器设置较大的权重值，权重值越大，随机的概率也会越大。
        * RoundRobin LoadBalance，轮询。按照公约后的权重设置轮询比例。
        * LeastActive LoadBalance，最少活跃调用书。处理较慢的节点将会收到更少的请求。
        * ConsistentHash LoadBalance，一致性Hash。相同参数的请求总是发送到同一个服务提供者。
    * 负载均衡配置方式：在@Service注解上增加loadbalance参数。

    * 服务降级：降级有多个层面的分类：
        * 按照是否自动化可以分为自动降级和人工降级。
        * 按照功能可分为读服务降级和写服务降级。       

    * 主机绑定规则：即查询接口发布地址IP。


* Apache Dubbo核心源码分析：

    * Dubbo的核心之SPI：扩展点实际上就是Dubbo中的SPI机制。自适应扩展点、指定名称的扩展点、激活扩展点三种。
        * Java SPI扩展点实现：类似于JDBC连接驱动，定义了接口，由第三方厂商提供实现。
        
        * Dubbo自定义协议扩展点：Dubbo或者SpringFactoriesLoader并没有使用JDK内置的SPI机制，只是利用了SPI的思想根据实际情况做了一些优化和调整。**Dubbo SPI的相关逻辑被封装在了ExtensionLoader类中**，通过ExtensionLoader我们可以加载指定的实现类。
            * 方法：通过注解@SPI注解标准接口为扩展点。在resources/META-INF/dubbo目录下创建以SPI接口命名的文件，配置接口实现类。
            
        * Dubbo SPI扩展点源码分析：


* 无处不在的自适应扩展点：

* Dubbo中的IoC和AOP：

* Dubbo和Spring完美集成的原理：


## [第5章 服务注册与发现](docs/第5章%20服务注册与发现.md "第5章 服务注册与发现")
* 服务注册与发现：
    * 引入原因：防止请求发送到已宕机的节点上造成请求失败。
    * 主要有以下功能：
        * 服务地址的管理。
        * 服务注册。
        * 服务动态感知。

* 什么是Alibab Nacos：
    * 关键特性：
        * 服务发现和服务健康监测。
        * 动态配置服务。提供了包括配置版本跟踪、金丝雀发布、一键回滚配置及客户端配置更新状态跟踪在内的一系列开箱即用的配置管理特性。
        * 动态DNS服务。开发者更容易地实现中间层负载均衡、更灵活的路由策略、流量控制，以及数据中心内网的简单DNS解析服务。
        * 服务及其元数据管理。包括管理服务的描述、生命周期、服务的静态依赖分析、服务的健康状态、服务的流量管理、路由及安全策略、服务的SLA及最重要的metrics统计数据。

* Nacos的基本使用：
    * Nacos的安装：分别是单机、集群和多集群。
    * Nacos服务注册发现相关API说明：

* Nocas集成Spring Boot实现服务注册与发现：
    * 添加Maven依赖。
    * 创建DiscoveryController类，通过@NacosInjected注入Nacos的NamingService，并提供discovery方法，可以根据服务名称获得注册到Nacos上的服务地址。
    * 在application.properties中添加Nacos服务地址的配置。
    
    * Nacos的高可用部署：Nacos提供了类似于ZooKeeper的集群架构，包括一个Leader节点和多个Follower节点。数据一致性算法采用的是Raft。
    
    * 安装环境要求：
    
    * 安装包及环境准备：

    * 集群配置:
    
    * 配置MySQL数据库：
    
    * 启动Nacos服务：

* Dubbo使用Nacos实现注册中心：
    * 添加依赖
    * 修改application.properties配置
    * 运行Spring Boot启动类，注意需要声明DubboComponentScan
    
    
* Spring Cloud Alibaba Nacos Discovery：
    * 服务端开发：
        * 暴露服务接口。
        * 接口的实现。 
        * 添加依赖：
            * spring-cloud-starter：Spring Cloud核心包。
            * spring-cloud-starter-dubb：引入Spring Cloud Alibaba Dubbo。
            * spring-cloud-dubbo-sample-api：API的接口声明。
            * spring-cloud-alibaba-nacos-discovery：基于Nacos的服务注册与发现。
        * 创建接口的实现类HelloServiceImpl，添加@Service（Dubbo服务的）注解，表示当前服务会发布成一个远程服务。
        * 在application.properties中提供Dubbo及Nacos的配置：
            * dubbo.scan.base-packages：功能等同于@DubboComponentScan，指定Dubbofu服务实现类的扫描包路径。
            * dubbo.registry.address：Dubbo服务注册中心的配置地址。
            * spring.cloud.nacos.discovery.server-addr：Nacos服务注册中心的地址。
     * 启动服务。
    * 消费端开发：    

* Nacos实现原理分析：





        
    

## [第8章 分布式事务](docs/第8章%20分布式事务.md "第8章 分布式事务")

* 数据库事务：作为单个逻辑工作单元执行的多个数据库操作，要么同时成功，要么同时失败，它必须满足ACID特性。
    * 原子性（Atomicity）：事务必须是原子工作单元，不可继续分割，要么全部成功，要么全部失败。
    * 一致性（Consistency）：事务完成时，所有数据都必须保持一致。
    * 隔离性（Isolation）：由于并发事务所做的修改必须与任何其他并发事务所做的修改隔离。
    * 持久性（Durability）：事务执行完成之后，它对系统的影响是永久的。

* 分布式事务：是指事务的参与者、支持事务的服务器、资源服务器以及事务管理器分别位于分布式系统的不同节点上。
* X/Open DTP：是X/Open这个组织定义的一套分布式事务的标准。提出了使用两个阶段提交（2PC，Two-Phase-Commit）来保证分布式事务的完整性。包含以下三种角色。(TM和多个RM之间的事务控制，是基于XA协议（XA Specification）来完成的。XA协议是X/Open提出的分布式事务处理规范，也是分布式事务处理的工业标准)
    * AP：Application，表示应用程序。
    * RM：Resource Manager，表示资源管理器，比如数据库。
    * TM：Transaction Manager，表示事物管理器。

* 两个阶段提交协议：
    * 第一阶段是事务的准备阶段。
    * 第二阶段是事务的提交或者回滚阶段。
    缺点：
        * 同步阻塞：RM都是事务阻塞型的，对于任何一次指令都必须要有明确的响应才能继续进行下一步，否则就会处于阻塞状态，占用的资源一直被锁定。
        * 过于保守：任何一个节点失败都会导致数据回滚。
        * 事务协调者的单点故障：如果协调者在第二阶段出现了故障，那么其他的参与者（RM）会一直处于锁定状态。
        * “脑裂”导致数据不一致问题：在第二阶段中，事务协调者向所有参与者（RM）发送commit请求后，发生局部网络异常导致只有一部分参与者（RM）接受了commit请求，这部分参与者（RM）收到请求后会执行commit操作，但是未收到commit请求的节点由于事务无法提交，导致数据出现不一致问题。

* 三阶段提交协议：（三阶段提交协议是两阶段提交协议的改进版本，它利用**超时机制**解决了同步阻塞的问题）
    * CanCommit（询问阶段）：事务协调者向参与者发送事务执行请求，询问是否可以完成指令，参与者只需要回答是或者不是即可，不需要做真正的事务操作，这个阶段会有**超时中止机制**。
    * PreCommit（准备阶段）：事务协调者会根据参与者的反馈结果决定是否继续执行，如果在询问阶段所有参与者都返回可以执行操作，则事务协调者会向所有参与者发送PreCommit请求，参与者收到请求后写redo和undo日志，执行事务操作但是不提交事务，然后返回ACK响应等待事务协调者的下一步通知。如果在询问阶段任意参与者返回不能执行操作的结果，那么事务协调者会向所有参与者发送事务中断请求。
    * DoCommit（提交或回滚阶段）：这个阶段也会存在两种结果，仍然根据上一步骤的执行结果来决定DoCommit的执行方法。如果每个参与者在PreCommit阶段都返回成功，那么事务协调者会向所有参与者发起事务提交指令。反之，如果参与者中的任一参与者返回失败，那么事务协调者就会发起中止指令来回滚事务。
    * 三阶段提交协议和两阶段提交协议相比有一些不同点：
        * 增加了一个CanCommit阶段，用于询问所有参与者是否可以执行事务操作并且响应，它的好处是，可以**尽早发现无法执行操作而中止后续的行为**。
        * 在准备阶段之后，事务协调者和参与者都引入了超时机制。
    * 两阶段提交和三阶段提交是XA协议解决分布式数据一致性问题的基本原理，但是这**两种方案为了保证数据的强一致性，降低了可用性**。

* CAP定理和BASE理论：
    * CAP定理：又叫布鲁尔定理。简单来说它是指在分布式系统中不可能同时满足一致性（C：Consistency）、可用性（A：Avaliability）、分区容错性（P：Partition Tolerance）这三个基本需求，最多同时满足两个
        * C：数据在多个副本中要保持强一致，比如前面说的分布式数据一致性问题。
        * A：系统对外提供的服务必须一直处于可用状态，在任何故障下，客户端都能在**合理的时间**内获得服务端的**非错误响应**。
        * P：**在分布式系统中遇到任何网络分区故障，系统仍然能够正常对外提供服务**。
        
        * AP：**对于AP来说，相当于放弃了强一致性，实现最终的一致，这是很多互联网公司解决分布式数据一致性问题的主要选择**。
        * CP：**放弃了高可用性，实现强一致性。前面提到的两阶段提交和三阶段提交都采用这种方案。可能导致的问题是用户完成一个操作会等待较长的时间**。
    * BASE理论：核心思想是通过牺牲数据的强一致性来获得高可用性。它有如下三个特性。
        * Basically Available（基本可用）：分布式系统在出现故障时，**允许损失一部分功能的可用性**，保证核心功能的可用。
        * Soft State（软状态）：允许系统中的数据存在中间状态，这个状态不影响系统的可用性，也就是**允许系统中不同节点的数据副本之间的同步存在延时**。
        * Eventually Consistent（最终一致性）：中间状态的数据在经过一段时间之后，会达到一个**最终的数据一致性**。
     
* 分布式事务问题的常见解决方案：
    * 基于CP的强一致性方案在**数据库性能和系统处理能力上会存在一定的瓶颈**。所以在互联网场景中更多采用**柔性事务**，所谓的**柔性事务是遵循BASE理论来实现的事务模型，它有两个特性：基本可用、柔性状态**。

* TCC补偿性方案：TCC（Try-Cofirm-Cancel）是一种比较成熟的分布式数据一致性解决方案，它实际上是把一个完整的业务拆分为如下三个步骤。
    * Try：这个阶段主要对数据的校验或者资源的预留。
    * Confirm：确认真正执行的任务，只操作Try阶段预留的资源。
    * Cancel：取消执行，释放Try阶段预留的资源。

* 基于可靠消息的最终一致性方案：主要利用消息中间件（Kafka、RocketMQ或RabbitMQ）的可靠性机制来实现数据一致性的投递。
    * 以电商平台的支付场景为例，用户完成订单的支付后不需要同步等待支付结果，可以继续做其他事情。支付服务收到支付结果通知后，先更新支付订单的状态，再发送一条消息到分布式消息队列中，账户服务会监听到指定队列的消息并进行相应的处理，完成数据的同步。
    * 问题：就是支付服务的本地事务与发送消息这个操作的原子性问题。
        * 先发送消息，再执行数据库事务，在这种情况下可能会出现消息发送成功但是本地事务更新失败的情况，仍然会导致数据不一致的问题。
        * 先执行数据库事务操作，再发布消息，在这种情况下可能会出现MQ响应超时导致异常，从而将本地事务回滚，但消息可能已经发生成功了，也会存在数据不一致的问题。
    * 成熟的解决方案，**以RocketMQ为例，它提供了事务消息模型，具体的执行逻辑如下**：
    * 生产者发送一个事务消息到消息队列上，消息队列只记录这条消息的数据，此时消费者无法消费这条消息。
    * 生产者执行具体的业务逻辑，完成本地事务的操作。
    * 接着生产者根据本地事务的执行结果发送一条确认消息给消息队列服务器，如果本地事务执行成功，则发送一个Commit消息，表示在第一步中**发送的消息可以被消费**，否则，消息队列服务器会把第一步存储的消息删除。
    * 如果生产者在执行本地事务的过程中因为某些情况一直未给消息队列服务器发送确认，那么**消息队列服务器会定时主动回查生产者**获取本地事务的执行结果，然后根据回查结果来决定这条消息是否需要投递给消费者。
    * 消息队列服务器上存储的消息被生产者确认之后，消费者就可以消费这条消息，消息消费完成之后发送一个确认标识给消息队列服务器，标识该消息投递成功。


* 最大努力通知型：最大努力通知型和基于可靠消息的最终一致性方案的实现是类似的，它是一种比较简单的柔性事务解决方案，也比较适用于对数据一致性要求不高的场景，最典型的使用场景是支付宝支付结果通知。实现流程如下：
    * 商户先创建一个支付订单，然后调用支付宝发起支付请求。
    * 支付宝唤醒支付页面完成支付操作，支付宝同样会针对该商户创建一个支付交易，并且根据用户的支付结果记录支付状态。
    * 支付完成后触发一个回调通知给商户，商户收到该通知后，根据结果修改本地支付订单的状态，并且返回一个处理状态给支付宝。
    * 针对这个订单，在理想状态下支付宝的交易状态和商户的交易状态会在通知完成后达到最终一致。**但是由于网络的不确定性，支付结果通知可能会失败或者丢失，导致商户端的支付订单的状态是未知的。所以最大努力通知型的作用就体现了，如果商户端在收到支付结果通知后没有返回一个“SUCCESS”状态码，那么这个支付结果回调请求会以衰减重试机制（逐步拉大通知的间隔）继续触发，比如1min、5min、10min、30min……直到达到最大通知次数**。如果达到指定次数后商户还没有返回确认状态，怎么处理呢？
    * 支付宝提供了一个交易结果查询接口，可以根据这个支付订单号去支付宝查询支付状态，然后根据返回的结果来更新商户的支付订单状态，这个过程可以通过定时器来触发，也可以通过人工对账来触发。


* 分布式事务框架Seata：
    * 提供了AT、TCC、Saga和XA事务模式，为开发者提供了一站式的分布式事务解决方案。

AT模式：Seata最主推的分布式事务解决方案，它是基于XA演进而来的一种分布式事务模式，所以它同样分为三大模块，分别是**TM、RM和TC，其中TM和RM作为Seata的客户端与业务系统集成，TC作为Seata的服务独立部署**。**TM表示事务管理器（Transaction Manager），它负责向TC注册一个全局事务，并生成一个全局唯一的XID**。在AT模式下，**每个数据库资源被当做一个RM（Resource Manager），在业务层面通过JDBC标准的接口访问RM时，Seata会对所有请求进行拦截。每个本地事务进行提交时，RM都会向TC（Transaction Coordinator，事务协调器）注册一个分支事务**。
    * 具体执行流程如下：
            * TM向TC注册全局事务，并生成全局唯一的XID。
            * RM向TC注册分支事务，并将其纳入该XID对应的全局事务范围。
            * RM想TC汇报资源的准备状态。
            * TC汇总所有事务参与者的执行状态，决定分布式事务是全部回滚还是提交。
            * TC通知所有RM提交/回滚事务。

* Saga模式：又称为长事务解决方案，**主要描述的是在没有两阶段提交的情况下如何解决分布式事务问题**。其核心思想是：**把一个业务流程中的长事务拆分为多个本地短事务，业务流程中的每个参与者都提交真实的提交给该本地短事务，当其中一个参与者事务执行失败，则通过补偿机制补偿前面已经成功的参与者**。
    * Saga的优劣势：
        * 和XA或者TCC相比，它的优势包括：**一阶段直接提交本地事务；没有锁等待，性能较高；在事件驱动的模式下，短事务可以异步执行；补偿机制的实现比较简单**。
        * 缺点是：**Saga并不提供原子性和隔离性支持，隔离性的影响是比较大的，比如用户购买一个商品后系统赠送一张优惠券，如果用户已经把优惠券使用了，那么事务如果出现异常要回滚时就会出现问题**。
    * Saga的实现方式：
        * 事件/编排式：**把Saga的决策和执行顺序逻辑分布在Saga的每一个参与者中，它们通过交换事件的方式来进行沟通**。
        * 命令/协同式：**把Saga的决策和执行顺序逻辑集中在一个Saga控制类中，它以命令/回复的方式与每项服务进行通信，告诉他们应该执行哪些操作**。

* Seata：
    * file存储模式：Server端存储模式（store.mode）有file、db两种（后续将引入Raft实现Seata的高可用机制），file存储模式无须改动，直接启动即可。File存储模式为单机模式，全局事务会话信息持久化在本地文件${SEATA_HOME}\bin\sessionStore\root.data中，性能较高。
    * db存储模式为高可用模式，全局事务会话信息通过db共享，性能相对差一些。
    * Seata服务端配置中心说明：在${SEATA_HOME}\conf目录下有两个配置文件，分别是registry.conf和file.conf。
        * registry.conf：包含两项配置。
            * registry：表示配置Seata服务注册的地址。
            * config：配置用于配置Seata服务端的配置文件地址。
        * file.conf：存储的是Seata服务端的配置信息。
    * Seata AT模式的实现原理：
        * 第一阶段：业务数据和回滚日志记录在同一个本地事务中提交，释放本地锁和连接资源。
            * 原理：Seata会基于数据源代理对原执行的SQL进行解析。
        * 第二阶段：提交异步化，非常快速地完成。回滚通过第一阶段的回滚日志进行反向补充。
            * 原理：清理UNDO_LOG日志即可。
    * 关于事务的隔离性保证：
        * 事务隔离性保证：基于全局锁来实现的。
            * 写隔离：在第一阶段本地事务提交之前，确保拿到全局锁。如果拿不到全局锁，则不能提交本地事务。并且获取全局锁的尝试会有一个范围限制。如果超出范围将会放弃全局锁的获取，并且回滚事务，释放本地锁。





















