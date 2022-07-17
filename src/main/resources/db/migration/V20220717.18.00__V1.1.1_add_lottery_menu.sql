-- 添加唯一索引
drop procedure if exists addIndex;
create procedure addIndex()
begin
    if not exists(select * from information_schema.statistics where table_schema=(select database()) and TABLE_NAME = 'lottery_history' and INDEX_NAME='unidx_lottery_history_id')
    then
        create unique index unidx_lottery_history_id on lottery_history(lottery_id, lottery_type);
    end if;
end;
call addIndex();
drop procedure if exists addIndex;

INSERT INTO sys_menu (menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, update_by, remark)
 VALUES ('小程序管理', 0, 4, 'miniapp', '', '0', 'M', '1', '1', '', 'miniapp', 'admin', 'admin', '');

-- 按钮父菜单ID
SELECT @miniAppParentId := LAST_INSERT_ID();

-- 彩票历史数据查询页面菜单 SQL
insert into sys_menu (menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, update_by, remark)
values('彩票历史数据', @miniAppParentId, 1, 'history', 'miniapp/lottery/history/index', 0, 'C', '1', '1', 'miniapp.lottery:history:list', 'lottery', 'admin', 'admin', '彩票历史数据菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, update_by, remark)
values('彩票历史数据查询', @parentId, '1',  '#', '', 0,  'F', '1', '1', 'miniapp.lottery:history:query',        '#', 'admin', 'admin', '');

insert into sys_menu (menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, update_by, remark)
values('彩票历史数据新增', @parentId, '2',  '#', '', 0,  'F', '1', '1', 'miniapp.lottery:history:add',          '#', 'admin', 'admin', '');

insert into sys_menu (menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, update_by, remark)
values('彩票历史数据修改', @parentId, '3',  '#', '', 0,  'F', '1', '1', 'miniapp.lottery:history:edit',         '#', 'admin', 'admin', '');

insert into sys_menu (menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, update_by, remark)
values('彩票历史数据删除', @parentId, '4',  '#', '', 0,  'F', '1', '1', 'miniapp.lottery:history:remove',       '#', 'admin', 'admin', '');

insert into sys_menu (menu_name, parent_id, sort_by, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, update_by, remark)
values('彩票历史数据导出', @parentId, '5',  '#', '', 0,  'F', '1', '1', 'miniapp.lottery:history:export',       '#', 'admin', 'admin', '');
