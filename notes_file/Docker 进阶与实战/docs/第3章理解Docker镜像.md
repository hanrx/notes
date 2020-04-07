
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


## 3.2.2  Build：创建一个镜像

创建镜像是一个很常用的功能，**既可以从无到有地创建镜像，也可以以现有的镜像为基础进行增量开发，还可以把容器保存为镜像**。下面就详细介绍这些方法。
* 1.直接下载镜像    
 我们可以从镜像仓库下载一个镜像，比如，以下为下载busybox镜像的示例。
 ![](images/3.2.2.1.png)



* 2.导入镜像    
还可以导入一个镜像，对此，Docker提供了两个可用的命令**docker import和docker load**。**docker load一般只用于导入由docker save导出的镜像**，导入后的镜像跟原镜像完全一样，包括拥有相同的镜像ID和分层等内容。下面的第一行命令可以导出busybox为busybox.tar，第二条命令则是导入该镜像：
![](images/3.2.2.2.png)
不同于docker load，docker import不能用于导入标准的Docker镜像，而是用于导入包含根文件系统的归档，并将之变成Docker镜像。


* 3.制作新的镜像  
前面说过，docker import用于导入包含根文件系统的归档，并将之变成Docker镜像。因此，**docker import常用来制作Docker基础镜像**，如Ubuntu等镜像。与此相对，**docker export则是把一个镜像导出为根文件系统的归档**。

提示
> 读者可以使用Debian提供的Debootstrap制作Debian或Ubuntu的Base image，可以在Docker官网找到教程（<https://docs.docker.com/articles/baseimages/>）。

    Docker提供的docker commit命令可以增量地生成一个镜像，该命令可以把容器保存为一个镜像，还能注明作者信息和镜像名称，这与git commit类似。当镜像名称为空时，就会形成“悬挂”镜像。当然，使用这种方式每新增加一层都需要数个步骤（比如，启动容器、修改、保存修改等），所以效率是比较低的，因此这种方式适合正式制作镜像前的尝试。当最终确定制作的步骤后，可以使用docker build命令，通过Dockerfile文件生成镜像。



## 3.2.3 Ship：传输一个镜像

镜像传输是连接开发和部署的桥梁。**可以使用Docker镜像仓库做中转传输**，**还可以使用docker export/docker save生成的tar包来实现**，或者**使用Docker镜像的模板文件Dockerfile做间接传输**。目前托管在Github等网站上的项目，已经原来越多地包含有Dockerfile文件；同时Docker官方镜像仓库使用了github.com的**webhook功能**，若代码被修改就会触发流程自动重新制作镜像。


## 3.2.4 Run：以image为模板启动一个容器

启动容器时，可以使用docker run命令，该命令有相关章节会详细描述，本节不做深入说明。
![](images/3.2.4.1.png)

图3-3总结了上文提到的Docker镜像生命周期管理的相关命令。现阶段Docker镜像相关的命令存在一些问题，包括：
* 命令间逻辑不一致，比如**列出容器使用的是docker ps**，**列出镜像使用的是docker images**。
* 混用命令导致命令语义不清晰，比如**查看容器和镜像详细信息的命令都是docker inspect**。



# 3.3 Docker image的组织结构

**Docker image是用来启动容器的只读模板**，提供容器**启动所需要的rootfs**，那么Docker是怎么组织这些数据的呢？

## 3.3.1 数据的内容

**Docker image包含着数据及必要的元数据**。**数据由一层层的image layer组成，元数据则是一下JSON文件，用来描述数据（image layer）之间的关系以及容器的一些配置信息**。下面使用overlay存储驱动对Docker image的组织结构进行分析，首先需要启动Docker daemon，命令如下：
![](images/3.3.1.1.png)

这里从官方镜像库下载busybox镜像用在分析。由于前面已经下载过该镜像，所有这里并没有重新下载，而只是做了简单的校验。可以看到Docker对进行进行了完整性校验，这种完整性的凭证是由镜像仓库提供的。
![](images/3.3.1.2.png)

> 该镜像包含cf2616975b4a、6ce2e90b0bc7、8c2e06607696三个layer。让我们先到本地存储路径一探究竟吧。
![](images/3.3.1.3.png)

* 1.总体信息    
从repositories-overlay文件可以看到该存储目录下的所有**image以及其对应的layer ID**。为了减少干扰，实验环境之中只包含一个镜像，其ID为8c2e06607696bd4af，如下。
![](images/3.3.1.4.png)

