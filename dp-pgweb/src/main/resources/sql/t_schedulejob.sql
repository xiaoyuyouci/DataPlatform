drop table if exists schedulejob;

/*==============================================================*/
/* Table: schedulejob                                           */
/*==============================================================*/
create table schedulejob
(
   id                   int(11) not null auto_increment comment '编号',
   creator              int(11) default 0 comment '创建者',
   creationtime         datetime comment '创建时间',
   modifier             int(11) default 0 comment '修改者',
   modificationtime     datetime comment '修改时间',
   jobgroup             varchar(60) comment '任务组',
   jobname              varchar(120) comment '任务名',
   jobstatus            varchar(6) comment '任务状态',
   cronexpression       varchar(120) comment '表达式',
   description          varchar(120) comment '描述',
   isconcurrent         varchar(120) comment '是否同时存在',
   beanclass            varchar(120) comment '任务执行时调用哪个类的方法 包名+类名',
   springid             varchar(120) comment 'spring的bean',
   methodname           varchar(120) comment '任务调用的方法名',
   primary key (id)
);

alter table schedulejob comment '定时任务表';


INSERT INTO schedulejob (creationtime, jobgroup, jobname, jobstatus, cronexpression, description, isconcurrent, beanclass, springid, methodname) VALUES (now(), 'group1', 'Dc_FcDailySchedule', '0', '0 0 5 * * ?', 'DC-每天凌晨五点统计上一天的数据存入缓存数据库中', '0', '', 'dc_FcDailySchedule', 'factoryDailyData');
INSERT INTO schedulejob (creationtime, jobgroup, jobname, jobstatus, cronexpression, description, isconcurrent, beanclass, springid, methodname) VALUES (now(), 'group1', 'Dc_FcRealtimeSchedule', '0', '0 0 0/2 * * ?', 'DC-工厂实时状态 监测一个时间段是否有上传数据', '0', '', 'dc_FcRealtimeSchedule', 'updateRealtime');
INSERT INTO schedulejob (creationtime, jobgroup, jobname, jobstatus, cronexpression, description, isconcurrent, beanclass, springid, methodname) VALUES (now(), 'group1', 'Dc_QrCodeUsageRatioSchedule', '0', '0 0 0/8 * * ?', 'DC-获取DC条码使用情况', '0', '', 'dc_QrCodeUsageRatioSchedule', 'execute');
INSERT INTO schedulejob (creationtime, jobgroup, jobname, jobstatus, cronexpression, description, isconcurrent, beanclass, springid, methodname) VALUES (now(), 'group1', 'Dc_UpdateBnoTmpSchedule', '0', '0 0 0/2 * * ?', 'DC-更新bno_tmp', '0', '', 'dc_UpdateBnoTmpSchedule', 'updateBnoTmp');
