参考：https://docs.spring.io/spring-boot/docs/2.6.0-M2/reference/htmlsingle/#features

# 7.SpringBoot 2.6.0-M2 核心功能

本节深入介绍 Spring Boot 的详细信息。

## 7.1. 弹簧应用

本SpringApplication类提供了一个方便的方式来引导该从开始Spring应用程序main()的方法。在许多情况下，您可以委托给静态SpringApplication.run方法，如以下示例所示：

```java
@SpringBootApplication
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }

}
```

当您的应用程序启动时，您应该会看到类似于以下输出的内容：

```json5
 .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::   v2.6.0-M2

2021-02-03 10:33:25.224  INFO 17321 --- [           main] o.s.b.d.s.s.SpringAppplicationExample    : Starting SpringAppplicationExample using Java 1.8.0_232 on mycomputer with PID 17321 (/apps/myjar.jar started by pwebb)
2021-02-03 10:33:25.226  INFO 17900 --- [           main] o.s.b.d.s.s.SpringAppplicationExample    : No active profile set, falling back to default profiles: default
2021-02-03 10:33:26.046  INFO 17321 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2021-02-03 10:33:26.054  INFO 17900 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2021-02-03 10:33:26.055  INFO 17900 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.41]
2021-02-03 10:33:26.097  INFO 17900 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2021-02-03 10:33:26.097  INFO 17900 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 821 ms
2021-02-03 10:33:26.144  INFO 17900 --- [           main] s.tomcat.SampleTomcatApplication         : ServletContext initialized
2021-02-03 10:33:26.376  INFO 17900 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2021-02-03 10:33:26.384  INFO 17900 --- [           main] o.s.b.d.s.s.SpringAppplicationExample    : Started SampleTomcatApplication in 1.514 seconds (JVM running for 1.823)
```

默认情况下，INFO会显示日志消息，包括一些相关的启动详细信息，例如启动应用程序的用户。如果您需要除 INFO 之外的日志级别，您可以设置它，如日志级别中所述。
应用程序版本是使用主应用程序类包中的实现版本确定的。可以通过设置spring.main.log-startup-info为关闭启动信息记录false。这也将关闭应用程序的活动配置文件的日志记录。

```markdown
要在启动期间添加其他日志记录，您可以logStartupInfo(boolean)在SpringApplication.
```

### 7.1.1. 启动失败

如果您的应用程序无法启动，注册FailureAnalyzers有机会提供专门的错误消息和解决问题的具体措施。
例如，如果您在端口上启动 Web 应用程序8080并且该端口已在使用中，您应该会看到类似于以下消息的内容：

```json5
***************************
APPLICATION FAILED TO START
***************************

Description:

Embedded servlet container failed to start. Port 8080 was already in use.

Action:

Identify and stop the process that's listening on port 8080 or configure this application to listen on another port.
```

**提示**：

```markdown
Spring Boot 提供了许多FailureAnalyzer实现，您可以添加自己的. 添加方法参见：17.1.1. 创建您自己的故障分析器
```

如果没有故障分析器能够处理异常，您仍然可以显示完整的条件报告以更好地了解出了什么问题。要做到这一点，你需要使debug财产或启用DEBUG日志记录的org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener。

例如，如果您使用 运行您的应用程序java -jar，您可以debug按如下方式启用该属性：

```shell
$ java -jar myproject-0.0.1-SNAPSHOT.jar --debug
```

### 7.1.2. 延迟初始化

SpringApplication允许延迟初始化应用程序。启用延迟初始化后，bean 将在需要时创建，而不是在应用程序启动期间创建。因此，启用延迟初始化可以减少应用程序启动所需的时间。在 Web 应用程序中，启用延迟初始化将导致许多与 Web 相关的 bean 在收到 HTTP 请求之前不会被初始化。

延迟初始化的一个缺点是它会延迟发现应用程序的问题。如果错误配置的 bean 被延迟初始化，则启动期间将不再发生故障，并且只有在初始化 bean 时问题才会变得明显。还必须注意确保 JVM 有足够的内存来容纳所有应用程序的 bean，而不仅仅是那些在启动期间初始化的 bean。由于这些原因，默认情况下不启用延迟初始化，建议在启用延迟初始化之前对 JVM 的堆大小进行微调。

可以使用lazyInitialization方法 onSpringApplicationBuilder或setLazyInitialization方法 on以编程方式启用延迟初始化SpringApplication。或者，可以使用spring.main.lazy-initialization以下示例中所示的属性启用它：

```properties
spring.main.lazy-initialization=true
```

```markdown
如果您想在对应用程序的其余部分使用延迟初始化的同时禁用某些 bean 的延迟初始化，您可以使用@Lazy(false)注释将它们的延迟属性显式设置为 false 。
```

### 7.1.3. 自定义横幅

可以通过将banner.txt文件添加到类路径或将spring.banner.location属性设置为此类文件的位置来更改启动时打印的横幅。如果文件的编码不是 UTF-8，您可以设置spring.banner.charset. 除了一个文本文件，你还可以添加一个banner.gif，banner.jpg或banner.png图像文件到类路径或设置spring.banner.image.location属性。图像被转换为 ASCII 艺术表现形式并打印在任何文本横幅上方。

在您的banner.txt文件中，您可以使用以下任何占位符：

表 4. 横幅变量


