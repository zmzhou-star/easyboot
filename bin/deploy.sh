#!/bin/bash
# 第一个参数是目标部署机器IP地址
targetIp=$1
chmod +x bin/*.sh
scp bin/startup.sh root@"$targetIp":/root/startup.sh;
scp target/easyboot*.jar root@"$targetIp":/root;

#打包vue-easyboot
cd vue-easyboot
npm install --registry=https://registry.npm.taobao.org
#构建生产环境
npm run build:prod
#打包压缩
tar -zcvf dist.tar.gz ./dist/
#传输
scp dist.tar.gz root@"$targetIp":/root;

ssh root@"$targetIp" "/root/startup.sh"; sleep 1 ;
