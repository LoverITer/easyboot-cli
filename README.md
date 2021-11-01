Spring Boot 多模块脚手架
======== 
这是一个基于Spring Boot搭建多模块脚手架工程，抽取了日常开发常用的目录结构、各种配置以及mvn依赖，使用项目模板可以节约创建一个工程需要耗费大量的时间，并且由于统一化的定制结构，对于管理项目代码风格一致，这是一个非常有效地手段。

1、说明
------------
> 这是一个基于Spring Boot搭建多模块脚手架工程，抽取了日常开发常用的目录结构、各种配置以及mvn依赖，使用项目模板可以节约创建一个工程需要耗费大量的时间，并且由于统一化的定制结构，对于管理项目代码风格一致，这是一个非常有效地手段。

 
 
2、脚手架结构 
------------
```text
easyblog-cli
├── easyblog-common
     ├── bean      对外交互的bean
     ├── constant 系统所使用的的任何常量
     ├── enums    枚举类
     ├── request  请求参数封装类
     ├── response  响应参数封装类
     └── util      常用工具类
├── easyblog-core
     ├── dao   DAO
          ├── mapper   mapper 接口
          └── model    领域模型实体
     ├── service 系统核心服务
     └── exception    自定义错误
├── easyblog-web
     ├── aspect       各种机遇AOP机制的配置、工具
     ├── config       系统的各种配置
     ├── controller   web服务控制层
     ├── handler      handler配置
     ├── schedule     系统定时任务
     └──  log         系统请求日志记录
```


3、脚手架的使用
------

##### 5.1 环境搭建

* 安装JDK 8或者更高的版本,程序中用到了java 8中的函数式编程的一些东西
* 安装MySQL,SQL文件在项目的根目录下,可以直接导入MySQL服务器执行
* 安装Maven(3.6版本以上),安装Redis
* 修改配置文件。application-dev.yml和application-pro.yml中的数据库配置需要变成自己的配置。前者是开发环境，后者是生产环境下的配置，想要那个环境起作用就在application的spring.profiles.active指定（dev或pro）

##### 5.2 拉取代码并构建应用
从这里拉取代码到你本地，使用`IntelliJ IDEA`打开项目，可以直接使用Maven打成war包，然后部署到Tomcat的webapps目录下（最好将war包的名字改为ROOT.war），这样就完成了部署

也可以使用Docker容器化部署：[详情点击这里](https://www.easyblog.top/article/details/211)


6、联系方式
------
QQ: 2489868503

Email: huangxin981230@163.com


版权
-------
该项目遵循 Apache 2.0 license.

