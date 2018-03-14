drop index idx_dc_frbu on reportplatform.dc_farealtime;

drop table if exists reportplatform.dc_farealtime;

/*==============================================================*/
/* Table: dc_farealtime                                           */
/*==============================================================*/
create table reportplatform.dc_farealtime
(
   bu                   national varchar(24) comment '日期',
   plantcode            national varchar(24) comment '工厂',
   linecode             national varchar(24) comment '线号',
   status               int(24) comment '1代表之前两个小时有数据刷新 0代表无刷新',
   batchno              national varchar(24) comment '批次',
   protype              int(11) comment '是否为一瓶一码',
   mcode                national varchar(24) comment '产品代码',
   productname          national varchar(256) comment '产品名称',
   ckqrnum1             int(11) comment '生产计划item数量',
   elqrnum1             int(11) comment 'item剔除数量',
   scannum1             int(11) comment '实际item数量',
   count1               int(11) comment '实际item数量',
   connext1             int(11) comment '接收数量',
   ckqrnum2             int(11) comment '计划生产case数量',
   elqrnum2             int(11) comment 'case剔除数量',
   scannum2             int(11) comment 'case本地数量',
   count2               int(11) comment '实际case数量',
   connext2             int(11) comment 'case实际接收数量',
   item_percent         float,
   case_percent         float,
   real_package         int(11),
   case_package         int(11),
   istrue               national varchar(6) comment '比例是否符合标准',
   up_time              national varchar(128) comment '改产线最近一次上传时间',
   case_item            national varchar(6) comment '箱瓶是否关联'
);

alter table reportplatform.dc_farealtime comment '工厂实时状态数据对应的部门产线缓存表示';

/*==============================================================*/
/* Index: search_date                                           */
/*==============================================================*/
create index idx_dc_frbu on reportplatform.dc_farealtime
(
   bu
);
