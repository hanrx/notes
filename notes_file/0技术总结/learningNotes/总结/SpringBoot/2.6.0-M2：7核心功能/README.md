
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
















































