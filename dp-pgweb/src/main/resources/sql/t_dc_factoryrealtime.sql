drop table if exists reportplatform.dc_factoryrealtime;

/*==============================================================*/
/* Table: dc_factoryrealtime                                    */
/*==============================================================*/
create table reportplatform.dc_factoryrealtime
(
   r_bu                 varchar(255) comment '事业部',
   r_plantcode          varchar(255),
   datatype             varchar(12) comment '数据类型 case 和item两种',
   r_linecode           varchar(255),
   batchno              varchar(255),
   fpc                  varchar(255),
   product_name         varchar(255),
   state                varchar(255) comment '当前状态',
   case_package         int(11),
   backnum              int(11) comment 'case 打印机回传数量',
   ckqrnum              int(11) comment '扫描数量',
   elqrnum              int(11) comment '剔除数量',
   scannum              int(11) comment '本地数量',
   context              int(11) comment 'case context接收数量',
   count                int(11) comment '后台系统数量',
   percent              float comment 'case 生产合格率',
   istrue               varchar(255) comment '是否符合case count',
   addtime              datetime
);

alter table reportplatform.dc_factoryrealtime comment 'DC项目，工厂每日状态数据的缓存表';


INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Hair', '1864', 'Case', 'L4');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Hair', '1864', 'Case', 'L5');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Hair', '1864', 'Case', 'L6');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Hair', '1864', 'Case', 'L8');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Hair', '0386', 'Case', 'B');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Hair', '0386', 'Case', 'C');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Hair', '0386', 'Case', 'F');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Hair', '0386', 'Case', 'G');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Hair', '0386', 'Case', 'K');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Hair', '0386', 'Case', 'R');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Hair', '0386', 'Case', 'S');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Hair', 'A868', 'Case', 'L1');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Hair', 'A868', 'Case', 'L2');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Hair', 'A868', 'Case', 'L3');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Hair', 'A868', 'Case', 'LS');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Hair', '1864', 'Case', 'S');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Hair', 'A684', 'Case', 'Csite');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Hair', 'A685', 'Case', 'Csite');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Hair', 'A727', 'Case', 'Csite');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Hair', 'A743', 'Case', 'Csite');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('PCC', '2799', 'Case', 'L2');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('PCC', '2799', 'Case', 'L3');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('PCC', '2799', 'Case', 'L4');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('PCC', '2799', 'Case', 'L5');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('PCC', '2799', 'Case', 'L6');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('PCC', '2799', 'Case', 'C1');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('PCC', 'A664', 'Case', 'L7');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Baby', '1864', 'Case', 'CP21');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Baby', '1864', 'Case', 'CP23');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Baby', '1864', 'Case', 'CP24');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Baby', '1869', 'Case', 'CP81');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Baby', '1869', 'Case', 'CP83');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Baby', '1869', 'Case', 'CP84');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Baby', '1869', 'Case', 'CP86');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('FC', '1864', 'Case', 'L2');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('FC', '1864', 'Case', 'L3');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('FC', '1864', 'Case', 'L4');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('FC', '0386', 'Case', '11');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('FC', '0386', 'Case', '12');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('FC', '0386', 'Case', '13A');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('FC', '0386', 'Case', '13B');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Oral', '1864', 'Case', '1');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Oral', '1864', 'Case', '2');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Oral', '1864', 'Case', '3');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Oral', '0386', 'Case', 'OA');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Oral', '0386', 'Case', 'OB');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Oral', '0386', 'Case', 'OC');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Oral', '0386', 'Case', 'OD');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Oral', '0386', 'Case', 'OE');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Oral', '0386', 'Case', 'OF');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Oral', '0386', 'Case', 'OG');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Shave Care', 'A743', 'Case', 'Csite');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Hair', '2799', 'Case', 'L1');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Shave Care', '7811', 'Case', 'CFFS');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Shave Care', '7811', 'Case', 'RFFS');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Shave Care', '7811', 'Case', 'Rota');
INSERT INTO dc_factoryrealtime (r_bu, r_plantcode, datatype, r_linecode) VALUES ('Shave Care', '7811', 'Case', 'TUCK');