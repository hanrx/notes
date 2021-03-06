package com.iafoot.notes.jvm.jmap;

import java.util.ArrayList;
import java.util.List;

/**
 * -Xms60m -Xmx60m -XX:SurvivorRatio=8
 * @author iAfoot
 * @Create 20210209 10:52
 * @Version 1.0.0
 */
public class GCTest {
    public static void main(String[] args) {
        ArrayList<byte[]> list = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            byte[] arr = new byte[1024*100];//100kb
            list.add(arr);
            try {
                Thread.sleep(120);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

//静态集合类
class MemoryLeak{
    static List list = new ArrayList();

    public void oomTests(){
        Object obj = new Object();//局部变量
        list.add(obj);
    }
}