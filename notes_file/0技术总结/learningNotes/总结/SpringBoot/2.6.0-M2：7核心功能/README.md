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

#### JavaBean 属性绑定
可以绑定声明标准 JavaBean 属性的 bean，如以下示例所示：
```java
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("my.service")
public class MyProperties {

    private boolean enabled;

    private InetAddress remoteAddress;

    private final Security security = new Security();

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public InetAddress getRemoteAddress() {
        return this.remoteAddress;
    }

    public void setRemoteAddress(InetAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public Security getSecurity() {
        return this.security;
    }

    public static class Security {

        private String username;

        private String password;

        private List<String> roles = new ArrayList<>(Collections.singleton("USER"));

        public String getUsername() {
            return this.username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return this.password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public List<String> getRoles() {
            return this.roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }

    }

}


```

前面的 POJO 定义了以下属性：
- my.service.enabled，false默认值为。
- my.service.remote-address, 具有可以从String.
- my.service.security.username，带有一个嵌套的“安全”对象，其名称由属性的名称决定。特别是，返回类型根本没有在那里使用，本来可以使用SecurityProperties.
- my.service.security.password.
- my.service.security.roles, 的集合String默认为USER.

笔记：
```json5
映射到@ConfigurationPropertiesSpring Boot 中可用类的属性，通过属性文件、YAML 文件、环境变量等配置，是公共 API，但类本身的访问器（getter/setter）并不意味着直接使用。
```

笔记
```json5
这种安排依赖于默认的空构造函数，并且 getter 和 setter 通常是强制性的，因为绑定是通过标准的 Java Beans 属性描述符进行的，就像在 Spring MVC 中一样。在以下情况下可以省略 setter：
- 映射，只要它们被初始化，就需要一个 getter，但不一定是一个 setter，因为它们可以被绑定器改变。
- 可以通过索引（通常使用 YAML）或使用单个逗号分隔值（属性）来访问集合和数组。在后一种情况下，setter 是强制性的。我们建议始终为此类类型添加 setter。如果您初始化一个集合，请确保它不是不可变的（如前面的示例所示）。
- 如果初始化嵌套的 POJO 属性（如Security前面示例中的字段），则不需要设置器。如果您希望活页夹使用其默认构造函数动态创建实例，则需要一个 setter。

有些人使用 Project Lombok 自动添加 getter 和 setter。确保 Lombok 不会为此类类型生成任何特定的构造函数，因为容器会自动使用它来实例化对象。

最后，只考虑标准 Java Bean 属性，不支持静态属性绑定。
```

#### 构造函数绑定
上一节中的示例可以以不可变的方式重写，如下例所示：
```java
import java.net.InetAddress;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConstructorBinding
@ConfigurationProperties("my.service")
public class MyProperties {

    private final boolean enabled;

    private final InetAddress remoteAddress;

    private final Security security;

    public MyProperties(boolean enabled, InetAddress remoteAddress, Security security) {
        this.enabled = enabled;
        this.remoteAddress = remoteAddress;
        this.security = security;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public InetAddress getRemoteAddress() {
        return this.remoteAddress;
    }

    public Security getSecurity() {
        return this.security;
    }

    public static class Security {

        private final String username;

        private final String password;

        private final List<String> roles;

        public Security(String username, String password, @DefaultValue("USER") List<String> roles) {
            this.username = username;
            this.password = password;
            this.roles = roles;
        }

        public String getUsername() {
            return this.username;
        }

        public String getPassword() {
            return this.password;
        }

        public List<String> getRoles() {
            return this.roles;
        }

    }

}


```
在此设置中，@ConstructorBinding注释用于指示应使用构造函数绑定。这意味着绑定器将期望找到一个带有您希望绑定的参数的构造函数。如果您使用的是 Java 16 或更高版本，则构造函数绑定可以与记录一起使用。在这种情况下，除非您的记录有多个构造函数，否则不需要使用@ConstructorBinding.

类的嵌套成员@ConstructorBinding（例如Security在上面的示例中）也将通过它们的构造函数绑定。

