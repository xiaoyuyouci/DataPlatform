/*==============================================================*/
/* Table: resource                                                  */
/*==============================================================*/
create table resource
(
   id                   int not null auto_increment comment '编号',
   creator              int default 0 comment '创建者',
   creationtime         datetime comment '创建时间',
   modifier             int default 0 comment '修改者',
   modificationtime     datetime comment '修改时间',
   sname            	varchar(600) not null comment '资源名称',
   skey             	varchar(600) not null comment '资源名称的key',
   svalue             	varchar(1200) comment '资源名称的值',
   memo               	varchar(1200) comment '备注',
   level				int default 0 comment '资源级别。0为用户级别，页面可见。1为系统级别，页面不可见',
   primary key (id)
);

alter table resource comment '资源表';

insert into resource(sname, skey, creationtime, svalue, level, memo) values('upd_bnotemp', 'lastid', now(), '0', 0, '更新Bno_tmp表。upload_idcode_temporary已经用过的ID');
insert into resource(sname, skey, creationtime, svalue, level, memo) values('dc_qrCodeUsageRatio', 'startDateOffset', now(), '-6', 0, '从DC数据库获取QR码文件申请日志的开始查找时间限制。单位月份。');
insert into resource(sname, skey, creationtime, svalue, level, memo) values('scheduleJob', 'runOnStart', now(), '0', 0, '定时任务。是否启动服务时运行任务。');