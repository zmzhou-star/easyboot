#!/bin/bash

path=/opt/eboot
name=easyboot-0.0.1-SNAPSHOT.jar
#cur_date="`date +%Y%m%d%H%M`"
#部署服务端
ps -ef | grep java |grep $path | grep -v grep | awk '{print $2}' | xargs kill -9;
echo "$path kill -9关闭";fi;

mv /root/$name $path; echo "Replace the new package complete!";

chown -R appuser:appuser $path

su appuser $path/startup.sh; echo "easyboot started!";

#部署前端
nginxPath=/usr/local/nginx/html
cd $nginxPath
#删除旧文件
rm -rf ./*
mv /root/dist.tar.gz $nginxPath
#解压缩
tar -zxvf dist.tar.gz
