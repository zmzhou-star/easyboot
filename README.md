# easyboot

#### 介绍
spring boot、spring security、jwt token、redis、jpa + vue-admin-template实现前后端分离简单入门框架

#数据库文档生成工具
`src\test\java\com\github\zmzhou\easyboot\DBGenerationTool.java`    
参考：[screw](https://gitee.com/leshalv/screw/tree/master/)

[网站地址](https://www.zmzhou-star.cn)

[Java全栈开发学习笔记](https://zmzhou-star.gitee.io/learnotes/#/%E5%8D%8E%E4%B8%BA%E6%8B%9B%E8%81%98/README)

[数据库设计文档](/docs/EasyBoot数据库设计文档.md ':include :type=markdown')

#sonar扫描命令
```bash
mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent -Dmaven.test.failure.ignore=true install sonar:sonar -X
```
#### 软件架构
软件架构说明  
spring boot、spring security、jwt token、redis、jpa + vue-admin-template、flyway
#### 系统截图

![登录页面](docs/login.png)
![首页](docs/dashboard.png)
![角色管理](docs/role.png)
![swagger+knife4j接口文档](docs/doc.png)

![sonar扫描报告](docs/sonar.png)

#### 安装教程
1. `打包`
```
mvn clean install -X -DskipTests
mvn clean install -X -Dmaven.test.skip=true
```
2. `运行（Windows或Linux）`
```
java -jar -server easyboot-0.0.1-SNAPSHOT.jar
```
当前ssh窗口被锁定，可按CTRL + C打断程序运行，或直接关闭窗口，程序退出。

3. `运行（Linux后台运行）`
```
nohup java -jar -server easyboot-0.0.1-SNAPSHOT.jar > easyboot.out 2>&1 &
```
nohup 意思是不挂断运行命令,当账户退出或终端关闭时,程序仍然运行。
&代表在后台运行

#### 使用说明
```
1. @EnableCaching常用注解说明
@Cacheable     触发缓存填充（查询）
@CachePut      更新缓存而不会干扰方法执行（更新）
@CacheEvict    触发缓存驱逐（删除）
@Caching       重新组合要在方法上应用的多个缓存操作
@CacheConfig   在类级别共享一些常见的缓存相关设置
```

#### License
[The Apache-2.0 License](https://www.apache.org/licenses/LICENSE-2.0)

请自由地享受和参与开源

#### 捐赠
开源不易，请多鼓励！（注：如果该项目对您有帮助，请捐赠以表示支持，谢谢！捐赠请备注easyboot捐赠和称呼哦，谢谢！）

| 支付宝 | 微信 |
| :------------: | :------------: |
| ![Alipay](https://gitee.com/zmzhou-star/learnotes/raw/master/docs/alipay.png) | ![Wechat](https://gitee.com/zmzhou-star/learnotes/raw/master/docs/wechatpay.png) |

#### 联系作者
email：<a href="mailto:zmzhou-star@foxmail.com">Contact zmzhou-star</a>

微信公众号：![微信公众号](https://gitee.com/zmzhou-star/learnotes/raw/master/docs/wechat-zmzhou-star.png)

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
