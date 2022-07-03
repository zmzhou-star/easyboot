drop table if exists lottery_history;
CREATE TABLE lottery_history
(
    id           BIGINT      NOT NULL AUTO_INCREMENT COMMENT 'ID',
    lottery_id   BIGINT      NOT NULL COMMENT '期号',
    lottery_type varchar(16) DEFAULT 'dlt' COMMENT '彩票类型',
    sales_amount varchar(16) NULL COMMENT '销售金额',
    lottery_date DATE        NULL COMMENT '开奖日期',
    red1         char(2)     NULL COMMENT '红球1',
    red2         char(2)     NULL COMMENT '红球2',
    red3         char(2)     NULL COMMENT '红球3',
    red4         char(2)     NULL COMMENT '红球4',
    red5         char(2)     NULL COMMENT '红球5',
    red6         char(2)     NULL COMMENT '红球6',
    blue1        char(2)     NULL COMMENT '红球1',
    blue2        char(2)     NULL COMMENT '蓝球2',
    prize_num1   char(4)     NULL COMMENT '一等奖数量',
    prize_bonus1 varchar(32) NULL COMMENT '一等奖奖金',
    prize_num2   varchar(8)  NULL COMMENT '二等奖数量',
    prize_bonus2 varchar(32) NULL COMMENT '二等奖奖金',
    prize_num3   varchar(8)  NULL COMMENT '三等奖数量',
    prize_bonus3 varchar(32) NULL COMMENT '三等奖奖金',
    create_by    varchar(64) NULL COMMENT '创建者',
    create_time  datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by    varchar(64) COMMENT '更新者',
    update_time  datetime    DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
) COMMENT ='彩票历史数据表';

-- 插入配置数据
drop procedure if exists insert_data;
create procedure insert_data()
begin
    if not exists(select * from sys_config where config_key = 'lottery.dlt.url')
    then
        INSERT INTO sys_config (config_name, config_key, config_value, config_type, create_by)
        VALUES ('大乐透开奖结果地址', 'lottery.dlt.url', 'https://kaijiang.78500.cn/dlt/', 'N', 'admin');
        INSERT INTO sys_config (config_name, config_key, config_value, config_type, create_by)
        VALUES ('大乐透开奖结果采集时间范围', 'lottery.dlt.collect.range', '2007-2022', 'N', 'admin');
    end if;
end;

call insert_data();
drop procedure if exists insert_data;
