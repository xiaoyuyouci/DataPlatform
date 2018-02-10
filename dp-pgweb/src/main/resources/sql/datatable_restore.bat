@echo off

if "%1"=="" goto usage
if "%2"=="" goto usage
if "%3"=="" goto usage
if "%4"=="" goto usage

:start

cd /d "%~dp0"

if exist my.cnf.temp del my.cnf.temp
echo [mysql]>>my.cnf.temp
echo user=%3>>my.cnf.temp
echo password='%4'>>my.cnf.temp

echo restore database [reportplatform] start...

rem create database reportplatform
echo "creating database reportplatform"
mysql --defaults-file=my.cnf.temp --default-character-set=utf8 -h %1 -P %2 < ./datatable_create.sql

rem create tables and views in database reportplatform
for %%f in (t_*.sql v_*.sql) do mysql --defaults-file=my.cnf.temp --default-character-set=utf8 -h %1 -P %2 -D reportplatform < .\%%f

rem create functions and procedures in database reportplatform
echo "creating functions and procedures"
mysql --defaults-file=my.cnf.temp --default-character-set=utf8 -h %1 -P %2 -D reportplatform < ./datatable_function_create.sql

rem create events in database reportplatform
rem echo "creating events"
rem mysql --defaults-file=my.cnf.temp --default-character-set=utf8 -h %1 -P %2 -D reportplatform < ./datatable_event_create.sql

rem initialize table with data
echo "initializing database reportplatform"
mysql --defaults-file=my.cnf.temp --default-character-set=utf8 -h %1 -P %2 -D reportplatform < ./datatable_init.sql

echo restore database [reportplatform] complete.
goto end

:usage

echo Usage: %0 MYSQL-HOST MYSQL-PORT USERNAME PASSWORD

:end
if exist my.cnf.temp del my.cnf.temp