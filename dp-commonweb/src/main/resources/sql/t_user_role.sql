/*==============================================================*/
/* Table: userrole                                                  */
/*==============================================================*/
create table userrole
(
   id                   int not null auto_increment comment '编号',
   creator              int default 0 comment '创建者',
   creationtime         datetime comment '创建时间',
   modifier             int default 0 comment '修改者',
   modificationtime     datetime comment '修改时间',
   userid          		int default 0 comment '用户编号',
   roleid              	int default 0 comment '角色编号',
   deletable			int default 1 comment '可删除。0|不可删除，1|可删除',
   primary key (id)
);

alter table userrole comment '用户角色表';

/*==============================================================*/
/* Index: i_userrole_loginname                                      */
/*==============================================================*/
create unique index i_userrole_1 on userrole
(
   userid,roleid
);


