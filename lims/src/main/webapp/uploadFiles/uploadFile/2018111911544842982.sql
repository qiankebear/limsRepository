#创建人：熊燕标	
#版本:V1.0.0												 
#创建时间：2018/10/22 

/* 修改系统用户表，添加字段 */
Alter TABLE sys_user ADD COLUMN `position_status` tinyint(4)  NOT NULL DEFAULT '1'  COMMENT '在职状态(1.在职 2.离职)';
Alter TABLE sys_user ADD COLUMN `visit_times` int(10) unsigned NOT NULL  COMMENT '访问次数';
Alter TABLE sys_user ADD COLUMN `prositon_category` tinyint(4) NOT NULL DEFAULT '1' COMMENT  '职位类别(1.法医 2.临床 )';

#修改版本:V1.0.1												 
#创建时间：2018/11/1 
#1.修改客户表，客户编号字段改为  varchar(100)

#修改版本:V1.0.2							 
#创建时间：2018/11/2
#1.新增产品说明书表---lims_product_manual
#2.仪器使用记录表(lims_instrument_record)   增加使用时间字段(instrument_time)    
#Alter TABLE lims_instrument_record  ADD COLUMN `instrument_time` timestamp  NOT NULL  CURRENT_TIMESTAMP COMMENT  '仪器使用时间)';

#修改版本:V1.0.3							 
#创建时间：2018/11/7
#1.样本表lims_sample新增关联项目表字段---sample_project_id
#Alter TABLE lims_sample  ADD COLUMN `sample_project_id` int(10)  NOT NULL   COMMENT  '关联项目表';


#修改版本:V1.0.4						 
#创建时间：2018/11/7
#1.样本表lims_sample去掉样本类型字段-special_sample
#2.孔类型表 hole_sample_course 样本进程  hole_sample_remark 样本备注   hole_special_sample 样本类型   hole_sample_serial 轮数

#修改版本:V1.0.5						 
#创建时间：2018/11/9
#创建触发器: sys_user_visit_times_update

#修改版本:V1.1.0						 
#创建时间：2018/11/12
#增加资源路径表: lims_resource_path

#修改版本:V1.2.0						 
#创建时间：2018/11/12
#1.项目表加一个关联客户表的字段
#Alter TABLE lims_project  ADD COLUMN `project_client` int(10)   NOT NULL  COMMENT '客户名称(关联客户表)'; 
#2.打孔记录表加一个关联孔板表的字段
#Alter TABLE lims_pore_plate_record  ADD COLUMN `pore_pore` int(10)   NOT NULL  COMMENT '孔板编号(关联孔板表)';




#项目表   
DROP TABLE IF EXISTS `lims_project`;
CREATE TABLE `lims_project` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `project_number` varchar(255)  NOT NULL  COMMENT '项目编号',
  `project_name` varchar(255)  NOT NULL COMMENT   '项目名称',
  `project_number_abbreviation`  varchar(255)  NOT NULL COMMENT  '项目名缩写(英文)',
  `project_status` tinyint(4)  NOT NULL DEFAULT '1' COMMENT '项目状态（1准备中 2进行中 3已完成 4其它）',
  `recheck_hole_amount` int(10)  NOT NULL  COMMENT  '复核孔个数',
  `project_starttime` timestamp  NOT NULL  DEFAULT CURRENT_TIMESTAMP  COMMENT '项目开始时间',
  `project_endtime` timestamp  NOT NULL  COMMENT '项目结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目表';
Alter TABLE lims_project  ADD COLUMN `project_client` int(10)   NOT NULL  COMMENT '客户名称(关联客户表)';

#项目和用户关系表
DROP TABLE IF EXISTS `lims_project_user`;
CREATE TABLE `lims_project_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `project_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '关联项目表',
  `user_id`  varchar(32)  NOT NULL COMMENT '关联用户表',
  `member_kind` tinyint(4)  NOT NULL DEFAULT '0' COMMENT  '身份(1.项目负责人 2.实验员 3.访客)',
  `project_permission` tinyint(4)  NOT NULL  COMMENT  '项目修改权限 (1.可修改 2.不可修改)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目和用户关系表';

#样本表
DROP TABLE IF EXISTS `lims_sample`;
CREATE TABLE `lims_sample` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `sample_number` varchar(100)  NOT NULL DEFAULT '0' COMMENT '样本编号',
  `sample_generate_time` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '收样时间',
  `sample_course` tinyint(4)  NOT NULL DEFAULT '1' COMMENT '样本进程(1.成功 2.重做 3.问题样本 4.放弃重做)',
  `sample_serial` tinyint(4)  NOT NULL DEFAULT '1' COMMENT '轮数(1.第一轮 2.第二轮 3.第三轮 4.第四轮)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='样本表';
