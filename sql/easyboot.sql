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

 Date: 07/09/2020 21:03:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for SYS_DICT
-- ----------------------------
DROP TABLE IF EXISTS SYS_DICT;
CREATE TABLE SYS_DICT  (
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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '数据字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of SYS_DICT
-- ----------------------------
INSERT INTO SYS_DICT VALUES (1, 1, '男', '1', 'SYS_USER_sex', '', '', 'Y', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '性别男');
INSERT INTO SYS_DICT VALUES (2, 2, '女', '0', 'SYS_USER_sex', '', '', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '性别女');
INSERT INTO SYS_DICT VALUES (3, 3, '未知', '2', 'SYS_USER_sex', '', '', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '性别未知');
INSERT INTO SYS_DICT VALUES (4, 1, '显示', '1', 'sys_show_hide', '', 'primary', 'Y', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '显示菜单');
INSERT INTO SYS_DICT VALUES (5, 2, '隐藏', '0', 'sys_show_hide', '', 'danger', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '隐藏菜单');
INSERT INTO SYS_DICT VALUES (6, 1, '正常', '1', 'sys_normal_disable', '', 'primary', 'Y', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '正常状态');
INSERT INTO SYS_DICT VALUES (7, 2, '停用', '0', 'sys_normal_disable', '', 'danger', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '停用状态');
INSERT INTO SYS_DICT VALUES (8, 1, '正常', '1', 'sys_job_status', '', 'primary', 'Y', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '正常状态');
INSERT INTO SYS_DICT VALUES (9, 2, '暂停', '0', 'sys_job_status', '', 'danger', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '停用状态');
INSERT INTO SYS_DICT VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '默认分组');
INSERT INTO SYS_DICT VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '系统分组');
INSERT INTO SYS_DICT VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '系统默认是');
INSERT INTO SYS_DICT VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '系统默认否');
INSERT INTO SYS_DICT VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '通知');
INSERT INTO SYS_DICT VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '公告');
INSERT INTO SYS_DICT VALUES (16, 1, '正常', '1', 'sys_notice_status', '', 'primary', 'Y', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '正常状态');
INSERT INTO SYS_DICT VALUES (17, 2, '关闭', '0', 'sys_notice_status', '', 'danger', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '关闭状态');
INSERT INTO SYS_DICT VALUES (18, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '新增操作');
INSERT INTO SYS_DICT VALUES (19, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '修改操作');
INSERT INTO SYS_DICT VALUES (20, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '删除操作');
INSERT INTO SYS_DICT VALUES (21, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '授权操作');
INSERT INTO SYS_DICT VALUES (22, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '导出操作');
INSERT INTO SYS_DICT VALUES (23, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '导入操作');
INSERT INTO SYS_DICT VALUES (24, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '强退操作');
INSERT INTO SYS_DICT VALUES (25, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '生成操作');
INSERT INTO SYS_DICT VALUES (26, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '清空操作');
INSERT INTO SYS_DICT VALUES (27, 1, '成功', '1', 'sys_common_status', '', 'primary', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '正常状态');
INSERT INTO SYS_DICT VALUES (28, 2, '失败', '0', 'sys_common_status', '', 'danger', 'N', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '停用状态');

