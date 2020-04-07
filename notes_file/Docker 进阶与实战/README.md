


# 目录

- [程序员修炼之路](#程序员修炼之路)
    - [学习/编程好习惯](#学习编程好习惯)
        - [正确提问](#正确提问)
- [第3章理解Docker镜像](##第3章理解Docker镜像)


## [第3章理解Docker镜像](docs/第3章理解Docker镜像.md "第3章 理解Docker镜像")
* 理解Docker镜像
    * 把应用的运行时环境和应用打包在一起，解决了部署环境依赖的问题。
    * 引入分层文件系统这种概念，解决了空间利用的问题。
    * 彻底消除了编译、打包与部署、运维之间的鸿沟。

* Docker image概念介绍
    * Docker image是用来启动容器的只读模板。
    
* 列出本机的镜像
    * 列出本地存储中镜像: 【docker images】。
    * 通过--help参数还可以查询docker images的详细用法: 【docker images --help】。
    * 显示出悬挂镜像：【docker images --filter "dangling=true"】。
    * 使用如下管道命令删除所有的“悬挂”镜像: 》Linux命令【docker images --filter "dangling=true" -q | xargs  docker rmi】。
    
* Build：创建一个镜像
    * 直接下载镜像：【docker pull busybox】。
    * 导入镜像： 【docker load】（一般只用于导入由docker save导出的镜像）和【docker import】（用于导入包含根文件系统的归档，【docker export】则是把一个镜像导出为根文件系统的归档）。
        * 导出busybox为busybox.tar：【docker save -o busybox.tar busybox】。
        * 导入镜像：【docker load -i busybox.tar】。
    * 制作新的镜像：【docker import】常用来制作Docker基础镜像。【docker export】则是把一个镜像导出为根文件系统的归档

* Ship：传输一个镜像
    * Docker镜像仓库做中转传输。
    * docker export/docker save生成的tar包来实现。
    * Docker镜像的模板文件Dockerfile做间接传输。

* Run：以image为模板启动一个容器
    * 启动容器时，可以使用命令：【docker run】。
    * 列出容器使用的是：【docker ps】。
    * 列出镜像使用的是：【docker images】。

* 数据的内容：Docker image包含数据及元数据。数据由一层层image layer组成，元数据是JSON文件来描述数据（image layer）之间关系以及容器一些配置信息。
    * 查看镜像 image layer：【docker history busybox】。
    
* 数据的组织: 通过docker inspect得到该层的元数据【docker inspect busybox】。
































