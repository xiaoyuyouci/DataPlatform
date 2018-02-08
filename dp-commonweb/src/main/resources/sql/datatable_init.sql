/** readme 初始化数据 */
use reportplatform;

/** 插入rolepermission */
insert into rolepermission(roleid, permissionid, deletable) 
select r.id, p.id, 0
from role r, permission p
order by r.id, p.id;

/** 插入userrole */
insert into userrole(creationtime, userid, roleid, deletable) 
select now(), u.id, r.id, 0
from user u, role r
order by u.id, r.id;