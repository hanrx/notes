package com.iafoot.notes.test;

import ch.qos.logback.core.net.SyslogOutputStream;

import java.util.ArrayList;
import java.util.List;

/**
 * -Xms60m -Xmx60m -XX:NewRatio=2 -XXSurvivorRatio=8 -XX:+PrintGCDetails
 */
public class TestJavaGC {
    public static void main(String[] args) throws Exception{
        //测试大对象直接进入老年代
        byte[] buffer = new byte[1024*1024*20];//20m 大对象

    }

    public void  testFullGC(Long l){
        int i = 0;
        try{
            List<String> list = new ArrayList<>();
            String a = "iafoot.com";
            while (true){
                list.add(a);
                a =a+a;
                i++;
                System.out.println("遍历次数为："+i);
                Thread.sleep(1000);
            }
        }catch (Throwable t){
            t.printStackTrace();
            System.out.println("遍历次数为："+i);
        }
    }
}
