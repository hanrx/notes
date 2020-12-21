# nginx配置信息

``` 
#================================以下是全局配置项
#指定运行nginx的用户和用户组，默认情况下该选项关闭（关闭的情况就是nobody）
#user  nobody nobody;
#运行nginx的进程数量，后文详细讲解
worker_processes  1;
#nginx运行错误的日志存放位置。当然您还可以指定错误级别
#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;
#指定主进程id文件的存放位置，虽然worker_processes != 1的情况下，会有很多进程，管理进程只有一个
#pid        logs/nginx.pid;

events {
#每一个进程可同时建立的连接数量，后问详细讲解
worker_connections  1024;   
#连接规则，可以采用[kqueue rtsig epoll select poll eventport ]，后文详细讲解
use   epoll;    
}
#================================以上是全局配置项

http {
#================================以下是Nginx后端服务配置项
upstream backendserver1 {
#nginx向后端服务器分配请求任务的方式，默认为轮询；如果指定了ip_hash，就是hash算法（上文介绍的算法内容）
#ip_hash    
#后端服务器 ip:port ，如果有多个服务节点，这里就配置多个
server 192.168.220.131:8080;
server 192.168.220.132:8080;    
#backup表示，这个是一个备份节点，只有当所有节点失效后，nginx才会往这个节点分配请求任务
#server 192.168.220.133:8080 backup;        
#weight，固定权重，还记得我们上文提到的加权轮询方式吧。
#server 192.168.220.134:8080 weight=100;    
}
#================================以上是Nginx后端服务配置项

    #=================================================以下是 http 协议主配置
    #安装nginx后，在conf目录下除了nginx.conf主配置文件以外，有很多模板配置文件，这里就是导入这些模板文件
    include       mime.types;
    #HTTP核心模块指令，这里设定默认类型为二进制流，也就是当文件类型未定义时使用这种方式
    default_type  application/octet-stream;     
    #日志格式
    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '   
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';
 
    #日志文件存放的位置
    #access_log  logs/access.log  main;         
 
    #sendfile 规则开启
    sendfile        on;
    #指定一个连接的等待时间（单位秒），如果超过等待时间，连接就会断掉。注意一定要设置，否则高并发情况下会产生性能问题。
    keepalive_timeout  65;                      
 
    #开启数据压缩，后文详细介绍
    gzip  on;                                   
    #=================================================以上是 http 协议主配置
 
    #=================================================以下是一个服务实例的配置
    server {
        #这个代理实例的监听端口
        listen       80;
        #server_name 取个唯一的实例名都要想半天？
        server_name  localhost; 
        #文字格式
        charset utf-8;  
        #access_log  logs/host.access.log  main;    
 
        #location将按照规则分流满足条件的URL。"location /"您可以理解为“默认分流位置”。
        location / {
            #root目录，这个html表示nginx主安装目录下的“html”目录。
            root   html;   
            #目录中的默认展示页面
            index  index.html index.htm;        
        }
 
        #location支持正则表达式，“~” 表示匹配正则表达式。
        location ~ ^/business/ {   
            #方向代理。后文详细讲解。
            proxy_pass http://backendserver1;   
        }
 
        #redirect server error pages to the static page /50x.html
        #error_page  404              /404.html;
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }
    }
    #=================================================以上是一个服务实例的配置
}
``` 































