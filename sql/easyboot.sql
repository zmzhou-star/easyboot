use mysql;
-- 创建数据库
CREATE DATABASE easyboot;
-- 创建用户
create user 'easyboot'@'%' identified by 'Zmzhou.V587';
-- 授权
grant select,insert,update,delete,create on `easyboot`.* to 'easyboot'@'%';
-- 或者赋所有权限 grant all privileges on `easyboot`.* to 'easyboot'@'%';
-- 查看一下用户列表
select host,user,authentication_string from mysql.user;
-- 刷新权限，立即启用修改
flush privileges;

-- 取消用户所有数据库（表）的所有权限
revoke all on `easyboot`.* from 'easyboot'@'%';
-- 删除用户
delete from mysql.user where user = 'easyboot';
-- 删除数据库
drop database easyboot;

-- 只需创建数据库和用户，建表语句由flyway管理启动服务会自动执行
