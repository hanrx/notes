

## [内容提要](docs/内容提要.md "内容提要")
* 提要：介绍如何将EagleEye项目一步一步地从单体架构重构成为微服务架构，进而将这个项目拆分成众多微服务，让它们运行在各自的Docker容器中，实现持续集成/持续部署，并最终自动部署到云环境（Amazon）中。


## [关于本书](docs/关于本书.md "关于本书")
* 适合的读者。
* 组织结构。
* 关于代码。

## [源码](docs/源码.md "源码")


## [附录A 在桌面运行云服务](docs/附录A%20在桌面运行云服务.md "附录A 在桌面运行云服务")
* 本附录主要内容
    * 列出运行本书中的代码所需的软件
    * 从GitHub上下载每章的源代码
    * 使用Maven编译和打包源代码
    * 构建和提供每章使用的Docker镜像
    * 使用Docker Compose启动由构建编译的Docker镜像
    * 构建Docker镜像：
        * Spotify Maven：服务都被打包为Docker镜像。
        * 使用Alpine Linux提供实例，常用来构建Docker镜像。
        * 将安装名为nc的命令行实用程序。nc命令用于ping服务器并查看特定的端口是否在网络上可用。该命令将在run.sh命令脚本中使用，以确保在启动服务之前，所有依赖的服务（如数据库和Spring Cloud Config服务）都已启动。
        * 为许可证服务的可执行JAR文件创建一个目录，然后将jar文件从本地文件系统复制到在Docker镜像上创建的目录中。
        * 通过ADD命令安装run.sh脚本。
        * 一旦将run.sh命令复制到许可证服务的Docker镜像，Docker命令CMD./run.sh用于告知Docker在实际镜像启动时执行run.sh启动脚本。
    * Docker Compose启动服务：
        * Docker Compose是一个服务编排工具，它允许开发人员将服务定义为一个组，然后作为一个单元一起启动。
        * Docker Compose还拥有为每个服务定义环境变量的功能。



## [第1章 欢迎迈入云世界，Spring](docs/第1章%20欢迎迈入云世界，Spring.md "第1章 欢迎迈入云世界，Spring")




























