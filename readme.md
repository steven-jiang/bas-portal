发布指南

#配置

1. 环境配置

com/kii/bas/config/bas.config.*[profile]*.properties

store.mongoDB.connectUrl=*[full mongoDB connect url string]* 
store.mongoDB.dbName=*[mongoDB name]* 

对应后文不同profile设置，编辑不同名称的properties文件，指定对应的mongoDB连接

2. log配置

com/kii/bas/config/log4j.*[profile]*.xml

默认的log文件生成位置为/data/log/bas/bas-portal.log
可自行编辑

#构建

运行*[prj_path]*/.gradlew war

在*[prj_path]*/bas-web-portal/build/libs 生成bas-portal.war

#运行
1. 保证本地存在jetty-runner应用
2. 运行 java -Dspring.profile=*[profile]* -jar  *[jetty-runner path]*  --port *[port_no]*  *[prj_path]*/bas-web-portal/jetty/webConfig.xml

其中

* *profile*:应用环境变量，匹配对应的配置文件

* *jetty-runner-path*：jetty-runner.jar的安装路径

* *port_no*：web应用服务端口号，任意指定

* *prj_path*：项目所在文件夹

#验证运行状态

GET http://localhost:*[port_no]*/bas-portal/api/info






