/*drop index idx_dc_frbu on reportplatform.dc_farealtime;*/

drop table if exists reportplatform.dc_farealtime;

/*==============================================================*/
/* Table: dc_farealtime                                           */
/*==============================================================*/
create table reportplatform.dc_farealtime
(
   bu                   varchar(24) comment '日期',
   plantcode            varchar(24) comment '工厂',
   linecode             varchar(24) comment '线号',
   status               int(24) comment '1代表之前两个小时有数据刷新 0代表无刷新',
   batchno              varchar(24) comment '批次',
   protype              int(11) comment '是否为一瓶一码',
   mcode                varchar(24) comment '产品代码',
   productname          varchar(256) comment '产品名称',
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
   istrue               varchar(6) comment '比例是否符合标准',
   up_time              varchar(128) comment '改产线最近一次上传时间',
   case_item            varchar(6) comment '箱瓶是否关联'
);

alter table reportplatform.dc_farealtime comment '工厂实时状态数据对应的部门产线缓存表示';

/*==============================================================*/
/* Index: search_date                                           */
/*==============================================================*/
create index idx_dc_frbu on reportplatform.dc_farealtime
(
   bu
);


insert into dc_farealtime(bu,plantcode,linecode) values('Hair','1864','L4');
insert into dc_farealtime(bu,plantcode,linecode) values('Hair','1864','L5');
insert into dc_farealtime(bu,plantcode,linecode) values('Hair','1864','L6');
insert into dc_farealtime(bu,plantcode,linecode) values('Hair','1864','L8');
insert into dc_farealtime(bu,plantcode,linecode) values('Hair','0386','B');
insert into dc_farealtime(bu,plantcode,linecode) values('Hair','0386','C');
insert into dc_farealtime(bu,plantcode,linecode) values('Hair','0386','F');
insert into dc_farealtime(bu,plantcode,linecode) values('Hair','0386','G');
insert into dc_farealtime(bu,plantcode,linecode) values('Hair','0386','K');
insert into dc_farealtime(bu,plantcode,linecode) values('Hair','0386','R');
insert into dc_farealtime(bu,plantcode,linecode) values('Hair','0386','S');
insert into dc_farealtime(bu,plantcode,linecode) values('Hair','A868','L1');
insert into dc_farealtime(bu,plantcode,linecode) values('Hair','A868','L2');
insert into dc_farealtime(bu,plantcode,linecode) values('Hair','A868','L3');
insert into dc_farealtime(bu,plantcode,linecode) values('Hair','A868','LS');
insert into dc_farealtime(bu,plantcode,linecode) values('Hair','1864','S');
insert into dc_farealtime(bu,plantcode,linecode) values('Hair','A684','Csite');
insert into dc_farealtime(bu,plantcode,linecode) values('Hair','A685','Csite');
insert into dc_farealtime(bu,plantcode,linecode) values('Hair','A727','Csite');
insert into dc_farealtime(bu,plantcode,linecode) values('Hair','A743','Csite');
insert into dc_farealtime(bu,plantcode,linecode) values('PCC','2799','L2');
insert into dc_farealtime(bu,plantcode,linecode) values('PCC','2799','L3');
insert into dc_farealtime(bu,plantcode,linecode) values('PCC','2799','L4');
insert into dc_farealtime(bu,plantcode,linecode) values('PCC','2799','L5');
insert into dc_farealtime(bu,plantcode,linecode) values('PCC','2799','L6');
insert into dc_farealtime(bu,plantcode,linecode) values('PCC','2799','C1');
insert into dc_farealtime(bu,plantcode,linecode) values('PCC','A664','L7');
insert into dc_farealtime(bu,plantcode,linecode) values('Baby','1864','CP21');
insert into dc_farealtime(bu,plantcode,linecode) values('Baby','1864','CP23');
insert into dc_farealtime(bu,plantcode,linecode) values('Baby','1864','CP24');
insert into dc_farealtime(bu,plantcode,linecode) values('Baby','1869','CP81');
insert into dc_farealtime(bu,plantcode,linecode) values('Baby','1869','CP83');
insert into dc_farealtime(bu,plantcode,linecode) values('Baby','1869','CP84');
insert into dc_farealtime(bu,plantcode,linecode) values('Baby','1869','CP86');
insert into dc_farealtime(bu,plantcode,linecode) values('Fem Care','1864','L2');
insert into dc_farealtime(bu,plantcode,linecode) values('Fem Care','1864','L3');
insert into dc_farealtime(bu,plantcode,linecode) values('Fem Care','1864','L4');
insert into dc_farealtime(bu,plantcode,linecode) values('Fem Care','0386','11');
insert into dc_farealtime(bu,plantcode,linecode) values('Fem Care','0386','12');
insert into dc_farealtime(bu,plantcode,linecode) values('Fem Care','0386','13A');
insert into dc_farealtime(bu,plantcode,linecode) values('Fem Care','0386','13B');
insert into dc_farealtime(bu,plantcode,linecode) values('Oral','1864','1');
insert into dc_farealtime(bu,plantcode,linecode) values('Oral','1864','2');
insert into dc_farealtime(bu,plantcode,linecode) values('Oral','1864','4');
insert into dc_farealtime(bu,plantcode,linecode) values('Oral','0386','OA');
insert into dc_farealtime(bu,plantcode,linecode) values('Oral','0386','OB');
insert into dc_farealtime(bu,plantcode,linecode) values('Oral','0386','OC');
insert into dc_farealtime(bu,plantcode,linecode) values('Oral','0386','OD');
insert into dc_farealtime(bu,plantcode,linecode) values('Oral','0386','OE');
insert into dc_farealtime(bu,plantcode,linecode) values('Oral','0386','OF');
insert into dc_farealtime(bu,plantcode,linecode) values('Oral','0386','OG');
insert into dc_farealtime(bu,plantcode,linecode) values('Shave Care','A743','Csite');
insert into dc_farealtime(bu,plantcode,linecode) values('Shave Care','7811','CFFS');
insert into dc_farealtime(bu,plantcode,linecode) values('Shave Care','7811','RFFS');
insert into dc_farealtime(bu,plantcode,linecode) values('Shave Care','7811','Rota');
insert into dc_farealtime(bu,plantcode,linecode) values('Shave Care','7811','TUCK');