可以使用指定默认值，@DefaultValue并且将应用相同的转换服务将String值强制转换为缺失属性的目标类型。默认情况下，如果没有绑定到 的属性Security，则MyProperties实例将包含 的null值security。如果您希望Security即使没有绑定任何属性也返回一个非空实例，您可以使用空@DefaultValue注释来执行此操作：
```java
public MyProperties(boolean enabled, InetAddress remoteAddress, @DefaultValue Security security) {
    this.enabled = enabled;
    this.remoteAddress = remoteAddress;
    this.security = security;
}


```

笔记：
```json5
要使用构造函数绑定，必须使用@EnableConfigurationProperties或配置属性扫描启用类。您不能对由常规 Spring 机制创建的 bean 使用构造函数绑定（例如@Componentbean、通过@Bean方法创建的bean 或使用 加载的 bean @Import）
```

提示：
```json5
如果你的类有多个构造函数，你也可以@ConstructorBinding直接在应该绑定的构造函数上使用。
```

笔记：
```json5
不推荐 使用java.util.Optional with ，@ConfigurationProperties因为它主要用作返回类型。因此，它不太适合配置属性注入。为了与其他类型的属性保持一致，如果你确实声明了一个Optional属性并且它没有值，null而不是一个空的Optional将被绑定。
```

#### 启用 @ConfigurationProperties 注释类型
Spring Boot 提供基础结构来绑定@ConfigurationProperties类型并将它们注册为 bean。您可以在逐个类的基础上启用配置属性，也可以启用以与组件扫描类似的方式工作的配置属性扫描。

有时，带有注释的类@ConfigurationProperties可能不适合扫描，例如，如果您正在开发自己的自动配置或者您想有条件地启用它们。在这些情况下，请使用@EnableConfigurationProperties注释指定要处理的类型列表。这可以在任何@Configuration类上完成，如以下示例所示：
```java
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SomeProperties.class)
public class MyConfiguration {

}


```

要使用配置属性扫描，请将@ConfigurationPropertiesScan注释添加到您的应用程序。通常，它被添加到带有注释的主应用程序类中，@SpringBootApplication但它可以添加到任何@Configuration类中。默认情况下，将从声明注释的类的包中进行扫描。如果要定义要扫描的特定包，可以按照以下示例进行操作：
```java
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan({ "com.example.app", "com.example.another" })
public class MyApplication {

}


```

笔记：
```json5
当@ConfigurationPropertiesbean使用配置属性扫描或通过注册@EnableConfigurationProperties，豆具有常规名称：<prefix>-<fqn>，其中，<prefix>是在指定的环境键前缀@ConfigurationProperties注释和<fqn>是bean的全限定名。如果注释不提供任何前缀，则仅使用 bean 的完全限定名称。

上面示例中的 bean 名称是com.example.app-com.example.app.SomeProperties.
```

我们建议@ConfigurationProperties只处理环境，特别是不要从上下文中注入其他 bean。对于极端情况，可以使用 setter 注入或*Aware框架提供的任何接口（例如，EnvironmentAware如果您需要访问Environment）。如果您仍然想使用构造函数注入其他 bean，则必须@Component使用基于 JavaBean 的属性绑定来注释和使用配置属性 bean 。


#### 使用 @ConfigurationProperties 注释类型
这种配置风格特别适用于SpringApplication外部 YAML 配置，如以下示例所示：
```yaml
my:
    service:
        remote-address: 192.168.1.1
        security:
            username: admin
            roles:
              - USER
              - ADMIN

```
要使用@ConfigurationPropertiesbean，您可以以与任何其他 bean 相同的方式注入它们，如以下示例所示：
```java
import org.springframework.stereotype.Service;

@Service
public class MyService {

    private final SomeProperties properties;

    public MyService(SomeProperties properties) {
        this.properties = properties;
    }

    public void openConnection() {
        Server server = new Server(this.properties.getRemoteAddress());
        server.start();
        // ...
    }

    // ...

}


```

提示：
```json5
Using@ConfigurationProperties还可以让您生成元数据文件，IDE 可以使用这些文件为您自己的密钥提供自动完成功能。详情见附录。
```


