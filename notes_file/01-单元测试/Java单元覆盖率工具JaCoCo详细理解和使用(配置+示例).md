jacoco官网：https://www.eclemma.org/jacoco/

https://blog.csdn.net/skh2015java/article/details/121775806

On the fly 模式：https://www.cnblogs.com/KevinFeng/p/15570436.html

一、下载安装jacoco
jacoco安装包下载地址： https://www.eclemma.org/jacoco/index.html

解压后，得到Lib包下 jacocoagent.jar和jacococli.jar两个文件。将包放到与src目录同级。

二、使用on-the-fly模式运行时插桩
java  -javaagent:jacocoagent.jar=includes=*,output=tcpserver,port=17297,address=127.0.0.1,append=true -jar ./fruit-0.0.1-SNAPSHOT.jar

-javaagent    jacocoagent.jar的地址，jacoco解压包的地址
includes       对需要插桩的包进行过滤，*表示对所有的class都要进行插桩
output          启动项目的两种方案，file或tcpserver,一般使用tcpserver
port              jacoco开启的tcpserver的端口，dump也用这个端口
address       jacoco对外开放的tcpserver的访问地址，地址为127.0.0.1的时候，只能再这个服务器上进行dump,
                    地址为实际的ip地址时，可以在任意的设备上进行数据的dump
append        默认为true,执行dump时,默认在原文件末尾进行追加，改为false时，在执行dump时会直接覆盖源文件
-jar              指向被执行的jar包地址


三、生成数据覆盖率exec文件
java -jar ./jacoco-0.8.7/lib/jacococli.jar dump --address 127.0.0.1 --port 17297 --destfile ./report/res.exec

-jar             jacococli.jar的地址
dump         生成覆盖率文件的命令
--address   jacoco启动项目的服务器地址
--port         jacoco启动项目的服务器端口
--destfile    生成exec文件的路径

四、生成html以及xml报告
java -jar ./jacoco-0.8.7/lib/jacococli.jar report ./report/res.exec --classfiles ./classes/com/ffy --sourcefiles ../src/main/java --html ./html-report

report             生成报告的命令，指向报告exec文件的地址
--classfiles     项目打包时生成的class文件的地址
--sourcefiles  项目源码的路径
--html            生成html报告的地址
--xml             生成xml报告的地址