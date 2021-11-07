easyboot-cli
========
easyboot-cli是一个基于SpringBoot搭建多模块脚手架，抽取了日常开发常用的目录结构、各种配置以及mvn依赖，使用脚手架模板可以节省大量在创建工程时耗费的时间，并且由于统一化的定制结构，对于管理项目代码风格一致，这是一个非常有效地手段。

1、快速开始 Getting Start
------------
easyboot-cli是一个基于SpringBoot搭建多模块脚手架工程，抽取了日常开发常用的目录结构、
各种配置以及mvn依赖，使用脚手架模板可以节约大量花费在新建工程上的时间，从而聚焦于业务设计和实现，并且由于统一化的定制结构，
对于管理项目代码风格一致，这是一个非常有效地手段。 脚手架特性：
1. 基于自定义注解 `ResponseWrapper` 自动封装Restful API响应体，无需在再代码中手动封装返回响应对象
2. 整合Logback，提供全量路追踪**请求id**和**事物id**日志
3. API接口签名验证保护接口不再“裸奔”

<br/>
easyboot-cli is a multi-module scaffolding project based on SpringBoot, which extracts the directory structure commonly used in daily development,
Various configurations and mvn dependencies, the use of scaffolding templates can save a lot of time spent on new projects, thus focusing on business design and implementation, and due to the unified customized structure,
This is a very effective method for managing project code style consistency. Below are some features of the scaffolding:

1. Automatically encapsulate the Restful API response body based on the custom annotation `ResponseWrapper`, no need to manually encapsulate the returned response object in the code
2. Integrate Logback to provide full track tracking **request id** and **transaction id** logs
3. API interface signature verification protection interface is no longer "streaking"
 
2、脚手架结构  Structure
------------
```puml
easyboot-cli
├── easyboot-common
     ├── bean          对外交互的bean
     ├── constant      系统所使用的的任何常量
     ├── enums         枚举类
     ├── request       请求参数封装类
     ├── response      响应参数封装类
     └── util          常用工具类
├── easyboot-core
     ├── dao           DAO
          ├── mapper   mapper接口
          └── model    领域模型实体
     ├── service       系统核心服务
     └── exception     自定义业务异常
├── easyboot-web
     ├── aspect       各种基于AOP机制的配置、工具
     ├── config       系统的各种配置
     ├── controller   web服务控制层
     ├── handler      全局异常处理、拦截器等handler
     ├── schedule     系统定时任务
     └──  log         系统请求日志记录管理
```


3、脚手架的使用  Using EasyBoot-cli
------

##### 3.1 环境搭建

* 安装JDK 8或者更高的版本,程序中用到了java 8中的函数式编程的一些东西
* 安装MySQL,SQL文件在项目的根目录下,可以直接导入MySQL服务器执行
* 安装Maven(3.6版本以上),安装Redis
* 修改配置文件。application-dev.yml和application-pro.yml中的数据库配置需要变成自己的配置。前者是开发环境，后者是生产环境下的配置，想要那个环境起作用就在application的spring.profiles.active指定（dev或pro）

##### 3.2 拉取代码并构建应用
从这里拉取代码到你本地，使用`IntelliJ IDEA`打开项目，可以直接使用Maven打成war包，然后部署到Tomcat的webapps目录下（最好将war包的名字改为ROOT.war），这样就完成了部署

也可以使用Docker容器化部署：[详情点击这里](https://www.easyboot.top/article/details/211)



4、技术栈 Built With
------
* [SpringBoot 2.4.12](https://docs.spring.io/spring-boot/docs/2.4.12/reference/html/index.html) - Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications.
* 

5、添砖加瓦 Contributing
-----
easyboot-cli的源码分为两类分支，功能如下：

| 分支      | 作用                                                         |
| --------- | ------------------------------------------------------------ |
| master | 主分支，release版本使用的分支，与中央库提交的一致，不接收任何pr或修改 |
| {feat}-{date}-{name}    | 自定义开发分支，接受修改或pr，分支命名规范：`{feat}` 为提交修改的主要特性; `date` 为修改日期; `name` 为你的名字,可以全称也可以缩写，比如 `sign-210912-hx` 表示hx小伙伴再2021年9月12日提交了一个验签相关的功能        |

<br/>
The source code of easyboot-cli is divided into two types of branches, with the following functions:

| branch      | function                                                         |
| --------- | ------------------------------------------------------------ |
| master | The main branch, the branch used by the release version, is consistent with the submission of the central library, and does not receive any pr or modification |
| {feat}-{date}-{name}    | Custom development branch, accept modification or pr, branch naming convention: `{feat}` is the main feature of the submitted modification; `date` is the modification date; `name` is your name, which can be full or abbreviated, such as `sign-210912-hx` means that the hx partner submitted a verification related function on September 12, 2021       |


5、开发者 Authors
------
* **frank.huang**  QQ: 2489868503  Email: huangxin981230@163.com


6、版权  License
-------
该项目遵循 Apache 2.0 license.

This project is licensed under the Apache 2.0 License - see the [LICENSE.md](LICENSE.md) file for details