Alter TABLE lims_sample  ADD COLUMN `sample_project_id` int(10)  NOT NULL   COMMENT  '关联项目表';

#修改记录表
DROP TABLE IF EXISTS `lims_update_record`;
CREATE TABLE `lims_update_record` (
	`id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
	`hole_number` varchar(12)  NOT NULL COMMENT '孔坐标',
	`hole_poreid` int(10)  unsigned NOT NULL  COMMENT '孔板id关联孔板表',
	`oldhole_type` tinyint(4)  NOT NULL DEFAULT '1'   COMMENT '旧孔类型(1.样本编号 2.P 3.O  4.复核孔 5.ladder 6.空孔)',
	`newhole_type` tinyint(4)  NOT NULL DEFAULT '1'   COMMENT '新孔类型(1.样本编号 2.P 3.O  4.复核孔 5.ladder 6.空孔)',
	`old_sampleid` int(10) unsigned NOT NULL  COMMENT '旧样本id关联样本表',
	`new_sampleid` int(10) unsigned NOT NULL  COMMENT '新样本id关联样本表',
	`oldspecial_sample` tinyint(4)  NOT NULL  COMMENT  '样本类型(0-正常,1-微变异、2-稀有等位、3-三等位、4-重复分型、5-失败、6-其它 、7-空卡 、8-其它问题)',
	`newspecial_sample` tinyint(4)  NOT NULL  COMMENT '样本类型(0-正常,1-微变异、2-稀有等位、3-三等位、4-重复分型、5-失败、6-其它 、7-空卡 、8-其它问题)',
	`update_type` tinyint(4)  NOT NULL    COMMENT '修改步骤(1.布板(修改样本编号、孔类型) 2.打孔(修改问题样本)  4.分析(修改特殊类型))',
	`update_people` varchar(32)  NOT NULL    COMMENT '修改者,关联用户表',
	`update_time`   timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP   COMMENT '修改时间',

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='修改记录表';

#孔板表
DROP TABLE IF EXISTS `lims_pore_plate`;
CREATE TABLE `lims_pore_plate` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `pore_plate_name` varchar(255)  NOT NULL  COMMENT '孔板名称，孔板默认编号 项目缩写-年月日-序号',
  `plate_project_id` int(10) unsigned NOT NULL  COMMENT '所属项目编号(关联项目表)',
  `fabric_swatch_people` varchar(100)  NOT NULL  COMMENT '布板者(关联用户表)',
  `sample_sum` int(10)  NOT NULL  COMMENT '样本总数',
  `pore_plate_type` tinyint(4)  NOT NULL DEFAULT '1'  COMMENT '孔板类型(1.普通板 2.质检板)',
  `pore_plate_quality` tinyint(4)  NOT NULL DEFAULT '1'  COMMENT '复核质检(1.通过 2.不通过)',
  `pore_plate_entirety` tinyint(4)  NOT NULL DEFAULT '1'  COMMENT '是否整版重扩(1.是 2.不是)',
  `current_procedure` varchar(12)  NOT NULL COMMENT '步骤(0.准备 1.布板 2.打孔 3.扩增 4.分析 5.检测 )',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='孔板表';

#孔类型表  
DROP TABLE IF EXISTS `lims_hole_type`;
CREATE TABLE `lims_hole_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `hole_type` tinyint(4)  NOT NULL DEFAULT '1'   COMMENT '孔类型(1.样本编号 2.P 3.O  4.复核孔 5.ladder 6.空孔 7.质检孔)',
  `hole_number` varchar(12)  NOT NULL COMMENT '孔编号',
   `hole_sample_course` tinyint(4)  NOT NULL DEFAULT '1' COMMENT '样本进程(1.成功 2.重做 3.问题样本 4.放弃重做)',
  `hole_sample_remark` varchar(255)  NOT NULL DEFAULT '' COMMENT '样本备注',
  `hole_special_sample` tinyint(4)  NOT NULL  COMMENT '样本类型(0-正常,1-微变异、2-稀有等位、3-三等位、4-重复分型、5-失败、6-其它 、7-空卡 、8-其它问题)',
  `hole_sample_serial` tinyint(4)  NOT NULL DEFAULT '1' COMMENT  '轮数(1.第一轮 2.第二轮 3.第三轮 4.第四轮)',
  `hole_poreid` int(10)  unsigned NOT NULL  COMMENT '孔板id关联孔板表',
  `hole_sampleid` int(10) unsigned NOT NULL  COMMENT '样本id关联样本表',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='孔类型表';

#孔板表和用户表的关系表             
DROP TABLE IF EXISTS `lims_pore_user`;
CREATE TABLE `lims_pore_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `pore_plate_id` int(10) unsigned  NOT NULL   COMMENT '孔板编号(关联孔板表)',
  `pore_plate_userid` varchar(100)   NOT NULL COMMENT '操作者名字(关联用户表)',
  `pore_plate_procedure` varchar(12)  NOT NULL COMMENT '步骤(1.布板 2.打孔 3.扩增 4.分析 )',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='孔板表和用户表的关系表';

#打孔记录表
DROP TABLE IF EXISTS `lims_pore_plate_record`;
CREATE TABLE `lims_pore_plate_record` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `slotting_userid` varchar(100)  NOT NULL  COMMENT '打孔人关联用户表',
  `slotting_complete` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '打孔完成时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='打孔记录表';
Alter TABLE lims_pore_plate_record  ADD COLUMN `pore_pore` int(10)   NOT NULL  COMMENT '孔板编号(关联孔板表)';

#PCR扩增记录
DROP TABLE IF EXISTS `lims_pcr_record`;
CREATE TABLE `lims_pcr_record` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `pcr_pore` int(10)  NOT NULL  COMMENT '孔板编号(关联孔板表)',
  `operation_time` timestamp  NOT NULL   COMMENT '上机时间',
  `deplane_time` timestamp  NOT NULL   COMMENT '下机时间',
  `pcr_time` timestamp  NOT NULL   COMMENT '扩增时长',
  `pcr_experimenter_userid` varchar(100)    NOT NULL   COMMENT '实验人员(关联用户表)',
  `instrument_info`  varchar(32)   NOT NULL   COMMENT  '仪器信息(关联仪器表)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='PCR扩增记录';

#产品说明书表
DROP TABLE IF EXISTS `lims_product_manual`;
CREATE TABLE `lims_product_manual` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `project_id` int(10)  NOT NULL  COMMENT '项目名称(关联项目表)',
  `manual_name` varchar(100)  NOT NULL  COMMENT '说明书名称',
  `manual_url` varchar(300)  NOT NULL  COMMENT '说明书url',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品说明书表';

#检测记录表
DROP TABLE IF EXISTS `lims_detection_record`;
CREATE TABLE `lims_detection_record` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `detection_pore` int(10)  NOT NULL  COMMENT '孔板编号(关联孔板表)',
  `operation_time` timestamp  NOT NULL   COMMENT '上机时间',
  `deplane_time` timestamp  NOT NULL   COMMENT '下机时间',
  `detection_time` timestamp  NOT NULL   COMMENT '检测总时长',
  `special_condition` varchar(255)  NOT NULL     COMMENT '特殊情况',
  `detection_experimenter_userid` varchar(100)    NOT NULL   COMMENT'实验人员(关联用户表)',
  `instrument_info` varchar(32)    NOT NULL   COMMENT'仪器信息(关联仪器表)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='检测记录表';

#分析记录表
DROP TABLE IF EXISTS `lims_analyze_record`;
CREATE TABLE `lims_analyze_record` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `analyze_pore` int(10)  NOT NULL  COMMENT '孔板编号(关联孔板表)',
  `slotting_userid` varchar(100)  NOT NULL  COMMENT '分析人(关联用户表)',
  `slotting_complete` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT'分析完成时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分析记录表';

#客户表
DROP TABLE IF EXISTS `lims_client`;
CREATE TABLE `lims_client` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `client_number` varchar(100)  NOT NULL  COMMENT '客户编号',
  `client_name` varchar(32)  NOT NULL   COMMENT '客户名称',
  `client_linkman` varchar(32)  NOT NULL   COMMENT '客户联系人名字',
  `client_phone` varchar(32)  NOT NULL   COMMENT '客户联系人电话',
  `client_emil` varchar(32)  NOT NULL   COMMENT '客户邮箱',
  `client_address` varchar(32)  NOT NULL   COMMENT '客户地址',
  `client_jointime` varchar(32)  NOT NULL   COMMENT '客户成立时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户表';

#仪器表
DROP TABLE IF EXISTS `lims_instrument`;
CREATE TABLE `lims_instrument` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `instrument_number` varchar(255)  NOT NULL  COMMENT '仪器编号',
  `instrument_type` varchar(255)  NOT NULL   COMMENT '仪器型号',
  `instrument_status` tinyint(4)  NOT NULL   COMMENT '仪器状态(1. 运行 2.保养 3.检修 4.封存 5.报废)',
  `instrument_classify` tinyint(4)  NOT NULL   COMMENT '仪器类别(1.PCR仪-对应扩增步骤 2.测序仪-对应检测步骤  3.移液器 4.其它设备)',
  `instrument_other` varchar(255)  NOT NULL   COMMENT '其它信息',
  `save_procedure` varchar(255)  NOT NULL   COMMENT '保存的程序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仪器表';


#仪器使用记录表
DROP TABLE IF EXISTS `lims_instrument_record`;
CREATE TABLE `lims_instrument_record` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `instrument_id` int(10) unsigned  NOT NULL  COMMENT '仪器id(关联仪器表)',
  `instrument_user` varchar(100)   NOT NULL   COMMENT '仪器使用者(关联user表)',
  `instrument_exception_info` varchar(255)  DEFAULT ''    COMMENT '仪器异常信息',
  `instrument_solution` varchar(255)  DEFAULT ''    COMMENT  '解决方法',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仪器使用记录表';
  `instrument_time` timestamp  NOT NULL  CURRENT_TIMESTAMP  COMMENT '使用时间',

#试剂盒与其余试剂和耗材表
DROP TABLE IF EXISTS `lims_kit`;
CREATE TABLE `lims_kit` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `kit_name` varchar(255)   NOT NULL  COMMENT '名称',
  `kit_type` tinyint(4)   NOT NULL   COMMENT '所属分类(1.A 2.X 3.Y 4.其它 10.采血卡 11.一代测序仪的耗材 12.其他耗材)',
  `kit_remark` varchar(255)  NOT NULL   COMMENT '备注',
  `classify` tinyint(4)  NOT NULL   COMMENT '类别(1.试剂盒 2其余试剂和耗材)',
  `specification` varchar(255)  NOT NULL   COMMENT '规格',
  `brand` varchar(255)  NOT NULL   COMMENT '品牌',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='试剂盒与其余试剂和耗材表';

#试剂盒与其余试剂和耗材库存表
DROP TABLE IF EXISTS `lims_kit_repertory`;
CREATE TABLE `lims_kit_repertory` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `kit_id` int(10) unsigned   NOT NULL  COMMENT '名称(关联试剂盒与其余试剂和耗材表)',
  `kit_num` int(11)   NOT NULL   COMMENT '现存量',
  `kit_project_id` int(10) unsigned  NOT NULL   COMMENT '项目编号(关联项目表)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='试剂盒与其余试剂和耗材库存表';

#试剂盒出入库记录表
DROP TABLE IF EXISTS `lims_kit_record`;
CREATE TABLE `lims_kit_record` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `kit_repertory_id` int(10) unsigned  NOT NULL  COMMENT '名称(关联试剂盒与其余试剂和耗材表)',
  `peration_time` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP   COMMENT '操作时间',
  `operation_name` varchar(100)   NOT NULL  COMMENT '操作者(关联user表)',
  `change_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '调整类型，0-增加 1-减少',
  `change_count` int(11) NOT NULL DEFAULT '0' COMMENT '调整数量',
  `current_count` int(11) NOT NULL DEFAULT '0' COMMENT '当前库存数量',
  `complete_count` int(11) NOT NULL DEFAULT '0' COMMENT '调整后数量',
  `repertory_project_id` int(10) unsigned  NOT NULL   COMMENT '项目编号(关联项目表)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='试剂盒出入库记录表';

#资源路径表
DROP TABLE IF EXISTS `lims_resource_path`;
CREATE TABLE `lims_resource_path` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `lims_resource` varchar(100)    NOT NULL  COMMENT '资源名称',
  `lims_path` varchar(200)   NOT NULL   COMMENT '资源路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源路径表';

#触发器，sys_user表中次数随最后一次登录时间的修改而修改
DROP TRIGGER IF EXISTS `sys_user_visit_times_update`;
DELIMITER ;;
create trigger `sys_user_visit_times_update` before update on `sys_user` for each row
begin
	IF(OLD.LAST_LOGIN != NEW.LAST_LOGIN )THEN
		SET NEW.visit_times = NEW.visit_times + 1;
	END IF;
end
;;
DELIMITER ;





























