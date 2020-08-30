-- ----------------------------
-- 1、用户表
-- ----------------------------
drop table if exists SYS_USER;
create table SYS_USER (
  id                bigint(20)      not null auto_increment    comment '用户ID',
  dept_id           bigint(20)      default null               comment '部门ID',
  user_name         varchar(30)     not null                   comment '用户账号',
  password          varchar(100)    default ''                 comment '密码',
  avatar            varchar(100)    default ''                 comment '头像地址',
  nick_name         varchar(30)     not null                   comment '用户昵称',
  user_type         varchar(2)      default '00'               comment '用户类型（00系统用户）',
  email             varchar(50)     default ''                 comment '用户邮箱',
  tel               varchar(11)     default ''                 comment '手机号码',
  sex               char(1)         default '1'                comment '用户性别（1男 0女 2未知）',
  status            char(1)         default '1'                comment '帐号状态（1正常 0停用）',
  login_ip          varchar(50)     default ''                 comment '最后登陆IP',
  login_date        datetime                                   comment '最后登陆时间',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime        DEFAULT CURRENT_TIMESTAMP  comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime        DEFAULT CURRENT_TIMESTAMP  comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (id),
  UNIQUE INDEX UNI_USER_NAME(user_name)
) engine=innodb auto_increment=3 comment = '用户信息表';

INSERT INTO SYS_USER VALUES (1, 1, 'admin', '$2a$10$QhJlrcTPQZuNmI1esT3x4OmFyafvIzMNspe3nYIOgZiT8F2vuVuFi', '', '管理员', '00', 'zmzhou8@qq.com', '17363939598', '1', '1', '', '2020-07-06 11:08:23', 'admin', '2020-07-06 11:08:35', 'admin', '2020-08-26 16:23:02', '管理员');
INSERT INTO SYS_USER VALUES (2, 1, 'test', '$2a$10$QaNzTFRkjcbOo8mysWNxl.LaT/AWV9CI/iasPdRlCDX0dsRZoDYNy', 'logo.png', '测试', '00', 'test@qq.com', '', '0', '1', '', '2020-07-07 10:28:10', 'admin', '2020-06-02 10:28:17', 'admin', '2020-07-21 17:38:15', 'ces');
INSERT INTO SYS_USER VALUES (3, 1, 'zmzhou', '$2a$10$8BNdkinCASP3tXZ8mLWspeJHYJXJxFU2cvokYygSpXUQdgpCAx05m', 'logo.png', '小周', '00', 'test@qq.com', '19866666666', '1', '1', '', '2020-07-07 10:28:10', 'admin', '2020-07-07 10:28:17', 'admin', '2020-08-26 16:27:26', '测试');


-- ----------------------------
-- 2、角色信息表
-- ----------------------------
drop table if exists SYS_ROLE;
create table SYS_ROLE (
	id           	  bigint(20)      not null auto_increment    comment '角色ID',
	role_code         varchar(100)    not null                   comment '角色权限编码',
	role_name         varchar(30)     not null                   comment '角色名称',
	sort_by           int(4)          not null                   comment '显示顺序',
	data_scope        char(1)         default '1'                comment '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
	status            char(1)         not null default '1'       comment '角色状态（1正常 0停用 2代表删除）',
	create_by         varchar(64)     default ''                 comment '创建者',
	create_time       datetime        DEFAULT CURRENT_TIMESTAMP  comment '创建时间',
	update_by         varchar(64)     default ''                 comment '更新者',
	update_time       datetime        DEFAULT CURRENT_TIMESTAMP  comment '更新时间',
	remark            varchar(500)    default null               comment '备注',
	primary key (id),
	UNIQUE INDEX UNI_ROLE_CODE(role_code)
) engine=innodb auto_increment=1 comment = '角色信息表';

