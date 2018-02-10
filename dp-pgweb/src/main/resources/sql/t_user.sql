/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   id                   int not null auto_increment comment '编号',
   creator              int default 0 comment '创建者',
   creationtime         datetime comment '创建时间',
   modifier             int default 0 comment '修改者',
   modificationtime     datetime comment '修改时间',
   loginname            varchar(30) not null comment '登录名',
   password             varchar(60) not null comment '密码',
   birthday             varchar(10) comment '生日',
   gender               int default 0 comment '性别。0|女，1|男',
   lastlogintime        datetime comment '上次登录时间',
   logintimes           int default 0 comment '登录时间',
   status				int default 0 comment '状态。0|可用，1|锁定',
   deletable			int default 1 comment '可删除。0|不可删除，1|可删除',
   primary key (id)
);

alter table user comment '用户表';

/*==============================================================*/
/* Index: i_user_loginname                                      */
/*==============================================================*/
create unique index i_user_loginname on user
(
   loginname
);

insert into user(loginname, password, creationtime, logintimes, deletable) values('admin', '3ef7164d1f6167cb9f2658c07d3c2f0a', now(), 0, 0);
