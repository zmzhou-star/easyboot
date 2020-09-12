# easyboot

#### 介绍
spring boot、spring security、jwt token、redis、jpa + vue-admin-template实现前后端分离简单入门框架

#数据库文档生成工具
src\test\java\com\github\zmzhou\easyboot\DBGenerationTool.java  
参考：[screw](https://gitee.com/leshalv/screw/tree/master/)

#sonar扫描命令
```bash
mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent -Dmaven.test.failure.ignore=true install sonar:sonar -X
```
#### 软件架构
软件架构说明


#### 安装教程

1.  xxxx
2.  xxxx
3.  xxxx

#### 使用说明

1. @EnableCaching常用注解说明
@Cacheable     触发缓存填充（查询）
@CachePut      更新缓存而不会干扰方法执行（更新）
@CacheEvict    触发缓存驱逐（删除）
@Caching       重新组合要在方法上应用的多个缓存操作
@CacheConfig   在类级别共享一些常见的缓存相关设置

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 码云特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5.  码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
