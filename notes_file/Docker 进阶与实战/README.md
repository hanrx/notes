


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
    











