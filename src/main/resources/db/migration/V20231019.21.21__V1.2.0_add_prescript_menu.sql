-- 菜单 SQL
INSERT INTO sys_menu (id, menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (5, '病历管理', 0, 1, 'medicalrecord', '', '0', 'M', '1', '1', null, 'peoples', 'admin', '2023-10-19 21:31:11', null, null, null);

insert into sys_menu (menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, update_by, remark)
values('药方', 5, 1, 'prescript', 'medicalrecord/prescript/index', 0, 'C', '1', '1', 'medicalrecord:prescript:list', '#', 'admin', 'admin', '药方菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, update_by, remark)
values('药方查询', @parentId, '1',  '#', '', 0,  'F', '1', '1', 'medicalrecord:prescript:query',        '#', 'admin', 'admin', '');

insert into sys_menu (menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, update_by, remark)
values('药方新增', @parentId, '2',  '#', '', 0,  'F', '1', '1', 'medicalrecord:prescript:add',          '#', 'admin', 'admin', '');

insert into sys_menu (menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, update_by, remark)
values('药方修改', @parentId, '3',  '#', '', 0,  'F', '1', '1', 'medicalrecord:prescript:edit',         '#', 'admin', 'admin', '');

insert into sys_menu (menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, update_by, remark)
values('药方删除', @parentId, '4',  '#', '', 0,  'F', '1', '1', 'medicalrecord:prescript:remove',       '#', 'admin', 'admin', '');

insert into sys_menu (menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, update_by, remark)
values('药方导出', @parentId, '5',  '#', '', 0,  'F', '1', '1', 'medicalrecord:prescript:export',       '#', 'admin', 'admin', '');
