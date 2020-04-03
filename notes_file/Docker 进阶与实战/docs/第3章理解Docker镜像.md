
# 理解Docker镜像

Docker所宣称的用户可以随心所欲地“**Build、Ship and Run**”**应用的能力**，其**核心是由Docker image（Docker镜像）来支撑**的。Docker通过把应用的运行时环境和应用打包在一起，解决了部署环境依赖的问题；通过引入**分层文件系统**这种概念，解决了空间利用的问题。它**彻底消除了编译、打包与部署、运维之间的鸿沟**，与现在互联网企业推崇的DevOps理念不谋而合，大大提高了应用开发部署的效率。Docker公司的理念被越来越多的人理解和认可也就是理所当然的了，而理解Docker image则是深入理解Docker技术的一个关键点。

本章主要介绍Docker image的使用和相关技术细节。

# 3.1 Docker image概念介绍

简单地说，**Docker image是用来启动容器的只读模板**，是容器启动所需要的rootfs，类似于虚拟机所使用的镜像。首先需要通过一定的规则和方法表示Docker image,如图3-1所示。   
![](images/3.1.1.png)

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




# 3.2 使用Docker image

**Docker内嵌了一系列命令制作、管理、上传和下载镜像**。可以调用REST API给Docker daemon发送相关命令，也可以使用client端提供的CLI命令完成操作。本书的第7章会详细阐述Docker REST API的细节，本节则主要根据功能对涉及image的命令进行说明。下面就从Docker image的生命周期角度说明Docker image的相关使用方法。

## 3.2.1 列出本机的镜像
下面的命令可以**列出本地存储中镜像**，也可以查看这些镜像的基本信息。
![](images/3.2.1.1.png)

此外，**通过--help参数还可以查询docker images的详细用法**，如下：
![](images/3.2.1.2.png)
* 其中，**--filter用于过滤docker images的结果**，过滤器采用key=value的这种形式。目前支持的过滤器为dangling和label。**—filer”dangling=true”会显示所有“悬挂”镜像**。“悬挂”镜像没有对应的名称和tag，并且其最上层不会被任何镜像所依赖。docker commit在一些情况下会产生这种“悬挂”镜像。下面第一条命令产生了一个“悬挂”镜像，第二条命令则根据其特点过滤出该镜像了。图3-2中的d08407d541f3就是这种镜像。 
![](images/3.2.1.3.png)

* 在上面的命令中，**--no-trunc参数可以列出完整长度的Image ID**。若添加参数-q则会只输出Image ID，该参数在管道命令中很有用处。一般来说悬挂镜像并不总是我们所需要的，并且会浪费磁盘空间。**可以使用如下管道命令删除所有的“悬挂”镜像**。
![](images/3.2.1.4.png)

* 这里的--digests比较特别，这个参数是伴随着新版本的Docker Registry V2（即Distribution）产生的，在本书接下来的第4章会详细说明。

按照Docker官方路标和最近的动作，**Docker只会保留最核心的image相关命令和功能**，因此那些非核心功能就会被删除。比如-- tree和—dot已经从Docker 1.7中删掉。**官方推荐使用dockerviz工具分析Docker image**。执行以下命令，可以图形化地展示Docker image的层次关系。
![](images/3.2.1.5.png)

执行结果如图3-2所示，可以看到，同一个仓库中的镜像并不一定要有特别的关系，比如ubuntu:14.04和ubuntu:14.04.2之间就没有共享任何层。
![](images/3.2.1.6.png)

见https://github.com/justone/dockviz。


# 3.2.2  Build：创建一个镜像

 创建镜像是一个很常用的功能，既可以从无到有地创建镜像，也可以以现有的镜像为基础进行增量开发，还可以把容器保存为镜像。下面就详细介绍这些方法。
直接下载镜像
    我们可以从镜像仓库下载一个镜像，比如，以下为下载busybox镜像的示例。

    具体使用镜像仓库的方法，本书会在后续章节详细描述，这里暂不做说明。

导入镜像
    还可以导入一个镜像，对此，Docker提供了两个可用的命令docker import和docker load。docker load一般只用于导入由docker save导出的镜像，导入后的镜像跟原镜像完全一样，包括拥有相同的镜像ID和分层等内容。下面的第一行命令可以导出busybox为busybox.tar，第二天命令则是导入该镜像：

    不同于docker load，docker import不能用于导入标准的Docker镜像，而是用于导入包含根文件系统的归档，并将之变成Docker镜像。

制作新的镜像
    前面说过，docker import用于导入包含根文件系统的归档，并将之变成Docker镜像。因此，docker import常用来制作Docker基础镜像，如Ubuntu等镜像。与此相对，docker export则是把一个镜像导出为根文件系统的归档。

    提示
    读者可以使用Debian提供的Debootstrap制作Debian或Ubuntu的Base image，可以在Docker官网找到教程（https://docs.docker.com/articles/baseimages/）。

    Docker提供的docker commit命令可以增量地生成一个镜像，该命令可以把容器保存为一个镜像，还能注明作者信息和镜像名称，这与git commit类似。当镜像名称为空时，就会形成“悬挂”镜像。当然，使用这种方式每新增加一层都需要数个步骤（比如，启动容器、修改、保存修改等），所以效率是比较低的，因此这种方式适合正式制作镜像前的尝试。当最终确定制作的步骤后，可以使用docker build命令，通过Dockerfile文件生成镜像。
























