###2019.11.23

####1、连接数据库时出现错误：
```text
There was an unexpected error (type=Internal Server Error, status=500).
nested exception is org.apache.ibatis.exceptions.PersistenceException: 
### Error updating database. Cause: org.springframework.jdbc.CannotGetJdbcConnectionException: 
Failed to obtain JDBC Connection; nested exception is java.sql.SQLException: 
The server time zone value '�й���׼ʱ��' is unrecognized or represents more than one time zone. You must configure either the server or JDBC driver (via the serverTimezone configuration property) to use a more specifc time zone value if you want to utilize time zone support. 
```
**原因：**
MySQL驱动（Driver）版本问题：8.*与5.*不兼容。

**解决：**
```xml
 <!-- Maven引入 -->
 <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.*.*</version>
    </dependency>
```

###2019.11.23

####Flyway:
  Version control for your database.数据库版本管理工具。方便多人协作时管理数据库版本一致问题。
  官网：https://flywaydb.org/
  
  ####错误1：
  ```text
java.lang.NoSuchMethodError: 
        life.east.community.dto.GithubUserDTO$$M$_jr_6F1DE4C7E0EF58A8_2.getAvat
```
 #####原因：
 
   修改实体类属性时，“编译器没反应过来”
 
 #####解决：
   重新编写相关调用代码。
   
 ###2019.11.25
 
 ####问题1：
 ```text
org.apache.ibatis.exceptions.TooManyResultsException: Expected one result (or null) to be returned by selectOne(), but found: 5
```
#####原因：
  ‘selectOne()’查询方法中得到不止一个结果（but found: 5，5个）,但只用了一个实体去接收结果集，程序不知道如何去分配。

#####解决：
  使用集合等去接受结果，或者逻辑不对需改变逻辑或查询方法。
  
####问题2：
  使用mybatis从数据库查询时某些字段值是正确的，某些却为null。

#####分析：
  mybatis没有开启驼峰命名规则映射.
 ```properties
#SpringBoot开启mybatis的驼峰命名规则映射！！！！！！！！！！！
mybatis.configuration.map-underscore-to-camel-case=true
```

####问题3：拦截器排除拦截带有请求参数的路径需配置一定规则才会匹配放行：
```java
@Override
public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(mySessionInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/","/index.html","/callback**");//注意双星（**）
//如http://localhost:8080/callback?code=14bb19006a134ae4a7df&state=1
}
```

####问题4：
```text
org.springframework.expression.spel.SpelEvaluationException: EL1011E: Method call: Attempted to call method getAvatarUrl() on null context object
```

#####分析：
  错误调用定位到某html页面中的调用：${question.user.getAvatarUrl()}，此时user不存在。问题出在数据库数据混乱，检查并改正之前调试遗留的数据库数据有误问题。
 
####Maven命令使用MyBatis Generator:
```bash
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```

###2019.11.26

####`未解决的问题：`       
   每次重启登录态消失
   
 #####原因：拦截器没有拦截主页，所以没有添加登录态