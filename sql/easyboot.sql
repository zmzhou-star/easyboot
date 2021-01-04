/*
 Navicat Premium Data Transfer

 Source Server         : easyboot
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : easyboot

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 20/12/2020 20:34:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for code_gen_table
-- ----------------------------
DROP TABLE IF EXISTS code_gen_table;
CREATE TABLE code_gen_table  (
  id bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  table_name varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表名称',
  table_comment varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '表描述',
  class_name varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '实体类名称',
  tpl_category varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  package_name varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成包路径',
  module_name varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成模块名',
  business_name varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成业务名',
  function_name varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成功能名',
  function_author varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成功能作者',
  others varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '其它生成选项',
  create_by varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  create_time datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  update_by varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  update_time datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  remark varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id) USING BTREE,
  UNIQUE INDEX UNI_TABLE_NAME(table_name) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '代码生成业务表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for code_gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS code_gen_table_column;
CREATE TABLE code_gen_table_column  (
  id bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  table_id bigint NOT NULL COMMENT '所属表编号',
  column_name varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '列名称',
  column_comment varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列描述',
  column_type varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '列类型',
  java_type varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'JAVA类型',
  java_field varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'JAVA字段名',
  is_pk char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否主键（1是）',
  is_increment char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否自增（1是）',
  is_required char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否必填（1是）',
  is_insert char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否为插入字段（1是）',
  is_edit char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否编辑字段（1是）',
  is_list char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否列表字段（1是）',
  is_query char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否查询字段（1是）',
  query_type varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  html_type varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  dict_type varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典类型',
  sort_by int NULL DEFAULT NULL COMMENT '排序',
  create_by varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  create_time datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  update_by varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  update_time datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  remark varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id) USING BTREE,
  INDEX IDX_CGTC_TABLE_ID(table_id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '代码生成业务表字段' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS sys_config;
CREATE TABLE sys_config  (
  id int NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  config_name varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数名称',
  config_key varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数键名',
  config_value varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数键值',
  config_type char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  create_by varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  create_time datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  update_by varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  update_time datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  remark varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '参数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO sys_config VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skin', '#1890ff', 'Y', 'admin', '2020-11-16 11:33:00', 'admin', '2020-12-19 15:41:36', '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO sys_config VALUES (2, '用户管理-账号初始密码', 'sys.user.initPassword', 'Zmzhou.1324', 'Y', 'admin', '2020-11-16 11:33:00', 'admin', '2020-12-14 17:02:09', '初始化密码 Zmzhou.1324');
INSERT INTO sys_config VALUES (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', '2020-11-16 11:33:00', 'admin', '2020-11-16 23:11:22', '深色主题theme-dark，浅色主题theme-light');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS sys_dict;
CREATE TABLE sys_dict  (
  id bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  dict_sort int NULL DEFAULT 0 COMMENT '字典排序',
  dict_label varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典标签',
  dict_value varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典键值',
  dict_name varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典名称',
  dict_type varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典类型',
  status char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '状态（1正常 0停用）',
  css_class varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  list_class varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  remark varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  create_by varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  create_time datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  update_by varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  update_time datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '数据字典表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO sys_dict VALUES (1, 1, NULL, NULL, '性别', 'sys_user_sex', '1', '用户性别', NULL, NULL, 'admin', '2020-11-17 21:19:01', 'admin', '2020-11-17 21:41:55');
INSERT INTO sys_dict VALUES (2, 2, NULL, NULL, '菜单状态', 'sys_show_hide', '1', '菜单状态', NULL, NULL, 'admin', '2020-11-17 21:35:40', 'admin', '2020-11-17 21:46:47');
INSERT INTO sys_dict VALUES (3, 3, NULL, NULL, '系统开关', 'sys_normal_disable', '1', '系统开关', NULL, NULL, 'admin', '2020-11-17 22:48:57', NULL, NULL);
INSERT INTO sys_dict VALUES (4, 4, NULL, NULL, '任务状态', 'sys_job_status', '1', '任务状态', NULL, NULL, 'admin', '2020-11-17 22:49:30', NULL, NULL);
INSERT INTO sys_dict VALUES (5, 5, NULL, NULL, '任务分组', 'sys_job_group', '1', '任务分组', NULL, NULL, 'admin', '2020-11-17 22:49:43', NULL, NULL);
INSERT INTO sys_dict VALUES (6, 6, NULL, NULL, '是否', 'sys_yes_no', '1', '是否', NULL, NULL, 'admin', '2020-11-17 22:49:53', NULL, NULL);
INSERT INTO sys_dict VALUES (7, 7, NULL, NULL, '通知类型', 'sys_notice_type', '1', '通知类型', NULL, NULL, 'admin', '2020-11-17 22:50:10', NULL, NULL);
INSERT INTO sys_dict VALUES (8, 8, NULL, NULL, '通知状态', 'sys_notice_status', '1', '通知状态', NULL, NULL, 'admin', '2020-11-17 22:50:32', NULL, NULL);
INSERT INTO sys_dict VALUES (9, 9, NULL, NULL, '操作类型', 'sys_oper_type', '1', '操作类型', NULL, NULL, 'admin', '2020-11-17 22:51:22', NULL, NULL);
INSERT INTO sys_dict VALUES (10, 10, NULL, NULL, '系统状态', 'sys_common_status', '1', '系统状态', NULL, NULL, 'admin', '2020-11-17 22:51:34', NULL, NULL);
INSERT INTO sys_dict VALUES (11, 1, '男', '1', '性别', 'sys_user_sex', '1', '性别男', '', '', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');
INSERT INTO sys_dict VALUES (12, 2, '女', '0', '性别', 'sys_user_sex', '1', '性别女', '', '', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');
INSERT INTO sys_dict VALUES (13, 3, '未知', '2', '性别', 'sys_user_sex', '1', '性别未知', '', '', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');
INSERT INTO sys_dict VALUES (14, 1, '显示', '1', '菜单状态', 'sys_show_hide', '1', '显示菜单', '', 'primary', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');
INSERT INTO sys_dict VALUES (15, 2, '隐藏', '0', '菜单状态', 'sys_show_hide', '1', '隐藏菜单', '', 'danger', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');
INSERT INTO sys_dict VALUES (16, 1, '正常', '1', '系统开关', 'sys_normal_disable', '1', '正常状态', '', 'primary', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');
INSERT INTO sys_dict VALUES (17, 2, '停用', '0', '系统开关', 'sys_normal_disable', '1', '停用状态', '', 'danger', 'admin', '2020-08-28 12:33:00', 'admin', '2020-11-22 18:34:54');
INSERT INTO sys_dict VALUES (18, 1, '正常', '1', '任务状态', 'sys_job_status', '1', '正常状态', '', 'primary', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');
INSERT INTO sys_dict VALUES (19, 2, '暂停', '0', '任务状态', 'sys_job_status', '1', '停用状态', '', 'danger', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');
INSERT INTO sys_dict VALUES (20, 1, '默认', 'DEFAULT', '任务分组', 'sys_job_group', '1', '默认分组', '', '', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');
INSERT INTO sys_dict VALUES (21, 2, '系统', 'SYSTEM', '任务分组', 'sys_job_group', '1', '系统分组', '', '', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');
INSERT INTO sys_dict VALUES (22, 1, '是', 'Y', '是否', 'sys_yes_no', '1', '系统默认是', '', 'primary', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');
INSERT INTO sys_dict VALUES (23, 2, '否', 'N', '是否', 'sys_yes_no', '1', '系统默认否', '', 'danger', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');
INSERT INTO sys_dict VALUES (24, 1, '通知', '1', '通知类型', 'sys_notice_type', '1', '通知', '', 'warning', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');
INSERT INTO sys_dict VALUES (25, 2, '公告', '2', '通知类型', 'sys_notice_type', '1', '公告', '', 'success', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');
INSERT INTO sys_dict VALUES (26, 1, '正常', '1', '通知状态', 'sys_notice_status', '1', '正常状态', '', 'primary', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');
INSERT INTO sys_dict VALUES (27, 2, '关闭', '0', '通知状态', 'sys_notice_status', '1', '关闭状态', '', 'danger', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');
INSERT INTO sys_dict VALUES (28, 1, '新增', '1', '操作类型', 'sys_oper_type', '1', '新增操作', '', 'info', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');
INSERT INTO sys_dict VALUES (29, 2, '修改', '2', '操作类型', 'sys_oper_type', '1', '修改操作', '', 'info', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');
INSERT INTO sys_dict VALUES (30, 3, '删除', '3', '操作类型', 'sys_oper_type', '1', '删除操作', '', 'danger', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');
INSERT INTO sys_dict VALUES (31, 4, '授权', '4', '操作类型', 'sys_oper_type', '1', '授权操作', '', 'primary', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');
INSERT INTO sys_dict VALUES (32, 5, '导出', '5', '操作类型', 'sys_oper_type', '1', '导出操作', '', 'warning', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');
INSERT INTO sys_dict VALUES (33, 6, '导入', '6', '操作类型', 'sys_oper_type', '1', '导入操作', '', 'warning', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');
INSERT INTO sys_dict VALUES (34, 7, '强退', '7', '操作类型', 'sys_oper_type', '1', '强退操作', '', 'danger', 'admin', '2020-08-28 12:33:00', 'admin', '2020-11-17 22:58:27');
INSERT INTO sys_dict VALUES (35, 8, '生成代码', '8', '操作类型', 'sys_oper_type', '1', '生成操作', '', 'warning', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');
INSERT INTO sys_dict VALUES (36, 9, '清空数据', '9', '操作类型', 'sys_oper_type', '1', '清空操作', '', 'danger', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');
INSERT INTO sys_dict VALUES (37, 1, '成功', '1', '系统状态', 'sys_common_status', '1', '正常状态', '', 'primary', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');
INSERT INTO sys_dict VALUES (38, 2, '失败', '0', '系统状态', 'sys_common_status', '1', '停用状态', '', 'danger', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00');

-- ----------------------------
-- Table structure for sys_log_login
-- ----------------------------
DROP TABLE IF EXISTS sys_log_login;
CREATE TABLE sys_log_login  (
  id bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  user_name varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户账号',
  ip_addr varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '登录IP地址',
  login_location varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '登录地点',
  coordinate varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '所在城市中心经纬度坐标',
  browser varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '浏览器类型',
  os varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作系统',
  status char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '登录状态（1成功 0失败）',
  msg varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '提示消息',
  login_time datetime(0) NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统登录日志记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_log_oper
-- ----------------------------
DROP TABLE IF EXISTS sys_log_oper;
CREATE TABLE sys_log_oper  (
  id bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  title varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '模块标题',
  method varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '方法名称',
  method_desc varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '方法描述',
  request_method varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求方式',
  oper_name varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作人员',
  oper_url varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求URL',
  oper_ip varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '主机地址',
  oper_location varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作地点',
  oper_param varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求参数',
  oper_result varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '返回参数',
  status char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '操作状态（1正常 0异常）',
  error_msg varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '错误消息',
  oper_time datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '操作日志记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS sys_menu;
CREATE TABLE sys_menu  (
  id bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  menu_name varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  parent_id bigint NULL DEFAULT 0 COMMENT '父菜单ID',
  sort_by int NULL DEFAULT 0 COMMENT '显示顺序',
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
) ENGINE = InnoDB AUTO_INCREMENT = 2019 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO sys_menu VALUES (1, '系统管理', 0, 1, 'system', '', '0', 'M', '1', '1', '', 'system', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '系统管理目录');
INSERT INTO sys_menu VALUES (2, '系统监控', 0, 2, 'monitor', '', '0', 'M', '1', '1', '', 'monitor', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '系统监控目录');
INSERT INTO sys_menu VALUES (3, '系统工具', 0, 3, 'tool', '', '0', 'M', '1', '1', '', 'tool', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '系统工具目录');
INSERT INTO sys_menu VALUES (4, '友情链接', 0, 4, 'link', NULL, '0', 'M', '1', '1', '', 'link', 'admin', '2020-09-09 22:01:48', 'admin', '2020-09-09 23:36:52', '项目参考文档链接地址');
INSERT INTO sys_menu VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', '0', 'C', '1', '1', 'system:user:list', 'user', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '用户管理菜单');
INSERT INTO sys_menu VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', '0', 'C', '1', '1', 'system:role:list', 'peoples', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '角色管理菜单');
INSERT INTO sys_menu VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', '0', 'C', '1', '1', 'system:menu:list', 'tree-table', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '菜单管理菜单');
INSERT INTO sys_menu VALUES (105, '字典管理', 1, 6, 'dict', 'system/dict/index', '0', 'C', '1', '1', 'system:dict:list', 'dict', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '字典管理菜单');
INSERT INTO sys_menu VALUES (106, '参数设置', 1, 7, 'config', 'system/config/index', '0', 'C', '1', '1', 'system:config:list', 'edit', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '参数设置菜单');
INSERT INTO sys_menu VALUES (107, '通知公告', 1, 8, 'notice', 'system/notice/index', '0', 'C', '1', '1', 'system:notice:list', 'message', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '通知公告菜单');
INSERT INTO sys_menu VALUES (108, '日志管理', 2, 5, 'log', 'router', '0', 'M', '1', '1', '', 'log', 'admin', '2020-08-28 12:33:00', 'admin', '2020-11-17 20:40:41', '日志管理菜单');
INSERT INTO sys_menu VALUES (109, '在线用户', 2, 1, 'online', 'monitor/online/index', '0', 'C', '1', '1', 'monitor:online:list', 'online', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '在线用户菜单');
INSERT INTO sys_menu VALUES (110, '定时任务', 2, 2, 'job', 'monitor/job/index', '0', 'C', '1', '1', 'monitor:job:list', 'job', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '定时任务菜单');
INSERT INTO sys_menu VALUES (111, '数据源监控', 2, 3, 'druid', 'monitor/druid/index', '0', 'C', '1', '1', 'monitor:druid:list', 'druid', 'admin', '2020-08-28 12:33:00', 'admin', '2020-09-11 21:40:43', '数据监控菜单');
INSERT INTO sys_menu VALUES (112, '服务器监控', 2, 4, 'server', 'monitor/server/index', '0', 'C', '1', '1', 'monitor:server:list', 'server', 'admin', '2020-08-28 12:33:00', 'admin', '2020-09-13 17:03:57', '服务器监控菜单');
INSERT INTO sys_menu VALUES (113, '表单构建', 3, 1, 'build', 'tool/build/index', '0', 'C', '1', '1', 'tool:build:list', 'build', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '表单构建菜单');
INSERT INTO sys_menu VALUES (114, '代码生成', 3, 2, 'gen', 'tool/gen/index', '0', 'C', '1', '1', 'tool:gen:list', 'code', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-28 12:33:00', '代码生成菜单');
INSERT INTO sys_menu VALUES (115, '接口文档', 3, 3, 'swagger', 'tool/swagger/index', '0', 'C', '1', '1', 'tool:swagger:list', 'swagger', 'admin', '2020-08-28 12:33:00', 'admin', '2020-09-11 21:39:32', '系统接口文档');
INSERT INTO sys_menu VALUES (116, '图标库', 3, 4, 'icons', 'tool/icons/index', '0', 'C', '1', '1', 'tool:icons:index', 'icon', 'admin', '2020-12-24 10:13:55', NULL, NULL, '图标库');
INSERT INTO sys_menu VALUES (401, '项目Git地址', 4, 1, 'https://github.com/zmzhou-star/easyboot', '', '1', 'C', '1', '1', NULL, 'github', 'admin', '2020-09-09 23:12:23', 'admin', '2020-09-09 23:15:07', NULL);
INSERT INTO sys_menu VALUES (402, 'vue文档地址', 4, 2, 'https://panjiachen.gitee.io/vue-element-admin-site/zh/', NULL, '1', 'C', '1', '1', NULL, 'documentation', 'admin', '2020-09-09 23:16:56', 'zmzhou', '2020-09-10 21:07:44', NULL);
INSERT INTO sys_menu VALUES (403, '若依官网', 4, 4, 'https://vue.ruoyi.vip', '', '1', 'M', '1', '1', '', 'guide', 'admin', '2020-08-28 12:33:00', 'admin', '2020-11-16 20:36:07', '若依官网地址');
INSERT INTO sys_menu VALUES (404, 'Element组件', 4, 4, 'https://element.eleme.cn/#/zh-CN/component/installation', '', '1', 'M', '1', '1', NULL, 'component', 'admin', '2021-01-04 18:00:12', 'admin', '2021-01-04 18:01:33', 'Element组件地址');
INSERT INTO sys_menu VALUES (500, '操作日志', 108, 2, 'operLog', 'monitor/operLog/index', '0', 'C', '1', '1', 'monitor:operLog:list', 'form', 'admin', '2020-08-28 12:33:00', 'admin', '2020-11-12 20:38:00', '操作日志菜单');
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
INSERT INTO sys_menu VALUES (1040, '操作查询', 500, 1, '#', '', '0', 'F', '1', '1', 'monitor:operLog:query', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-11-12 20:38:13', '');
INSERT INTO sys_menu VALUES (1041, '操作删除', 500, 2, '#', '', '0', 'F', '1', '1', 'monitor:operLog:remove', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-11-12 20:38:23', '');
INSERT INTO sys_menu VALUES (1042, '日志导出', 500, 4, '#', '', '0', 'F', '1', '1', 'monitor:operLog:export', '#', 'admin', '2020-08-28 12:33:00', 'admin', '2020-11-12 20:38:30', '');
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
INSERT INTO sys_menu VALUES (2006, '字典数据', 1, 5, 'dict/data/:dictId(\\d+)', 'system/dict/data', '0', 'C', '0', '1', 'system:dict:list', 'dict', 'zmzhou', '2020-11-18 11:24:27', 'admin', '2020-11-18 11:39:46', '字典数据菜单');
INSERT INTO sys_menu VALUES (2008, '新增字典数据', 2006, 1, NULL, NULL, '0', 'F', '0', '1', 'system:dict:add', '', 'admin', '2020-11-18 11:51:39', NULL, NULL, NULL);
INSERT INTO sys_menu VALUES (2009, '修改字典数据', 2006, 2, NULL, NULL, '0', 'F', '0', '1', 'system:dict:edit', '', 'admin', '2020-11-18 11:52:19', 'admin', '2020-11-18 11:52:55', NULL);
INSERT INTO sys_menu VALUES (2010, '删除字典数据', 2006, 3, NULL, NULL, '0', 'F', '0', '1', 'system:dict:remove', '', 'admin', '2020-11-18 11:52:48', NULL, NULL, NULL);
INSERT INTO sys_menu VALUES (2011, '导出字典数据', 2006, 4, NULL, NULL, '0', 'F', '0', '1', 'system:dict:export', '', 'admin', '2020-11-18 11:53:26', 'admin', '2020-11-18 11:57:46', NULL);
INSERT INTO sys_menu VALUES (2012, '立即执行', 110, 6, NULL, NULL, '0', 'F', '1', '1', 'monitor:job:run', '#', 'admin', '2020-12-18 19:19:16', NULL, NULL, '立即执行定时任务');
INSERT INTO sys_menu VALUES (2013, '定时任务日志', 2, 2, 'job/log', 'monitor/job/log', '0', 'C', '0', '1', 'monitor:jobLog:list', 'log', 'admin', '2020-12-18 19:33:46', 'admin', '2020-12-18 20:57:36', NULL);
INSERT INTO sys_menu VALUES (2014, '查询定时任务日志', 2013, 1, NULL, NULL, '0', 'F', '1', '1', 'monitor:jobLog:list', '#', 'admin', '2020-12-18 21:00:44', 'admin', '2020-12-18 21:01:53', NULL);
INSERT INTO sys_menu VALUES (2015, '删除定时任务日志', 2013, 2, '#', NULL, '0', 'F', '1', '1', 'monitor:jobLog:remove', '#', 'admin', '2020-12-18 21:01:36', 'admin', '2020-12-18 21:05:32', NULL);
INSERT INTO sys_menu VALUES (2016, '导出定时任务日志', 2013, 3, NULL, NULL, '0', 'F', '1', '1', 'monitor:jobLog:export', '#', 'admin', '2020-12-18 21:02:19', 'admin', '2020-12-18 21:04:23', NULL);
INSERT INTO sys_menu VALUES (2017, '定时任务日志按钮', 110, 8, '#', '', '0', 'F', '1', '1', 'monitor:job:log', '#', 'admin', '2020-12-18 21:13:30', 'admin', '2020-12-18 21:16:27', '定时任务日志按钮');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS sys_notice;
CREATE TABLE sys_notice  (
  id int NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  notice_title varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '公告标题',
  notice_type char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '公告类型（1通知 2公告）',
  notice_content longblob NULL COMMENT '公告内容',
  status char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '1' COMMENT '公告状态（1正常 0关闭）',
  create_by varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  create_time datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  update_by varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  update_time datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  remark varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '通知公告信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role  (
  id bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  role_code varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色权限编码',
  role_name varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  sort_by int NOT NULL COMMENT '显示顺序',
  data_scope char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  status char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '角色状态（1正常 0停用 2代表删除）',
  create_by varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  create_time datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  update_by varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  update_time datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  remark varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id) USING BTREE,
  UNIQUE INDEX UNI_ROLE_CODE(role_code) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO sys_role VALUES (1, 'admin', '系统管理员', 1, '1', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-08-30 18:46:08', '管理员');
INSERT INTO sys_role VALUES (2, 'super', '超级管理员', 2, NULL, '1', 'admin', '2020-08-30 00:14:10', 'admin', '2020-11-16 20:37:20', '超级管理员');
INSERT INTO sys_role VALUES (3, 'common', '普通角色', 3, '2', '1', 'admin', '2020-08-28 12:33:00', 'admin', '2020-12-18 21:12:02', '普通角色');
INSERT INTO sys_role VALUES (4, 'develop', '开发人员', 4, NULL, '1', 'admin', '2020-08-29 22:05:57', 'admin', '2020-12-18 21:10:19', '开发人员');
INSERT INTO sys_role VALUES (5, 'test', '测试人员', 5, NULL, '1', 'admin', '2020-08-29 22:09:00', 'admin', '2020-09-14 22:19:40', '测试人员角色');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS sys_role_menu;
CREATE TABLE sys_role_menu  (
  role_id bigint NOT NULL COMMENT '角色ID',
  menu_id bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (role_id, menu_id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO sys_role_menu VALUES (2, 1);
INSERT INTO sys_role_menu VALUES (2, 2);
INSERT INTO sys_role_menu VALUES (2, 3);
INSERT INTO sys_role_menu VALUES (2, 4);
INSERT INTO sys_role_menu VALUES (2, 5);

-- ----------------------------
-- Table structure for sys_task
-- ----------------------------
DROP TABLE IF EXISTS sys_task;
CREATE TABLE sys_task  (
  id bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  job_name varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务名称',
  job_group varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务分组',
  bean_name varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务执行时调用哪个类',
  method_name varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类的方法名',
  method_params varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类的方法参数',
  cron_expression varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'cron表达式',
  misfire_policy char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  concurrent char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否并发执行（1允许 0禁止）',
  status char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '任务状态（1正常 0关闭）',
  create_by varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  create_time datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  update_by varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  update_time datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  remark varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_task
-- ----------------------------
INSERT INTO sys_task VALUES (1, '心跳', 'DEFAULT', 'easyScheduler', 'heartbeat', NULL, '0/30 * * * * ?', '2', '0', '1', 'admin', '2020-12-16 20:58:39', 'admin', '2020-12-18 20:17:32', '心跳');
INSERT INTO sys_task VALUES (4, 'taskWithParams', 'DEFAULT', 'easyScheduler', 'taskWithParams', 'testTaskWithParams', '0 0/1 * * * ?', '3', '0', '1', 'admin', '2020-12-16 22:33:06', 'admin', '2020-12-18 20:17:27', 'taskWithParams');
INSERT INTO sys_task VALUES (5, '删除临时文件', 'DEFAULT', 'easyScheduler', 'deleteTempFile', NULL, '0 0/1 * * * ?', '3', '1', '1', 'admin', '2020-12-18 19:00:07', 'admin', '2020-12-18 20:17:19', '删除临时文件');
INSERT INTO sys_task VALUES (7, '定时查询不在线用户，修改用户状态', 'DEFAULT', 'easyScheduler', 'updateOfflineUserStatus', NULL, '0 0/2 * * * ?', '3', '0', '1', 'admin', '2020-12-18 19:07:10', 'admin', '2020-12-18 20:17:29', '定时查询不在线用户，修改用户状态');
INSERT INTO sys_task VALUES (8, 'heartbeat', 'DEFAULT', 'easyScheduler', 'heartbeat', NULL, '0/50 * * * * ?', '3', '0', '1', 'admin', '2020-12-19 15:25:50', 'admin', '2020-12-19 15:30:44', NULL);

-- ----------------------------
-- Table structure for sys_task_log
-- ----------------------------
DROP TABLE IF EXISTS sys_task_log;
CREATE TABLE sys_task_log  (
  id bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  job_name varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务名称',
  job_group varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务分组',
  bean_name varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务执行时调用哪个类',
  method_name varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类的方法名',
  method_params varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类的方法参数',
  cron_expression varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'cron表达式',
  time_consuming bigint NULL DEFAULT NULL COMMENT '耗时',
  status char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '执行状态（1正常 0失败）',
  exception_info varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '异常信息',
  create_time datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务日志表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user  (
  id bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
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
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO sys_user VALUES (1, 'admin', '$2a$10$sLhbu4HK5nc9MVYXWHP7iOURnk11KfaBiInYT1WiThZjGATirIkDm', '', '系统管理员', 'zmzhou8@gmail.com', '19866165166', '1', '1', '1', '120.238.219.91', '广东省湛江市 移通', '2020-12-20 17:39:12', 'admin', '2020-07-06 11:08:00', 'admin', '2020-11-09 21:42:46', '系统管理员');
INSERT INTO sys_user VALUES (2, 'super', '$2a$10$ITCu.fY4GnLFAtEnfZM.ruZ/Gq3UWpwqrvYVRaGGjQ/XXCPOSZPGi', 'super/avatar.jpeg', '超级管理员', 'zmzhou8@gmail.com', '19866165166', '0', '1', '0', '120.238.219.91', '广东省湛江市 移通', '2020-12-20 17:00:12', 'admin', '2020-08-29 22:02:00', 'admin', '2020-12-20 16:59:59', '超级管理员');
INSERT INTO sys_user VALUES (3, 'zmzhou', '$2a$10$zRIe6ECzh0TLvx9.MSYW7OmaO8C5yfAffoRxlWEiT/GP51SXqxbaW', 'zmzhou/avatar.jpeg', '小周', 'zmzhou8@qq.com', '19866165166', '1', '1', '0', '120.238.219.91', '广东省湛江市 移通', '2020-12-20 17:01:02', 'admin', '2020-07-07 10:28:00', 'zmzhou', '2020-12-20 16:31:20', '开发者');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS sys_user_role;
CREATE TABLE sys_user_role  (
  user_id bigint NOT NULL COMMENT '用户ID',
  role_id bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (user_id, role_id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户和角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO sys_user_role VALUES (1, 1);
INSERT INTO sys_user_role VALUES (2, 2);
INSERT INTO sys_user_role VALUES (3, 4);
INSERT INTO sys_user_role VALUES (5, 4);
INSERT INTO sys_user_role VALUES (35, 5);

SET FOREIGN_KEY_CHECKS = 1;