#### 第三方配置
除了@ConfigurationProperties用于注释类之外，您还可以在公共@Bean方法上使用它。当您想要将属性绑定到您无法控制的第三方组件时，这样做会特别有用。

要从Environment属性配置 bean ，请添加@ConfigurationProperties到其 bean 注册中，如以下示例所示：
```java
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ThirdPartyConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "another")
    public AnotherComponent anotherComponent() {
        return new AnotherComponent();
    }

}


```

使用another前缀定义的任何 JavaBean 属性都AnotherComponent以类似于前面SomeProperties示例的方式映射到该bean 。


#### 松弛绑定
Spring Boot 使用一些宽松的规则将Environment属性绑定到@ConfigurationPropertiesbean，因此Environment属性名称和 bean 属性名称之间不需要完全匹配。这很有用的常见示例包括以破折号分隔的环境属性（例如，context-path绑定到contextPath）和大写的环境属性（例如，PORT绑定到port）。

例如，考虑以下@ConfigurationProperties类：
```java
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "my.main-project.person")
public class MyPersonProperties {

    private String firstName;

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}


```
使用前面的代码，可以使用以下属性名称：

表 6. 宽松绑定

| 财产 | 笔记 |
| ------ | ------ |
| my.main-project.person.first-name     |烤肉盒，推荐用于.properties和.yml文件。      |
|my.main-project.person.firstName      |标准的驼峰式语法。      |
| my.main-project.person.first_name     |下划线表示法，这是在.properties和.yml文件中使用的另一种格式。      |
| MY_MAINPROJECT_PERSON_FIRSTNAME     | 大写格式，在使用系统环境变量时推荐使用。     |

笔记：
```json5
prefix注释 的值必须采用 kebab 大小写（小写并由 分隔-，例如my.main-project.person）。
```

表 7. 每个属性源的宽松绑定规则

| 物业来源 | 简单的 | 列表 |
| ------ | ------ | ------ |
|  属性文件    |    Camel case、kebab case 或下划线符号  |   使用[ ]或逗号分隔值的标准列表语法   |
|  YAML 文件    |   Camel case、kebab case 或下划线符号   | 标准 YAML 列表语法或逗号分隔值     |
|   环境变量   |  下划线作为分隔符的大写格式（请参阅从环境变量绑定）。    |   由下划线包围的数值（请参阅从环境变量绑定）   |
|  系统属性    |  Camel case、kebab case 或下划线符号    |   使用[ ]或逗号分隔值的标准列表语法   |
		

提示：
```json5
我们建议，在可能的情况下，属性以小写的 kebab 格式存储，例如my.person.first-name=Rod
```

##### 绑定地图
绑定到Map属性时，您可能需要使用特殊的括号表示法，以便key保留原始值。如果键没有被 包围[]，则任何非字母数字字符-或被.删除。

例如，考虑将以下属性绑定到 a Map<String,String>：
```properties
my.map.[/key1]=value1
my.map.[/key2]=value2
my.map./key3=value3

```

笔记：
```json5
对于 YAML 文件，括号需要用引号括起来，以便正确解析键。
```

上面的属性将绑定到Mapwith /key1, /key2andkey3作为地图中的键。斜线已被删除，key3因为它没有被方括号包围。

如果您key包含 a.并且您绑定到非标量值，您可能偶尔也需要使用括号表示法。例如，绑定a.b=c到Map<String, Object>将返回带有条目的 Map{"a"={"b"="c"}}而[a.b]=c将返回带有条目的 Map {"a.b"="c"}。

##### 从环境变量绑定
大多数操作系统对可用于环境变量的名称施加了严格的规则。例如，Linux shell 变量只能包含字母（atoz或Ato Z）、数字（0to 9）或下划线字符（_）。按照惯例，Unix shell 变量的名称也为大写。

Spring Boot 宽松的绑定规则是为了兼容这些命名限制而设计的。

要将规范形式的属性名称转换为环境变量名称，您可以遵循以下规则：

