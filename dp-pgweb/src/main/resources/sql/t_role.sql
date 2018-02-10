/*==============================================================*/
/* Table: role                                                  */
/*==============================================================*/
create table role
(
   id                   int not null auto_increment comment '编号',
   creator              int default 0 comment '创建者',
   creationtime         datetime comment '创建时间',
   modifier             int default 0 comment '修改者',
   modificationtime     datetime comment '修改时间',
   name            		varchar(30) not null comment '角色名',
   deletable			int default 1 comment '可删除。0|不可删除，1|可删除',
   remark 				varchar(30) comment '备注',
   primary key (id)
);

alter table role comment '角色表';

/*==============================================================*/
/* Index: i_role_loginname                                      */
/*==============================================================*/
create unique index i_role_name on role
(
   name
);

insert into role(creationtime, name, deletable) values(now(), 'Administrator', 0);