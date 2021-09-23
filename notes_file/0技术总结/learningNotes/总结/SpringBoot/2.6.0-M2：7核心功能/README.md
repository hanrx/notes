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








































































































| col1 | col2 | col3 |
| ------ | ------ | ------ |
|      |      |      |
|      |      |      |
