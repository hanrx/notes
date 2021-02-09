package com.iafoot.notes.jvm.jstat;

import java.util.ArrayList;

/**
 * 03-jstat：查看JVM统计信息：官方文档》https://docs.oracle.com/javase/8/docs/technotes/tools/unix/jstat.html
 * > * 基本情况
 * >   jstat(JVM Statistics Monitoring Tool)：用于监视虚拟机各种运行状态信息的命令行工具。它可以显示本地或者远程虚拟机进程中的类装载、内存、
 * >   垃圾收集、JIT编译等运行数据。
 * >   在没有GUI图形界面，只提供纯文本控制台环境的服务器上，它将是运行期定位虚拟机性能问题的首选工具。常用语检测垃圾回收问题以及内存泄漏问题。
 * > * 基本语法：jstat -<option> [-t] [-h<lines>] <vmid> [<interval> [<count>]]
 * >   * 查看命令相关参数：jstat -h 或 jstat -help
 * > * 参数说明：
 * >   * option：option参数可由以下值构成。
 * >     * 类装载相关的：
 * >       * -class：显式ClassLoader的相关信息：类的装载、卸载数量、总空间、类装载所消耗的时间等。
 * >     * 垃圾回收相关的：
 * >       * -gc：显式与GC相关的对信息。包括Eden区、两个Survivor区、老年代、永久代等的容量、已用空间、GC时间合计等信息。
 * >       * -gccapacity：显式内容与-gc基本相同，但输出主要关注Java堆各个区域使用到的最大、最小空间。
 * >       * -gcutil：显式内容与-gc基本相同，但输出主要关注已使用空间占总空间的百分比。
 * >       * -gccause：与-gcutil功能一样，但是会额外输出导致最后一次或当前正在发生的GC产生的原因。
 * >       * -gcnew：显示新生代GC状况。
 * >       * -gcnewcapacity：显示内容与-gcnew基本相同，输出主要关注使用到的最大、最小空间。
 * >       * -gcold：显示老年代GC状况。
 * >       * -gcoldcapacity：显示内容与-gcold基本相同，输出主要关注使用到的最大、最小空间。
 * >       * -gcpermcapacity：显示永久代使用到的最大、最小空间。
 * >     * JIT相关的：
 * >       * -compiler：显示JIT编译器编译过的方法、耗时等信息。
 * >       * -printcompilation：输出已经被JIT编译的方法。
 * >   * interval：用于指定输出统计数据的周期，单位为毫秒。即：查询间隔。
 * >   * count：用于指定查询的总字数。
 * >   * -t：可以在输出信息前加上一个Timestamp列，显示程序的运行时间，单位秒。即程序开始执行到现在多少秒。
 * >     * 经验：
 * >     我们可以比较Java进程的启动时间以及总GC时间（GCT列），或者两次测量的间隔时间以及总GC时间的增量，来得出GC时间占运行时间的比例。
 * >     如果该比例超过20%，则说明目前堆的压力比较大；如果该比例超过90%,则说明几乎没有可用空间，随时都可能抛出OOM异常。
 * >   * -h：可以周期性输出输出时，输出多少行数据后输出一个表头信息。
 * > 说明：
 * > * option参数：
 * >   * 新生代相关：
 * >     * S0C：是第一个幸存者区的大小（字节）。
 * >     * S1C：是第二个幸存者区的大小（字节）。
 * >     * S0U：是第一个幸存者区已使用的大小（字节）。
 * >     * S1U：是第二个幸存者区已使用的大小（字节）。
 * >     * EC：是Eden空间的大小（字节）。
 * >     * EU：是Eden空间已使用大小（字节）。
 * >   * 老年代相关：
 * >     * OC：是老年代的大小（字节）。
 * >     * OU：是老年代已使用的大小（字节）。
 * >   * 方法区（元空间）相关：
 * >     * MC：是方法区的大小。
 * >     * MU：是方法区已使用的大小。
 * >     * CCSC：是压缩类空间的大小。
 * >     * CCSU：是压缩类空间已使用的大小。
 * >   * 其他：
 * >     * YGC：是指从应用程序启动到采用时young gc次数。
 * >     * YGCT：是指从应用程序启动到采样时young gc消耗的时间（秒）。
 * >     * FGC：是指从应用程序启动到采用时full gc次数。
 * >     * FGCT：是指从应用程序启动到采样时full gc消耗的时间（秒）。
 * >     * GCT：是指从应用程序启动到采样时gc的总时间。
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
