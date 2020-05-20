# Learn Java
this git record my first spring project

# 资料
[Spring 文档](https://spring.io/guides)   
[Spring Web](https://spring.io/guides/gs/serving-web-content/)  
[es](https://elasticsearch.cn/)  
[bootstrap](https://elasticsearch.cn/)  
[Github Oauth](https://developer.github.com/apps/)  

# 工具
[git]()  
[Visual Paradiam]()  

# 脚本
```sql
create table USER
(
    ID           INT auto_increment,
    ACCOUNT_ID   VARCHAR(100),
    NAME         VARCHAR(50),
    TOKEN        VARCHAR(36),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
    constraint USER_PK
        primary key (ID)
);
```