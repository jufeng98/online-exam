-- 密码是123456
insert into users
values ('admin','','', '$2a$10$fyglVJ8Flczap1mIUiSMp.b5VFYEW8F3Fn72qrnpoDlase2Mkhq9O','', true, true, true, true, false,'','','','admin',now(),'admin',now());
insert into users
values ('1050106266','','', '$2a$10$fyglVJ8Flczap1mIUiSMp.b5VFYEW8F3Fn72qrnpoDlase2Mkhq9O','', true, true, true, true, false,'','','','admin',now(),'admin',now());
insert into users
values ('1050106158','','', '$2a$10$oCMqtWAAol2CFgFY7.Dg8OpV/LSOsIL5VF/GgEuNhYcDGDWHl8Hc6','',true, true, true, true, false,'','','','admin',now(),'admin',now());
insert into users_authorities values ('ROLE_ADMIN','超级管理员');
insert into users_authorities values ('ROLE_ROOT','ROOT管理员');
insert into users_authorities values ('ROLE_ACTUATOR','ACTUATOR管理员');
insert into users_authorities values ('ROLE_ORDINARY','一般用户');
insert into users_authorities values ('ROLE_ANONYMOUS','匿名用户');
insert into authorities
values ('admin', 'ROLE_ADMIN');
insert into authorities
values ('admin', 'ROLE_ACTUATOR');
insert into authorities
values ('1050106266', 'ROLE_ADMIN');
insert into authorities
values ('1050106266', 'ROLE_ACTUATOR');
insert into authorities
values ('1050106158', 'ROLE_ORDINARY');
insert into groups(group_name)
values ('超级管理员');
insert into groups(group_name)
values ('一般用户');
insert into group_authorities
values ((select id from groups where group_name='超级管理员'), 'ROLE_ADMIN');
insert into group_authorities
values ((select id from groups where group_name='超级管理员'), 'ROLE_ROOT');
insert into group_authorities
values ((select id from groups where group_name='一般用户'), 'ROLE_ORDINARY');
insert into group_authorities
values ((select id from groups where group_name='一般用户'), 'ROLE_ANONYMOUS');
insert into group_members
values ((select id from groups where group_name='超级管理员'), '1050106266');
insert into group_members
values ((select id from groups where group_name='一般用户'), '1050106158');

insert into menus(name,icon) values ('培训实施','el-icon-message');
insert into menus(name,icon) values ('统计监控','el-icon-menu');
insert into menus(name,icon) values ('系统管理','el-icon-setting');

insert into menus(parent_id,name) select (select id from menus where name='培训实施'),(select '学习培训');
insert into menus(parent_id,name) select (select id from menus where name='培训实施'),(select '考试测评');
insert into menus(parent_id,name) select (select id from menus where name='培训实施'),(select '日常管理');
update menus set has_sub_menu=true where name='培训实施';

insert into menus(parent_id,name,path) select (select id from menus where name='学习培训'),(select '知识管理'),(select '/#/knowledgeManage');
insert into menus(parent_id,name,path) select (select id from menus where name='考试测评'),(select '试题管理'),(select '/#/questionManage');
insert into menus(parent_id,name,path) select (select id from menus where name='日常管理'),(select '证书管理'),(select '/#/certManage');
update menus set has_sub_menu=true where name='学习培训';
update menus set has_sub_menu=true where name='考试测评';
update menus set has_sub_menu=true where name='日常管理';


insert into menus(parent_id,name) select (select id from menus where name='统计监控'),(select '培训监控');
insert into menus(parent_id,name) select (select id from menus where name='统计监控'),(select '培训统计');
update menus set has_sub_menu=true where name='统计监控';

insert into menus(parent_id,name,path)select (select id from menus where name='培训监控'),(select '考试监控'),(select '/#/examMonitor');
insert into menus(parent_id,name,path)select (select id from menus where name='培训统计'),(select '考试统计'),(select '/#/examStatistics');
update menus set has_sub_menu=true where name='培训监控';
update menus set has_sub_menu=true where name='培训统计';

insert into menus(parent_id,name,path)select (select id from menus where name='系统管理'),(select '用户管理'),(select '/#/usersManage');
insert into menus(parent_id,name,path)select (select id from menus where name='系统管理'),(select '角色管理'),(select '/#/authoritiesManage');
insert into menus(parent_id,name,path)select (select id from menus where name='系统管理'),(select '菜单管理'),(select '/#/menusManage');
insert into menus(parent_id,name,path)select (select id from menus where name='系统管理'),(select '样例'),(select '/#/demo');
insert into menus(parent_id,name,path)select (select id from menus where name='系统管理'),(select '环境变量(env)'),(select '/#/env');
insert into menus(parent_id,name,path)select (select id from menus where name='系统管理'),(select '路径映射(mappings)'),(select '/#/mappings');
insert into menus(parent_id,name,path)select (select id from menus where name='系统管理'),(select '配置信息(configprops)'),(select '/#/configprops');
insert into menus(parent_id,name,path)select (select id from menus where name='系统管理'),(select '自动配置(conditions)'),(select '/#/conditions');
insert into menus(parent_id,name,path)select (select id from menus where name='系统管理'),(select '应用Bean(beans)'),(select '/#/beans');
insert into menus(parent_id,name,path)select (select id from menus where name='系统管理'),(select '日志级别(loggers)'),(select '/#/loggers');
insert into menus(parent_id,name,path)select (select id from menus where name='系统管理'),(select '度量信息(metrics)'),(select '/#/metrics');
insert into menus(parent_id,name,path)select (select id from menus where name='系统管理'),(select '线程信息(threads)'),(select '/#/threads');
update menus set has_sub_menu=true where name='系统管理';