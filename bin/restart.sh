#!/bin/bash
appName='easyboot-1.0'
pid=`ps aux | grep $appName | grep -v grep | awk '{print $2}'`
kill $pid
echo "kill $pid successes!!!"
nohup java -jar -server $appName.jar >/dev/null 2>&1 &
echo "restart successes!!!"
