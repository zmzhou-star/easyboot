# EasyBoot数据库设计文档

**数据库名：** easyboot

**文档版本：** 1.0.0

**文档描述：** EasyBoot数据库设计文档

| 表名                  | 说明       |
| :---: | :---: |
| [code_gen_table](#code_gen_table) | 代码生成业务表 |
| [code_gen_table_column](#code_gen_table_column) | 代码生成业务表字段 |
| [flyway_schema_history](#flyway_schema_history) |  |
| [sys_config](#sys_config) | 参数配置表 |
| [sys_dict](#sys_dict) | 数据字典表 |
| [sys_log_login](#sys_log_login) | 系统登录日志记录 |
| [sys_log_oper](#sys_log_oper) | 操作日志记录 |
| [sys_mail](#sys_mail) | 系统邮件记录表 |
| [sys_menu](#sys_menu) | 菜单权限表 |
| [sys_notice](#sys_notice) | 通知公告信息表 |
| [sys_role](#sys_role) | 角色信息表 |
| [sys_role_menu](#sys_role_menu) | 角色和菜单关联表 |
| [sys_task](#sys_task) | 定时任务表 |
| [sys_task_log](#sys_task_log) | 定时任务日志表 |
| [sys_user](#sys_user) | 用户信息表 |
| [sys_user_role](#sys_user_role) | 用户和角色关联表 |
| [sys_wechat_mini_user](#sys_wechat_mini_user) | 微信小程序用户信息表 |

**表名：** <a id="code_gen_table">code_gen_table</a>

**说明：** 代码生成业务表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   bigint   | 20 |   0    |    N     |  Y   |       | 编号  |
|  2   | table_name |   varchar   | 200 |   0    |    N     |  N   |       | 表名称  |
|  3   | table_comment |   varchar   | 500 |   0    |    Y     |  N   |       | 表描述  |
|  4   | class_name |   varchar   | 100 |   0    |    Y     |  N   |       | 实体类名称  |
|  5   | tpl_category |   varchar   | 200 |   0    |    Y     |  N   |   crud    | 使用的模板（crud单表操作tree树表操作）  |
|  6   | package_name |   varchar   | 100 |   0    |    Y     |  N   |       | 生成包路径  |
|  7   | module_name |   varchar   | 30 |   0    |    Y     |  N   |       | 生成模块名  |
|  8   | business_name |   varchar   | 30 |   0    |    Y     |  N   |       | 生成业务名  |
|  9   | function_name |   varchar   | 50 |   0    |    Y     |  N   |       | 生成功能名  |
|  10   | function_author |   varchar   | 50 |   0    |    Y     |  N   |       | 生成功能作者  |
|  11   | others |   varchar   | 1000 |   0    |    Y     |  N   |       | 其它生成选项  |
|  12   | create_by |   varchar   | 64 |   0    |    Y     |  N   |       | 创建者  |
|  13   | create_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 创建时间  |
|  14   | update_by |   varchar   | 64 |   0    |    Y     |  N   |       | 更新者  |
|  15   | update_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 更新时间  |
|  16   | remark |   varchar   | 500 |   0    |    Y     |  N   |       | 备注  |

**表名：** <a id="code_gen_table_column">code_gen_table_column</a>

**说明：** 代码生成业务表字段

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   bigint   | 20 |   0    |    N     |  Y   |       | 编号  |
|  2   | table_id |   bigint   | 20 |   0    |    N     |  N   |       | 所属表编号  |
|  3   | column_name |   varchar   | 200 |   0    |    N     |  N   |       | 列名称  |
|  4   | column_comment |   varchar   | 500 |   0    |    Y     |  N   |       | 列描述  |
|  5   | column_type |   varchar   | 100 |   0    |    N     |  N   |       | 列类型  |
|  6   | java_type |   varchar   | 500 |   0    |    Y     |  N   |       | JAVA类型  |
|  7   | java_field |   varchar   | 200 |   0    |    Y     |  N   |       | JAVA字段名  |
|  8   | is_pk |   char   | 1 |   0    |    Y     |  N   |       | 是否主键（1是）  |
|  9   | is_increment |   char   | 1 |   0    |    Y     |  N   |       | 是否自增（1是）  |
|  10   | is_required |   char   | 1 |   0    |    Y     |  N   |       | 是否必填（1是）  |
|  11   | is_insert |   char   | 1 |   0    |    Y     |  N   |       | 是否为插入字段（1是）  |
|  12   | is_edit |   char   | 1 |   0    |    Y     |  N   |       | 是否编辑字段（1是）  |
|  13   | is_list |   char   | 1 |   0    |    Y     |  N   |       | 是否列表字段（1是）  |
|  14   | is_query |   char   | 1 |   0    |    Y     |  N   |       | 是否查询字段（1是）  |
|  15   | query_type |   varchar   | 200 |   0    |    Y     |  N   |   EQ    | 查询方式（等于、不等于、大于、小于、范围）  |
|  16   | html_type |   varchar   | 200 |   0    |    Y     |  N   |       | 显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）  |
|  17   | dict_type |   varchar   | 200 |   0    |    Y     |  N   |       | 字典类型  |
|  18   | sort_by |   int   | 10 |   0    |    Y     |  N   |       | 排序  |
|  19   | create_by |   varchar   | 64 |   0    |    Y     |  N   |       | 创建者  |
|  20   | create_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 创建时间  |
|  21   | update_by |   varchar   | 64 |   0    |    Y     |  N   |       | 更新者  |
|  22   | update_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 更新时间  |
|  23   | remark |   varchar   | 500 |   0    |    Y     |  N   |       | 备注  |

**表名：** <a id="flyway_schema_history">flyway_schema_history</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | installed_rank |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | version |   varchar   | 50 |   0    |    Y     |  N   |       |   |
|  3   | description |   varchar   | 200 |   0    |    N     |  N   |       |   |
|  4   | type |   varchar   | 20 |   0    |    N     |  N   |       |   |
|  5   | script |   varchar   | 1000 |   0    |    N     |  N   |       |   |
|  6   | checksum |   int   | 10 |   0    |    Y     |  N   |       |   |
|  7   | installed_by |   varchar   | 100 |   0    |    N     |  N   |       |   |
|  8   | installed_on |   timestamp   | 19 |   0    |    N     |  N   |   CURRENT_TIMESTAMP    |   |
|  9   | execution_time |   int   | 10 |   0    |    N     |  N   |       |   |
|  10   | success |   bit   | 1 |   0    |    N     |  N   |       |   |

**表名：** <a id="sys_config">sys_config</a>

**说明：** 参数配置表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 参数主键  |
|  2   | config_name |   varchar   | 100 |   0    |    Y     |  N   |       | 参数名称  |
|  3   | config_key |   varchar   | 100 |   0    |    Y     |  N   |       | 参数键名  |
|  4   | config_value |   varchar   | 500 |   0    |    Y     |  N   |       | 参数键值  |
|  5   | config_type |   char   | 1 |   0    |    Y     |  N   |   N    | 系统内置（Y是N否）  |
|  6   | create_by |   varchar   | 64 |   0    |    Y     |  N   |       | 创建者  |
|  7   | create_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 创建时间  |
|  8   | update_by |   varchar   | 64 |   0    |    Y     |  N   |       | 更新者  |
|  9   | update_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 更新时间  |
|  10   | remark |   varchar   | 500 |   0    |    Y     |  N   |       | 备注  |

**表名：** <a id="sys_dict">sys_dict</a>

**说明：** 数据字典表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   bigint   | 20 |   0    |    N     |  Y   |       | 字典编码  |
|  2   | dict_sort |   int   | 10 |   0    |    Y     |  N   |   0    | 字典排序  |
|  3   | dict_label |   varchar   | 100 |   0    |    Y     |  N   |       | 字典标签  |
|  4   | dict_value |   varchar   | 100 |   0    |    Y     |  N   |       | 字典键值  |
|  5   | dict_name |   varchar   | 64 |   0    |    Y     |  N   |       | 字典名称  |
|  6   | dict_type |   varchar   | 100 |   0    |    Y     |  N   |       | 字典类型  |
|  7   | status |   char   | 1 |   0    |    Y     |  N   |   1    | 状态（1正常0停用）  |
|  8   | remark |   varchar   | 500 |   0    |    Y     |  N   |       | 备注  |
|  9   | css_class |   varchar   | 100 |   0    |    Y     |  N   |       | 样式属性（其他样式扩展）  |
|  10   | list_class |   varchar   | 100 |   0    |    Y     |  N   |       | 表格回显样式  |
|  11   | create_by |   varchar   | 64 |   0    |    Y     |  N   |       | 创建者  |
|  12   | create_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 创建时间  |
|  13   | update_by |   varchar   | 64 |   0    |    Y     |  N   |       | 更新者  |
|  14   | update_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 更新时间  |

**表名：** <a id="sys_log_login">sys_log_login</a>

**说明：** 系统登录日志记录

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   bigint   | 20 |   0    |    N     |  Y   |       | ID  |
|  2   | user_name |   varchar   | 30 |   0    |    Y     |  N   |       | 用户账号  |
|  3   | ip_addr |   varchar   | 50 |   0    |    Y     |  N   |       | 登录IP地址  |
|  4   | login_location |   varchar   | 255 |   0    |    Y     |  N   |       | 登录地点  |
|  5   | coordinate |   varchar   | 32 |   0    |    Y     |  N   |       | 所在城市中心经纬度坐标  |
|  6   | browser |   varchar   | 50 |   0    |    Y     |  N   |       | 浏览器类型  |
|  7   | os |   varchar   | 50 |   0    |    Y     |  N   |       | 操作系统  |
|  8   | status |   char   | 1 |   0    |    Y     |  N   |   1    | 登录状态（1成功0失败）  |
|  9   | msg |   varchar   | 255 |   0    |    Y     |  N   |       | 提示消息  |
|  10   | login_time |   datetime   | 19 |   0    |    Y     |  N   |       | 访问时间  |

**表名：** <a id="sys_log_oper">sys_log_oper</a>

**说明：** 操作日志记录

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   bigint   | 20 |   0    |    N     |  Y   |       | 日志主键  |
|  2   | title |   varchar   | 50 |   0    |    Y     |  N   |       | 模块标题  |
|  3   | method |   varchar   | 128 |   0    |    Y     |  N   |       | 方法名称  |
|  4   | method_desc |   varchar   | 64 |   0    |    Y     |  N   |       | 方法描述  |
|  5   | request_method |   varchar   | 10 |   0    |    Y     |  N   |       | 请求方式  |
|  6   | oper_name |   varchar   | 32 |   0    |    Y     |  N   |       | 操作人员  |
|  7   | oper_url |   varchar   | 255 |   0    |    Y     |  N   |       | 请求URL  |
|  8   | oper_ip |   varchar   | 64 |   0    |    Y     |  N   |       | 主机地址  |
|  9   | oper_location |   varchar   | 255 |   0    |    Y     |  N   |       | 操作地点  |
|  10   | oper_param |   varchar   | 2048 |   0    |    Y     |  N   |       | 请求参数  |
|  11   | oper_result |   varchar   | 2048 |   0    |    Y     |  N   |       | 返回参数  |
|  12   | status |   char   | 1 |   0    |    Y     |  N   |   1    | 操作状态（1正常0异常）  |
|  13   | error_msg |   varchar   | 2048 |   0    |    Y     |  N   |       | 错误消息  |
|  14   | oper_time |   datetime   | 19 |   0    |    Y     |  N   |       | 操作时间  |

**表名：** <a id="sys_mail">sys_mail</a>

**说明：** 系统邮件记录表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 邮件ID  |
|  2   | send_from |   varchar   | 64 |   0    |    Y     |  N   |       | 邮件发送人  |
|  3   | send_to |   varchar   | 511 |   0    |    Y     |  N   |       | 邮件接收人（多个邮箱则用分号";"隔开）  |
|  4   | subject |   varchar   | 255 |   0    |    Y     |  N   |       | 邮件主题  |
|  5   | MAIL_TEXT |   varchar   | 512 |   0    |    Y     |  N   |       | 邮件内容  |
|  6   | cc |   varchar   | 255 |   0    |    Y     |  N   |       | 抄送  |
|  7   | bcc |   varchar   | 255 |   0    |    Y     |  N   |       | 密送  |
|  8   | status |   varchar   | 1 |   0    |    Y     |  N   |       | 状态  |
|  9   | error |   varchar   | 255 |   0    |    Y     |  N   |       | 报错信息  |
|  10   | send_date |   datetime   | 19 |   0    |    Y     |  N   |       | 发送时间  |

**表名：** <a id="sys_menu">sys_menu</a>

**说明：** 菜单权限表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   bigint   | 20 |   0    |    N     |  Y   |       | 菜单ID  |
|  2   | menu_name |   varchar   | 50 |   0    |    N     |  N   |       | 菜单名称  |
|  3   | parent_id |   bigint   | 20 |   0    |    Y     |  N   |   0    | 父菜单ID  |
|  4   | sort_by |   int   | 10 |   0    |    Y     |  N   |   0    | 显示顺序  |
|  5   | path |   varchar   | 200 |   0    |    Y     |  N   |       | 路由地址  |
|  6   | component |   varchar   | 255 |   0    |    Y     |  N   |       | 组件路径  |
|  7   | is_frame |   char   | 1 |   0    |    Y     |  N   |   0    | 是否为外链（1是0否）  |
|  8   | menu_type |   char   | 1 |   0    |    Y     |  N   |       | 菜单类型（M目录C菜单F按钮）  |
|  9   | visible |   char   | 1 |   0    |    Y     |  N   |   1    | 菜单状态（1显示0隐藏）  |
|  10   | status |   char   | 1 |   0    |    Y     |  N   |   1    | 菜单状态（1正常0停用）  |
|  11   | perms |   varchar   | 100 |   0    |    Y     |  N   |       | 权限标识  |
|  12   | icon |   varchar   | 100 |   0    |    Y     |  N   |   #    | 菜单图标  |
|  13   | create_by |   varchar   | 64 |   0    |    Y     |  N   |       | 创建者  |
|  14   | create_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 创建时间  |
|  15   | update_by |   varchar   | 64 |   0    |    Y     |  N   |       | 更新者  |
|  16   | update_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 更新时间  |
|  17   | remark |   varchar   | 500 |   0    |    Y     |  N   |       | 备注  |

**表名：** <a id="sys_notice">sys_notice</a>

**说明：** 通知公告信息表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 公告ID  |
|  2   | notice_title |   varchar   | 64 |   0    |    N     |  N   |       | 公告标题  |
|  3   | notice_type |   char   | 1 |   0    |    N     |  N   |       | 公告类型（1通知2公告）  |
|  4   | notice_content |   longblob   | 2147483647 |   0    |    Y     |  N   |       | 公告内容  |
|  5   | status |   char   | 1 |   0    |    Y     |  N   |   1    | 公告状态（1正常0关闭）  |
|  6   | create_by |   varchar   | 64 |   0    |    Y     |  N   |       | 创建者  |
|  7   | create_time |   datetime   | 19 |   0    |    N     |  N   |   CURRENT_TIMESTAMP    | 创建时间  |
|  8   | update_by |   varchar   | 64 |   0    |    Y     |  N   |       | 更新者  |
|  9   | update_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 更新时间  |
|  10   | remark |   varchar   | 255 |   0    |    Y     |  N   |       | 备注  |

**表名：** <a id="sys_role">sys_role</a>

**说明：** 角色信息表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   bigint   | 20 |   0    |    N     |  Y   |       | 角色ID  |
|  2   | role_code |   varchar   | 100 |   0    |    N     |  N   |       | 角色权限编码  |
|  3   | role_name |   varchar   | 30 |   0    |    N     |  N   |       | 角色名称  |
|  4   | sort_by |   int   | 10 |   0    |    N     |  N   |       | 显示顺序  |
|  5   | data_scope |   char   | 1 |   0    |    Y     |  N   |   1    | 数据范围（1：全部数据权限2：自定数据权限3：本部门数据权限4：本部门及以下数据权限）  |
|  6   | status |   char   | 1 |   0    |    N     |  N   |   1    | 角色状态（1正常0停用2代表删除）  |
|  7   | create_by |   varchar   | 64 |   0    |    Y     |  N   |       | 创建者  |
|  8   | create_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 创建时间  |
|  9   | update_by |   varchar   | 64 |   0    |    Y     |  N   |       | 更新者  |
|  10   | update_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 更新时间  |
|  11   | remark |   varchar   | 500 |   0    |    Y     |  N   |       | 备注  |

**表名：** <a id="sys_role_menu">sys_role_menu</a>

**说明：** 角色和菜单关联表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | role_id |   bigint   | 20 |   0    |    N     |  Y   |       | 角色ID  |
|  2   | menu_id |   bigint   | 20 |   0    |    N     |  Y   |       | 菜单ID  |

**表名：** <a id="sys_task">sys_task</a>

**说明：** 定时任务表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   bigint   | 20 |   0    |    N     |  Y   |       | 主键  |
|  2   | job_name |   varchar   | 128 |   0    |    N     |  N   |       | 任务名称  |
|  3   | job_group |   varchar   | 32 |   0    |    N     |  N   |   DEFAULT    | 任务分组  |
|  4   | bean_name |   varchar   | 128 |   0    |    N     |  N   |       | 任务执行时调用哪个类  |
|  5   | method_name |   varchar   | 32 |   0    |    N     |  N   |       | 类的方法名  |
|  6   | method_params |   varchar   | 64 |   0    |    Y     |  N   |       | 类的方法参数  |
|  7   | cron_expression |   varchar   | 64 |   0    |    N     |  N   |       | cron表达式  |
|  8   | misfire_policy |   char   | 1 |   0    |    Y     |  N   |   3    | 计划执行错误策略（1立即执行2执行一次3放弃执行）  |
|  9   | concurrent |   char   | 1 |   0    |    Y     |  N   |   0    | 是否并发执行（1允许0禁止）  |
|  10   | status |   char   | 1 |   0    |    N     |  N   |   1    | 任务状态（1正常0关闭）  |
|  11   | create_by |   varchar   | 64 |   0    |    Y     |  N   |       | 创建者  |
|  12   | create_time |   datetime   | 19 |   0    |    N     |  N   |   CURRENT_TIMESTAMP    | 创建时间  |
|  13   | update_by |   varchar   | 64 |   0    |    Y     |  N   |       | 更新者  |
|  14   | update_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 更新时间  |
|  15   | remark |   varchar   | 255 |   0    |    Y     |  N   |       | 备注  |

**表名：** <a id="sys_task_log">sys_task_log</a>

**说明：** 定时任务日志表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   bigint   | 20 |   0    |    N     |  Y   |       | 主键  |
|  2   | job_name |   varchar   | 128 |   0    |    N     |  N   |       | 任务名称  |
|  3   | job_group |   varchar   | 32 |   0    |    N     |  N   |   DEFAULT    | 任务分组  |
|  4   | bean_name |   varchar   | 128 |   0    |    N     |  N   |       | 任务执行时调用哪个类  |
|  5   | method_name |   varchar   | 32 |   0    |    N     |  N   |       | 类的方法名  |
|  6   | method_params |   varchar   | 64 |   0    |    Y     |  N   |       | 类的方法参数  |
|  7   | cron_expression |   varchar   | 64 |   0    |    N     |  N   |       | cron表达式  |
|  8   | time_consuming |   bigint   | 20 |   0    |    Y     |  N   |       | 耗时  |
|  9   | status |   char   | 1 |   0    |    Y     |  N   |   1    | 执行状态（1正常0失败）  |
|  10   | exception_info |   varchar   | 512 |   0    |    Y     |  N   |       | 异常信息  |
|  11   | create_time |   datetime   | 19 |   0    |    N     |  N   |   CURRENT_TIMESTAMP    | 创建时间  |

**表名：** <a id="sys_user">sys_user</a>

**说明：** 用户信息表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   bigint   | 20 |   0    |    N     |  Y   |       | 用户ID  |
|  2   | user_name |   varchar   | 30 |   0    |    N     |  N   |       | 用户账号  |
|  3   | password |   varchar   | 100 |   0    |    Y     |  N   |       | 密码  |
|  4   | avatar |   varchar   | 100 |   0    |    Y     |  N   |       | 头像地址  |
|  5   | nick_name |   varchar   | 30 |   0    |    N     |  N   |       | 用户昵称  |
|  6   | email |   varchar   | 50 |   0    |    Y     |  N   |       | 用户邮箱  |
|  7   | tel |   varchar   | 11 |   0    |    Y     |  N   |       | 手机号码  |
|  8   | sex |   char   | 1 |   0    |    Y     |  N   |   1    | 用户性别（1男0女2未知）  |
|  9   | status |   char   | 1 |   0    |    Y     |  N   |   1    | 帐号状态（1正常0停用）  |
|  10   | online |   char   | 1 |   0    |    Y     |  N   |   0    | 是否在线（1在线0不在线）  |
|  11   | login_ip |   varchar   | 50 |   0    |    Y     |  N   |       | 最后登陆IP  |
|  12   | login_addr |   varchar   | 50 |   0    |    Y     |  N   |       | 最后登陆地址  |
|  13   | login_date |   datetime   | 19 |   0    |    Y     |  N   |       | 最后登陆时间  |
|  14   | create_by |   varchar   | 64 |   0    |    Y     |  N   |       | 创建者  |
|  15   | create_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 创建时间  |
|  16   | update_by |   varchar   | 64 |   0    |    Y     |  N   |       | 更新者  |
|  17   | update_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 更新时间  |
|  18   | remark |   varchar   | 500 |   0    |    Y     |  N   |       | 备注  |

**表名：** <a id="sys_user_role">sys_user_role</a>

**说明：** 用户和角色关联表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | user_id |   bigint   | 20 |   0    |    N     |  Y   |       | 用户ID  |
|  2   | role_id |   bigint   | 20 |   0    |    N     |  Y   |       | 角色ID  |

**表名：** <a id="sys_wechat_mini_user">sys_wechat_mini_user</a>

**说明：** 微信小程序用户信息表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   bigint   | 20 |   0    |    N     |  Y   |       | 微信小程序用户ID  |
|  2   | user_id |   bigint   | 20 |   0    |    Y     |  N   |       | 系统用户ID  |
|  3   | open_id |   varchar   | 63 |   0    |    N     |  N   |       | 用户唯一标识  |
|  4   | union_id |   varchar   | 63 |   0    |    Y     |  N   |       | 用户在开放平台的唯一标识符  |
|  5   | session_key |   varchar   | 127 |   0    |    N     |  N   |       | 会话密钥  |
|  6   | avatar_Url |   varchar   | 100 |   0    |    Y     |  N   |       | 头像地址  |
|  7   | nick_name |   varchar   | 30 |   0    |    Y     |  N   |       | 用户昵称  |
|  8   | tel |   varchar   | 11 |   0    |    Y     |  N   |       | 手机号码  |
|  9   | gender |   char   | 1 |   0    |    Y     |  N   |   2    | 用户性别（1男0女2未知）  |
|  10   | login_date |   datetime   | 19 |   0    |    Y     |  N   |       | 最后登陆时间  |
|  11   | create_by |   varchar   | 64 |   0    |    Y     |  N   |       | 创建者  |
|  12   | create_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 创建时间  |
|  13   | update_by |   varchar   | 64 |   0    |    Y     |  N   |       | 更新者  |
|  14   | update_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 更新时间  |
|  15   | remark |   varchar   | 500 |   0    |    Y     |  N   |       | 备注  |
