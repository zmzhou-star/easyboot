DROP TABLE IF EXISTS patient;
CREATE TABLE patient
(
  id                bigint       NOT NULL AUTO_INCREMENT COMMENT '病人ID',
  user_name         varchar(30)  NOT NULL COMMENT '姓名',
  tel               varchar(11)  NULL DEFAULT '' COMMENT '手机号码',
  sex               char(1)      NULL DEFAULT '1' COMMENT '性别（1男 0女 2未知）',
  birth_date        date         NULL COMMENT '出生日期',
  birthplace        varchar(255) NULL DEFAULT '' COMMENT '出生地',
  permanent_address varchar(255) NOT NULL COMMENT '常住地址',
  career            varchar(50)  NULL DEFAULT '' COMMENT '职业',
  marriage          varchar(50)  NULL DEFAULT '' COMMENT '婚育史',
  create_by         varchar(64)  NULL DEFAULT '' COMMENT '创建者',
  create_time       datetime(0)  NULL DEFAULT CURRENT_TIMESTAMP COMMENT '初诊时间',
  update_by         varchar(64)  NULL DEFAULT '' COMMENT '更新者',
  update_time       datetime(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id) USING BTREE,
  UNIQUE INDEX UNI_IDX_USER_NAME_TEL (user_name, tel) USING BTREE
) CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '病人信息表';

DROP TABLE IF EXISTS prescript;
CREATE TABLE prescript
(
  id             bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
  prescript_name varchar(30)  NOT NULL COMMENT '药方名',
  purpose        varchar(128) NOT NULL COMMENT '用途',
  medicines      text         NULL DEFAULT NULL COMMENT '治法方药',
  create_by      varchar(64)  NULL DEFAULT '' COMMENT '创建者',
  create_time    datetime(0)  NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_by      varchar(64)  NULL DEFAULT '' COMMENT '更新者',
  update_time    datetime(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id) USING BTREE,
  UNIQUE INDEX UNI_IDX_USER_NAME_TEL (prescript_name) USING BTREE
) CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '药方';

DROP TABLE IF EXISTS purpose_record;
CREATE TABLE purpose_record
(
  id              bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
  patient_id      bigint       NOT NULL COMMENT '病人ID',
  chief_complaint varchar(500) NULL DEFAULT NULL COMMENT '主诉',
  medicines       text         NULL DEFAULT NULL COMMENT '治法方药',
  create_by       varchar(64)  NULL DEFAULT '' COMMENT '创建者',
  create_time     datetime(0)  NULL DEFAULT CURRENT_TIMESTAMP COMMENT '复诊时间',
  update_by       varchar(64)  NULL DEFAULT '' COMMENT '更新者',
  update_time     datetime(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id) USING BTREE
) CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '看诊记录';
