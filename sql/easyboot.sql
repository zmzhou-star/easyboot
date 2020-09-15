/*
 Navicat Premium Data Transfer

 Source Server         : easyboot
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : easyboot

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 14/09/2020 22:56:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS sys_dict;
CREATE TABLE sys_dict  (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  dict_sort int(4) NULL DEFAULT 0 COMMENT '字典排序',
  dict_label varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典标签',
  dict_value varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典键值',
  dict_type varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典类型',
  css_class varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  list_class varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  is_default char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  status char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '状态（1正常 0停用）',
  create_by varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  create_time datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  update_by varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  update_time datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  remark varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '数据字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO sys_dict VALUES (1, 1, '男', '1', 'sys_user_sex', '', '', 'Y', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '性别男');
INSERT INTO sys_dict VALUES (2, 2, '女', '0', 'sys_user_sex', '', '', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '性别女');
INSERT INTO sys_dict VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '性别未知');
INSERT INTO sys_dict VALUES (4, 1, '显示', '1', 'sys_show_hide', '', 'primary', 'Y', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '显示菜单');
INSERT INTO sys_dict VALUES (5, 2, '隐藏', '0', 'sys_show_hide', '', 'danger', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '隐藏菜单');
INSERT INTO sys_dict VALUES (6, 1, '正常', '1', 'sys_normal_disable', '', 'primary', 'Y', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '正常状态');
INSERT INTO sys_dict VALUES (7, 2, '停用', '0', 'sys_normal_disable', '', 'danger', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '停用状态');
INSERT INTO sys_dict VALUES (8, 1, '正常', '1', 'sys_job_status', '', 'primary', 'Y', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '正常状态');
INSERT INTO sys_dict VALUES (9, 2, '暂停', '0', 'sys_job_status', '', 'danger', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '停用状态');
INSERT INTO sys_dict VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '默认分组');
INSERT INTO sys_dict VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '系统分组');
INSERT INTO sys_dict VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '系统默认是');
INSERT INTO sys_dict VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '系统默认否');
INSERT INTO sys_dict VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '通知');
INSERT INTO sys_dict VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '公告');
INSERT INTO sys_dict VALUES (16, 1, '正常', '1', 'sys_notice_status', '', 'primary', 'Y', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '正常状态');
INSERT INTO sys_dict VALUES (17, 2, '关闭', '0', 'sys_notice_status', '', 'danger', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '关闭状态');
INSERT INTO sys_dict VALUES (18, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '新增操作');
INSERT INTO sys_dict VALUES (19, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '修改操作');
INSERT INTO sys_dict VALUES (20, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '删除操作');
INSERT INTO sys_dict VALUES (21, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '授权操作');
INSERT INTO sys_dict VALUES (22, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '导出操作');
INSERT INTO sys_dict VALUES (23, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '导入操作');
INSERT INTO sys_dict VALUES (24, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '强退操作');
INSERT INTO sys_dict VALUES (25, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '生成操作');
INSERT INTO sys_dict VALUES (26, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '清空操作');
INSERT INTO sys_dict VALUES (27, 1, '成功', '1', 'sys_common_status', '', 'primary', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '正常状态');
INSERT INTO sys_dict VALUES (28, 2, '失败', '0', 'sys_common_status', '', 'danger', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '停用状态');

-- ----------------------------
-- Table structure for sys_log_login
-- ----------------------------
DROP TABLE IF EXISTS sys_log_login;
CREATE TABLE sys_log_login  (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  user_name varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户账号',
  ip_addr varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '登录IP地址',
  login_location varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '登录地点',
  browser varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '浏览器类型',
  os varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作系统',
  status char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '登录状态（1成功 0失败）',
  msg varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '提示消息',
  login_time datetime(0) NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统登录日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log_login
-- ----------------------------
INSERT INTO sys_log_login VALUES (1, 'admin', '14.28.136.193', '广东省深圳市 电信', 'Chrome 8', 'Windows 10', '1', '登录成功', '2020-09-13 23:53:43');

-- ----------------------------
-- Table structure for sys_log_oper
-- ----------------------------
DROP TABLE IF EXISTS SYS_LOG_OPER;
CREATE TABLE SYS_LOG_OPER  (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  title varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '模块标题',
  method varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '方法名称',
  method_desc varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '方法描述',
  request_method varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求方式',
  oper_name varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作人员',
  oper_url varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求URL',
  oper_ip varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '主机地址',
  oper_location varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作地点',
  oper_param varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求参数',
  oper_result varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '返回参数',
  status char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '操作状态（1正常 0异常）',
  error_msg varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '错误消息',
  oper_time datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log_oper
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS sys_menu;
CREATE TABLE sys_menu  (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  menu_name varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  parent_id bigint(20) NULL DEFAULT 0 COMMENT '父菜单ID',
  sort_by int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  path varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '路由地址',
  component varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '组件路径',
  is_frame char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '是否为外链（1是 0否）',
  menu_type char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  visible char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '菜单状态（1显示 0隐藏）',
  status char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '菜单状态（1正常 0停用）',
  perms varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  icon varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '#' COMMENT '菜单图标',
  create_by varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  create_time datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  update_by varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  update_time datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  remark varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2006 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO sys_menu VALUES (1, '系统管理', 0, 1, 'system', '', '0', 'M', '1', '1', '', 'system', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '系统管理目录');
INSERT INTO sys_menu VALUES (2, '系统监控', 0, 2, 'monitor', '', '0', 'M', '1', '1', '', 'monitor', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '系统监控目录');
INSERT INTO sys_menu VALUES (3, '系统工具', 0, 3, 'tool', '', '0', 'M', '1', '1', '', 'tool', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '系统工具目录');
INSERT INTO sys_menu VALUES (4, '友情链接', 0, 4, 'link', NULL, '0', 'M', '1', '1', '', 'link', 'admin', '2020-09-09 22:01:48', 'admin', '2020-09-09 23:36:52', '项目参考文档链接地址');
INSERT INTO sys_menu VALUES (5, '若依官网', 4, 4, 'http://ruoyi.vip', '', '1', 'M', '1', '1', '', 'guide', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '若依官网地址');
INSERT INTO sys_menu VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', '0', 'C', '1', '1', 'system:user:list', 'user', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '用户管理菜单');
INSERT INTO sys_menu VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', '0', 'C', '1', '1', 'system:role:list', 'peoples', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '角色管理菜单');
INSERT INTO sys_menu VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', '0', 'C', '1', '1', 'system:menu:list', 'tree-table', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '菜单管理菜单');
INSERT INTO sys_menu VALUES (105, '字典管理', 1, 6, 'dict', 'system/dict/index', '0', 'C', '1', '1', 'system:dict:list', 'dict', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '字典管理菜单');
INSERT INTO sys_menu VALUES (106, '参数设置', 1, 7, 'config', 'system/config/index', '0', 'C', '1', '1', 'system:config:list', 'edit', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '参数设置菜单');
INSERT INTO sys_menu VALUES (107, '通知公告', 1, 8, 'notice', 'system/notice/index', '0', 'C', '1', '1', 'system:notice:list', 'message', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '通知公告菜单');
INSERT INTO sys_menu VALUES (108, '日志管理', 2, 5, 'log', 'monitor/loginLog/index', '0', 'M', '1', '1', '', 'log', 'admin', '2020-08-28 12:33:00', 'admin', '2020-09-13 21:07:55', '日志管理菜单');
INSERT INTO sys_menu VALUES (109, '在线用户', 2, 1, 'online', 'monitor/online/index', '0', 'C', '1', '1', 'monitor:online:list', 'online', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '在线用户菜单');
INSERT INTO sys_menu VALUES (110, '定时任务', 2, 2, 'job', 'monitor/job/index', '0', 'C', '1', '1', 'monitor:job:list', 'job', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '定时任务菜单');
INSERT INTO sys_menu VALUES (111, '数据源监控', 2, 3, 'druid', 'monitor/druid/index', '0', 'C', '1', '1', 'monitor:druid:list', 'druid', 'admin', '2020-08-28 12:33:00', 'admin', '2020-09-11 21:40:43', '数据监控菜单');
INSERT INTO sys_menu VALUES (112, '服务器监控', 2, 4, 'server', 'monitor/server/index', '0', 'C', '1', '1', 'monitor:server:list', 'server', 'admin', '2020-08-28 12:33:00', 'admin', '2020-09-13 17:03:57', '服务器监控菜单');
INSERT INTO sys_menu VALUES (113, '表单构建', 3, 1, 'build', 'tool/build/index', '0', 'C', '1', '1', 'tool:build:list', 'build', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '表单构建菜单');
INSERT INTO sys_menu VALUES (114, '代码生成', 3, 2, 'gen', 'tool/gen/index', '0', 'C', '1', '1', 'tool:gen:list', 'code', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '代码生成菜单');
INSERT INTO sys_menu VALUES (115, '接口文档', 3, 3, 'swagger', 'tool/swagger/index', '0', 'C', '1', '1', 'tool:swagger:list', 'swagger', 'admin', '2020-08-28 12:33:00', 'admin', '2020-09-11 21:39:32', '系统接口文档');
INSERT INTO sys_menu VALUES (500, '操作日志', 108, 2, 'operlog', 'monitor/operlog/index', '0', 'C', '1', '1', 'monitor:operlog:list', 'form', 'admin', '2020-08-28 12:33:00', 'admin', '2020-09-12 20:53:44', '操作日志菜单');
INSERT INTO sys_menu VALUES (501, '登录日志', 108, 1, 'loginLog', 'monitor/loginLog/index', '0', 'C', '1', '1', 'monitor:loginLog:list', 'logininfor', 'admin', '2020-08-28 12:33:00', 'admin', '2020-09-13 16:58:04', '登录日志菜单');
INSERT INTO sys_menu VALUES (1001, '用户查询', 100, 1, '', '', '0', 'F', '1', '1', 'system:user:query', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-09-14 22:12:02', '用户管理查询');
INSERT INTO sys_menu VALUES (1002, '用户新增', 100, 2, '', '', '0', 'F', '1', '1', 'system:user:add', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1003, '用户修改', 100, 3, '', '', '0', 'F', '1', '1', 'system:user:edit', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1004, '用户删除', 100, 4, '', '', '0', 'F', '1', '1', 'system:user:remove', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1005, '用户导出', 100, 5, '', '', '0', 'F', '1', '1', 'system:user:export', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1006, '用户导入', 100, 6, '', '', '0', 'F', '1', '1', 'system:user:import', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1007, '重置密码', 100, 7, '', '', '0', 'F', '1', '1', 'system:user:resetPwd', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1008, '角色查询', 101, 1, '', '', '0', 'F', '1', '1', 'system:role:query', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1009, '角色新增', 101, 2, '', '', '0', 'F', '1', '1', 'system:role:add', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1010, '角色修改', 101, 3, '', '', '0', 'F', '1', '1', 'system:role:edit', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1011, '角色删除', 101, 4, '', '', '0', 'F', '1', '1', 'system:role:remove', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1012, '角色导出', 101, 5, '', '', '0', 'F', '1', '1', 'system:role:export', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1013, '菜单查询', 102, 1, '', '', '0', 'F', '1', '1', 'system:menu:query', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1014, '菜单新增', 102, 2, '', '', '0', 'F', '1', '1', 'system:menu:add', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1015, '菜单修改', 102, 3, '', '', '0', 'F', '1', '1', 'system:menu:edit', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1016, '菜单删除', 102, 4, '', '', '0', 'F', '1', '1', 'system:menu:remove', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1026, '字典查询', 105, 1, '#', '', '0', 'F', '1', '1', 'system:dict:query', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1027, '字典新增', 105, 2, '#', '', '0', 'F', '1', '1', 'system:dict:add', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1028, '字典修改', 105, 3, '#', '', '0', 'F', '1', '1', 'system:dict:edit', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1029, '字典删除', 105, 4, '#', '', '0', 'F', '1', '1', 'system:dict:remove', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1030, '字典导出', 105, 5, '#', '', '0', 'F', '1', '1', 'system:dict:export', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1031, '参数查询', 106, 1, '#', '', '0', 'F', '1', '1', 'system:config:query', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1032, '参数新增', 106, 2, '#', '', '0', 'F', '1', '1', 'system:config:add', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1033, '参数修改', 106, 3, '#', '', '0', 'F', '1', '1', 'system:config:edit', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1034, '参数删除', 106, 4, '#', '', '0', 'F', '1', '1', 'system:config:remove', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1035, '参数导出', 106, 5, '#', '', '0', 'F', '1', '1', 'system:config:export', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1036, '公告查询', 107, 1, '#', '', '0', 'F', '1', '1', 'system:notice:query', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1037, '公告新增', 107, 2, '#', '', '0', 'F', '1', '1', 'system:notice:add', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1038, '公告修改', 107, 3, '#', '', '0', 'F', '1', '1', 'system:notice:edit', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1039, '公告删除', 107, 4, '#', '', '0', 'F', '1', '1', 'system:notice:remove', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1040, '操作查询', 500, 1, '#', '', '0', 'F', '1', '1', 'monitor:operlog:query', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1041, '操作删除', 500, 2, '#', '', '0', 'F', '1', '1', 'monitor:operlog:remove', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1042, '日志导出', 500, 4, '#', '', '0', 'F', '1', '1', 'monitor:operlog:export', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1043, '登录查询', 501, 1, '#', '', '0', 'F', '1', '1', 'monitor:loginLog:query', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-09-13 16:58:22', '');
INSERT INTO sys_menu VALUES (1044, '登录删除', 501, 2, '#', '', '0', 'F', '1', '1', 'monitor:loginLog:remove', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-09-13 16:58:34', '');
INSERT INTO sys_menu VALUES (1045, '导出登录日志', 501, 3, '#', '', '0', 'F', '1', '1', 'monitor:loginLog:export', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-09-13 17:00:40', '');
INSERT INTO sys_menu VALUES (1046, '在线查询', 109, 1, '#', '', '0', 'F', '1', '1', 'monitor:online:query', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1047, '批量强退', 109, 2, '#', '', '0', 'F', '1', '1', 'monitor:online:batchLogout', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1048, '单条强退', 109, 3, '#', '', '0', 'F', '1', '1', 'monitor:online:forceLogout', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1049, '任务查询', 110, 1, '#', '', '0', 'F', '1', '1', 'monitor:job:query', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1050, '任务新增', 110, 2, '#', '', '0', 'F', '1', '1', 'monitor:job:add', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1051, '任务修改', 110, 3, '#', '', '0', 'F', '1', '1', 'monitor:job:edit', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1052, '任务删除', 110, 4, '#', '', '0', 'F', '1', '1', 'monitor:job:remove', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1053, '状态修改', 110, 5, '#', '', '0', 'F', '1', '1', 'monitor:job:changeStatus', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1054, '任务导出', 110, 7, '#', '', '0', 'F', '1', '1', 'monitor:job:export', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1055, '生成查询', 114, 1, '#', '', '0', 'F', '1', '1', 'tool:gen:query', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1056, '生成修改', 114, 2, '#', '', '0', 'F', '1', '1', 'tool:gen:edit', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1057, '生成删除', 114, 3, '#', '', '0', 'F', '1', '1', 'tool:gen:remove', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1058, '导入代码', 114, 2, '#', '', '0', 'F', '1', '1', 'tool:gen:import', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1059, '预览代码', 114, 4, '#', '', '0', 'F', '1', '1', 'tool:gen:preview', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (1060, '生成代码', 114, 5, '#', '', '0', 'F', '1', '1', 'tool:gen:code', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO sys_menu VALUES (2004, '项目Git地址', 4, 1, 'https://github.com/zmzhou-star/easyboot', '', '1', 'C', '1', '1', NULL, 'github', 'admin', '2020-09-09 23:12:23', 'admin', '2020-09-09 23:15:07', NULL);
INSERT INTO sys_menu VALUES (2005, 'vue文档地址', 4, 2, 'https://panjiachen.gitee.io/vue-element-admin-site/zh/', NULL, '1', 'C', '1', '1', NULL, 'documentation', 'admin', '2020-09-09 23:16:56', 'zmzhou', '2020-09-10 21:07:44', NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role  (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  role_code varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色权限编码',
  role_name varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  sort_by int(4) NOT NULL COMMENT '显示顺序',
  data_scope char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  status char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '角色状态（1正常 0停用 2代表删除）',
  create_by varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  create_time datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  update_by varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  update_time datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  remark varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id) USING BTREE,
  UNIQUE INDEX UNI_ROLE_CODE(role_code) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO sys_role VALUES (1, 'admin', '系统管理员', 1, '1', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-30 18:46:08', '管理员');
INSERT INTO sys_role VALUES (2, 'super', '超级管理员', 2, NULL, '1', 'admin', '2020-08-30 00:14:10', 'admin', '2020-09-14 21:02:39', '超级管理员');
INSERT INTO sys_role VALUES (3, 'common', '普通角色', 3, '2', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-09-12 19:52:43', '普通角色');
INSERT INTO sys_role VALUES (4, 'develop', '开发人员', 4, NULL, '1', 'admin', '2020-08-29 22:05:57', 'admin', '2020-09-12 19:52:55', '开发人员');
INSERT INTO sys_role VALUES (5, 'test', '测试人员', 5, NULL, '1', 'admin', '2020-08-29 22:09:00', 'admin', '2020-09-14 22:19:40', '测试人员角色');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS sys_role_menu;
CREATE TABLE sys_role_menu  (
  role_id bigint(20) NOT NULL COMMENT '角色ID',
  menu_id bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (role_id, menu_id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO sys_role_menu VALUES (2, 1);
INSERT INTO sys_role_menu VALUES (2, 2);
INSERT INTO sys_role_menu VALUES (2, 3);
INSERT INTO sys_role_menu VALUES (2, 4);
INSERT INTO sys_role_menu VALUES (2, 5);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user  (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  user_name varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户账号',
  password varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '密码',
  avatar varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '头像地址',
  nick_name varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  email varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户邮箱',
  tel varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '手机号码',
  sex char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '用户性别（1男 0女 2未知）',
  status char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '帐号状态（1正常 0停用）',
  online char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '是否在线（1在线0不在线）',
  login_ip varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '最后登陆IP',
  login_addr varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '最后登陆地址',
  login_date datetime(0) NULL DEFAULT NULL COMMENT '最后登陆时间',
  create_by varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  create_time datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  update_by varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  update_time datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  remark varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id) USING BTREE,
  UNIQUE INDEX UNI_USER_NAME(user_name) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO sys_user VALUES (1, 'admin', '$2a$10$PvuYLV5id4v9kvI9uAUJ4.4VKDV.y8NKdTWSix4e2CAW.z6z/7FSe', '', '系统管理员', 'zmzhou8@gmail.com', '19866165166', '1', '1', '1', '14.28.155.78', '广东省深圳市 电信', '2020-09-14 22:11:25', 'admin', '2020-07-06 11:08:00', 'admin', '2020-09-14 22:28:00', '系统管理员');
INSERT INTO sys_user VALUES (2, 'super', '$2a$10$OTcz8wWQEobW6qXJaSeSZerd7/v/VmngizzKB8bo.oJ8QjChOQZp2', 'super/avatar.jpeg', '超级管理员', 'zmzhou8@gmail.com', '19866165166', '0', '1', '0', '14.28.155.78', '广东省深圳市 电信', '2020-09-14 20:48:27', 'admin', '2020-08-29 22:02:00', 'admin', '2020-09-12 18:49:16', '超级管理员');
INSERT INTO sys_user VALUES (3, 'zmzhou', '$2a$10$jEv2KBzmFyr6Y2dv261OH.chq4fwO7MMb/ApAp4no6Uaa0anfUp0q', 'zmzhou/avatar.jpeg', '小周', 'zmzhou8@qq.com', '19866165166', '1', '1', '0', '14.28.155.78', '广东省深圳市 电信', '2020-09-14 20:43:36', 'admin', '2020-07-07 10:28:00', 'admin', '2020-09-14 22:16:31', '开发者');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS sys_user_role;
CREATE TABLE sys_user_role  (
  user_id bigint(20) NOT NULL COMMENT '用户ID',
  role_id bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (user_id, role_id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO sys_user_role VALUES (1, 1);
INSERT INTO sys_user_role VALUES (2, 2);
INSERT INTO sys_user_role VALUES (3, 2);
INSERT INTO sys_user_role VALUES (3, 4);
INSERT INTO sys_user_role VALUES (5, 4);
INSERT INTO sys_user_role VALUES (35, 5);

SET FOREIGN_KEY_CHECKS = 1;
