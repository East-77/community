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