* 2.数据和元数据  
graph目录和overlay目录包含**本地镜像库中的所有元数据和数据信息**。对于不同的存储驱动，数据的存储位置和存储结构是不同的，本章不做深入的讨论。可以通过下面的命令观察数据和元数据中的具体内容。元数据包含json和layersize两个文件，其中json文件包含了必要的层次和配置信息，layersize文件则包含了该层的大小。
![](images/3.3.1.5.png)
可以看到Docker镜像存储下已经存储了足够的信息，**Docker daemon可以通过这些信息还原出Docker image：先通过repositories-overlay获得image对应的layer ID；再根据layer对应的元数据梳理出image包含的所有层，以及层与层之间的关系；然后使用联合挂载技术还原出容器启动所需要的rootfs和一些基本的配置信息**。

## 3.3.2 数据的组织

从上节看到，**通过repositories-overlay可以找到某个镜像的最上层layer ID，进而找到对应的元数据**，那么元数据都存了那些信息呢？可以通过docker inspect得到该层的元数据。为了简单起见，下面的命令输出中删除了一些讨论无关的层次信息。

注意
>docker insper并不是直接输出磁盘中的元数据文件，而是对元数据文件进行了整理，使其更易读，比如标记镜像创建时间的条目由created改成了Created；标记容器配置的条目由container_config该成了ContainerConfig，但是两者的数据是完全一致的。
![](images/3.3.2.1.png)
![](images/3.3.2.2.png)
![](images/3.3.2.3.png)

对于上面的输出，有几项需要重点说明一下：    
* Id：Image的ID。通过上面的讨论，可以看到image ID**实际上只是最上层的layer ID**，所以docker inspect也适用于任意一层layer。
* Parent：**该layer的父层**，可以递归地获得某个image的所有layer信息。
* Comment：非常**类似于Git的commit message**，可以为该层做一些历史记录，方便其他人理解。
* Container：这个条目比较有意思，其中包含哲学的味道。比如前面提到容器的启动需要以image为**模板**。但又可以把该容器保存为镜像，所以一般来说image的每个layer都保存自一个容器，所以该容器可以说是image layer的“模板”。
* Config：包含了该image的一些配置信息，其中比较重要的是：**“env”容器启动时会作为容器的环境变量；“Cmd”作为容器启动时的默认命令；“Labels”参数可以用于docker images命令过滤**。
* Architecture：该image对应的CPU体系结构。现在Docker官方支持amd64，对其他体系架构的支持也在进行中。

通过这些元数据信息，可以得到某个image包含的所有layer，进而组合出容器的rootfs，再加上元数据中的配置信息（环境变量、启动参数、体系架构等）作为容器启动时的参数。至此已经具备启动容器必需的所有信息。


# 3.4 Docker image扩展知识

Cgroup和Namespace等容器相关技术已经存在很久，在VPS、PaaS等领域也有很广泛的应用，但是直到Docker的出现才真正把这些技术带入到大众的视野。同样，Docker的出现才让我们发现原来可以这样管理镜像，可以这样糅合老技术以适应新的需求。**Docker引入联合挂载技术（Union mount）使镜像分层成为可能；而Git式的管理方式，使基础镜像的重用成为可能**。现在就了解一下相关的技术吧。

## 3.4.1 联合挂载
    
联合文件系统这种思想由来已久，这类文件系统会把多个目录（可能对应不同的文件系统）挂载到同一个目录，对外呈现这些目录的联合。1993年Werner Almsberger实现的“Inhering File System”可以看作是一个开端。但是该项目最终废弃了，而后其他开发者又为Linux社区贡献了unionfs（2003年）、aufs（2006年）和Union mounts（2004年），但都因种种原因未合入社区。直到OverlayFS在2014年合入Linux主线，才结束了Linux主线中无联合文件系统的历史。

这种联合文件系统早期是用在LiveCD领域。在一些发型版中我们可以用LiveCD快速地引导一个系统去初始化或检测磁盘等硬件资源。**之所以速度很快，是因为我们不需要把CD的信息拷贝到磁盘或内存等可读可写的介质中。而只需把CD只读挂载到特定目录**，然后在其上附加一层可读可写的文件层，任何导致文件变动的修改都会被添加到新的文件层内。这就是**写时复制**（copy-on-write）的概念。

































