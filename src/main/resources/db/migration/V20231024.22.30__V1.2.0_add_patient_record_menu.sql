-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, update_by, remark)
values('看诊记录', 5, 2, 'record', 'medicalrecord/record/index', 0, 'C', '1', '1', 'medicalrecord:record:list', '#', 'admin', 'admin', '看诊记录菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, update_by, remark)
values('看诊记录查询', @parentId, '1',  '#', '', 0,  'F', '1', '1', 'medicalrecord:record:query',        '#', 'admin', 'admin', '');

insert into sys_menu (menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, update_by, remark)
values('看诊记录新增', @parentId, '2',  '#', '', 0,  'F', '1', '1', 'medicalrecord:record:add',          '#', 'admin', 'admin', '');

insert into sys_menu (menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, update_by, remark)
values('看诊记录修改', @parentId, '3',  '#', '', 0,  'F', '1', '1', 'medicalrecord:record:edit',         '#', 'admin', 'admin', '');

insert into sys_menu (menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, update_by, remark)
values('看诊记录删除', @parentId, '4',  '#', '', 0,  'F', '1', '1', 'medicalrecord:record:remove',       '#', 'admin', 'admin', '');

insert into sys_menu (menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, update_by, remark)
values('看诊记录导出', @parentId, '5',  '#', '', 0,  'F', '1', '1', 'medicalrecord:record:export',       '#', 'admin', 'admin', '');
