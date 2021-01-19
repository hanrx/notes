package com.iafoot.notes.test;

import ch.qos.logback.core.net.SyslogOutputStream;

import java.util.ArrayList;
import java.util.List;

/**
 * -Xms60m -Xmx60m -XX:NewRatio=2 -XXSurvivorRatio=8 -XX:+PrintGCDetails
 *  -XX:+PrintFlagsInitial：查看所有参数的默认值。
 *     * -XX:+PrintFlagFinal：查看所有的参数的最终值（可能会存在修改，不再是默认值）。
 *     * -Xms：初始堆空间内存（默认是物理内存的1/64）。
 *     * -Xmx：最大堆空间内存（默认物理内存的1/4）。
 *     * -Xmn：设置新生代的大小。（初始值及最大值）。
 *     * -XX: NewRatio：配置新生代与老年代在堆结构的占比。
 *     * -XX:SurvivorRatio：设置新生代中Eden和s0/s1空间的比例。
 *     * -XX:MaxTenuringThreshold：设置新生代垃圾的最大年龄。
 *     * -XX:+PrintGCDetails：输出详细的GC处理日志
 *         * 打印gc简要信息：-XX:+PrintGC。-verbose:gc。
 *     * -XX:HandlePromotionFailure：是否设置空间分配担保。
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
