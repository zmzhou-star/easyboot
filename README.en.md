# easyboot

#### Description
spring boot、spring security、jwt token、redis、jpa + vue-admin-template A simple entry framework for separating front and back ends

#database document generation tool
`src\test\java\com\github\zmzhou\easyboot\DBGenerationTool.java`    
reference：[screw](https://gitee.com/leshalv/screw/tree/master/)

[website address](https://www.zmzhou-star.cn)

[Java learnotes](https://zmzhou-star.gitee.io/learnotes/#/%E5%8D%8E%E4%B8%BA%E6%8B%9B%E8%81%98/README)

[database](/docs/EasyBoot数据库设计文档.md ':include :type=markdown')

#sonar scan command
```bash
mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent -Dmaven.test.failure.ignore=true install sonar:sonar -X
```
#### Software Architecture
Software architecture description

spring boot、spring security、jwt token、redis、jpa + vue-admin-template、flyway
#### system screenshot

![login page](docs/login.png)
![dashboard](docs/dashboard.png)
![role management](docs/role.png)
![swagger+knife4j Interface Documentation](docs/doc.png)

![sonar scan report](docs/sonar.png)

#### Installation
1. `package`
```
mvn clean install -X -DskipTests
```
2. `run（Windows or Linux）`
```
java -jar -server easyboot-0.0.1-SNAPSHOT.jar
```
The current ssh window is locked, you can press CTRL + C to interrupt the program running, or directly close the window, the program exits.
3. `run（Linux daemon）`
```
nohup java -jar -server easyboot-0.0.1-SNAPSHOT.jar > easyboot.out 2>&1 &
```
nohup It means to run the command without hanging up, and the program will still run when the account is exited or the terminal is closed.
&represents running in the background


#### License
[The Apache-2.0 License](http://www.apache.org/licenses/LICENSE-2.0)

Please feel free to enjoy and participate in open source

#### Donate
Open source is not easy, please encourage! (Note: If this project is helpful to you, please donate to show your support, thank you! Please note the web-shell donation and title for donation, thank you!)

| Alipay  | Wechat  |
| :------------: | :------------: |
| ![Alipay](https://gitee.com/zmzhou-star/learnotes/raw/master/docs/alipay.png) | ![Wechat](https://gitee.com/zmzhou-star/learnotes/raw/master/docs/wechatpay.png) |

#### contact me
email：<a href="mailto:zmzhou-star@foxmail.com">Contact zmzhou-star</a>

WeChat：![WeChat](https://gitee.com/zmzhou-star/learnotes/raw/master/docs/wechat-zmzhou-star.png)

#### Contribution

1.  Fork the repository
2.  Create Feat_xxx branch
3.  Commit your code
4.  Create Pull Request


#### Gitee Feature

1.  You can use Readme\_XXX.md to support different languages, such as Readme\_en.md, Readme\_zh.md
2.  Gitee blog [blog.gitee.com](https://blog.gitee.com)
3.  Explore open source project [https://gitee.com/explore](https://gitee.com/explore)
4.  The most valuable open source project [GVP](https://gitee.com/gvp)
5.  The manual of Gitee [https://gitee.com/help](https://gitee.com/help)
6.  The most popular members  [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
