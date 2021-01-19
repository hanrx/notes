package com.iafoot.notes.test;

import ch.qos.logback.core.net.SyslogOutputStream;

import java.util.ArrayList;
import java.util.List;

public class TestJavaP {
    public static void main(String[] args) throws Exception{
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

    public void  test(Long l){
        for (int i=0;i>l;i++){

        }
    }
}
