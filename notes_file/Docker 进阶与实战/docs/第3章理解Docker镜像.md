
# 理解Docker镜像

Docker所宣称的用户可以随心所欲地“B**uild、Ship and Run**”**应用的能力**，其**核心是由Docker image（Docker镜像）来支撑**的。Docker通过把应用的运行时环境和应用打包在一起，解决了部署环境依赖的问题；通过引入**分层文件系统**这种概念，解决了空间利用的问题。它**彻底消除了编译、打包与部署、运维之间的鸿沟**，与现在互联网企业推崇的DevOps理念不谋而合，大大提高了应用开发部署的效率。Docker公司的理念被越来越多的人理解和认可也就是理所当然的了，而理解Docker image则是深入理解Docker技术的一个关键点。

本章主要介绍Docker image的使用和相关技术细节。

# Docker image概念介绍

简单地说，**Docker image是用来启动容器的只读模板**，是容器启动所需要的rootfs，类似于虚拟机所使用的镜像。首先需要通过一定的规则和方法表示Docker image,如图3-1所示。
![](images/3-1.png)

图3-1是典型的Docker镜像的表示方法，可以看到其被“/”分为了三个部分，其中每部分都可以类比Github中的概念。下面按照从左到右的顺序介绍这几个部分以及相关的一些重要概念。

* Remote docker hub：**集中存储镜像的Web服务器地址**。该部分的存在使得可以区分从不同镜像库中拉取的镜像。若Docker的镜像表示中缺少该部分，说明使用的是默认镜像库，及Docker官方镜像库。
* Namespace：**类似于Github中的命名空间**，是一个用户或组织中所有镜像的集合。
* Repository：**类似于Git仓库**，一个仓库可以有多个镜像，不同镜像通过tag来区分。
* Tag：**类似Git仓库中的tag**，一般用来区分同一类镜像的不同版本。
* Layer：**镜像由一系列层组成**，每层都有64位的十六进制数表示，非常类似于Git仓库中的commit。
* Image ID：**镜像最上层的layer ID就是该镜像的ID**,Repo：tag提供了易于人类识别的名字，而ID便于脚本处理、操作镜像。


镜像库是Docker公司最先提出的概念，非常类似应用市场的概念。用户可以发布自己的镜像，也可以使用别人的镜像。Docker开源了镜像存储部分的源代码（Docker Registry以及Distribution）,但是这些开源组件并不适合独立地发挥功能，需要使用Nginx等代理工具添加基本的鉴权功能，才能搭建出私有镜像仓库。本地镜像则是已经下载到本地的镜像，可以使用docker images等命令进行管理。这些镜像默认存储在/var/lib/docker路径下，该路径也可以使用docker daemon - g参数在启动Daemon时指定。

**提示：**     
> Docker的镜像已经支持更多层级，比如用户的命名空间之前可以包含组织（Remote-dockerhub.com/group/namespace/bar:latest）。但是目前Docker官方的镜像库还不具备该能力

见<https://hub.docker.com>。
