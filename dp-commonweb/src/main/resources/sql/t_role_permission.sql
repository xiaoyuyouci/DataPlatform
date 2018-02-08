/*==============================================================*/
/* Table: role                                                  */
/*==============================================================*/
create table rolepermission
(
   id                   int not null auto_increment comment '编号',
   creator              int default 0 comment '创建者',
   creationtime         datetime comment '创建时间',
   modifier             int default 0 comment '修改者',
   modificationtime     datetime comment '修改时间',
   roleid            	int not null comment '角色编号',
   permissionid         int not null comment '权限编号',
   deletable			int default 1 comment '可删除。0|不可删除，1|可删除',
   primary key (id)
);

alter table rolepermission comment '角色权限表';

/*==============================================================*/
/* Index: i_role_permission_1                                      */
/*==============================================================*/
create unique index i_rolepermission_1 on rolepermission
(
   roleid,permissionid
);
