
# [README](../README.md "回到 README")

本附录主要内容
* 列出运行本书中的代码所需的软件
* 从GitHub上下载每章的源代码
* 使用Maven编译和打包源代码
* 构建和提供每章使用的Docker镜像
* 使用Docker Compose启动由构建编译的Docker镜像


在编写本书中的代码示例和选择部署代码所需的运行时技术时，我有两个目标。第一个目标是确保代码示例易于使用并且易于设置。请记住，一个微服务应用程序有多个移动部件，如果没有一些深谋远虑的话，要建立这些部件来用最小的工作量顺畅运行微服务可能会很困难。

第二个目标是**让每一章都是完全独立的**，这样读者就可以选择书中的任何一章，并拥有一个完整的运行时环境，它封装了运行这一章中的代码示例所需的所有服务和软件，而不依赖于其他章。


为此，在本书的每一章中都会用到下列技术和模式。
（1）所有项目都是要**Apache Maven作为这一章的构建工具**。每个服务都是使用Maven项目结构构建的，每个服务的结构都是按章组织的。
（2）这一章中**开发的所有服务都编译为Docker容器镜像**。Docker是一个非常出色的运行时虚拟化引擎，它能够运行在Windows、OS X和Linux上。使用Docker，我可以在桌面上构建一个完整的运行时环境，包括应用程序服务和支持这些服务所需的所有基础设施。此外，Docker不像其他专有的虚拟化技术，**Docker可以轻松跨多个云供应商进行移植**。
我**使用Spotify和Docker Maven插件将Docker容器的构建与Maven构建过程集成在一起**。
（3）为了在编译成Docker镜像之后启动这些服务，我使用Docker Compose以一个组来启动这些服务。我有意避免使用更复杂的Docker编排工具，如Kubernetes或Mesos，以保持各章示例简单且可移植。
    
    所有Docker镜像的提供都是通过简单的shell脚本完成的。

A.1 所需的软件
    要为所有章构建软件，读者需要在桌面上安装下列软件。注意，这些是我为本书所用的软件的版本。这些软件的其他版本可能也能运行，但以下软件是我用来构建本书代码的。
    （1）Apache Maven——我使用的是Maven的3.3.9版本。我之所以选择Maven，是因为虽然其他构建工具（如Gradle）非常流行，但Maven仍然是Java生态系统中使用的主要构建工具。本书中所有代码示例都是使用Java 1.8版进行编译的。
    （2）Docker——我在本书中使用Docker的1.12版本构建代码示例。本书中的代码示例可以用早期版本Docker处理，但是如果读者想在Docker的早期版本中使用这些代码，可能必须切换到版本1的Docker-compose链接格式。
    （3）Git客户端——本书的所有源代码都存储在一个GitHub存储库中。在本书中，我使用了Git客户端的1.8.4版本。

    我不打算一一介绍如何安装这些组件。上面列出的每个软件包都有简单的安装指导，它们应该很容易安装。Docker有一个用于安装GUI客户端。


A.2 从GitHub下载项目
    本书的所有源代码都在我的GitHub存储库（http://github.com/carnellj）中。本书中的每一章都有自己的源代码存储库。下面是本书中使用的所有GitHub存储库的清单。
	* 
第1章——http://github.com/carnellj/spmiachapter1。
	* 
第2章——http://github.com/carnellj/spmiachapter2。
	* 
第3章——http://github.com/carnellj/spmiachapter3和http://github.com/carnellj/configrepo。
	* 
第4章——http://github.com/carnellj/spmiachapter4。
	* 
第5章——http://github.com/carnellj/spmiachapter5。
	* 
第6章——http://github.com/carnellj/spmiachapter6。
	* 
第7章——http://github.com/carnellj/spmiachapter7。
	* 
第8章——http://github.com/carnellj/spmiachapter8。
	* 
第9章——http://github.com/carnellj/spmiachapter9。
	* 
第10章——http://github.com/carnellj/spmiachapter10和http://github.com/carnellj/chapter10platformtests。


    通过GitHub，读者可以使用Web UI将文件作为zip文件进行下载。每个GitHub存储库都会有一个下载按钮。图A-1展示了下载按钮在第1章的GitHub存储库中的位置。

    如果读者是一名命令行用户，那么可以安装git客户端并克隆项目。例如，如果用户想使用git客户端从GitHub下载第1章，则可以打开一个命令行并发出以下命令：

    这将把第1章的所有项目文件下载到一个名为spmia-chapter1的目录中，该目录位于运行git命令的目录中。


A.3 剖析每一章
    本书中的每一章都有一个或多个与之相关联的服务。各章中的每个服务都有自己的项目目录。例如，如果读者查看第6章，会发现里面有以下7个服务。
    （1）confsvr——Spring Cloud Config服务器。
    （2）eurakasvr——使用Eureka的Spring Cloud服务。
    （3）licensing-service——EagleEye的许可证服务。
    （4）organization-service——EagleEye的组织服务。
    （5）orgservice-new——EagleEye的组织服务的新测试版本。
    （6）specialroutes-service——A/B路由服务。
    （7）zuulsvr——EagleEye的Zuul服务。
    每一章的每个服务目录都是作为基于Maven的构建项目组织的。每个项目里面都有一个src/main目录，其中包含以下子目录。
    （1）java——这个目录包含用于构建服务的Java源代码。
    （2）docker——这个目录包含两个文件，用于为每个服务构建一个Docker镜像。第一个文件总是被称为Dockerfile，它包含Docker用来构建Docker镜像的步骤指导。第二个文件run.sh是一个在Docker容器内部运行的自定义Bash脚本。此脚本确保服务在某些关键依赖性（如数据库已启动并正在运行）可用之前不会启动。
    （3）resources——resources目录包含所有服务的application.yml文件。虽然应用程序配置存储在Spring Cloud Config中，但所有服务都在application.yml中拥有本地存储配置。此外，resources目录还包含一个schema.sql文件，它包含所有SQL命令，用于创建表以及将这些服务的数据预加载到Postgres数据库中。