-- ----------------------------
-- 初始化-角色信息表数据
-- ----------------------------
insert into SYS_ROLE values('1', 'admin','管理员',     1, 1, '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '管理员');
insert into SYS_ROLE values('2', 'common','普通角色',  2, 2, '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '普通角色');


-- ----------------------------
-- 3、菜单权限表
-- ----------------------------
drop table if exists SYS_MENU;
create table SYS_MENU (
	id           	  bigint(20)      not null auto_increment    comment '菜单ID',
	menu_name         varchar(50)     not null                   comment '菜单名称',
	parent_id         bigint(20)      default 0                  comment '父菜单ID',
	sort_by           int(4)          default 0                  comment '显示顺序',
	path              varchar(200)    default ''                 comment '路由地址',
	component         varchar(255)    default null               comment '组件路径',
	is_frame          int(1)          default 1                  comment '是否为外链（1是 0否）',
	menu_type         char(1)         default ''                 comment '菜单类型（M目录 C菜单 F按钮）',
	visible           char(1)         default 0                  comment '菜单状态（1显示 0隐藏）',
	status            char(1)         default 0                  comment '菜单状态（1正常 0停用）',
	perms             varchar(100)    default null               comment '权限标识',
	icon              varchar(100)    default '#'                comment '菜单图标',
	create_by         varchar(64)     default ''                 comment '创建者',
	create_time       datetime        DEFAULT CURRENT_TIMESTAMP  comment '创建时间',
	update_by         varchar(64)     default ''                 comment '更新者',
	update_time       datetime        DEFAULT CURRENT_TIMESTAMP   comment '更新时间',
	remark            varchar(500)    default ''                 comment '备注',
	primary key (id)
) engine=innodb auto_increment=2000 comment = '菜单权限表';

-- ----------------------------
-- 初始化-菜单信息表数据
-- ----------------------------
-- 一级菜单
insert into SYS_MENU values('1', '系统管理', '0', '1', 'system',           null,   1, 'M', '0', '0', '', 'system',   'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '系统管理目录');
insert into SYS_MENU values('2', '系统监控', '0', '2', 'monitor',          null,   1, 'M', '0', '0', '', 'monitor',  'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '系统监控目录');
insert into SYS_MENU values('3', '系统工具', '0', '3', 'tool',             null,   1, 'M', '0', '0', '', 'tool',     'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '系统工具目录');
insert into SYS_MENU values('4', '若依官网', '0', '4', 'http://ruoyi.vip', null ,  0, 'M', '0', '0', '', 'guide',    'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '若依官网地址');
-- 二级菜单
insert into SYS_MENU values('100',  '用户管理', '1',   '1', 'user',       'system/user/index',        1, 'C', '0', '0', 'system:user:list',        'user',          'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '用户管理菜单');
insert into SYS_MENU values('101',  '角色管理', '1',   '2', 'role',       'system/role/index',        1, 'C', '0', '0', 'system:role:list',        'peoples',       'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '角色管理菜单');
insert into SYS_MENU values('102',  '菜单管理', '1',   '3', 'menu',       'system/menu/index',        1, 'C', '0', '0', 'system:menu:list',        'tree-table',    'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '菜单管理菜单');
insert into SYS_MENU values('103',  '部门管理', '1',   '4', 'dept',       'system/dept/index',        1, 'C', '0', '0', 'system:dept:list',        'tree',          'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '部门管理菜单');
insert into SYS_MENU values('104',  '岗位管理', '1',   '5', 'post',       'system/post/index',        1, 'C', '0', '0', 'system:post:list',        'post',          'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '岗位管理菜单');
insert into SYS_MENU values('105',  '字典管理', '1',   '6', 'dict',       'system/dict/index',        1, 'C', '0', '0', 'system:dict:list',        'dict',          'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '字典管理菜单');
insert into SYS_MENU values('106',  '参数设置', '1',   '7', 'config',     'system/config/index',      1, 'C', '0', '0', 'system:config:list',      'edit',          'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '参数设置菜单');
insert into SYS_MENU values('107',  '通知公告', '1',   '8', 'notice',     'system/notice/index',      1, 'C', '0', '0', 'system:notice:list',      'message',       'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '通知公告菜单');
insert into SYS_MENU values('108',  '日志管理', '1',   '9', 'log',        'system/log/index',         1, 'M', '0', '0', '',                        'log',           'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '日志管理菜单');
insert into SYS_MENU values('109',  '在线用户', '2',   '1', 'online',     'monitor/online/index',     1, 'C', '0', '0', 'monitor:online:list',     'online',        'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '在线用户菜单');
insert into SYS_MENU values('110',  '定时任务', '2',   '2', 'job',        'monitor/job/index',        1, 'C', '0', '0', 'monitor:job:list',        'job',           'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '定时任务菜单');
insert into SYS_MENU values('111',  '数据监控', '2',   '3', 'druid',      'monitor/druid/index',      1, 'C', '0', '0', 'monitor:druid:list',      'druid',         'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '数据监控菜单');
insert into SYS_MENU values('112',  '服务监控', '2',   '4', 'server',     'monitor/server/index',     1, 'C', '0', '0', 'monitor:server:list',     'server',        'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '服务监控菜单');
insert into SYS_MENU values('113',  '表单构建', '3',   '1', 'build',      'tool/build/index',         1 ,'C', '0', '0', 'tool:build:list',         'build',         'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '表单构建菜单');
insert into SYS_MENU values('114',  '代码生成', '3',   '2', 'gen',        'tool/gen/index',           1, 'C', '0', '0', 'tool:gen:list',           'code',          'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '代码生成菜单');
insert into SYS_MENU values('115',  '系统接口', '3',   '3', 'swagger',    'tool/swagger/index',       1, 'C', '0', '0', 'tool:swagger:list',       'swagger',       'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '系统接口菜单');
-- 三级菜单
insert into SYS_MENU values('500',  '操作日志', '108', '1', 'operlog',    'monitor/operlog/index',    1, 'C', '0', '0', 'monitor:operlog:list',    'form',          'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '操作日志菜单');
insert into SYS_MENU values('501',  '登录日志', '108', '2', 'logininfor', 'monitor/logininfor/index', 1, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor',    'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '登录日志菜单');
-- 用户管理按钮
insert into SYS_MENU values('1001', '用户查询', '100', '1',  '', '', 1, 'F', '0', '0', 'system:user:query',          '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1002', '用户新增', '100', '2',  '', '', 1, 'F', '0', '0', 'system:user:add',            '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1003', '用户修改', '100', '3',  '', '', 1, 'F', '0', '0', 'system:user:edit',           '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1004', '用户删除', '100', '4',  '', '', 1, 'F', '0', '0', 'system:user:remove',         '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1005', '用户导出', '100', '5',  '', '', 1, 'F', '0', '0', 'system:user:export',         '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1006', '用户导入', '100', '6',  '', '', 1, 'F', '0', '0', 'system:user:import',         '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1007', '重置密码', '100', '7',  '', '', 1, 'F', '0', '0', 'system:user:resetPwd',       '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
-- 角色管理按钮
insert into SYS_MENU values('1008', '角色查询', '101', '1',  '', '', 1, 'F', '0', '0', 'system:role:query',          '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1009', '角色新增', '101', '2',  '', '', 1, 'F', '0', '0', 'system:role:add',            '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1010', '角色修改', '101', '3',  '', '', 1, 'F', '0', '0', 'system:role:edit',           '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1011', '角色删除', '101', '4',  '', '', 1, 'F', '0', '0', 'system:role:remove',         '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1012', '角色导出', '101', '5',  '', '', 1, 'F', '0', '0', 'system:role:export',         '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
-- 菜单管理按钮
insert into SYS_MENU values('1013', '菜单查询', '102', '1',  '', '', 1, 'F', '0', '0', 'system:menu:query',          '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1014', '菜单新增', '102', '2',  '', '', 1, 'F', '0', '0', 'system:menu:add',            '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1015', '菜单修改', '102', '3',  '', '', 1, 'F', '0', '0', 'system:menu:edit',           '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1016', '菜单删除', '102', '4',  '', '', 1, 'F', '0', '0', 'system:menu:remove',         '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
-- 部门管理按钮
insert into SYS_MENU values('1017', '部门查询', '103', '1',  '', '', 1, 'F', '0', '0', 'system:dept:query',          '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1018', '部门新增', '103', '2',  '', '', 1, 'F', '0', '0', 'system:dept:add',            '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1019', '部门修改', '103', '3',  '', '', 1, 'F', '0', '0', 'system:dept:edit',           '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1020', '部门删除', '103', '4',  '', '', 1, 'F', '0', '0', 'system:dept:remove',         '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
-- 岗位管理按钮
insert into SYS_MENU values('1021', '岗位查询', '104', '1',  '', '', 1, 'F', '0', '0', 'system:post:query',          '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1022', '岗位新增', '104', '2',  '', '', 1, 'F', '0', '0', 'system:post:add',            '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1023', '岗位修改', '104', '3',  '', '', 1, 'F', '0', '0', 'system:post:edit',           '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1024', '岗位删除', '104', '4',  '', '', 1, 'F', '0', '0', 'system:post:remove',         '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1025', '岗位导出', '104', '5',  '', '', 1, 'F', '0', '0', 'system:post:export',         '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
-- 字典管理按钮
insert into SYS_MENU values('1026', '字典查询', '105', '1', '#', '', 1, 'F', '0', '0', 'system:dict:query',          '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1027', '字典新增', '105', '2', '#', '', 1, 'F', '0', '0', 'system:dict:add',            '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1028', '字典修改', '105', '3', '#', '', 1, 'F', '0', '0', 'system:dict:edit',           '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1029', '字典删除', '105', '4', '#', '', 1, 'F', '0', '0', 'system:dict:remove',         '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1030', '字典导出', '105', '5', '#', '', 1, 'F', '0', '0', 'system:dict:export',         '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
-- 参数设置按钮
insert into SYS_MENU values('1031', '参数查询', '106', '1', '#', '', 1, 'F', '0', '0', 'system:config:query',        '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1032', '参数新增', '106', '2', '#', '', 1, 'F', '0', '0', 'system:config:add',          '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1033', '参数修改', '106', '3', '#', '', 1, 'F', '0', '0', 'system:config:edit',         '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1034', '参数删除', '106', '4', '#', '', 1, 'F', '0', '0', 'system:config:remove',       '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1035', '参数导出', '106', '5', '#', '', 1, 'F', '0', '0', 'system:config:export',       '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
-- 通知公告按钮
insert into SYS_MENU values('1036', '公告查询', '107', '1', '#', '', 1, 'F', '0', '0', 'system:notice:query',        '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1037', '公告新增', '107', '2', '#', '', 1, 'F', '0', '0', 'system:notice:add',          '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1038', '公告修改', '107', '3', '#', '', 1, 'F', '0', '0', 'system:notice:edit',         '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1039', '公告删除', '107', '4', '#', '', 1, 'F', '0', '0', 'system:notice:remove',       '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
-- 操作日志按钮
insert into SYS_MENU values('1040', '操作查询', '500', '1', '#', '', 1, 'F', '0', '0', 'monitor:operlog:query',      '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1041', '操作删除', '500', '2', '#', '', 1, 'F', '0', '0', 'monitor:operlog:remove',     '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1042', '日志导出', '500', '4', '#', '', 1, 'F', '0', '0', 'monitor:operlog:export',     '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
-- 登录日志按钮
insert into SYS_MENU values('1043', '登录查询', '501', '1', '#', '', 1, 'F', '0', '0', 'monitor:logininfor:query',   '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1044', '登录删除', '501', '2', '#', '', 1, 'F', '0', '0', 'monitor:logininfor:remove',  '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1045', '日志导出', '501', '3', '#', '', 1, 'F', '0', '0', 'monitor:logininfor:export',  '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
-- 在线用户按钮
insert into SYS_MENU values('1046', '在线查询', '109', '1', '#', '', 1, 'F', '0', '0', 'monitor:online:query',       '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1047', '批量强退', '109', '2', '#', '', 1, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1048', '单条强退', '109', '3', '#', '', 1, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
-- 定时任务按钮
insert into SYS_MENU values('1049', '任务查询', '110', '1', '#', '', 1, 'F', '0', '0', 'monitor:job:query',          '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1050', '任务新增', '110', '2', '#', '', 1, 'F', '0', '0', 'monitor:job:add',            '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1051', '任务修改', '110', '3', '#', '', 1, 'F', '0', '0', 'monitor:job:edit',           '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1052', '任务删除', '110', '4', '#', '', 1, 'F', '0', '0', 'monitor:job:remove',         '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1053', '状态修改', '110', '5', '#', '', 1, 'F', '0', '0', 'monitor:job:changeStatus',   '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1054', '任务导出', '110', '7', '#', '', 1, 'F', '0', '0', 'monitor:job:export',         '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
-- 代码生成按钮
insert into SYS_MENU values('1055', '生成查询', '114', '1', '#', '', 1, 'F', '0', '0', 'tool:gen:query',             '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1056', '生成修改', '114', '2', '#', '', 1, 'F', '0', '0', 'tool:gen:edit',              '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1057', '生成删除', '114', '3', '#', '', 1, 'F', '0', '0', 'tool:gen:remove',            '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1058', '导入代码', '114', '2', '#', '', 1, 'F', '0', '0', 'tool:gen:import',            '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1059', '预览代码', '114', '4', '#', '', 1, 'F', '0', '0', 'tool:gen:preview',           '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');
insert into SYS_MENU values('1060', '生成代码', '114', '5', '#', '', 1, 'F', '0', '0', 'tool:gen:code',              '#', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '');


-- ----------------------------
-- 4、用户和角色关联表  用户N-1角色
-- ----------------------------
drop table if exists SYS_USER_ROLE;
create table SYS_USER_ROLE (
	user_id   bigint(20) not null comment '用户ID',
	role_id   bigint(20) not null comment '角色ID',
	primary key(user_id, role_id)
) engine=innodb auto_increment=3 comment = '用户和角色关联表';

-- ----------------------------
-- 初始化-用户和角色关联表数据
-- ----------------------------
insert into SYS_USER_ROLE values ('1', '1');
insert into SYS_USER_ROLE values ('2', '2');


-- ----------------------------
-- 5、角色和菜单关联表  角色1-N菜单
-- ----------------------------
drop table if exists SYS_ROLE_MENU;
create table SYS_ROLE_MENU (
	role_id   bigint(20) not null comment '角色ID',
	menu_id   bigint(20) not null comment '菜单ID',
	primary key(role_id, menu_id)
) engine=innodb comment = '角色和菜单关联表';

-- ----------------------------
-- 初始化-角色和菜单关联表数据
-- ----------------------------
insert into SYS_ROLE_MENU values ('2', '1');
insert into SYS_ROLE_MENU values ('2', '2');
insert into SYS_ROLE_MENU values ('2', '3');
insert into SYS_ROLE_MENU values ('2', '4');
insert into SYS_ROLE_MENU values ('2', '100');
insert into SYS_ROLE_MENU values ('2', '101');
insert into SYS_ROLE_MENU values ('2', '102');
insert into SYS_ROLE_MENU values ('2', '103');

-- ----------------------------
-- 6、数据字典表
-- ----------------------------
drop table if exists SYS_DICT;
create table SYS_DICT
(
	id        		 bigint(20)      not null auto_increment    comment '字典编码',
	dict_sort        int(4)          default 0                  comment '字典排序',
	dict_label       varchar(100)    default ''                 comment '字典标签',
	dict_value       varchar(100)    default ''                 comment '字典键值',
	dict_type        varchar(100)    default ''                 comment '字典类型',
	css_class        varchar(100)    default null               comment '样式属性（其他样式扩展）',
	list_class       varchar(100)    default null               comment '表格回显样式',
	is_default       char(1)         default 'N'                comment '是否默认（Y是 N否）',
	status           char(1)         default '1'                comment '状态（1正常 0停用）',
	create_by        varchar(64)     default ''                 comment '创建者',
	create_time      datetime        DEFAULT CURRENT_TIMESTAMP  comment '创建时间',
	update_by        varchar(64)     default ''                 comment '更新者',
	update_time      datetime        DEFAULT CURRENT_TIMESTAMP  comment '更新时间',
	remark           varchar(500)    default null               comment '备注',
	primary key (id)
) engine=innodb auto_increment=30 comment = '数据字典表';

insert into SYS_DICT values(1,  1,  '男',       '0',       'sys_user_sex',        '',   '',        'Y', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '性别男');
insert into SYS_DICT values(2,  2,  '女',       '1',       'sys_user_sex',        '',   '',        'N', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '性别女');
insert into SYS_DICT values(3,  3,  '未知',     '2',       'sys_user_sex',        '',   '',        'N', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '性别未知');
insert into SYS_DICT values(4,  1,  '显示',     '0',       'sys_show_hide',       '',   'primary', 'Y', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '显示菜单');
insert into SYS_DICT values(5,  2,  '隐藏',     '1',       'sys_show_hide',       '',   'danger',  'N', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '隐藏菜单');
insert into SYS_DICT values(6,  1,  '正常',     '0',       'sys_normal_disable',  '',   'primary', 'Y', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '正常状态');
insert into SYS_DICT values(7,  2,  '停用',     '1',       'sys_normal_disable',  '',   'danger',  'N', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '停用状态');
insert into SYS_DICT values(8,  1,  '正常',     '0',       'sys_job_status',      '',   'primary', 'Y', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '正常状态');
insert into SYS_DICT values(9,  2,  '暂停',     '1',       'sys_job_status',      '',   'danger',  'N', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '停用状态');
insert into SYS_DICT values(10, 1,  '默认',     'DEFAULT', 'sys_job_group',       '',   '',        'Y', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '默认分组');
insert into SYS_DICT values(11, 2,  '系统',     'SYSTEM',  'sys_job_group',       '',   '',        'N', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '系统分组');
insert into SYS_DICT values(12, 1,  '是',       'Y',       'sys_yes_no',          '',   'primary', 'Y', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '系统默认是');
insert into SYS_DICT values(13, 2,  '否',       'N',       'sys_yes_no',          '',   'danger',  'N', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '系统默认否');
insert into SYS_DICT values(14, 1,  '通知',     '1',       'sys_notice_type',     '',   'warning', 'Y', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '通知');
insert into SYS_DICT values(15, 2,  '公告',     '2',       'sys_notice_type',     '',   'success', 'N', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '公告');
insert into SYS_DICT values(16, 1,  '正常',     '0',       'sys_notice_status',   '',   'primary', 'Y', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '正常状态');
insert into SYS_DICT values(17, 2,  '关闭',     '1',       'sys_notice_status',   '',   'danger',  'N', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '关闭状态');
insert into SYS_DICT values(18, 1,  '新增',     '1',       'sys_oper_type',       '',   'info',    'N', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '新增操作');
insert into SYS_DICT values(19, 2,  '修改',     '2',       'sys_oper_type',       '',   'info',    'N', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '修改操作');
insert into SYS_DICT values(20, 3,  '删除',     '3',       'sys_oper_type',       '',   'danger',  'N', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '删除操作');
insert into SYS_DICT values(21, 4,  '授权',     '4',       'sys_oper_type',       '',   'primary', 'N', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '授权操作');
insert into SYS_DICT values(22, 5,  '导出',     '5',       'sys_oper_type',       '',   'warning', 'N', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '导出操作');
insert into SYS_DICT values(23, 6,  '导入',     '6',       'sys_oper_type',       '',   'warning', 'N', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '导入操作');
insert into SYS_DICT values(24, 7,  '强退',     '7',       'sys_oper_type',       '',   'danger',  'N', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '强退操作');
insert into SYS_DICT values(25, 8,  '生成代码', '8',       'sys_oper_type',       '',   'warning', 'N', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '生成操作');
insert into SYS_DICT values(26, 9,  '清空数据', '9',       'sys_oper_type',       '',   'danger',  'N', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '清空操作');
insert into SYS_DICT values(27, 1,  '成功',     '0',       'sys_common_status',   '',   'primary', 'N', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '正常状态');
insert into SYS_DICT values(28, 2,  '失败',     '1',       'sys_common_status',   '',   'danger',  'N', '1', 'admin', '2020-08-28 12-33-00', 'admin', '2020-08-28 12-33-00', '停用状态');

