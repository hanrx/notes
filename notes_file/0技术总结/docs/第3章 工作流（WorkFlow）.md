# 第3章 工作流（WorkFlow）

* 需求初衷：
    * 调度：shell脚本程序，java程序，mapreduce程序、hive脚本等。
    * 定时调度：
    * 可视化：
    * 调度过程管理：
* 定义：
    * 工作流是将一组任务组织起来一完成某个经营过程：定义了任务的出发顺序和触发条件，每个任务可以由一个或多个软件系统完成，也可以由一个或一组人完成，还可以由一个或多个人与系统软件协作完成；


* 解决问题：



## 3.1 Activiti 和 Activiti Cloud
![](images/3.1.1.png)
* 难度

* 优点：
    * 原生支持Spring：可轻松进行Spring集成，非常方便管理事务。
    *  专门的流程设计器：Eclipse Designer、基于Web的Activiti Modeler流程设计器。

* 缺点
    * 无持久化标准。

* 支持

* 原理

* 使用

* 其他

* 对比其他：
![](images/3.1.2.png)
![](images/3.1.3.png)
* 参考：
    * https://www.jianshu.com/p/86c0a4afd28e
    * http://www.mossle.com/docs/activiti/index.html




Activiti Cloud版本7.x

https://activiti.gitbook.io/activiti-7-developers-guide/overview


Shark，osworkflow，jbpm！Azkaban
WorkFlowEngine

## 3.3 其他：
* OpenWebFlow：
    OpenWebFlow是基于Activiti扩展的工作流引擎。是一个新兴的基于 Apache 许可的支持 BPMN 2.0 标准的开源 BPM 产品，它是一个轻量级，可嵌入的 BPM 引擎，并且提供了功能丰富的开发和流程设计工具。OpenWebFlow与业务应用系统之间的关系如下图所示。
![](images/3.3.1.png)

* OpenWebFlow扩展的功能包括：
    * 完全接管了Activiti对活动（activity）权限的管理：  Activiti允许在设计model的时候指定每个活动的执行权限，但是，业务系统可能需要根据实际情况动态设置这些任务的执行权限（如：动态的Group）。OpenWebFlow完全实现了与流程定义时期的解耦，即用户对活动的访问控制信息单独管理（而不是在流程定义中预先写死），这样有利于动态调整权限，详见自定义活动权限管理；  
    * 完全接管了Activiti对用户表（IDENTITY_XXX表）的管理：  在标准的工作流定义中，每个节点可以指定其候选人和候选用户组，但是比较惨的是，Activiti绑架了用户信息表的设计！这个是真正致命的，因为几乎每个业务系统都会属于自己的用户信息结构（包括User/Group/Membership），但不一定它存储在Activiti喜欢的那个库中，表的结构也不一定一样，有的时候，某些信息（如：动态的Group）压根儿就不采用表来存储。OpenWebFlow剥离了用户信息表的统一管理，客户程序可以忘掉Activiti的用户表、群组表、成员关系表，详见自定义用户成员关系管理；  
    * 允许运行时定义activity：  彻底满足“中国特色”，并提供了安全的（同时也是优雅的）催办、代办、加签（包括前加签/后加签）、自由跳转（包括前进/后）、分裂节点等功能；

* 缺点：
    * 代码迭代慢：最近一次更新三年前（https://github.com/bluejoe2008/openwebflow）。
    * 参考资料少。