A.4 构建和编译项目
    因为本书中的所有章都遵循相同的结构，并使用Maven作为构建工具，所以构建源代码变得非常简单。每一章都在目录的根目录中有一个pom.xml，作为所有子章节的父pom。如果读者想要编译源代码并在单个章节中为所有项目构建Docker镜像，则需要在这一章的根目录下运行mvn clean package docker:build命令。
    这将在每个服务目录中执行Maven pom.xml文件，并在本地构建Docker镜像。
    如果读者要在这一章中构建单个服务，则可以切换到特定的服务目录，然后再运行mvn clean package docker:build命令。


A.5 构建Docker镜像
    在构建过程中，本书中的所有服务都被打包为Docker镜像。这个过程由Spotify Maven插件执行。有关此插件的实战示例，读者可以查看第3章许可证服务的pom.xml文件（chapter3/licensing-service）。代码清单A-1展示了在每个服务的pom.xml文件中配置此插件的XML片段。


    这个XML片段做了以下3件事。
    （1）它将服务的可执行jar和src/main/docker目录的内容复制到targer/docker。
    （2）它执行target/docker目录中定义的Dockerfile。Dockerfile是一个命令列表，每当为该服务提供新Docker镜像时，就会执行这些命令。
    （3）它将Docker镜像推送到安装Docker时安装的本地Docker镜像库。

    代码清单A-2展示了许可证服务的Dockerfile的内容。

    在代码清单A-2所示的Dockerfile中，将使用Alpine Linux提供实例。Alpine Linux是一个小型Linux发型版，常用来构建Docker镜像。正在使用的Alpine Linux镜像已经安装了Java JDK。
    在提供Docker镜像时，将安装名为nc的命令行实用程序。nc命令用于ping服务器并查看特定的端口是否在网络上可用。该命令将在run.sh命令脚本中使用，以确保在启动服务之前，所有依赖的服务（如数据库和Spring Cloud Config服务）都已启动。nc命令通过监视依赖的服务监听的端口来做到这一点。nc的安装是通过RUN apk update && apk upgrade && apk add netcat-openbsd完成的，使用Docker Compose运行服务。

    接下来，Dockerfile将为许可证服务的可执行JAR文件创建一个目录，然后将jar文件从本地文件系统复制到在Docker镜像上创建的目录中。这都是通过ADD licensing-service-0.0.1-SNAPSHOT.jar /user/local/licensingservice/完成的。
    配置过程的下一步是通过ADD命令安装run.sh脚本。run.sh脚本是我写的一个自定义脚本，它用于在启动Docker镜像时启动目标服务。该脚本使用nc命令来监听许可证服务所需的所有关键服务依赖项的端口，然后阻塞许可证服务，直到这些依赖项都已启动。
    代码清单A-3展示了如何使用run.sh来启动许可证服务。


    一旦将run.sh命令复制到许可证服务的Docker镜像，Docker命令CMD./run.sh用于告知Docker在实际镜像启动时执行run.sh启动脚本。



A.6 使用Docker Compose启动服务
    Maven构建完成后，现在就可以使用Docker Compose来启动对应章的所有服务了。Docker Compose作为Docker安装过程的一部分安装。Docker Compose是一个服务编排工具，它允许开发人员将服务定义为一个组，然后作为一个单元一起启动。Docker Compose还拥有为每个服务定义环境变量的功能。
    Docker Compose是YAML文件来定义将要启动的服务。本书的每一章中都有一个名为“<<chapter>>/docker/common/docker-compose.yml”的文件。该文件包含在这一章中启动服务的服务定义。让我们来看一下第3章中使用的docker-compose.yml文件。代码清单A-4展示了这个文件的内容。


    在代码清单A-4所示的docker-compose.yml中，我们看到定义了3个服务（configserver、database和licensingservice）。每个服务都有一个使用image标签定义的Docker镜像。当每个服务启动时，它将通过port标签公开端口，然后通过environment标签将环境变量传递到启动的Docker容器。
    接下来，在从GitHub拉取的章目录的根目录执行以下命令来启动Docker容器：

    当这个命令发出时，docker-compose启动docker-compose.yml文件中定义的所有服务。每个服务将打印其标准输出到控制台。图A-2展示了第3章中docker-compose.yml文件的输出。

    所有在本书中使用的Docker容器都是暂时的——它们在启动和停止时不会保留它们的状态。如果读者开始运行代码，那么在重启容器之后数据会丢失，请牢记这一点。如果读者想让自己的Postgres数据库在容器的启动和停止之间保持持久性，建议查阅Postgres Docker的资料。









































# [README](../README.md "回到 README")
