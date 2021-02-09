package com.iafoot.notes.config;

import com.iafoot.notes.jvm.jps.ScannerTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author iAfoot
 * @Create 20210209 16:52
 * @Version 1.0.0
 */
@Configuration//告诉Spring Boot这是一个配置类
public class MyConfig {
    @Bean //给容器中添加组件，以方法名为组件的id。返回类型就是组件类型。返回的值，就是组件在容器中的实例。
    public ScannerTest scannerTest(){
        return new ScannerTest();
    }
}
