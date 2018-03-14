drop table if exists dc_qrcodeusageratio;

/*==============================================================*/
/* Table: dc_qrcodeusageratio                                   */
/*==============================================================*/
create table dc_qrcodeusageratio
(
   id                   int(11) not null auto_increment comment '编号',
   creator              int(11) default 0 comment '创建者',
   creationtime         datetime comment '创建时间',
   modifier             int(11) default 0 comment '修改者',
   modificationtime     datetime comment '修改时间',
   filename             varchar(60) not null comment '文件名',
   applytime            datetime comment '申请时间',
   applycount           int comment '申请数量',
   firstuploadtime      datetime comment '首次上传时间',
   lastuploadtime       datetime comment '最新上传时间',
   uploadcount          int comment '上传数量',
   firstouttime         datetime comment '首次出库时间',
   lastouttime          datetime comment '最新上传时间',
   outcount             int comment '出库数量',
   primary key (id)
);

alter table dc_qrcodeusageratio comment 'DC码申请文件中的码的生产率，出库率';
