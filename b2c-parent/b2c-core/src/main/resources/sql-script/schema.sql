-- Spring Security使用的表结构
drop table if exists users;
create table users (
  username                varchar(20) primary key
  comment '用户名',
  nickname                varchar(20)  not null default ''
  comment '别名',
  gender                  char(1)      not null default ''
  comment '性别,M:男;F:女',
  password                varchar(128) not null
  comment '密码',
  pic_url                 varchar(200) not null default ''
  comment '头像url',
  account_non_expired     tinyint(1)   not null default true
  comment '账户永不过期',
  account_non_locked      tinyint(1)   not null default true
  comment '账户永不锁定',
  credentials_non_expired tinyint(1)   not null default true
  comment '凭证永不过期',
  enabled                 tinyint(1)   not null default true
  comment '账户启用状态',
  del_flag                tinyint(1)   not null default false
  comment '账户删除状态',
  mobile                  varchar(11)  not null default ''
  comment '手机',
  email                   varchar(50)  not null default ''
  comment '邮箱',
  remark                  varchar(50)  not null default ''
  comment '备注',
  create_username         varchar(20)  not null
  comment '账户创建人',
  create_time             datetime     not null default now()
  comment '账户创建时间',
  last_op_username        varchar(20)  not null
  comment '最后操作人',
  last_op_time            datetime     not null default now()
  comment '最后操作时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '用户表';

drop table if exists users_authorities;
create table users_authorities (
  authority      varchar(30) primary key
  comment '权限',
  authority_name varchar(18) not null default ''
  comment '权限名称',
  remark         varchar(50) not null default ''
  comment '备注',
  create_time    datetime    not null default now()
  comment '创建时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '权限表';

drop table if exists authorities;
create table authorities (
  id        int primary key auto_increment
  comment '主键id',
  username  varchar(20) not null
  comment '关联users表的username',
  authority varchar(30) not null
  comment '关联权限表的authority'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '用户权限表';

drop table if exists groups;
create table groups (
  id         int primary key auto_increment
  comment '主键id',
  group_name varchar(8) not null
  comment '组名'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '权限组表';

drop table if exists group_authorities;
create table group_authorities (
  id        int primary key auto_increment
  comment '主键id',
  group_id  int         not null
  comment '关联groups表id',
  authority varchar(30) not null
  comment '关联权限表的authority'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '权限组权限表';

drop table if exists group_members;
create table group_members (
  id       int primary key auto_increment
  comment '主键id',
  group_id int         not null
  comment '关联groups表id',
  username varchar(20) not null
  comment '关联users表username'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '权限组用户表';

drop table if exists persistent_logins;
create table persistent_logins (
  series    varchar(64) primary key,
  username  varchar(20) not null,
  token     varchar(64) not null,
  last_used timestamp   not null
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '记住登录用户表';

drop table if exists menus;
create table menus (
  id           int primary key                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             auto_increment
  comment '主键id',
  parent_id    int default 0                                    not null                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     default 0
  comment '父id,0:顶级菜单',
  has_sub_menu tinyint(1) default false                         not null                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     default false
  comment '是否有子菜单',
  name         varchar(20) unique                               not null
  comment '名称',
  path         varchar(128) default ''                          not null                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     default ''
  comment 'path',
  should_show  boolean default true                             not null                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     default true
  comment '是否应显示',
  icon         varchar(30) default ''                           not null                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     default ''
  comment '图标类名',
  create_time  datetime default now()                           not null                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     default now()
  comment '创建时间',
  op_time      datetime default now()                           not null                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     default now()
  comment '最后操作时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '菜单表';

drop table if exists authorities_menus;
create table authorities_menus (
  id        int primary key auto_increment
  comment '主键id',
  authority varchar(30)
  comment '关联users_authorities表的authority',
  menus_id  int comment '关联menus表的id'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '权限菜单表';

drop table if exists topics;
create table `topics` (
  `id`                 int primary key         auto_increment
  comment '主键id',
  `topics_code`        varchar(6) default ''     not null
  comment '主题编码,T+5位数字',
  `topics_name`        varchar(32) default ''    not null
  comment '主题名称',
  `topics_cover_image` mediumblob                not null
  comment '主题封面',
  `topics_type`        tinyint(2) default null
  comment '主题类型,枚举值-CODING:1:编程挑战;WEB:2:网页开发;LANG:3:编程语言;DATA:4:数据科学;FUND:5:开发基础',
  `exams_code`         varchar(6) default ''     not null
  comment '关联考试表exams_code',
  `create_usename`     varchar(20) default ''    not null
  comment '创建人编号',
  `create_time`        datetime default now()    not null
  comment '创建时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '主题表';

drop table if exists sections;
create table `sections` (
  `id`                   int primary key         auto_increment
  comment '主键id',
  `sections_code`        varchar(6) default ''   not null
  comment '章节编码,S+5位数字',
  `sections_name`        varchar(32) default ''  not null
  comment '章节名称',
  `sections_cover_image` mediumblob              not null
  comment '章节封面',
  `topics_code`          varchar(6) default ''   not null
  comment '关联主题表topics_code',
  `sort_order`           int default 0           not null
  comment '顺序',
  `create_usename`       varchar(20) default ''  not null
  comment '创建人编号',
  `create_time`          datetime default now()  not null
  comment '创建时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '章节表';

drop table if exists knowledges;
CREATE TABLE `knowledges` (
  `id`              int PRIMARY KEY     AUTO_INCREMENT
  COMMENT '主键id',
  `knowledges_code` varchar(6) DEFAULT ''   not null
  COMMENT '知识编码,K+5位数字',
  `knowledges_name` varchar(32) DEFAULT ''  not null
  COMMENT '知识名称',
  `sort_order`      int default 0           not null
  comment '顺序',
  `sections_code`   varchar(6) default ''   not null
  comment '关联章节表的sections_code',
  `create_usename`  varchar(20) default ''  not null
  comment '创建人编号',
  `create_time`     datetime default now()  not null
  comment '创建时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '知识表';

drop table if exists knowledge_points;
CREATE TABLE `knowledge_points` (
  `id`                       int PRIMARY KEY     AUTO_INCREMENT
  COMMENT '主键id',
  `knowledge_points_code`    varchar(7) DEFAULT ''     not null
  COMMENT '知识点编码,KP+5位数字',
  `knowledge_points_content` varchar(2000) DEFAULT ''  not null
  COMMENT '知识点内容',
  `sort_order`               int default 0             not null
  comment '顺序',
  `knowledges_code`          varchar(6) default ''     not null
  comment '关联知识表的knowledges_code',
  `questionsCode`            varchar(6) default ''     not null
  comment '关联题目表的questions_code',
  `create_usename`           varchar(20) default ''    not null
  comment '创建人编号',
  `create_time`              datetime default now()    not null
  comment '创建时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '知识点表';

drop table if exists questions;
CREATE TABLE `questions` (
  `id`              int PRIMARY KEY     AUTO_INCREMENT
  COMMENT '主键id',
  `questions_code`  varchar(6) DEFAULT ''               not null
  COMMENT '题目编码,Q+5位数字',
  `questions_title` varchar(200) DEFAULT ''             not null
  COMMENT '题目标题',
  `questions_type`  tinyint                             not null
  COMMENT '题目类型,1:单选;2:多选;3:判断:4:排序',
  `questions_score` int                                 not null
  comment '分数',
  `sort_order`      int default 0                       not null
  comment '顺序',
  `answer_analysis` varchar(200) default ''             not null
  comment '答案解析',
  `create_usename`  varchar(20) default ''              not null
  comment '创建人编号',
  `create_time`     datetime default now()              not null
  comment '创建时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '题目表';

drop table if exists options;
CREATE TABLE `options` (
  `id`             int PRIMARY KEY AUTO_INCREMENT
  COMMENT '主键id',
  `option_name`    varchar(200) DEFAULT ''   not null
  COMMENT '选项名称',
  `correct`        boolean DEFAULT false     not null
  COMMENT '是否正确答案,仅针对非排序题',
  `sort`           int default 0             not null
  comment '选项排序,仅针对排序题',
  `questions_code` varchar(6) DEFAULT ''     not null
  COMMENT '关联题目表questions_code',
  `create_usename` varchar(20) default ''    not null
  comment '创建人编号',
  `create_time`    datetime default now()    not null
  comment '创建时间'

)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '选项表';

drop table if exists certs;
CREATE TABLE `certs` (
  `id`              int primary key AUTO_INCREMENT
  COMMENT '主键id',
  `certs_name`      varchar(32) default ''   not null
  COMMENT '证书名称',
  `certs_desc`      varchar(100) default ''  not null
  COMMENT '证书说明',
  `pic_url`         varchar(128) default ''  not null
  COMMENT '证书样图url',
  `create_username` varchar(200) default ''  not null
  COMMENT '创建人名称',
  `create_time`     datetime default now()   not null
  COMMENT '创建时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '证书表';

DROP TABLE IF EXISTS `learns_record`;
CREATE TABLE `learns_record` (
  `id`                    int(11)     NOT NULL AUTO_INCREMENT
  COMMENT '主键id',
  `username`              varchar(20) NOT NULL
  COMMENT '用户名',
  `topics_code`           varchar(6)  NOT NULL
  COMMENT '关联主题表topics_code',
  `sections_code`         varchar(6)  NOT NULL
  COMMENT '关联章节表sections_code',
  `knowledges_code`       varchar(6)  NOT NULL
  COMMENT '关联知识表knowledges_code',
  `knowledge_points_code` varchar(7)  NOT NULL
  COMMENT '关联知识点表knowledge_points_code',
  `create_time`           datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '学习记录表';

DROP TABLE IF EXISTS `exams_record`;
CREATE TABLE `exams_record` (
  `id`          int(11)     NOT NULL AUTO_INCREMENT
  COMMENT '主键id',
  `username`    varchar(20) NOT NULL
  COMMENT '用户名',
  `exams_code`  varchar(6)  NOT NULL
  COMMENT '关联考试表exams_code',
  `score`       smallint    NOT NULL
  comment '得分',
  `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '考试记录表';

DROP TABLE IF EXISTS `answers_record`;
CREATE TABLE `answers_record` (
  `id`             int(11)     NOT NULL AUTO_INCREMENT
  COMMENT '主键id',
  `username`       varchar(20) NOT NULL
  COMMENT '用户名',
  `exams_code`     varchar(6)  NOT NULL
  COMMENT '关联考试表exams__code',
  `questions_code` varchar(6)  NOT NULL
  COMMENT '关联题目表questions_code',
  `score`          tinyint     NOT NULL
  comment '本题得分',
  `answer`         varchar(50) NOT NULL
  comment '用户答案的选项id,多选或者排序用英文逗号隔开',
  `create_time`    datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '答题记录表';

DROP TABLE IF EXISTS `messages`;
CREATE TABLE `messages` (
  `id`           int(11)      NOT NULL AUTO_INCREMENT
  COMMENT '主键id',
  `username`     varchar(20)  NOT NULL
  COMMENT '用户名',
  `content`      varchar(200) NOT NULL
  COMMENT '消息内容',
  `already_read` tinyint      NOT NULL default 1
  comment '是否已读,1:否;2:是',
  `create_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '消息表';

DROP TABLE IF EXISTS `discussions`;
CREATE TABLE `discussions` (
  `id`            int(11)       NOT NULL AUTO_INCREMENT
  COMMENT '主键id',
  `username`      varchar(20)   NOT NULL
  COMMENT '用户名',
  `question`      varchar(128)  NOT NULL
  COMMENT '问题',
  `description`   varchar(1024) NOT NULL default ''
  comment '描述',
  `relevant_tags` varchar(128)  NOT NULL default ''
  comment '关联标签,用;隔开',
  `create_time`   datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '讨论表';

DROP TABLE IF EXISTS `replys`;
CREATE TABLE `replys` (
  `id`             int(11)       NOT NULL AUTO_INCREMENT
  COMMENT '主键id',
  `discussions_id` int(11)       NOT NULL
  COMMENT '关联discussions表主键id',
  `username`       varchar(20)   NOT NULL
  COMMENT '回复人用户名',
  `description`    varchar(1024) NOT NULL default ''
  comment '描述',
  `relevant_tags`  varchar(128)  NOT NULL default ''
  comment '关联标签,用;隔开',
  `create_time`    datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '讨论表';

DROP TABLE IF EXISTS `votes`;
CREATE TABLE `votes` (
  `id`          int(11)     NOT NULL AUTO_INCREMENT
  COMMENT '主键id',
  `type`        tinyint     NOT NULL
  comment '投票类型,1:讨论;2:回复',
  `relate_id`   int(11)     NOT NULL
  COMMENT '关联discussions表主键id或replys表主键id',
  `username`    varchar(20) NOT NULL
  COMMENT '投票人用户名',
  `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '投票表';