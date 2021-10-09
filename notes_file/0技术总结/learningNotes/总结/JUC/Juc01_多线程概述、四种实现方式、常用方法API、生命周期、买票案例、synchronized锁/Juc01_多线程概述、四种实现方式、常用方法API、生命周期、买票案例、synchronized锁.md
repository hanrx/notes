# 1.  多线程的概述

## ①. 为什么使用多线程及其重要

- 摩尔定律失效(硬件方面):
    - (1). 集成电路上可以容纳的晶体管数目在大约每经过18个月便会增加一倍,可是从2003年开始CPU主频已经不再翻倍,而是采用多核而不是更快的主频
    - (2). 在主频不再提高且核数不断增加的情况下,要想让程序更快就要用到并行或并发编程
- 高并发系统,异步+回调的生产需求(软件方面)

## ②. 进程、线程、管程(monitor 监视器)

- 1.线程就是程序执行的一条路径,一个进程中可以包含多条线程
- 2.多线程并发执行可以提高程序的效率,可以同时完成多项工作
- 3.举例:
```markdown
[你打开一个word就是一个进程开启了,这个时候你重启后在打开word,这个时候有一个点击恢复的按钮,这就是一个线程,可能这个线程你看不到,你打字的时候,单词打错了,word中会有一个波浪线,这也是一个线程]

```
- 4.管程:Monitor(监视器),也就是我们平时所说的锁
```markdown
(Monitor其实是一种同步机制,它的义务是保证(在同一时间)只有一个线程可以访问被保护的数据和代码)
JVM中同步时基于进入和退出的监视器对象(Monitor,管程),每个对象实例都有一个Monitor对象。
Monitor对象和JVM对象一起销毁,底层由C来实现
```

## ③. 多线程并行和并发的区别

- 并行就是两个任务同时运行,就是甲任务进行的同时,乙任务也在进行(需要多核CPU)
- 并发是指两个任务都请求运行,而处理器只能接收一个任务,就是把这两个任务安排轮流进行,由于时间间隔较短,使人感觉两个任务都在运行(12306抢票的案例)

## ④. wait | sleep的区别？功能都是当前线程暂停,有什么区别？
- wait放开手去睡,放开手里的锁；wait是Object类中的方法 
- sleep握紧手去睡,醒了手里还有锁 ;sleep是Thread中的方法

## ⑤. synchronized 和 lock的区别？

- (1). 原始构成
  - a. synchronized是关键字属于JVM层面
    ```markdown
    monitor对象,每个java对象都自带了一个monitor,需要拿到monitor对象才能做事情
    monitorenter(底层是通过monitor对象来完成,其实wait/notify等方法也依赖monitor对象,
    只能在同步块或方法中才能调用wait/notify等方法),进入
    monitorexit:退出
    ```
  - b. lock是api层面的锁,主要使用ReentrantLock实现
- (2). 使用方法
  - a. synchronized不需要用户手动释放锁,当synchronized代码完成后系统会自动让线程释放
对锁的占用
  - b. ReentrantLock则需要用户手动释放锁若没有主动释放锁,就有可能会导致死锁的现象
- (3). 等待是否可中断?
  - a. synchronized不可中断,除非抛出异常或者正常运行完成
  - b. ReentrantLock可中断
(设置超时时间tryLock(long timeout,TimeUnit unit),调用interrupt方法中断)
- (4). 加锁是否公平
  - a. synchronized非公平锁
  - b. ReentrantLock两者都可以,默认是非公平锁,构造方法可以传入boolean值,true为公平锁,
false为非公平锁
- (5). 锁绑定多个Condition
  - a.synchronized没有
  - b.ReentrantLock用来实现分组唤醒需要唤醒线程们,可以精确唤醒,而不是像synchronized要么
随机唤醒一个\要么多个


# 2. 多线程的实现方式

## ①. 继承Thread











