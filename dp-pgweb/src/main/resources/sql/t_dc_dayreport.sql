/*drop index idx_dc_drdate on reportplatform.dc_dayreport;*/

drop table if exists reportplatform.dc_dayreport;

/*==============================================================*/
/* Table: dc_dayreport                                          */
/*==============================================================*/
create table reportplatform.dc_dayreport
(
   date                  varchar(24) comment '日期',
   plantcode             varchar(24) comment '工厂',
   linecode              varchar(24) comment '线号',
   bu                    varchar(24),
   batchno               varchar(24) comment '批次',
   protype              int(11) comment '是否为一瓶一码',
   mcode                 varchar(24) comment '产品代码',
   productname           varchar(256) comment '产品名称',
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
   istrue                varchar(2) comment '比例是否符合标准',
   case_item             varchar(6) comment '判断是否箱码关联'
);

alter table reportplatform.dc_dayreport comment 'DC项目，工厂每日状态的缓存表';

/*==============================================================*/
/* Index: search_date                                           */
/*==============================================================*/
create index idx_dc_drdate on reportplatform.dc_dayreport
(
   date
);