- 将点 ( .)替换为下划线 ( _)。
- 删除所有破折号 ( -)。
- 转换为大写。

例如，配置属性spring.main.log-startup-info将是一个名为 的环境变量SPRING_MAIN_LOGSTARTUPINFO。

绑定到对象列表时也可以使用环境变量。要绑定到 a List，元素编号应在变量名称中用下划线括起来。

例如，配置属性my.service[0].other将使用名为 的环境变量MY_SERVICE_0_OTHER。


#### 合并复杂类型
当列表在多个地方配置时，覆盖通过替换整个列表来工作。

例如，假设一个MyPojo对象具有默认的name和description属性null。以下示例公开了MyPojo来自的对象列表MyProperties：
```java
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("my")
public class MyProperties {

    private final List<MyPojo> list = new ArrayList<>();

    public List<MyPojo> getList() {
        return this.list;
    }

}


```
考虑以下配置：
```properties
my.list[0].name=my name
my.list[0].description=my description
#---
spring.config.activate.on-profile=dev
my.list[0].name=my another name

```
如果dev配置文件未激活，则MyProperties.list包含一个MyPojo条目，如先前定义的那样。dev但是，如果配置文件已启用，则该配置文件list 仍仅包含一个条目（名称为my another name和描述为null）。此配置不会将第二个MyPojo实例添加到列表中，也不会合并项目。

当List在多个配置文件中指定了a 时，使用具有最高优先级（并且仅那个）的那个。考虑以下示例：
```properties
my.list[0].name=my name
my.list[0].description=my description
my.list[1].name=another name
my.list[1].description=another description
#---
spring.config.activate.on-profile=dev
my.list[0].name=my another name

```
在前面的示例中，如果dev配置文件处于活动状态，则MyProperties.list包含一个 MyPojo条目（名称为my another name，描述为null）。对于 YAML，逗号分隔列表和 YAML 列表均可用于完全覆盖列表的内容。

对于Map属性，您可以绑定来自多个来源的属性值。但是，对于多个来源中的相同属性，使用优先级最高的一个。以下示例公开了Map<String, MyPojo>from MyProperties：
```java
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("my")
public class MyProperties {

    private final Map<String, MyPojo> map = new LinkedHashMap<>();

    public Map<String, MyPojo> getMap() {
        return this.map;
    }

}


```
考虑以下配置：
```properties
my.map.key1.name=my name 1
my.map.key1.description=my description 1
#---
spring.config.activate.on-profile=dev
my.map.key1.name=dev name 1
my.map.key2.name=dev name 2
my.map.key2.description=dev description 2

```
如果dev配置文件未激活，则MyProperties.map包含一个带键的条目key1（名称为my name 1，描述为my description 1）。dev但是，如果配置文件已启用，则map包含两个带有键的条目key1（名称为dev name 1，描述为my description 1）和key2（名称为dev name 2，描述为dev description 2）。

笔记：
```json5
前面的合并规则适用于来自所有属性源的属性，而不仅仅是文件。
```

#### 属性转换
Spring Boot 在绑定到@ConfigurationPropertiesbean时尝试将外部应用程序属性强制为正确的类型。如果您需要自定义类型转换，您可以提供一个ConversionServicebean（带有一个名为 的 bean conversionService）或自定义属性编辑器（通过一个CustomEditorConfigurerbean）或自定义Converters（带有注释为 的 bean 定义@ConfigurationPropertiesBinding）。

笔记：
```json5
由于在应用程序生命周期的早期请求此 bean，因此请确保限制您ConversionService正在使用的依赖项。通常，您需要的任何依赖项在创建时可能未完全初始化。您可能需要重命名您的自定义ConversionService如果不需要配置键强制它只有依靠合格的自定义转换器@ConfigurationPropertiesBinding。
```

#### 转换持续时间
Spring Boot 专门支持表达持续时间。如果公开java.time.Duration属性，则应用程序属性中的以下格式可用：

- 常规long表示（使用毫秒作为默认单位，除非@DurationUnit指定了 a）
- 标准的ISO-8601格式使用java.time.Duration
- 一种更易读的格式，其中值和单位是耦合的（例如，10s表示 10 秒）