-- ----------------------------
-- Table structure for SYS_MENU
-- ----------------------------
DROP TABLE IF EXISTS SYS_MENU;
CREATE TABLE SYS_MENU  (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  menu_name varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  parent_id bigint(20) NULL DEFAULT 0 COMMENT '父菜单ID',
  sort_by int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  path varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '路由地址',
  component varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径',
  is_frame int(1) NULL DEFAULT 0 COMMENT '是否为外链（1是 0否）',
  menu_type char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  visible int(1) NULL DEFAULT 1 COMMENT '菜单状态（1显示 0隐藏）',
  status int(1) NULL DEFAULT 1 COMMENT '菜单状态（1正常 0停用）',
  perms varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  icon varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '#' COMMENT '菜单图标',
  create_by varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  create_time datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  update_by varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  update_time datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  remark varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of SYS_MENU
-- ----------------------------
INSERT INTO SYS_MENU VALUES (1, '系统管理', 0, 1, 'system', NULL, 0, 'M', 1, 1, '', 'system', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '系统管理目录');
INSERT INTO SYS_MENU VALUES (2, '系统监控', 0, 2, 'monitor', NULL, 0, 'M', 1, 1, '', 'monitor', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '系统监控目录');
INSERT INTO SYS_MENU VALUES (3, '系统工具', 0, 3, 'tool', NULL, 0, 'M', 1, 1, '', 'tool', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '系统工具目录');
INSERT INTO SYS_MENU VALUES (4, '若依官网', 0, 4, 'http://ruoyi.vip', NULL, 1, 'M', 1, 1, '', 'guide', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '若依官网地址');
INSERT INTO SYS_MENU VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', 0, 'C', 1, 1, 'system:user:list', 'user', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '用户管理菜单');
INSERT INTO SYS_MENU VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', 0, 'C', 1, 1, 'system:role:list', 'peoples', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '角色管理菜单');
INSERT INTO SYS_MENU VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', 0, 'C', 1, 1, 'system:menu:list', 'tree-table', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '菜单管理菜单');
INSERT INTO SYS_MENU VALUES (103, '部门管理', 1, 4, 'dept', 'system/dept/index', 0, 'C', 1, 1, 'system:dept:list', 'tree', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '部门管理菜单');
INSERT INTO SYS_MENU VALUES (104, '岗位管理', 1, 5, 'post', 'system/post/index', 0, 'C', 1, 1, 'system:post:list', 'post', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '岗位管理菜单');
INSERT INTO SYS_MENU VALUES (105, '字典管理', 1, 6, 'dict', 'system/dict/index', 0, 'C', 1, 1, 'system:dict:list', 'dict', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '字典管理菜单');
INSERT INTO SYS_MENU VALUES (106, '参数设置', 1, 7, 'config', 'system/config/index', 0, 'C', 1, 1, 'system:config:list', 'edit', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '参数设置菜单');
INSERT INTO SYS_MENU VALUES (107, '通知公告', 1, 8, 'notice', 'system/notice/index', 0, 'C', 1, 1, 'system:notice:list', 'message', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '通知公告菜单');
INSERT INTO SYS_MENU VALUES (108, '日志管理', 1, 9, 'log', 'system/log/index', 0, 'M', 1, 1, '', 'log', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '日志管理菜单');
INSERT INTO SYS_MENU VALUES (109, '在线用户', 2, 1, 'online', 'monitor/online/index', 0, 'C', 1, 1, 'monitor:online:list', 'online', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '在线用户菜单');
INSERT INTO SYS_MENU VALUES (110, '定时任务', 2, 2, 'job', 'monitor/job/index', 0, 'C', 1, 1, 'monitor:job:list', 'job', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '定时任务菜单');
INSERT INTO SYS_MENU VALUES (111, '数据监控', 2, 3, 'druid', 'monitor/druid/index', 0, 'C', 1, 1, 'monitor:druid:list', 'druid', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '数据监控菜单');
INSERT INTO SYS_MENU VALUES (112, '服务监控', 2, 4, 'server', 'monitor/server/index', 0, 'C', 1, 1, 'monitor:server:list', 'server', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '服务监控菜单');
INSERT INTO SYS_MENU VALUES (113, '表单构建', 3, 1, 'build', 'tool/build/index', 0, 'C', 1, 1, 'tool:build:list', 'build', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '表单构建菜单');
INSERT INTO SYS_MENU VALUES (114, '代码生成', 3, 2, 'gen', 'tool/gen/index', 0, 'C', 1, 1, 'tool:gen:list', 'code', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '代码生成菜单');
INSERT INTO SYS_MENU VALUES (115, '系统接口', 3, 3, 'swagger', 'tool/swagger/index', 0, 'C', 1, 1, 'tool:swagger:list', 'swagger', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '系统接口菜单');
INSERT INTO SYS_MENU VALUES (500, '操作日志', 108, 1, 'operlog', 'monitor/operlog/index', 0, 'C', 1, 1, 'monitor:operlog:list', 'form', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '操作日志菜单');
INSERT INTO SYS_MENU VALUES (501, '登录日志', 108, 2, 'logininfor', 'monitor/logininfor/index', 0, 'C', 1, 1, 'monitor:logininfor:list', 'logininfor', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '登录日志菜单');
INSERT INTO SYS_MENU VALUES (1001, '用户查询', 100, 1, '', '', 0, 'F', 1, 1, 'system:user:query', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1002, '用户新增', 100, 2, '', '', 0, 'F', 1, 1, 'system:user:add', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1003, '用户修改', 100, 3, '', '', 0, 'F', 1, 1, 'system:user:edit', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1004, '用户删除', 100, 4, '', '', 0, 'F', 1, 1, 'system:user:remove', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1005, '用户导出', 100, 5, '', '', 0, 'F', 1, 1, 'system:user:export', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1006, '用户导入', 100, 6, '', '', 0, 'F', 1, 1, 'system:user:import', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1007, '重置密码', 100, 7, '', '', 0, 'F', 1, 1, 'system:user:resetPwd', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1008, '角色查询', 101, 1, '', '', 0, 'F', 1, 1, 'system:role:query', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1009, '角色新增', 101, 2, '', '', 0, 'F', 1, 1, 'system:role:add', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1010, '角色修改', 101, 3, '', '', 0, 'F', 1, 1, 'system:role:edit', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1011, '角色删除', 101, 4, '', '', 0, 'F', 1, 1, 'system:role:remove', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1012, '角色导出', 101, 5, '', '', 0, 'F', 1, 1, 'system:role:export', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1013, '菜单查询', 102, 1, '', '', 0, 'F', 1, 1, 'system:menu:query', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1014, '菜单新增', 102, 2, '', '', 0, 'F', 1, 1, 'system:menu:add', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1015, '菜单修改', 102, 3, '', '', 0, 'F', 1, 1, 'system:menu:edit', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1016, '菜单删除', 102, 4, '', '', 0, 'F', 1, 1, 'system:menu:remove', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1017, '部门查询', 103, 1, '', '', 0, 'F', 1, 1, 'system:dept:query', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1018, '部门新增', 103, 2, '', '', 0, 'F', 1, 1, 'system:dept:add', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1019, '部门修改', 103, 3, '', '', 0, 'F', 1, 1, 'system:dept:edit', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1020, '部门删除', 103, 4, '', '', 0, 'F', 1, 1, 'system:dept:remove', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1021, '岗位查询', 104, 1, '', '', 0, 'F', 1, 1, 'system:post:query', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1022, '岗位新增', 104, 2, '', '', 0, 'F', 1, 1, 'system:post:add', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1023, '岗位修改', 104, 3, '', '', 0, 'F', 1, 1, 'system:post:edit', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1024, '岗位删除', 104, 4, '', '', 0, 'F', 1, 1, 'system:post:remove', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1025, '岗位导出', 104, 5, '', '', 0, 'F', 1, 1, 'system:post:export', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1026, '字典查询', 105, 1, '#', '', 0, 'F', 1, 1, 'system:dict:query', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1027, '字典新增', 105, 2, '#', '', 0, 'F', 1, 1, 'system:dict:add', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1028, '字典修改', 105, 3, '#', '', 0, 'F', 1, 1, 'system:dict:edit', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1029, '字典删除', 105, 4, '#', '', 0, 'F', 1, 1, 'system:dict:remove', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1030, '字典导出', 105, 5, '#', '', 0, 'F', 1, 1, 'system:dict:export', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1031, '参数查询', 106, 1, '#', '', 0, 'F', 1, 1, 'system:config:query', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1032, '参数新增', 106, 2, '#', '', 0, 'F', 1, 1, 'system:config:add', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1033, '参数修改', 106, 3, '#', '', 0, 'F', 1, 1, 'system:config:edit', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1034, '参数删除', 106, 4, '#', '', 0, 'F', 1, 1, 'system:config:remove', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1035, '参数导出', 106, 5, '#', '', 0, 'F', 1, 1, 'system:config:export', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1036, '公告查询', 107, 1, '#', '', 0, 'F', 1, 1, 'system:notice:query', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1037, '公告新增', 107, 2, '#', '', 0, 'F', 1, 1, 'system:notice:add', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1038, '公告修改', 107, 3, '#', '', 0, 'F', 1, 1, 'system:notice:edit', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1039, '公告删除', 107, 4, '#', '', 0, 'F', 1, 1, 'system:notice:remove', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1040, '操作查询', 500, 1, '#', '', 0, 'F', 1, 1, 'monitor:operlog:query', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1041, '操作删除', 500, 2, '#', '', 0, 'F', 1, 1, 'monitor:operlog:remove', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1042, '日志导出', 500, 4, '#', '', 0, 'F', 1, 1, 'monitor:operlog:export', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1043, '登录查询', 501, 1, '#', '', 0, 'F', 1, 1, 'monitor:logininfor:query', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1044, '登录删除', 501, 2, '#', '', 0, 'F', 1, 1, 'monitor:logininfor:remove', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1045, '日志导出', 501, 3, '#', '', 0, 'F', 1, 1, 'monitor:logininfor:export', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1046, '在线查询', 109, 1, '#', '', 0, 'F', 1, 1, 'monitor:online:query', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1047, '批量强退', 109, 2, '#', '', 0, 'F', 1, 1, 'monitor:online:batchLogout', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1048, '单条强退', 109, 3, '#', '', 0, 'F', 1, 1, 'monitor:online:forceLogout', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1049, '任务查询', 110, 1, '#', '', 0, 'F', 1, 1, 'monitor:job:query', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1050, '任务新增', 110, 2, '#', '', 0, 'F', 1, 1, 'monitor:job:add', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1051, '任务修改', 110, 3, '#', '', 0, 'F', 1, 1, 'monitor:job:edit', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1052, '任务删除', 110, 4, '#', '', 0, 'F', 1, 1, 'monitor:job:remove', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1053, '状态修改', 110, 5, '#', '', 0, 'F', 1, 1, 'monitor:job:changeStatus', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1054, '任务导出', 110, 7, '#', '', 0, 'F', 1, 1, 'monitor:job:export', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1055, '生成查询', 114, 1, '#', '', 0, 'F', 1, 1, 'tool:gen:query', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1056, '生成修改', 114, 2, '#', '', 0, 'F', 1, 1, 'tool:gen:edit', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1057, '生成删除', 114, 3, '#', '', 0, 'F', 1, 1, 'tool:gen:remove', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1058, '导入代码', 114, 2, '#', '', 0, 'F', 1, 1, 'tool:gen:import', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1059, '预览代码', 114, 4, '#', '', 0, 'F', 1, 1, 'tool:gen:preview', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');
INSERT INTO SYS_MENU VALUES (1060, '生成代码', 114, 5, '#', '', 0, 'F', 1, 1, 'tool:gen:code', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '');

-- ----------------------------
-- Table structure for SYS_ROLE
-- ----------------------------
DROP TABLE IF EXISTS SYS_ROLE;
CREATE TABLE SYS_ROLE  (
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
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of SYS_ROLE
-- ----------------------------
INSERT INTO SYS_ROLE VALUES (1, 'admin', '管理员', 1, '1', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-30 18:46:08', '管理员');
INSERT INTO SYS_ROLE VALUES (2, 'common', '普通角色', 2, '2', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-30 18:57:26', '普通角色');
INSERT INTO SYS_ROLE VALUES (3, 'develop', '开发人员', 3, NULL, '1', 'admin', '2020-08-29 22:05:57', 'admin', '2020-08-29 23:38:14', '开发人员');
INSERT INTO SYS_ROLE VALUES (4, 'test', '测试人员', 4, NULL, '1', 'admin', '2020-08-29 22:09:00', 'admin', '2020-09-06 21:40:30', '测试人员');
INSERT INTO SYS_ROLE VALUES (8, 'super', '超级管理员', 5, NULL, '1', 'admin', '2020-08-30 00:14:10', 'admin', '2020-08-30 18:45:45', '超级管理员');

-- ----------------------------
-- Table structure for SYS_ROLE_MENU
-- ----------------------------
DROP TABLE IF EXISTS SYS_ROLE_MENU;
CREATE TABLE SYS_ROLE_MENU  (
  role_id bigint(20) NOT NULL COMMENT '角色ID',
  menu_id bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (role_id, menu_id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of SYS_ROLE_MENU
-- ----------------------------
INSERT INTO SYS_ROLE_MENU VALUES (2, 1);
INSERT INTO SYS_ROLE_MENU VALUES (2, 2);
INSERT INTO SYS_ROLE_MENU VALUES (2, 3);
INSERT INTO SYS_ROLE_MENU VALUES (2, 4);
INSERT INTO SYS_ROLE_MENU VALUES (2, 100);
INSERT INTO SYS_ROLE_MENU VALUES (2, 101);
INSERT INTO SYS_ROLE_MENU VALUES (2, 102);
INSERT INTO SYS_ROLE_MENU VALUES (2, 103);

-- ----------------------------
-- Table structure for SYS_USER
-- ----------------------------
DROP TABLE IF EXISTS SYS_USER;
CREATE TABLE SYS_USER  (
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
  login_addr varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '最后登陆地址',
  login_date datetime(0) NULL DEFAULT NULL COMMENT '最后登陆时间',
  create_by varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  create_time datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  update_by varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  update_time datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  remark varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id) USING BTREE,
  UNIQUE INDEX UNI_USER_NAME(user_name) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of SYS_USER
-- ----------------------------
INSERT INTO SYS_USER VALUES (1, 'admin', '$2a$10$cfb2PDDnGo0AzAvc7wj2f.7ixN7bMGgvEEyE5608KXFi0T7J6MRsm', '', '系统管理员',
                             'zmzhou8@gmail.com', '19866165191', '1', '1', '0', '内网IP','', '2020-09-06 23:17:46',
                             'admin', '2020-07-06 11:08:00', 'admin', '2020-09-05 23:59:14', '管理员');
INSERT INTO SYS_USER VALUES (2, 'super', '$2a$10$e7FELY2lYp57zN3k3TydSO195eNzyBShYBuPpMTBkNm/D060Z9k6K', NULL, 
                             '超级管理员', 'zmzhou8@gmail.com', '19866165191', '0', '1', '1', '内网IP','', '2020-09-06
23:31:20', 'admin', '2020-08-29 22:02:00', 'super', '2020-09-06 23:38:50', '超级管理员');
INSERT INTO SYS_USER VALUES (3, 'zmzhou', '$2a$10$sGYhQN6z8t4MrO8NUoJQYuU09YDWDEvuTZytgWMK2WU09aox1ZJRe',
                             'zmzhou/avatar.jpeg', '小周', 'zmzhou8@qq.com', '17363939598', '1', '1', '0', '内网IP','', '2020-09-06 23:16:42', 'admin', '2020-07-07 10:28:00', 'zmzhou', '2020-09-06 22:57:24', '测试');

-- ----------------------------
-- Table structure for SYS_USER_ROLE
-- ----------------------------
DROP TABLE IF EXISTS SYS_USER_ROLE;
CREATE TABLE SYS_USER_ROLE  (
  user_id bigint(20) NOT NULL COMMENT '用户ID',
  role_id bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (user_id, role_id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of SYS_USER_ROLE
-- ----------------------------
INSERT INTO SYS_USER_ROLE VALUES (1, 1);
INSERT INTO SYS_USER_ROLE VALUES (2, 2);
INSERT INTO SYS_USER_ROLE VALUES (3, 3);
INSERT INTO SYS_USER_ROLE VALUES (4, 2);

SET FOREIGN_KEY_CHECKS = 1;
