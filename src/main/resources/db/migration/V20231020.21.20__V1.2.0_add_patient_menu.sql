-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, update_by, remark)
values('病人信息', 5, 1, 'patient', 'medicalrecord/patient/index', 0, 'C', '1', '1', 'medicalrecord:patient:list', '#', 'admin', 'admin', '病人信息菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, update_by, remark)
values('病人信息查询', @parentId, '1',  '#', '', 0,  'F', '1', '1', 'medicalrecord:patient:query',        '#', 'admin', 'admin', '');

insert into sys_menu (menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, update_by, remark)
values('病人信息新增', @parentId, '2',  '#', '', 0,  'F', '1', '1', 'medicalrecord:patient:add',          '#', 'admin', 'admin', '');

insert into sys_menu (menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, update_by, remark)
values('病人信息修改', @parentId, '3',  '#', '', 0,  'F', '1', '1', 'medicalrecord:patient:edit',         '#', 'admin', 'admin', '');

insert into sys_menu (menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, update_by, remark)
values('病人信息删除', @parentId, '4',  '#', '', 0,  'F', '1', '1', 'medicalrecord:patient:remove',       '#', 'admin', 'admin', '');

insert into sys_menu (menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, update_by, remark)
values('病人信息导出', @parentId, '5',  '#', '', 0,  'F', '1', '1', 'medicalrecord:patient:export',       '#', 'admin', 'admin', '');