考虑以下示例：
```java
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

@ConfigurationProperties("my")
public class MyProperties {

    @DurationUnit(ChronoUnit.SECONDS)
    private Duration sessionTimeout = Duration.ofSeconds(30);

    private Duration readTimeout = Duration.ofMillis(1000);

    public Duration getSessionTimeout() {
        return this.sessionTimeout;
    }

    public void setSessionTimeout(Duration sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public Duration getReadTimeout() {
        return this.readTimeout;
    }

    public void setReadTimeout(Duration readTimeout) {
        this.readTimeout = readTimeout;
    }

}


```
要将会话超时指定为 30 秒30，PT30S和30s都是等效的。500ms 的读取超时可以指定为以下任何形式：500,PT0.5S和500ms。

您还可以使用任何受支持的单位。这些是：

- ns 纳秒
- us 微秒
- ms 毫秒
- s 几秒钟
- m 几分钟
- h 用了几个小时
- d 持续数天

默认单位是毫秒，可以使用@DurationUnit上面示例中所示的方法覆盖。

如果您更喜欢使用构造函数绑定，则可以公开相同的属性，如以下示例所示：

```java
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.convert.DurationUnit;

@ConfigurationProperties("my")
@ConstructorBinding
public class MyProperties {

    private final Duration sessionTimeout;

    private final Duration readTimeout;

    public MyProperties(@DurationUnit(ChronoUnit.SECONDS) @DefaultValue("30s") Duration sessionTimeout,
            @DefaultValue("1000ms") Duration readTimeout) {
        this.sessionTimeout = sessionTimeout;
        this.readTimeout = readTimeout;
    }

    public Duration getSessionTimeout() {
        return this.sessionTimeout;
    }

    public Duration getReadTimeout() {
        return this.readTimeout;
    }

}


```

提示：
```markdown
如果您要升级Long属性，请确保定义单位（使用@DurationUnit）（如果不是毫秒）。这样做提供了一个透明的升级路径，同时支持更丰富的格式。
```

#### 转换期
除了持续时间之外，Spring Boot 还可以使用java.time.Period类型。可以在应用程序属性中使用以下格式：

- 常规int表示（使用天作为默认单位，除非@PeriodUnit指定了 a）
- 标准的ISO-8601格式使用java.time.Period
- 一种更简单的格式，其中值和单位对是耦合的（例如，1y3d表示 1 年和 3 天）

简单格式支持以下单位：

- y 多年
- m 几个月
- w 数周
- d 持续数天

笔记：
```markdown
该java.time.Period类型从不实际存储周数，它是一个表示“7 天”的快捷方式。
```


##### 转换数据大小
Spring Framework 具有DataSize以字节为单位表示大小的值类型。如果公开DataSize属性，则应用程序属性中的以下格式可用：

- 常规long表示（使用字节作为默认单位，除非@DataSizeUnit指定了 a）
- 一种更易读的格式，其中值和单位是耦合的（例如，10MB表示 10 兆字节）

考虑以下示例：
```java
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

@ConfigurationProperties("my")
public class MyProperties {

   @DataSizeUnit(DataUnit.MEGABYTES)
   private DataSize bufferSize = DataSize.ofMegabytes(2);

   private DataSize sizeThreshold = DataSize.ofBytes(512);

   public DataSize getBufferSize() {
      return this.bufferSize;
   }

   public void setBufferSize(DataSize bufferSize) {
      this.bufferSize = bufferSize;
   }

   public DataSize getSizeThreshold() {
      return this.sizeThreshold;
   }

   public void setSizeThreshold(DataSize sizeThreshold) {
      this.sizeThreshold = sizeThreshold;
   }

}



```
指定缓冲区大小为 10 兆字节，10并且10MB是等效的。256 字节的大小阈值可以指定为256或256B。

您还可以使用任何受支持的单位。这些是：

- B 对于字节
- KB 千字节
- MB 兆字节
- GB 千兆字节
- TB TB 级

