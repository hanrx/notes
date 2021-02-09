package com.iafoot.notes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotesApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotesApplication.class, args);
/*

        //打印所有容器中bean。
        //1. 返回我们IOC容器
        ConfigurableApplicationContext run = SpringApplication.run(NotesApplication.class, args);
        //2.查看容器里面的组件。
        String[] names = run.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println("容器中bean名字："+name);
        }
        // 3.从容器中获取组件
        ScannerTest scannerTest = run.getBean("scannerTest",ScannerTest.class);
*/



    }

}
