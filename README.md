# 基于spring-cloud-config-server的扩展

# 简述
由于原生的`spring-cloud-config-server`只支持单一的**application-profile-label**加载，对多个配置文件加载不够灵活，该项目可以通过添加**配置关联**，方便配置基于数据库的多个配置文件的加载。当多个配置key重合时，依赖最近的配置优先级最高。进而达到**配置覆盖**的功能。

# config-server
新建springcloud项目，添加如下依赖
```
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.3</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.49</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>
```
配置文件如下：
```
server.port=8888
spring.profiles.active=jdbc
spring.datasource.hikari.connection-timeout=5000
spring.datasource.hikari.maximum-pool-size=10
spring.cloud.config.server.jdbc.sql= SELECT key_, value_ FROM properties WHERE application_=? and profile_=? and label_=?
#spring.cloud.config.server.jdbc.order=1
spring.datasource.url=jdbc:mysql://localhost:3306/config?useSSL=false&charset=utf8
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=root

mybatis.mapper-locations=mapper/*.xml
```
其中数据库config在resources路径下的config.sql文件

编写类`RelationalJdbcEnvironmentRepository`，重写`findOne`方法，该方法就是关联多个配置文件的实现。

启动项目，测试`PropertiesController`中的`post`方法，可以模拟前端页面，添加、修改配置。

# config-client 测试
新建springcloud项目，添加如下依赖
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
```
application.properties配置文件如下:
```
spring.application.name=app-1
spring.cloud.config.label=label2
spring.cloud.config.profile=test
key2=clientvalue1
#key1[0]=key10
#key1[1]=key12
```
表示，请求**加载服务器上名为app-1，label为label2，profile为test的配置**，由于服务器上面已经整合了多配置关联，故，客户端也可实现多配置。

启动项目，测试`TestController`中的`test`方法，可测试，多配置关联，配置覆盖。