默认单位是字节，可以使用@DataSizeUnit上面示例中所示的方法覆盖。

如果您更喜欢使用构造函数绑定，则可以公开相同的属性，如以下示例所示：

```java
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

@ConfigurationProperties("my")
@ConstructorBinding
public class MyProperties {

    private final DataSize bufferSize;

    private final DataSize sizeThreshold;

    public MyProperties(@DataSizeUnit(DataUnit.MEGABYTES) @DefaultValue("2MB") DataSize bufferSize,
            @DefaultValue("512B") DataSize sizeThreshold) {
        this.bufferSize = bufferSize;
        this.sizeThreshold = sizeThreshold;
    }

    public DataSize getBufferSize() {
        return this.bufferSize;
    }

    public DataSize getSizeThreshold() {
        return this.sizeThreshold;
    }

}


```

提示：
```markdown
如果您要升级Long属性，请确保定义单位（使用@DataSizeUnit）（如果它不是字节）。这样做提供了一个透明的升级路径，同时支持更丰富的格式。
```

#### @ConfigurationProperties 验证
@ConfigurationProperties每当使用 Spring 的@Validated注解进行注解时，Spring Boot 都会尝试验证类。您可以javax.validation直接在配置类上使用 JSR-303约束注释。为此，请确保您的类路径上有一个兼容的 JSR-303 实现，然后向您的字段添加约束注释，如以下示例所示：
````java
import java.net.InetAddress;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("my.service")
@Validated
public class MyProperties {

    @NotNull
    private InetAddress remoteAddress;

    public InetAddress getRemoteAddress() {
        return this.remoteAddress;
    }

