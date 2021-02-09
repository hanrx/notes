package com.iafoot.notes.jvm.jps;

import java.util.Scanner;

/**
 * > jps（Java Process Status）
 * > * 基本语法：jps [options][hostid]
 * > * options参数：
 * >   * -q：仅仅显式LVMID（local virtual machine id），即本地虚拟机唯一id。不显示主类的名称等。
 * >   * -l：输出应用程序主类的全类名 或 如果进程执行的jar包，则输出jar完整路径。
 * >   * -m：输出虚拟机进程启动时传递给主类main()的参数。
 * >   * -v：列出虚拟机进程启动时的JVM参数。比如：-Xms20m -Xmx50m是启动程序指定的jvm参数。
 * > 说明：以上参数可以综合实验。
 * > 补充：如果某Java进程关闭了默认开启的UsePerfData参数(即使用参数-XX:-UsePerData)，那么jps命令（以及下面介绍的jstat）将无法探知该Java进程。
 * >
 * @author iAfoot
 * @Create 20210209 8:43
 * @Version 1.0.0
 */
public class ScannerTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String info = scanner.next();
    }
}
