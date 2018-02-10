use reportplatform;

drop procedure if exists proc_dbcache_clean;

DELIMITER //
create procedure proc_dbcache_clean (IN timeOut int)
BEGIN
DECLARE last_datetime datetime;
DECLARE timeout_count int;
select timestampadd(SECOND,-timeOut,sysdate()) into last_datetime;
select count(id) into timeout_count from t_cache_ack t where t.caac_time<=last_datetime ;
IF timeout_count != 0 THEN
	create table test(id int);
END IF;
END;
// 
DELIMITER ;

drop EVENT if exists dbcache_clean_event;

DELIMITER //
create EVENT if not exists dbcache_clean_event
on schedule every 10 SECOND STARTS NOW()
on COMPLETION PRESERVE ENABLE
comment 'for call procedure proc_dbcache_clean'
do call proc_dbcache_clean(20);
// 
DELIMITER ;
