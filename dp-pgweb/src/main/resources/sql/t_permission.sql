/*==============================================================*/
/* Table: permission                                                  */
/*==============================================================*/
create table permission
(
   id                   int not null auto_increment comment '编号',
   creator              int default 0 comment '创建者',
   creationtime         datetime comment '创建时间',
   modifier             int default 0 comment '修改者',
   modificationtime     datetime comment '修改时间',
   name            		varchar(300) not null comment '权限名',
   nameCn      			varchar(300) comment '权限页面显示中文名',
   url               	varchar(300) comment 'url',
   filter				varchar(300) comment '过滤方式',
   level				int default 0 comment '权限级别。0为用户级别，页面可见。1为系统级别，页面不可见',
   deletable			int default 1 comment '可删除。0|不可删除，1|可删除',
   editable 			int default 1 comment '可编辑。0|不可编辑，1|可编辑',
   remark 				varchar(30) comment '备注',
   primary key (id)
);

alter table permission comment '权限表';

insert into permission(name, nameCn, url, filter, level, deletable) values('p0001', null, '/res/**', 'anon', 1, 0);
insert into permission(name, nameCn, url, filter, level, deletable) values('p0002', null, '/403', 'anon', 1, 0);
insert into permission(name, nameCn, url, filter, level, deletable) values('p0003', null, '/toLogin', 'anon', 1, 0);
insert into permission(name, nameCn, url, filter, level, deletable) values('p0004', null, '/login', 'anon', 1, 0);

insert into permission(name, nameCn, url, filter, level, deletable) values('dashboard:list', '仪表盘', '/dashboard', 'authc', 0, 0);

insert into permission(name, nameCn, url, filter, level, deletable) values('user:list', '查看用户', '/user/listUser', 'authc', 0, 0);
insert into permission(name, nameCn, url, filter, level, deletable) values('user:add', '新增用户', '/user/addUser', 'authc', 0, 0);
insert into permission(name, nameCn, url, filter, level, deletable) values('user:del', '删除用户', '/user/ajaxDelUser', 'authc', 0, 0);
insert into permission(name, nameCn, url, filter, level, deletable) values('user:edit', '修改用户', '/user/editUser', 'authc', 0, 0);
insert into permission(name, nameCn, url, filter, level, deletable) values('user:role', '查看用户角色', '/user/listUserRole', 'authc', 0, 0);
insert into permission(name, nameCn, url, filter, level, deletable) values('user:addRole', '新增用户角色', '/user/ajaxAddUserRole', 'authc', 0, 0);
insert into permission(name, nameCn, url, filter, level, deletable) values('user:delRole', '删除用户角色', '/user/ajaxDelUserRole', 'authc', 0, 0);

insert into permission(name, nameCn, url, filter, level, deletable) values('role:list', '查看角色', '/role/listRole', 'authc', 0, 0);
insert into permission(name, nameCn, url, filter, level, deletable) values('role:add', '新增角色', '/role/addRole', 'authc', 0, 0);
insert into permission(name, nameCn, url, filter, level, deletable) values('role:del', '删除角色', '/role/ajaxDelRole', 'authc', 0, 0);
insert into permission(name, nameCn, url, filter, level, deletable) values('role:edit', '编辑角色', '/role/editRole', 'authc', 0, 0);
insert into permission(name, nameCn, url, filter, level, deletable) values('role:permission', '查看角色权限', '/role/listRolePermission', 'authc', 0, 0);
insert into permission(name, nameCn, url, filter, level, deletable) values('role:addPermission', '新增角色权限', '/role/ajaxAddRolePermission', 'authc', 0, 0);
insert into permission(name, nameCn, url, filter, level, deletable) values('role:delPermission', '删除角色权限', '/role/ajaxDelRolePermission', 'authc', 0, 0);

insert into permission(name, nameCn, url, filter, level, deletable) values('permission:list', '查看权限', '/permission/listPermission', 'authc', 0, 0);
insert into permission(name, nameCn, url, filter, level, deletable) values('permission:add', '新增权限', '/permission/addPermission', 'authc', 0, 0);
insert into permission(name, nameCn, url, filter, level, deletable) values('permission:del', '删除权限', '/permission/ajaxDelPermission', 'authc', 0, 0);
insert into permission(name, nameCn, url, filter, level, deletable) values('permission:edit', '编辑权限', '/permission/editPermission', 'authc', 0, 0);



insert into permission(name, nameCn, url, filter, level, deletable) values('report:list', '我的报表', '', 'none', 0, 0);
insert into permission(name, nameCn, url, filter, level, deletable) values('report:fcRealtimeGrid', '工厂实时状态', '/report/fcRealtimeGrid', 'authc', 0, 0);
insert into permission(name, nameCn, url, filter, level, deletable) values('report:fcDailyGrid', '工厂每日状态', '/report/fcDailyGrid', 'authc', 0, 0);
insert into permission(name, nameCn, url, filter, level, deletable) values('report:dcRealtimeGrid', 'DC实时状态', '/report/dcRealtimeGrid', 'authc', 0, 0);
insert into permission(name, nameCn, url, filter, level, deletable) values('report:dcDailyGrid', 'DC每日状态', '/report/dcDailyGrid', 'authc', 0, 0);
insert into permission(name, nameCn, url, filter, level, deletable) values('report:dcQrCodeTimeConsuming', 'DC耗时查询', '/report/dcQrCodeTimeConsuming', 'authc', 0, 0);
insert into permission(name, nameCn, url, filter, level, deletable) values('report:uidGrid', 'UID查询', '/report/uidGrid', 'authc', 0, 0);
insert into permission(name, nameCn, url, filter, level, deletable) values('report:ppStatisticsGrid', '包材厂数据统计', '/report/ppStatisticsGrid', 'authc', 0, 0);