    public void setRemoteAddress(InetAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

}


````

提示：
```markdown
您还可以通过使用 注释@Bean创建配置属性的方法来触发验证@Validated。
```

为确保始终为嵌套属性触发验证，即使未找到任何属性，关联字段也必须使用@Valid. 以下示例建立在前面的MyProperties示例之上：

```java
import java.net.InetAddress;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("my.service")
@Validated
public class MyProperties {

    @NotNull
    private InetAddress remoteAddress;

    @Valid
    private final Security security = new Security();

    public InetAddress getRemoteAddress() {
        return this.remoteAddress;
    }

    public void setRemoteAddress(InetAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public Security getSecurity() {
        return this.security;
    }

    public static class Security {

        @NotEmpty
        private String username;

        public String getUsername() {
            return this.username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

    }

}


```

您还可以Validator通过创建一个名为 的 bean 定义来添加自定义 Spring configurationPropertiesValidator。该@Bean方法应该被声明static。配置属性验证器是在应用程序生命周期的早期创建的，将@Bean方法声明为静态方法可以创建 bean，而无需实例化@Configuration类。这样做可以避免早期实例化可能导致的任何问题。

提示：
```markdown

该spring-boot-actuator模块包括一个公开所有@ConfigurationPropertiesbean的端点。将您的 Web 浏览器指向/actuator/configprops或使用等效的 JMX 端点。有关详细信息，请参阅“生产就绪功能”部分。
```


#### @ConfigurationProperties 与 @Value
的@Value注释是核心容器的功能，和它不提供相同的功能，类型安全配置属性。下表总结了@ConfigurationProperties和支持的功能@Value：

| 特征 | @ConfigurationProperties | @Value |
| ------ | ------ | ------ |
|  松绑    |   是的   |   有限（见下面的注释）   |
|  元数据支持    |  是的    |   不   |
|   SpEL 评估   |   不   |    是的  |

笔记：
```markdown
如果您确实想使用@Value，我们建议您使用其规范形式（kebab-case 仅使用小写字母）来引用属性名称。这将允许 Spring Boot 使用与放松绑定时相同的逻辑@ConfigurationProperties。例如，@Value("{demo.item-price}")会从文件中以及从系统环境中拾取demo.item-price和demo.itemPrice形成表格。如果你使用的不是，并不会予以考虑。 application.propertiesDEMO_ITEMPRICE@Value("{demo.itemPrice}")demo.item-priceDEMO_ITEMPRICE
```
如果您为自己的组件定义了一组配置键，我们建议您将它们分组到带有@ConfigurationProperties. 这样做将为您提供结构化、类型安全的对象，您可以将其注入到您自己的 bean 中。

SpEL在解析这些文件和填充环境时，不会处理来自应用程序属性文件的表达式。但是，可以SpEL在@Value. 如果应用程序属性文件中的属性值是一个SpEL表达式，则在通过@Value.
		

## 7.3. 侧面 Profiles

Spring Profiles 提供了一种分离应用程序配置部分并使其仅在某些环境中可用的方法。Any @Component，@Configuration或者@ConfigurationProperties可以在@Profile加载时标记为限制，如下例所示：
```java
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration(proxyBeanMethods = false)
@Profile("production")
public class ProductionConfiguration {

    // ...

}


```

笔记：
```markdown
如果@ConfigurationPropertiesbean 是通过@EnableConfigurationProperties而不是自动扫描注册的，则@Profile需要在@Configuration具有@EnableConfigurationProperties注释的类上指定注释。在@ConfigurationProperties扫描的情况下，@Profile可以在@ConfigurationProperties类本身上指定。
```

您可以使用spring.profiles.active Environment属性来指定哪些配置文件处于活动状态。您可以使用本章前面描述的任何方式指定属性。例如，您可以将它包含在您的 中application.properties，如下例所示：

```properties
spring.profiles.active=dev,hsqldb

```

您还可以使用以下开关在命令行上指定它：--spring.profiles.active=dev,hsqldb.

如果没有配置文件处于活动状态，则启用默认配置文件。默认配置文件的名称是default，可以使用该spring.profiles.default Environment属性进行调整，如以下示例所示：
```properties
spring.profiles.default=none

```

### 7.3.1. 添加活动配置文件
该spring.profiles.active属性遵循与其他属性相同的排序规则：最高者PropertySource获胜。这意味着您可以在其中指定活动配置文件application.properties，然后使用命令行开关替换它们。

有时，将属性添加到活动配置文件而不是替换它们是很有用的。该SpringApplication入口点设置额外的配置文件的Java API（即，是对那些被激活的顶级spring.profiles.active属性）。请参阅SpringApplication 中的setAdditionalProfiles()方法。如果给定的配置文件处于活动状态，则下一节中描述的配置文件组也可用于添加活动配置文件。


### 7.3.2. 个人资料组
有时，您在应用程序中定义和使用的配置文件过于精细，使用起来很麻烦。例如，你可能有proddb和prodmq配置文件，您使用启用数据库和信息功能独立。

为了解决这个问题，Spring Boot 允许您定义配置文件组。配置文件组允许您为相关的配置文件组定义逻辑名称。

例如，我们可以创建一个production包含我们proddb和prodmq个人资料的组
```properties
spring.profiles.group.production[0]=proddb
spring.profiles.group.production[1]=prodmq

```
我们的应用程序现在可以开始使用，一--spring.profiles.active=production键激活production,proddb和prodmq配置文件。


### 7.3.3. 以编程方式设置配置文件
您可以通过SpringApplication.setAdditionalProfiles(…​)在应用程序运行之前调用来以编程方式设置活动配置文件。也可以使用 Spring 的ConfigurableEnvironment界面来激活配置文件。



### 7.3.4. 特定于配置文件的配置文件
application.properties(或application.yml) 和通过引用的文件的特定于配置文件的变体@ConfigurationProperties被视为文件并加载。有关详细信息，请参阅“配置文件特定文件”。


## 7.4. 日志记录
Spring Boot 使用Commons Logging进行所有内部日志记录，但保持底层日志实现处于打开状态。为Java Util Logging、Log4J2和Logback提供了默认配置。在每种情况下，记录器都预先配置为使用控制台输出，也可以使用可选的文件输出。

默认情况下，如果您使用“Starters”，则使用 Logback 进行日志记录。还包括适当的 Logback 路由，以确保使用 Java Util Logging、Commons Logging、Log4J 或 SLF4J 的依赖库都能正常工作。

提示：
```markdown

有许多适用于 Java 的日志记录框架。如果上面的列表看起来令人困惑，请不要担心。通常，您不需要更改日志记录依赖项，并且 Spring Boot 默认值工作得很好。
```

提示：
```markdown
将应用程序部署到 servlet 容器或应用程序服务器时，通过 Java Util Logging API 执行的日志记录不会路由到应用程序的日志中。这可以防止容器或其他已部署到它的应用程序执行的日志记录出现在您的应用程序日志中。
```

### 7.4.1. 日志格式
Spring Boot 的默认日志输出类似于以下示例：
```markdown
2019-03-05 10:57:51.112 INFO 45469 --- [main] org.apache.catalina.core.StandardEngine：启动 Servlet 引擎：Apache Tomcat/7.0.52
2019-03-05 10:57:51.253 INFO 45469 --- [ost-startStop-1] oaccC[Tomcat].[localhost].[/] : 初始化 Spring 嵌入式 WebApplicationContext
2019-03-05 10:57:51.253 INFO 45469 --- [ost-startStop-1] osweb.context.ContextLoader：根 WebApplicationContext：初始化在 1358 毫秒内完成
2019 年 3 月 5 日 10：57：51.698 信息 45469 --- [ost-startStop-1] osbceServletRegistrationBean：将 servlet：'dispatcherServlet' 映射到 [/]
2019-03-05 10:57:51.702 INFO 45469 --- [ost-startStop-1] osbcembedded.FilterRegistrationBean：映射过滤器：'hiddenHttpMethodFilter'到：[/*]
```

输出以下项目：

- 日期和时间：毫秒精度且易于排序。
- 日志级别：ERROR，WARN，INFO，DEBUG，或TRACE。
- 进程标识。
- 一个---分离器来区分实际日志消息的开始。
- 线程名称：括在方括号中（可能会被截断以用于控制台输出）。
- 记录器名称：这通常是源类名称（通常缩写）。
- 日志消息。

笔记：
```markdown
Logback 没有FATAL级别。它被映射到ERROR.
```


### 7.4.2. 控制台输出
默认日志配置在写入消息时将消息回显到控制台。默认情况下，会记录ERROR-level、WARN-level 和INFO-level 消息。您还可以通过使用--debug标志启动应用程序来启用“调试”模式。
```shell
$ java -jar myapp.jar --debug
```

笔记：
```markdown
您也可以debug=true在您的application.properties.
```

启用调试模式后，会配置一系列核心记录器（嵌入式容器、Hibernate 和 Spring Boot）以输出更多信息。启用调试模式并没有配置您的应用程序记录所有消息DEBUG的水平。

或者，您可以通过使用--trace标志（或trace=true在您的application.properties）中启动您的应用程序来启用“跟踪”模式。这样做可以为选择的核心记录器（嵌入式容器、Hibernate 模式生成和整个 Spring 产品组合）启用跟踪日志记录。


#### 彩色编码输出
如果您的终端支持 ANSI，则使用颜色输出来提高可读性。您可以设置spring.output.ansi.enabled为支持的值以覆盖自动检测。

使用%clr转换字配置颜色编码。在最简单的形式中，转换器根据日志级别为输出着色，如以下示例所示：
```markdown
%clr(%5p)

```

下表描述了日志级别到颜色的映射：

| 等级 | 颜色 |
| ------ | ------ |
|   FATAL   |   红色的   | 
|  ERROR    |    红色的  | 
|   WARN   |   黄色   | 
|  INFO    |  绿    | 
|   DEBUG   |  绿    | 
|  TRACE    |   绿   | 


或者，您可以通过将其作为转换选项提供来指定应使用的颜色或样式。例如，要使文本变黄，请使用以下设置：
```markdown
%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){yellow}

```

支持以下颜色和样式：

- blue
- cyan
- faint
- green
- magenta
- red
- yellow








































| col1 | col2 |
| ------ | ------ |
|      |      | 
|      |      | 





| col1 | col2 | col3 |
| ------ | ------ | ------ |
|      |      |      |
|      |      |      |
