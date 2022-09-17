#!/bin/bash
# 定义常量
time=date +'%Y%m%d-%H%M%S'
sourceCodePath=/opt/code/easyboot
webSourceCodePath=/opt/code/easyboot/vue-easyboot/
deployPath=/opt/eboot
bakPath=$deployPath/eboot_bak/$time
webUrl=/usr/local/nginx/html
# 拉取最新代码
git pull

# 打包后端
cd $sourceCodePath
mvn clean install -X -Dmaven.test.skip=true -f $sourceCodePath/pom.xml -P prod

#备份旧包
mkdir $bakPath
echo "mkdir $bakPath"
cd $deployPath
mv lib/ easyboot-1.0.jar $bakPath

# 部署后端
cp -r $sourceCodePath/target/easyboot-1.0.jar $sourceCodePath/target/lib $deployPath
chown -R appuser:appuser $deployPath
su appuser $deployPath/restart.sh; echo "easyboot restart!";

# 打包前端
cd $webSourceCodePath
npm install --registry=https://registry.npm.taobao.org
# 构建生产环境
npm run build:prod

# 部署前端
cp -r dist/* $webUrl