| 多变的                                                                                 | 描述                                                                                                                                                                                                                                 |
| ---------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `${application.version}`                                                               | 应用程序的版本号，如`MANIFEST.MF`. 例如，`Implementation-Version: 1.0`打印为`1.0`.                                                                                                                                                  |
| `${application.formatted-version}`                                                     | 您的应用程序的版本号，如在 中声明`MANIFEST.MF`并设置为显示格式（用方括号括起来并以 为前缀`v`）。例如`(v1.0)`。                                                                                                                       |
| `${spring-boot.version}`                                                               | 您正在使用的 Spring Boot 版本。例如`2.6.0-M2`。                                                                                                                                                                                      |
| `${spring-boot.formatted-version}`                                                     | 您正在使用的 Spring Boot 版本，已格式化以显示（用括号括起来并以 为前缀`v`）。例如`(v2.6.0-M2)`。                                                                                                                                     |
| `${Ansi.NAME}`(或`${AnsiColor.NAME}`, `${AnsiBackground.NAME}`, `${AnsiStyle.NAME}`) | `NAME`ANSI 转义码的名称在哪里。详情请参阅[`AnsiPropertySource`](https://github.com/spring-projects/spring-boot/tree/v2.6.0-M2/spring-boot-project/spring-boot/src/main/java/org/springframework/boot/ansi/AnsiPropertySource.java)。 |
| `${application.title}`                                                                 | 您的应用程序的标题，如`MANIFEST.MF`. 例如`Implementation-Title: MyApp`打印为`MyApp`.                                                                                                                                                |

提示：
```markdown
SpringApplication.setBanner(…​)如果您想以编程方式生成横幅，则可以使用 该方法。使用org.springframework.boot.Banner接口并实现您自己的printBanner()方法。
```
您还可以使用该spring.main.banner-mode属性来确定横幅是否必须打印在System.out( console)、发送到配置的记录器 ( log) 或根本不生成 ( off)。

打印的横幅在以下名称下注册为单例 bean：springBootBanner.

笔记：
```markdown
在${application.version}和${application.formatted-version}，如果你使用Spring启动发射特性才可用。如果您正在运行解压缩的 jar 并以 .jar 开头，则不会解析这些值java -cp <classpath> <mainclass>。

这就是为什么我们建议您始终使用java org.springframework.boot.loader.JarLauncher. 这将application.*在构建类路径和启动您的应用程序之前初始化横幅变量。
```


### 7.1.4. 自定义 SpringApplication

如果SpringApplication默认设置不合您的口味，您可以改为创建本地实例并对其进行自定义。例如，要关闭横幅，您可以编写：
```java
@SpringBootApplication
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MyApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

}
```

笔记：
```markdown
传递给的构造函数参数SpringApplication是 Spring bean 的配置源。在大多数情况下，这些是对@Configuration类的引用，但它们也可以是直接引用@Component类。
```

也可以SpringApplication使用application.properties文件进行配置。有关详细信息，请参阅外部化配置。外化配置：https://docs.spring.io/spring-boot/docs/2.6.0-M2/reference/htmlsingle/#features.external-config。

有关配置选项的完整列表，请参阅SpringApplication Javadoc。SpringApplication完整列表参见：https://docs.spring.io/spring-boot/docs/2.6.0-M2/api/org/springframework/boot/SpringApplication.html


### 7.1.5. 流利的构建器 API

如果您需要构建ApplicationContext层次结构（具有父/子关系的多个上下文），或者如果您更喜欢使用“流畅”构建器 API，则可以使用SpringApplicationBuilder.

在SpringApplicationBuilder让要链接的多个方法调用，并且包括parent和child其让你创建层次结构，以显示在下面的示例性方法：
```java
new SpringApplicationBuilder()
        .sources(Parent.class)
        .child(Application.class)
        .bannerMode(Banner.Mode.OFF)
        .run(args);
```
笔记：
```json5
创建ApplicationContext层次结构时有一些限制。例如，Web 组件必须包含在子上下文中，并且Environment对于父上下文和子上下文都相同。有关完整的详细信息，请参阅SpringApplicationBuilderJavadoc。
https://docs.spring.io/spring-boot/docs/2.6.0-M2/api/org/springframework/boot/builder/SpringApplicationBuilder.html
```

### 7.1.6. 应用程序可用性

在平台上部署时，应用程序可以使用Kubernetes Probes【https://kubernetes.io/docs/tasks/configure-pod-container/configure-liveness-readiness-startup-probes/】等基础设施向平台提供有关其可用性的信息。Spring Boot 包括对常用“活跃”和“就绪”可用性状态的开箱即用支持。如果您使用 Spring Boot 的“执行器”支持，那么这些状态将作为健康端点组公开。

此外，您还可以通过将ApplicationAvailability接口注入您自己的 bean来获取可用性状态。

#### 活跃状态
应用程序的“活跃”状态表明它的内部状态是否允许它正常工作，或者如果它当前失败则自行恢复。损坏的“Liveness”状态意味着应用程序处于无法恢复的状态，基础设施应该重新启动应用程序。

笔记：
```json5
通常，“Liveness”状态不应基于外部检查，例如Health 检查。如果确实如此，则出现故障的外部系统（数据库、Web API、外部缓存）将触发整个平台的大规模重启和级联故障。
```

Spring Boot 应用程序的内部状态主要由 Spring 表示ApplicationContext。如果应用程序上下文已成功启动，Spring Boot 会假定应用程序处于有效状态。一旦上下文刷新，应用程序就被认为是活动的，请参阅Spring Boot 应用程序生命周期和相关的应用程序事件。
https://docs.spring.io/spring-boot/docs/2.6.0-M2/reference/htmlsingle/#features.spring-application.application-events-and-listeners

#### 准备状态
应用程序的“就绪”状态表明应用程序是否已准备好处理流量。失败的“就绪”状态告诉平台它现在不应将流量路由到应用程序。这通常在启动过程中发生的，而CommandLineRunner和ApplicationRunner组件正在处理，或在任何时候，如果应用程序决定，它是太忙了额外的流量。

一旦应用程序和命令行运行程序被调用，应用程序就被认为已准备就绪，请参阅Spring Boot 应用程序生命周期和相关的应用程序事件。

提示：
```json5
预期在启动期间运行的任务应该由CommandLineRunner和ApplicationRunner组件执行，而不是使用 Spring 组件生命周期回调，例如@PostConstruct.
```

#### 管理应用程序可用性状态
应用程序组件可以通过注入ApplicationAvailability接口并在其上调用方法，随时检索当前的可用性状态。更多的时候，应用程序会想要监听状态更新或更新应用程序的状态。

例如，我们可以将应用程序的“Readiness”状态导出到一个文件中，这样 Kubernetes 的“exec Probe”就可以查看这个文件：
```java
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyReadinessStateExporter {

    @EventListener
    public void onStateChange(AvailabilityChangeEvent<ReadinessState> event) {
        switch (event.getState()) {
        case ACCEPTING_TRAFFIC:
            // create file /tmp/healthy
            break;
        case REFUSING_TRAFFIC:
            // remove file /tmp/healthy
            break;
        }
    }

}


```

当应用程序中断且无法恢复时，我们还可以更新应用程序的状态：
```java
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class MyLocalCacheVerifier {

    private final ApplicationEventPublisher eventPublisher;

    public MyLocalCacheVerifier(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void checkLocalCache() {
        try {
            // ...
        }
        catch (CacheCompletelyBrokenException ex) {
            AvailabilityChangeEvent.publish(this.eventPublisher, ex, LivenessState.BROKEN);
        }
    }

}


```
Spring Boot通过 Actuator Health Endpoints 为“Liveness”和“Readiness”提供Kubernetes HTTP 探测。您可以在专用部分中获得有关在 Kubernetes 上部署 Spring Boot 应用程序的更多指导。


### 7.1.7. 应用程序事件和侦听器

除了通常的 Spring Framework 事件（例如 ）之外ContextRefreshedEvent【https://docs.spring.io/spring-framework/docs/5.3.9/javadoc-api/org/springframework/context/event/ContextRefreshedEvent.html】，aSpringApplication还会发送一些额外的应用程序事件。

笔记：
```json5
某些事件实际上ApplicationContext是在创建之前触发的，因此您不能将这些事件注册为@Bean. 您可以使用SpringApplication.addListeners(…​)方法或SpringApplicationBuilder.listeners(…​)方法注册它们。

如果您希望自动注册这些侦听器，无论应用程序的创建方式如何，都可以将META-INF/spring.factories文件添加到您的项目并使用org.springframework.context.ApplicationListener密钥引用您的侦听器，如以下示例所示：


org.springframework.context.ApplicationListener=com.example.project.MyListener
```

当您的应用程序运行时，应用程序事件按以下顺序发送：

- 1. An ApplicationStartingEvent在运行开始时发送，但在任何处理之前发送，除了侦听器和初始化程序的注册。

- 2. 一个ApplicationEnvironmentPreparedEvent当被发送Environment到中已知的上下文中使用，但是在创建上下文之前。

- 3. 一个ApplicationContextInitializedEvent在当发送ApplicationContext准备和ApplicationContextInitializers一直呼吁，但被加载任何bean定义之前。

- 4. ApplicationPreparedEvent在刷新开始之前但在加载 bean 定义之后发送一个。

- 5. ApplicationStartedEvent在刷新上下文之后但在调用任何应用程序和命令行运行程序之前发送An 。

- 6. 紧随AvailabilityChangeEvent其后发送一个，LivenessState.CORRECT以指示该应用程序被视为实时应用程序。

- 7. 在ApplicationReadyEvent任何之后被送到应用程序和命令行亚军已被调用。

- 8. AvailabilityChangeEvent在 with 之后立即发送一个，ReadinessState.ACCEPTING_TRAFFIC以指示应用程序已准备好为请求提供服务。

- 9. ApplicationFailedEvent如果启动时出现异常，则发送An 。


上面的列表只包括SpringApplicationEvent绑定到 a 的 s SpringApplication。除此之外，以下事件也在 之后ApplicationPreparedEvent和之前发布ApplicationStartedEvent：
- 准备好 WebServerInitializedEvent后发送A。和分别是 servlet 和反应式变体。WebServerServletWebServerInitializedEventReactiveWebServerInitializedEvent
- 刷新ContextRefreshedEvent时发送A。ApplicationContext

提示：
```json5
您通常不需要使用应用程序事件，但知道它们存在会很方便。在内部，Spring Boot 使用事件来处理各种任务。
```

笔记：
```json5
默认情况下，事件侦听器不应运行在同一线程中执行的潜在冗长任务。考虑改用应用程序和命令行运行程序。
https://docs.spring.io/spring-boot/docs/2.6.0-M2/reference/htmlsingle/#features.spring-application.command-line-runner
```

应用程序事件通过使用 Spring Framework 的事件发布机制发送。此机制的一部分确保发布到子上下文中的侦听器的事件也发布到任何祖先上下文中的侦听器。因此，如果您的应用程序使用SpringApplication实例层次结构，则侦听器可能会收到同一类型应用程序事件的多个实例。

为了让您的侦听器区分其上下文的事件和后代上下文的事件，它应该请求注入其应用程序上下文，然后将注入的上下文与事件的上下文进行比较。上下文可以通过实现注入，ApplicationContextAware或者如果侦听器是 bean，则使用@Autowired.


### 7.1.8. 网络环境

ASpringApplication尝试ApplicationContext代表您创建正确的类型。用于确定 a 的算法WebApplicationType如下：

- 如果存在 Spring MVC，AnnotationConfigServletWebServerApplicationContext则使用an
- 如果 Spring MVC 不存在而 Spring WebFlux 存在，AnnotationConfigReactiveWebServerApplicationContext则使用an
- 否则，AnnotationConfigApplicationContext使用

这意味着如果您WebClient在同一个应用程序中使用 Spring MVC 和来自 Spring WebFlux的 new ，则默认情况下将使用 Spring MVC。您可以通过调用轻松覆盖它setWebApplicationType(WebApplicationType)。

还可以ApplicationContext通过调用完全控制所使用的类型setApplicationContextClass(…)。

提示：
```json5
在 JUnit 测试中setWebApplicationType(WebApplicationType.NONE)使用时 通常需要调用SpringApplication。
```


### 7.1.9. 访问应用程序参数

如果您需要访问传递给 的应用程序参数SpringApplication.run(…​)，您可以注入一个org.springframework.boot.ApplicationArgumentsbean。所述ApplicationArguments接口提供访问两个原始String[]以及解析参数option和non-option参数，如下面的例子所示：
```java
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Component
public class MyBean {

    public MyBean(ApplicationArguments args) {
        boolean debug = args.containsOption("debug");
        List<String> files = args.getNonOptionArgs();
        if (debug) {
            System.out.println(files);
        }
        // if run with "--debug logfile.txt" prints ["logfile.txt"]
    }

}


```

提示：
```json5
Spring Boot 还CommandLinePropertySource向 Spring注册了一个Environment。这使您还可以使用@Value注释注入单个应用程序参数。
```


### 7.1.10. 使用 ApplicationRunner 或 CommandLineRunner

如果您需要在启动后运行某些特定代码SpringApplication，您可以实现ApplicationRunner或CommandLineRunner接口。这两个接口以相同的方式工作并提供一个run方法，在SpringApplication.run(…​)完成之前调用该方法。

笔记：
```json5
此合约非常适合应在应用程序启动之后但在开始接受流量之前运行的任务。
```

所述CommandLineRunner接口提供访问的应用程序的参数作为一个字符串数组，而ApplicationRunner用途ApplicationArguments接口前面讨论。以下示例显示了CommandLineRunner一个run方法：
```java
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        // Do something...
    }

}


```
如果定义了必须按特定顺序调用的多个CommandLineRunner或ApplicationRunnerbean，则可以另外实现org.springframework.core.Ordered接口或使用org.springframework.core.annotation.Order注解。

### 申请退出
每个都SpringApplication向 JVM 注册一个关闭挂钩，以确保ApplicationContext退出时正常关闭。可以使用所有标准的 Spring 生命周期回调（例如DisposableBean接口或@PreDestroy注解）。

此外，org.springframework.boot.ExitCodeGenerator如果bean希望在SpringApplication.exit()调用时返回特定的退出代码，它们可以实现该接口。然后可以将此退出代码传递给以将System.exit()其作为状态代码返回，如以下示例所示：
```java
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyApplication {

    @Bean
    public ExitCodeGenerator exitCodeGenerator() {
        return () -> 42;
    }

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(MyApplication.class, args)));
    }

}


```
此外，ExitCodeGenerator接口可以通过异常来实现。当遇到这样的异常时，Spring Boot 会返回实现getExitCode()方法提供的退出码。


### 7.1.12. 管理功能
可以通过指定spring.application.admin.enabled属性为应用程序启用与管理相关的功能。这SpringApplicationAdminMXBean【https://github.com/spring-projects/spring-boot/blob/v2.6.0-M2/spring-boot-project/spring-boot/src/main/java/org/springframework/boot/admin/SpringApplicationAdminMXBean.java】
在平台上暴露了MBeanServer。您可以使用此功能远程管理您的 Spring Boot 应用程序。此功能也可用于任何服务包装器实现

提示：
```json5
如果您想知道应用程序在哪个 HTTP 端口上运行，请获取键为 的属性local.server.port。
```


### 7.1.13. 应用程序启动跟踪
在应用程序启动期间，SpringApplication和ApplicationContext执行许多与应用程序生命周期、bean 生命周期甚至处理应用程序事件相关的任务。有了ApplicationStartup，Spring Framework允许您使用StartupStep对象跟踪应用程序启动顺序。收集这些数据是为了分析目的，或者只是为了更好地了解应用程序启动过程。

您可以ApplicationStartup在设置SpringApplication实例时选择一个实现。例如，要使用BufferingApplicationStartup，您可以编写：
```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

@SpringBootApplication
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MyApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.run(args);
    }

}


```
第一个可用的实现FlightRecorderApplicationStartup是由 Spring Framework 提供的。它将特定于 Spring 的启动事件添加到 Java Flight Recorder 会话中，用于分析应用程序并将其 Spring 上下文生命周期与 JVM 事件（例如分配、GC、类加载......）相关联。配置完成后，您可以通过在启用飞行记录器的情况下运行应用程序来记录数据：
```shell
$ java -XX:StartFlightRecording:filename=recording.jfr,duration=10s -jar demo.jar

```
Spring Boot 随附BufferingApplicationStartup变体；此实现旨在缓冲启动步骤并将它们排入外部指标系统。应用程序可以BufferingApplicationStartup在任何组件中请求类型的 bean 。

Spring Boot 还可以配置为公开一个startup端点，该端点以 JSON 文档的形式提供此信息。


## 7.2. 外化配置

Spring Boot 允许您将配置外部化，以便您可以在不同环境中使用相同的应用程序代码。您可以使用各种外部配置源，包括 Java 属性文件、YAML 文件、环境变量和命令行参数。

属性值可以通过直接注射到你的bean@Value注释，通过Spring的访问Environment抽象，或者被绑定到结构化对象【https://docs.spring.io/spring-boot/docs/2.6.0-M2/reference/htmlsingle/#features.external-config.typesafe-configuration-properties】
通过@ConfigurationProperties。


Spring Boot 使用一个非常特殊的PropertySource顺序，旨在允许合理地覆盖值。属性按以下顺序考虑（较低项目的值覆盖较早的项目）：

1. 默认属性（由 setting 指定SpringApplication.setDefaultProperties）。
2. @PropertySource你的@Configuration类的注释。请注意，Environment在刷新应用程序上下文之前，不会将此类属性源添加到。现在配置某些属性（例如在刷新开始之前读取的logging.*和）为时已晚spring.main.*。 
3. 配置数据（如application.properties文件）
4. 一RandomValuePropertySource，只有在拥有性能random.*。
5. 操作系统环境变量。
6. Java 系统属性 ( System.getProperties())。
7. JNDI 属性来自java:comp/env.
8. ServletContext 初始化参数。
9. ServletConfig 初始化参数。
10. 来自SPRING_APPLICATION_JSON（嵌入在环境变量或系统属性中的内联 JSON）的属性。
11. 命令行参数。
12. properties属性在您的测试中。可用于测试应用程序的特定部分@SpringBootTest的测试注释。
13. @TestPropertySource 测试中的注释。
14. $HOME/.config/spring-boot当 devtools 处于活动状态时，目录中的Devtools 全局设置属性。


配置数据文件按以下顺序考虑：
1. 打包在 jar 中的应用程序属性（application.properties和 YAML 变体）。
2. 打包在 jar 中的特定于配置文件的应用程序属性（application-{profile}.properties和 YAML 变体）。
3. 打包 jar 之外的应用程序属性（application.properties和 YAML 变体）。
4. 打包的 jar（application-{profile}.properties和 YAML 变体）之外的特定于配置文件的应用程序属性。

笔记：
```json5
建议您的整个应用程序坚持使用一种格式。如果您的配置文件在同一位置同时具有.properties和.yml格式，.properties则优先。
```

为了提供一个具体示例，假设您开发了一个@Component使用name属性的 ，如以下示例所示：
```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyBean {

    @Value("${name}")
    private String name;

    // ...

}


```
在您的应用程序类路径上（例如，在您的 jar 中），您可以拥有一个application.properties文件，该文件为name. 在新环境中运行时，application.properties可以在 jar 之外提供一个覆盖name. 对于一次性测试，您可以使用特定的命令行开关（例如，java -jar app.jar --name="Spring"）启动。

提示：
```json5
该env和configprops端点可以在确定为什么属性具有特定值有用。您可以使用这两个端点来诊断意外的属性值。有关详细信息，请参阅“生产就绪功能”【https://docs.spring.io/spring-boot/docs/2.6.0-M2/reference/htmlsingle/#actuator.endpoints】部分。
```

### 7.2.1. 访问命令行属性
默认情况下，SpringApplication将任何命令行选项参数（即以 开头的参数--，例如--server.port=9000）转换为 aproperty并将它们添加到 Spring Environment。如前所述，命令行属性始终优先于基于文件的属性源。

如果您不希望将命令行属性添加到 中Environment，可以使用 禁用它们SpringApplication.setAddCommandLineProperties(false)。


### 7.2.2. JSON 应用程序属性
环境变量和系统属性通常有限制，这意味着某些属性名称不能使用。为了解决这个问题，Spring Boot 允许您将一组属性编码为单个 JSON 结构。

当您的应用程序启动时，任何spring.application.json或SPRING_APPLICATION_JSON属性将被解析并添加到Environment.

例如，SPRING_APPLICATION_JSON可以在 UN*X shell 的命令行上提供该属性作为环境变量：
```shell
$ SPRING_APPLICATION_JSON='{"my":{"name":"test"}}' java -jar myapp.jar

```
在前面的示例中，您最终my.name=test在 Spring 中Environment。

同样的 JSON 也可以作为系统属性提供：
```shell
$ java -Dspring.application.json='{"my":{"name":"test"}}' -jar myapp.jar

```

或者您可以使用命令行参数提供 JSON：
```shell
$ java -jar myapp.jar --spring.application.json='{"my":{"name":"test"}}'

```

如果您要部署到经典应用程序服务器，您还可以使用名为java:comp/env/spring.application.json.

笔记：
```json5
尽管null来自 JSON 的值将添加到生成的属性源中，但PropertySourcesPropertyResolver会将null属性视为缺失值。这意味着 JSON 无法使用null值覆盖来自低阶属性源的属性。
```


### 7.2.3. 外部应用程序属性

春天开机就会自动查找和加载application.properties和application.yaml应用程序启动时的文件从以下位置：

1. 从类路径
   - a. 类路径根 
   - b. 类路径/config包
2. 从当前目录
   - a. 当前目录 
   - b. /config当前目录下的子目录
   - c. 的的直接子目录/config的子目录

该列表按优先级排序（较低项目的值覆盖较早的项目）。加载文件中的文档将添加PropertySources到 Spring Environment.

如果你不喜欢application作为配置文件名，你可以通过指定一个spring.config.name环境属性来切换到另一个文件名。例如，要查找myproject.properties和myproject.yaml文件，您可以按如下方式运行您的应用程序：
```shell
$ java -jar myproject.jar --spring.config.name=myproject

```
您还可以使用spring.config.location环境属性来引用显式位置。此属性接受一个或多个要检查的位置的逗号分隔列表。

以下示例显示如何指定两个不同的文件
```shell
$ java -jar myproject.jar --spring.config.location=\
    optional:classpath:/default.properties,\
    optional:classpath:/override.properties

```

提示：
```json5

optional:如果位置是可选的并且您不介意它们不存在， 请使用前缀。
```

警告：
```json5
spring.config.name, spring.config.location, 和spring.config.additional-location很早就用于确定必须加载哪些文件。它们必须定义为环境属性（通常是操作系统环境变量、系统属性或命令行参数）。
```

如果spring.config.location包含目录（而不是文件），它们应该以/. 在运行时，它们将附加spring.config.name在加载之前生成的名称。中指定的文件spring.config.location直接导入。

笔记：
```json5
目录和文件位置值也被扩展以检查特定于配置文件的文件。举例来说，如果你有一个spring.config.location的classpath:myconfig.properties，你也能找到相应的classpath:myconfig-<profile>.properties文件被加载。
```
在大多数情况下，spring.config.location您添加的每个项目都将引用单个文件或目录。位置按照定义的顺序进行处理，后面的位置可以覆盖前面位置的值。

如果您有一个复杂的位置设置，并且您使用特定于配置文件的配置文件，您可能需要提供进一步的提示，以便 Spring Boot 知道它们应该如何分组。位置组是所有被视为同一级别的位置的集合。例如，您可能希望对所有类路径位置进行分组，然后对所有外部位置进行分组。位置组中的项目应以 分隔;。
有关更多详细信息，请参阅“配置文件特定文件”【https://docs.spring.io/spring-boot/docs/2.6.0-M2/reference/htmlsingle/#features.external-config.files.profile-specific】部分中的示例。

使用配置的位置spring.config.location替换默认位置。例如，如果spring.config.location配置了 value optional:classpath:/custom-config/,optional:file:./custom-config/，则考虑的完整位置集是：
1. optional:classpath:custom-config/
2. optional:file:./custom-config/

如果您更喜欢添加其他位置而不是替换它们，您可以使用spring.config.additional-location. 从其他位置加载的属性可以覆盖默认位置中的属性。例如，如果spring.config.additional-location配置了 value optional:classpath:/custom-config/,optional:file:./custom-config/，则考虑的完整位置集是：
1. optional:classpath:/;optional:classpath:/config/
2. optional:file:./;optional:file:./config/;optional:file:./config/*/
3. optional:classpath:custom-config/
4. optional:file:./custom-config/

笔记：
```json5
如果您使用环境变量而不是系统属性，大多数操作系统不允许使用句点分隔的键名，但您可以使用下划线代替（例如，SPRING_CONFIG_NAME代替spring.config.name）。有关详细信息，请参阅从环境变量绑定。
```

笔记：
```json5
如果您的应用程序在 servlet 容器或应用程序服务器中运行，则可以使用 JNDI 属性（in java:comp/env）或 servlet 上下文初始化参数来代替或同时使用环境变量或系统属性。
```

#### 可选位置
默认情况下，当指定的配置数据位置不存在时，Spring Boot 将抛出 aConfigDataLocationNotFoundException并且您的应用程序将不会启动。

如果要指定位置，但不介意它是否始终存在，则可以使用optional:前缀。您可以将此前缀与spring.config.location和spring.config.additional-location属性以及spring.config.import声明一起使用。

例如，spring.config.import值optional:file:./myconfig.properties允许您的应用程序启动，即使myconfig.properties文件丢失。

如果您想忽略所有ConfigDataLocationNotFoundExceptions并始终继续启动您的应用程序，您可以使用该spring.config.on-not-found属性。将值设置为ignoreusingSpringApplication.setDefaultProperties(…​)或 with 系统/环境变量。


#### 通配符位置
如果配置文件位置包含*最后一个路径段的字符，则将其视为通配符位置。加载配置时会扩展通配符，以便还检查直接子目录。当有多个配置属性源时，通配符位置在 Kubernetes 等环境中特别有用。

例如，如果您有一些 Redis 配置和一些 MySQL 配置，您可能希望将这两个配置分开，同时要求它们都存在于一个application.properties文件中。这可能会导致在application.properties不同位置安装两个单独的文件，例如/config/redis/application.properties和/config/mysql/application.properties。在这种情况下，通配符位置为config/*/, 将导致两个文件都被处理。

默认情况下，Spring Boot 包含config/*/在默认搜索位置。这意味着/config将搜索 jar 之外目录的所有子目录。

您可以自己将通配符位置与spring.config.location和spring.config.additional-location属性一起使用。

笔记：
```json5
通配符位置必须仅包含一个*并以*/目录结尾或*/<filename>文件搜索位置结尾。带有通配符的位置根据文件名的绝对路径按字母顺序排序。
```

提示：
```json5
通配符位置仅适用于外部目录。不能在classpath:位置中使用通配符。
```

#### 配置文件特定文件
除了application属性文件，Spring Boot 还将尝试使用命名约定加载特定于配置文件的文件application-{profile}。例如，如果你的应用程序启动了一个名为轮廓prod，并使用YAML文件，然后双方application.yml并application-prod.yml会予以考虑。

特定于配置文件的属性从与标准相同的位置加载application.properties，特定于配置文件的文件始终覆盖非特定文件。如果指定了多个配置文件，则采用最后获胜的策略。例如，如果配置文件prod,live由spring.profiles.active属性指定，则 中的值application-prod.properties可以被 中的值覆盖application-live.properties。

笔记：
```json5
最后获胜策略适用于位置组级别。A spring.config.locationofclasspath:/cfg/,classpath:/ext/将不会具有与 相同的覆盖规则classpath:/cfg/;classpath:/ext/。

例如，继续prod,live上面的例子，我们可能有以下文件：

/配置文件
  应用程序-live.properties
/分机
  应用程序-live.properties
  application-prod.properties
当我们拥有spring.config.location的classpath:/cfg/,classpath:/ext/，我们处理所有/cfg的所有之前的文件/ext的文件：
1. /cfg/application-live.properties
2. /ext/application-prod.properties
3. /ext/application-live.properties

当我们有classpath:/cfg/;classpath:/ext/替代（用;分隔符），我们处理/cfg并/ext在同一水平：
1. /ext/application-prod.properties
2. /cfg/application-live.properties
3. /ext/application-live.properties
```
在Environment具有一组默认的配置文件（默认[default]）如果没有活动的简档设置中使用。换句话说，如果没有明确激活配置文件，则application-default考虑来自的属性。

笔记：
```json5
属性文件只加载一次。如果您已经直接导入了配置文件特定的属性文件，则不会再次导入。
```

#### 导入附加数据
应用程序属性可以使用该spring.config.import属性从其他位置导入更多配置数据。进口在被发现时被处理，并被视为附加文件，紧接在声明进口的文件之下。

例如，您的类路径application.properties文件中可能包含以下内容：
```json5
spring.application.name=myapp
spring.config.import=optional:file:./dev.properties

```

这将触发dev.properties在当前目录中导入文件（如果存在这样的文件）。导入的值dev.properties将优先于触发导入的文件。在上面的示例中，dev.properties可以重新定义spring.application.name为不同的值。

一个导入无论声明多少次都只会被导入一次。导入在 properties/yaml 文件中的单个文档中定义的顺序无关紧要。例如，下面的两个示例产生相同的结果：

```json5
spring.config.import=my.properties
my.property=value

```

```json5
my.property=value
spring.config.import=my.properties
```

笔记：
```json5

适当时，还考虑导入特定于配置文件的变体。上面的示例将导入两者my.properties以及任何my-<profile>.properties变体。
```

提示：
```json5
Spring Boot 包含可插入的 API，允许支持各种不同的位置地址。默认情况下，您可以导入 Java 属性、YAML 和“配置树”。

第三方 jar 可以提供对其他技术的支持（不要求文件是本地的）。例如，您可以想象配置数据来自外部存储，例如 Consul、Apache ZooKeeper 或 Netflix Archaius。

如果要支持自己的位置，请参阅包中的ConfigDataLocationResolver和ConfigDataLoader类org.springframework.boot.context.config。
```

#### 导入无扩展名文件
某些云平台无法为卷挂载文件添加文件扩展名。要导入这些无扩展名的文件，您需要给 Spring Boot 一个提示，以便它知道如何加载它们。您可以通过将扩展提示放在方括号中来做到这一点。

例如，假设您有一个/etc/config/myconfig要作为 yaml 导入的文件。您可以application.properties使用以下方法将其导入：
```json5
spring.config.import=file:/etc/config/myconfig[.yaml]

```

#### 使用配置树
在云平台（例如 Kubernetes）上运行应用程序时，您通常需要读取平台提供的配置值。出于此类目的使用环境变量的情况并不少见，但这可能有缺点，尤其是在值应该保密的情况下。

作为环境变量的替代方案，许多云平台现在允许您将配置映射到已安装的数据卷中。例如，Kubernetes 可以卷挂载ConfigMaps和Secrets.

有两种常见的卷安装模式可以使用：
1. 单个文件包含一组完整的属性（通常编写为 YAML）。
2. 多个文件被写入目录树，文件名成为“键”，内容成为“值”。

对于第一种情况，你可以直接导入使用YAML或属性文件中spring.config.import所描述的以上。对于第二种情况，您需要使用configtree:前缀，以便 Spring Boot 知道它需要将所有文件公开为属性。

例如，让我们假设 Kubernetes 已经挂载了以下卷：
```json5
etc/
  config/
    myapp/
      username
      password
```
该username文件的内容将是一个配置值，而 的内容password将是一个秘密。

要导入这些属性，您可以将以下内容添加到您的application.properties或application.yaml文件中：
```json5
spring.config.import=optional:configtree:/etc/config/

```
然后，您可以以通常的方式访问或注入myapp.username和myapp.password属性Environment。

提示：
```json5
根据预期的内容， 配置树值可以绑定到字符串String和byte[]类型。
```
如果您有多个配置树要从同一个父文件夹导入，您可以使用通配符快捷方式。configtree:以 结尾的任何位置/*/都会将所有直接子项导入为配置树。

例如，给定以下体积：
```json5
etc/
  config/
    dbconfig/
      db/
        username
        password
    mqconfig/
      mq/
        username
        password
```
您可以configtree:/etc/config/*/用作导入位置：
```json5
spring.config.import=optional:configtree:/etc/config/*/

```
这将增加db.username，db.password，mq.username和mq.password属性。

笔记：
```json5
使用通配符加载的目录按字母顺序排序。如果您需要不同的顺序，那么您应该将每个位置作为单独的导入列出
```
配置树也可用于 Docker 机密。当 Docker swarm 服务被授予访问机密的权限时，机密会被挂载到容器中。例如，如果db.password在 location 挂载了一个名为的秘密，则/run/secrets/可以db.password使用以下命令使 Spring 环境可用：
```json5
spring.config.import=optional:configtree:/run/secrets/

```

#### 属性占位符
application.properties和application.yml中的值在Environment使用时会通过现有值进行过滤，因此您可以参考以前定义的值（例如，来自系统属性）。标准${name}属性占位符语法可用于值内的任何地方。

例如，以下文件将设置app.description为“MyApp is a Spring Boot application”：
```json5
app.name=MyApp
app.description=${app.name} is a Spring Boot application

```
提示：
```json5
您还可以使用此技术创建现有 Spring Boot 属性的“短”变体。有关详细信息，请参阅使用“短”命令行参数操作方法。
```

#### 处理多文档文件
Spring Boot 允许您将单个物理文件拆分为多个独立添加的逻辑文档。文档按顺序处理，从上到下。后面的文档可以覆盖在前面的文档中定义的属性。

对于application.yml文件，使用标准的 YAML 多文档语法。三个连续的连字符代表一个文档的结束和下一个文档的开始。

例如，以下文件有两个逻辑文档：
```json5
spring.application.name: MyApp
---
spring.config.activate.on-cloud-platform: kubernetes
spring.application.name: MyCloudApp

```
对于application.properties文件，使用特殊#---注释来标记文档拆分：
```json5
spring.application.name=MyApp
#---
spring.config.activate.on-cloud-platform=kubernetes
spring.application.name=MyCloudApp

```
笔记：
```json5
属性文件分隔符不能有任何前导空格，并且必须正好有三个连字符。分隔符前后的行不能是注释。
```

提示：
```json5
多文档属性文件通常与激活属性（如spring.config.activate.on-profile. 有关详细信息，请参阅下一节。
```

警告：
```json5
不能使用@PropertySource或@TestPropertySource注释加载多文档属性文件。
```

#### 激活属性
有时仅在满足某些条件时激活给定的属性 get 很有用。例如，您可能拥有仅在特定配置文件处于活动状态时才相关的属性。

您可以使用 有条件地激活属性文档spring.config.activate.*。

以下激活属性可用：

_表 5. 激活属性_

| col1 | col2 |
| ------ | ------ |
| on-profile     |   必须匹配才能使文档处于活动状态的配置文件表达式。   | 
|  on-cloud-platform    |    在CloudPlatform必须对文件进行检测活跃。  | 

例如，以下指定第二个文档仅在 Kubernetes 上运行时才处于活动状态，并且仅当“prod”或“staging”配置文件处于活动状态时：

```json5
myprop=always-set
#---
spring.config.activate.on-cloud-platform=kubernetes
spring.config.activate.on-profile=prod | staging
myotherprop=sometimes-set

```


### 7.2.4. 加密属性
Spring Boot 不提供任何对加密属性值的内置支持，但是，它提供了修改 Spring 中包含的值所需的挂钩点Environment。该EnvironmentPostProcessor界面允许您Environment在应用程序启动之前进行操作。有关详细信息，请参阅在开始之前自定义环境或 ApplicationContext。

如果您正在寻找一种安全的方式来存储凭证和密码，Spring Cloud Vault项目提供了在HashiCorp Vault 中存储外部化配置的支持。


### 7.2.5. 使用 YAML
YAML是 JSON 的超集，因此是一种用于指定分层配置数据的便捷格式。该SpringApplication级自动支持YAML来替代，只要你有属性SnakeYAML在classpath库。

笔记：
```json5
如果您使用“Starters”，SnakeYAML 将自动由spring-boot-starter.
```

#### 将 YAML 映射到属性
YAML 文档需要从其分层格式转换为可与 Spring 一起使用的平面结构Environment。例如，考虑以下 YAML 文档：
```json5
environments:
  dev:
    url: https://dev.example.com
    name: Developer Setup
  prod:
    url: https://another.example.com
    name: My Cool App

```
为了从 访问这些属性Environment，它们将被展平如下：
```json5
environments.dev.url=https://dev.example.com
environments.dev.name=Developer Setup
environments.prod.url=https://another.example.com
environments.prod.name=My Cool App

```
同样，YAML 列表也需要扁平化。它们表示为带有[index]解引用器的属性键。例如，考虑以下 YAML：
```json5
my:
 servers:
 - dev.example.com
 - another.example.com

```
前面的示例将转换为以下属性：
```json5
my.servers[0]=dev.example.com
my.servers[1]=another.example.com

```

提示：
```json5
使用该[index]符号的属性可以绑定到 JavaList或Set使用 Spring BootBinder类的对象。有关更多详细信息，请参阅下面的“类型安全配置属性”部分。
```

警告：
```json5

无法使用@PropertySource或@TestPropertySource注释加载 YAML 文件。因此，如果您需要以这种方式加载值，则需要使用属性文件。
```

#### 直接加载 YAML
Spring Framework 提供了两个方便的类，可用于加载 YAML 文档。该YamlPropertiesFactoryBean负载YAML作为Properties和YamlMapFactoryBean负载YAML作为Map。

YamlPropertySourceLoader如果要将 YAML 作为 Spring 加载，也可以使用该类PropertySource。


###  配置随机值
这RandomValuePropertySource对于注入随机值（例如，注入机密或测试用例）很有用。它可以生成整数、长整数、uuid 或字符串，如以下示例所示：
```json5
my.secret=${random.value}
my.number=${random.int}
my.bignumber=${random.long}
my.uuid=${random.uuid}
my.number-less-than-ten=${random.int(10)}
my.number-in-range=${random.int[1024,65536]}

```
该random.int*语法是OPEN value (,max) CLOSE其中的OPEN,CLOSE任何字符和value,max是整数。如果max提供，则value是最小值和max最大值（不包括）。


### 7.2.7. 配置系统环境属性
Spring Boot 支持为环境属性设置前缀。如果系统环境由具有不同配置要求的多个 Spring Boot 应用程序共享，这将非常有用。系统环境属性的前缀可以直接设置在SpringApplication.

例如，如果将前缀设置为input，则诸如在系统环境中的属性remote.timeout也将被解析input.remote.timeout。



### 7.2.8. 类型安全的配置属性
使用@Value("${property}")注解注入配置属性有时会很麻烦，尤其是当您使用多个属性或您的数据本质上是分层的时。Spring Boot 提供了一种处理属性的替代方法，让强类型 bean 管理和验证应用程序的配置。

提示：
```json5
另请参阅和类型安全配置属性之间@Value的差异。
https://docs.spring.io/spring-boot/docs/2.6.0-M2/reference/htmlsingle/#features.external-config.typesafe-configuration-properties.vs-value-annotation
```















| col1 | col2 | col3 |
| ------ | ------ | ------ |
|      |      |      |
|      |      |      |
