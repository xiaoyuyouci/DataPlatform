drop table if exists reportplatform.dc_bnotmp;

/*==============================================================*/
/* Table: dc_bnotmp                                             */
/*==============================================================*/
create table reportplatform.dc_bnotmp
(
   batchno              national varchar(24),
   bno                  national varchar(24)
);

alter table reportplatform.dc_bnotmp comment 'DC项目，单据批次表';
