/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.7.22 : Database - deehow
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `act_evt_log` */

DROP TABLE IF EXISTS `act_evt_log`;

CREATE TABLE `act_evt_log` (
  `LOG_NR_` bigint(20) NOT NULL AUTO_INCREMENT,
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TIME_STAMP_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DATA_` longblob,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
  `IS_PROCESSED_` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`LOG_NR_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_ge_bytearray` */

DROP TABLE IF EXISTS `act_ge_bytearray`;

CREATE TABLE `act_ge_bytearray` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTES_` longblob,
  `GENERATED_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_BYTEARR_DEPL` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_BYTEARR_DEPL` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_ge_property` */

DROP TABLE IF EXISTS `act_ge_property`;

CREATE TABLE `act_ge_property` (
  `NAME_` varchar(64) COLLATE utf8_bin NOT NULL,
  `VALUE_` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  PRIMARY KEY (`NAME_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_hi_actinst` */

DROP TABLE IF EXISTS `act_hi_actinst`;

CREATE TABLE `act_hi_actinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin NOT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CALL_PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ACT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_ACT_INST_START` (`START_TIME_`) USING BTREE,
  KEY `ACT_IDX_HI_ACT_INST_END` (`END_TIME_`) USING BTREE,
  KEY `ACT_IDX_HI_ACT_INST_PROCINST` (`PROC_INST_ID_`,`ACT_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_ACT_INST_EXEC` (`EXECUTION_ID_`,`ACT_ID_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_hi_attachment` */

DROP TABLE IF EXISTS `act_hi_attachment`;

CREATE TABLE `act_hi_attachment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `URL_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CONTENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TIME_` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_hi_comment` */

DROP TABLE IF EXISTS `act_hi_comment`;

CREATE TABLE `act_hi_comment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TIME_` datetime(3) NOT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `MESSAGE_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `FULL_MSG_` longblob,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_hi_detail` */

DROP TABLE IF EXISTS `act_hi_detail`;

CREATE TABLE `act_hi_detail` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TIME_` datetime(3) NOT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_DETAIL_PROC_INST` (`PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DETAIL_ACT_INST` (`ACT_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DETAIL_TIME` (`TIME_`) USING BTREE,
  KEY `ACT_IDX_HI_DETAIL_NAME` (`NAME_`) USING BTREE,
  KEY `ACT_IDX_HI_DETAIL_TASK_ID` (`TASK_ID_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_hi_identitylink` */

DROP TABLE IF EXISTS `act_hi_identitylink`;

CREATE TABLE `act_hi_identitylink` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_USER` (`USER_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_IDENT_LNK_TASK` (`TASK_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_IDENT_LNK_PROCINST` (`PROC_INST_ID_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_hi_procinst` */

DROP TABLE IF EXISTS `act_hi_procinst`;

CREATE TABLE `act_hi_procinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `START_USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `END_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `PROC_INST_ID_` (`PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_PRO_INST_END` (`END_TIME_`) USING BTREE,
  KEY `ACT_IDX_HI_PRO_I_BUSKEY` (`BUSINESS_KEY_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_hi_taskinst` */

DROP TABLE IF EXISTS `act_hi_taskinst`;

CREATE TABLE `act_hi_taskinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `CLAIM_TIME_` datetime(3) DEFAULT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `DUE_DATE_` datetime(3) DEFAULT NULL,
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_hi_varinst` */

DROP TABLE IF EXISTS `act_hi_varinst`;

CREATE TABLE `act_hi_varinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` datetime(3) DEFAULT NULL,
  `LAST_UPDATED_TIME_` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_PROCVAR_PROC_INST` (`PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_PROCVAR_NAME_TYPE` (`NAME_`,`VAR_TYPE_`) USING BTREE,
  KEY `ACT_IDX_HI_PROCVAR_TASK_ID` (`TASK_ID_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_id_group` */

DROP TABLE IF EXISTS `act_id_group`;

CREATE TABLE `act_id_group` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_id_info` */

DROP TABLE IF EXISTS `act_id_info`;

CREATE TABLE `act_id_info` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `VALUE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PASSWORD_` longblob,
  `PARENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_id_membership` */

DROP TABLE IF EXISTS `act_id_membership`;

CREATE TABLE `act_id_membership` (
  `USER_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `GROUP_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`USER_ID_`,`GROUP_ID_`),
  KEY `ACT_FK_MEMB_GROUP` (`GROUP_ID_`) USING BTREE,
  CONSTRAINT `act_id_membership_ibfk_1` FOREIGN KEY (`GROUP_ID_`) REFERENCES `act_id_group` (`ID_`),
  CONSTRAINT `act_id_membership_ibfk_2` FOREIGN KEY (`USER_ID_`) REFERENCES `act_id_user` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_id_user` */

DROP TABLE IF EXISTS `act_id_user`;

CREATE TABLE `act_id_user` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `FIRST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LAST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PWD_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PICTURE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_re_deployment` */

DROP TABLE IF EXISTS `act_re_deployment`;

CREATE TABLE `act_re_deployment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `DEPLOY_TIME_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_re_model` */

DROP TABLE IF EXISTS `act_re_model`;

CREATE TABLE `act_re_model` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LAST_UPDATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `VERSION_` int(11) DEFAULT NULL,
  `META_INFO_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_EXTRA_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_MODEL_SOURCE` (`EDITOR_SOURCE_VALUE_ID_`) USING BTREE,
  KEY `ACT_FK_MODEL_SOURCE_EXTRA` (`EDITOR_SOURCE_EXTRA_VALUE_ID_`) USING BTREE,
  KEY `ACT_FK_MODEL_DEPLOYMENT` (`DEPLOYMENT_ID_`) USING BTREE,
  CONSTRAINT `act_re_model_ibfk_1` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`),
  CONSTRAINT `act_re_model_ibfk_2` FOREIGN KEY (`EDITOR_SOURCE_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `act_re_model_ibfk_3` FOREIGN KEY (`EDITOR_SOURCE_EXTRA_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_re_procdef` */

DROP TABLE IF EXISTS `act_re_procdef`;

CREATE TABLE `act_re_procdef` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DGRM_RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `HAS_START_FORM_KEY_` tinyint(4) DEFAULT NULL,
  `HAS_GRAPHICAL_NOTATION_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_PROCDEF` (`KEY_`,`VERSION_`,`TENANT_ID_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_ru_event_subscr` */

DROP TABLE IF EXISTS `act_ru_event_subscr`;

CREATE TABLE `act_ru_event_subscr` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `EVENT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EVENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTIVITY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CONFIGURATION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EVENT_SUBSCR_CONFIG_` (`CONFIGURATION_`),
  KEY `ACT_FK_EVENT_EXEC` (`EXECUTION_ID_`),
  CONSTRAINT `ACT_FK_EVENT_EXEC` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_ru_execution` */

DROP TABLE IF EXISTS `act_ru_execution`;

CREATE TABLE `act_ru_execution` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_EXEC_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `IS_ACTIVE_` tinyint(4) DEFAULT NULL,
  `IS_CONCURRENT_` tinyint(4) DEFAULT NULL,
  `IS_SCOPE_` tinyint(4) DEFAULT NULL,
  `IS_EVENT_SCOPE_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `CACHED_ENT_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EXEC_BUSKEY` (`BUSINESS_KEY_`),
  KEY `ACT_FK_EXE_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_EXE_PARENT` (`PARENT_ID_`),
  KEY `ACT_FK_EXE_SUPER` (`SUPER_EXEC_`),
  KEY `ACT_FK_EXE_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_EXE_PARENT` FOREIGN KEY (`PARENT_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ACT_FK_EXE_SUPER` FOREIGN KEY (`SUPER_EXEC_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_ru_identitylink` */

DROP TABLE IF EXISTS `act_ru_identitylink`;

CREATE TABLE `act_ru_identitylink` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_IDENT_LNK_GROUP` (`GROUP_ID_`),
  KEY `ACT_IDX_ATHRZ_PROCEDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_TSKASS_TASK` (`TASK_ID_`),
  KEY `ACT_FK_IDL_PROCINST` (`PROC_INST_ID_`),
  CONSTRAINT `ACT_FK_ATHRZ_PROCEDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_IDL_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TSKASS_TASK` FOREIGN KEY (`TASK_ID_`) REFERENCES `act_ru_task` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_ru_job` */

DROP TABLE IF EXISTS `act_ru_job`;

CREATE TABLE `act_ru_job` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int(11) DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_JOB_EXCEPTION` (`EXCEPTION_STACK_ID_`),
  CONSTRAINT `ACT_FK_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_ru_task` */

DROP TABLE IF EXISTS `act_ru_task`;

CREATE TABLE `act_ru_task` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DELEGATION_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `DUE_DATE_` datetime(3) DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_TASK_CREATE` (`CREATE_TIME_`),
  KEY `ACT_FK_TASK_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_TASK_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_TASK_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_TASK_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_ru_variable` */

DROP TABLE IF EXISTS `act_ru_variable`;

CREATE TABLE `act_ru_variable` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_VARIABLE_TASK_ID` (`TASK_ID_`),
  KEY `ACT_FK_VAR_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_VAR_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_VAR_BYTEARRAY` (`BYTEARRAY_ID_`),
  CONSTRAINT `ACT_FK_VAR_BYTEARRAY` FOREIGN KEY (`BYTEARRAY_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `common_verification` */

DROP TABLE IF EXISTS `common_verification`;

CREATE TABLE `common_verification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mobile` char(11) NOT NULL COMMENT '手机号',
  `code` varchar(16) NOT NULL COMMENT '验证码',
  `biz_type` varchar(16) NOT NULL DEFAULT 'register' COMMENT '业务类型',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `expired_time` datetime NOT NULL COMMENT '过期时间',
  `validated` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否验证1=是，0=否',
  PRIMARY KEY (`id`),
  KEY `validated` (`validated`),
  KEY `mobile` (`mobile`),
  KEY `code` (`code`),
  KEY `biz_code` (`biz_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='验证码';

/*Table structure for table `component_add_apply` */

DROP TABLE IF EXISTS `component_add_apply`;

CREATE TABLE `component_add_apply` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `apply_process_key` varchar(64) NOT NULL DEFAULT '' COMMENT '新建申请的流程实例key',
  `put_storage_process_key` varchar(64) NOT NULL DEFAULT '' COMMENT '入库的流程实例key',
  `status_` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'status = 0 //初始态，未领取 ；status = 1 //订单被领取；status = 2 //手动开启了第一个流程；status = 3 //第一个流程审批通过；status = 4 //预留状态。第一个流程被打回；status = 5 //第二个流程启动；status = 6 //第二个流程通过；status = 7 //第二个流程不通过;status = 8 // 已入库;status = 9 //已经 点击重新修改了；',
  `claimant_` bigint(20) NOT NULL DEFAULT '0' COMMENT '认领人。（=0 还未认领）',
  `part_name` varchar(100) NOT NULL DEFAULT '' COMMENT '元器件名称',
  `package_type` varchar(20) NOT NULL DEFAULT '' COMMENT '封装形式',
  `data_sheet` varchar(100) NOT NULL DEFAULT '' COMMENT '手册',
  `email_` varchar(100) NOT NULL DEFAULT '' COMMENT '联系人邮箱',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '备注（物料描述、建库特殊要求等）',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`),
  KEY `idx_s_c` (`status_`,`claimant_`),
  KEY `idx_apply_process_key` (`apply_process_key`),
  KEY `idx_put_storage_process_key` (`put_storage_process_key`)
) ENGINE=InnoDB AUTO_INCREMENT=1034622546179055618 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='新元器件新增申请表';

/*Table structure for table `component_category` */

DROP TABLE IF EXISTS `component_category`;

CREATE TABLE `component_category` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单编号',
  `category_no` varchar(20) NOT NULL DEFAULT '' COMMENT '类目编号',
  `category_name` varchar(20) NOT NULL DEFAULT '' COMMENT '类目名称',
  `sort_no` int(4) NOT NULL DEFAULT '99' COMMENT '排序号',
  `is_show` tinyint(1) NOT NULL DEFAULT '1' COMMENT '叶子节点(0:树枝节点;1:叶子节点)',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父级类目',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `uk_no` (`category_no`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1034342657983045633 DEFAULT CHARSET=utf8 COMMENT='元器件类目管理';

/*Table structure for table `component_goods` */

DROP TABLE IF EXISTS `component_goods`;

CREATE TABLE `component_goods` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `number_` varchar(20) NOT NULL DEFAULT '' COMMENT '物品编号',
  `int_number` int(11) NOT NULL DEFAULT '0' COMMENT '用来记录当前编号对应的 数字值',
  `name_` varchar(20) NOT NULL DEFAULT '' COMMENT '物品名称',
  `category_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '末级分类id',
  `is_revise` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1 正常，2 提起审批修改中',
  `version_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '最新版本id',
  `part_type` varchar(20) NOT NULL DEFAULT '' COMMENT '器件类型',
  `product_model` varchar(100) NOT NULL DEFAULT '' COMMENT '规格型号',
  `value_` varchar(20) NOT NULL DEFAULT '' COMMENT 'value',
  `manual_` varchar(100) NOT NULL DEFAULT '' COMMENT '手册',
  `package_type` varchar(50) NOT NULL DEFAULT '' COMMENT '封装类型',
  `sym_cds` varchar(20) NOT NULL DEFAULT '' COMMENT '原理图符合',
  `sym_cds_olb` varchar(100) NOT NULL DEFAULT '' COMMENT '原理图符合olb',
  `sym_cds_svg` varchar(100) NOT NULL DEFAULT '' COMMENT '原理图符合svg',
  `foot_print_cds` varchar(20) NOT NULL DEFAULT '' COMMENT 'Cadence封装符号',
  `foot_print_cds_olb` varchar(100) NOT NULL DEFAULT '' COMMENT 'Cadence封装符号olb',
  `foot_print_cds_svg` varchar(100) NOT NULL DEFAULT '' COMMENT 'Cadence封装符号svg',
  `model_3d` varchar(20) NOT NULL DEFAULT '' COMMENT '三维模型',
  `model_3d_path` varchar(100) NOT NULL DEFAULT '' COMMENT '三维模型path',
  `manufacturer_` varchar(100) NOT NULL DEFAULT '' COMMENT '生产厂家',
  `pre_ranking` varchar(20) NOT NULL DEFAULT '' COMMENT '优选等级/质量等级',
  `remark_` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `uk_number` (`number_`),
  KEY `idx_category_id` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1034399908039024641 DEFAULT CHARSET=utf8 COMMENT='元器件物品表';

/*Table structure for table `component_goods_version` */

DROP TABLE IF EXISTS `component_goods_version`;

CREATE TABLE `component_goods_version` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `number_` varchar(20) NOT NULL DEFAULT '' COMMENT '版本编号',
  `goods_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '元器件物品id',
  `is_main` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1 主版本，2历史版本',
  `part_type` varchar(20) NOT NULL DEFAULT '' COMMENT '器件类型',
  `product_model` varchar(100) NOT NULL DEFAULT '' COMMENT '规格型号',
  `value_` varchar(20) NOT NULL DEFAULT '' COMMENT 'value',
  `manual_` varchar(100) NOT NULL DEFAULT '' COMMENT '手册',
  `package_type` varchar(50) NOT NULL DEFAULT '' COMMENT '封装形式',
  `sym_cds` varchar(20) NOT NULL DEFAULT '' COMMENT '原理图符合',
  `sym_cds_olb` varchar(100) NOT NULL DEFAULT '' COMMENT '原理图符合olb',
  `sym_cds_svg` varchar(100) NOT NULL DEFAULT '' COMMENT '原理图符合svg',
  `foot_print_cds` varchar(20) NOT NULL DEFAULT '' COMMENT 'Cadence封装符号',
  `foot_print_cds_olb` varchar(100) NOT NULL DEFAULT '' COMMENT 'Cadence封装符号olb',
  `foot_print_cds_svg` varchar(100) NOT NULL DEFAULT '' COMMENT 'Cadence封装符号svg',
  `model_3d` varchar(20) NOT NULL DEFAULT '' COMMENT '三维模型',
  `model_3d_path` varchar(100) NOT NULL DEFAULT '' COMMENT '三维模型path',
  `manufacturer_` varchar(100) NOT NULL DEFAULT '' COMMENT '生产厂家',
  `pre_ranking` varchar(20) NOT NULL DEFAULT '' COMMENT '优选等级/质量等级',
  `remark_` varchar(200) NOT NULL DEFAULT '' COMMENT '备注,描述',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`),
  KEY `idx_g_m` (`goods_id`,`is_main`)
) ENGINE=InnoDB AUTO_INCREMENT=1034399908043218945 DEFAULT CHARSET=utf8 COMMENT='元器件版本表';

/*Table structure for table `component_olb_library` */

DROP TABLE IF EXISTS `component_olb_library`;

CREATE TABLE `component_olb_library` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `type_` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1原理图符合，2封装符号 ',
  `olb_name` varchar(20) NOT NULL DEFAULT '',
  `olb_path` varchar(200) NOT NULL DEFAULT '' COMMENT 'olb文件地址',
  `xml_name` varchar(20) NOT NULL DEFAULT '',
  `xml_path` varchar(200) NOT NULL DEFAULT '' COMMENT 'xml文件地址',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB AUTO_INCREMENT=1034392920580218881 DEFAULT CHARSET=utf8 COMMENT='olb库';

/*Table structure for table `component_property_category_relation` */

DROP TABLE IF EXISTS `component_property_category_relation`;

CREATE TABLE `component_property_category_relation` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `name_` varchar(20) NOT NULL DEFAULT '' COMMENT '显示名称',
  `property_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '属性id',
  `category_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '末级分类id',
  `sort_no` int(4) NOT NULL DEFAULT '9999' COMMENT '排序号',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `uk_c_p` (`category_id`,`property_id`),
  KEY `idx_category_id` (`category_id`,`sort_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1034287537672962049 DEFAULT CHARSET=utf8 COMMENT='属性类目关系表';

/*Table structure for table `component_public_property_name` */

DROP TABLE IF EXISTS `component_public_property_name`;

CREATE TABLE `component_public_property_name` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '末级分类id',
  `part_type` varchar(20) NOT NULL DEFAULT '' COMMENT '器件类型 名称',
  `product_model` varchar(20) NOT NULL DEFAULT '' COMMENT '规格型号 名称',
  `value_` varchar(20) NOT NULL DEFAULT '' COMMENT 'value 名称',
  `manual_` varchar(20) NOT NULL DEFAULT '' COMMENT '手册 名称',
  `package_type` varchar(20) NOT NULL DEFAULT '' COMMENT '封装形式 名称',
  `sym_cds` varchar(20) NOT NULL DEFAULT '' COMMENT '原理图符合 名称',
  `foot_print_cds` varchar(20) NOT NULL DEFAULT '' COMMENT 'Cadence封装符号 名称',
  `model_3d` varchar(20) NOT NULL DEFAULT '' COMMENT '三维模型 名称',
  `manufacturer_` varchar(20) NOT NULL DEFAULT '' COMMENT '生产厂家 名称',
  `pre_ranking` varchar(20) NOT NULL DEFAULT '' COMMENT '优选等级/质量等级 名称',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `uk_category_id` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='元器件公共属性标题显示名称维护';

/*Table structure for table `component_revise_apply` */

DROP TABLE IF EXISTS `component_revise_apply`;

CREATE TABLE `component_revise_apply` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `apply_process_key` varchar(64) NOT NULL DEFAULT '' COMMENT '修改申请的流程实例key',
  `put_storage_process_key` varchar(64) NOT NULL DEFAULT '' COMMENT '入库的流程实例key',
  `status_` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'status = 0	//初始态，并且开启了一个流程;status = 1	//修改申请流程通过;status = 2	//修改申请流程打回;status = 3	//此修改被认领==可以修改了;status = 4	//确认修改完成，开启入库流程;status = 5	//入库流程通过;status = 6  //入库流程打回		，打回后可以继续修改，继续提交入库申请;status = 7  //确认入库',
  `claimant_` bigint(20) NOT NULL DEFAULT '0' COMMENT '认领人。（=0 还未认领）',
  `goods_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '元器件id',
  `part_name` varchar(20) NOT NULL DEFAULT '' COMMENT '元器件名称',
  `data_sheet` varchar(200) NOT NULL DEFAULT '' COMMENT '手册',
  `email_` varchar(20) NOT NULL DEFAULT '' COMMENT '联系人邮箱',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '备注（物料描述、建库特殊要求等）',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`),
  KEY `idx_s_c` (`status_`,`claimant_`),
  KEY `idx_apply_process_key` (`apply_process_key`),
  KEY `idx_put_storage_process_key` (`put_storage_process_key`)
) ENGINE=InnoDB AUTO_INCREMENT=1034358903814283265 DEFAULT CHARSET=utf8 COMMENT='新元器件修改申请表';

/*Table structure for table `component_revise_apply_detail` */

DROP TABLE IF EXISTS `component_revise_apply_detail`;

CREATE TABLE `component_revise_apply_detail` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `revise_apply_id` bigint(20) NOT NULL DEFAULT '0',
  `modified` varchar(20) NOT NULL DEFAULT '' COMMENT '修改项',
  `original_value` varchar(100) NOT NULL DEFAULT '' COMMENT '原值',
  `modified_value` varchar(100) NOT NULL DEFAULT '' COMMENT '修改值',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '备注（物料描述、建库特殊要求等）',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`),
  KEY `idx_revise_apply_id` (`revise_apply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1034358903952695297 DEFAULT CHARSET=utf8 COMMENT='新元器件修改申请详细值';

/*Table structure for table `component_revise_public_data` */

DROP TABLE IF EXISTS `component_revise_public_data`;

CREATE TABLE `component_revise_public_data` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `add_apply_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '元器件新增申请id',
  `revise_apply_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '元器件修改申请id',
  `category_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '所属末级分类id',
  `part_type` varchar(20) NOT NULL DEFAULT '' COMMENT '器件类型',
  `product_model` varchar(20) NOT NULL DEFAULT '' COMMENT '规格型号',
  `value_` varchar(20) NOT NULL DEFAULT '' COMMENT 'value',
  `manual_` varchar(100) NOT NULL DEFAULT '' COMMENT '手册',
  `package_type` varchar(20) NOT NULL DEFAULT '' COMMENT '封装形式',
  `sym_cds` varchar(20) NOT NULL DEFAULT '' COMMENT '原理图符合',
  `sym_cds_olb` varchar(100) NOT NULL DEFAULT '' COMMENT '原理图符合olb',
  `sym_cds_svg` varchar(100) NOT NULL DEFAULT '' COMMENT '原理图符合svg',
  `foot_print_cds` varchar(20) NOT NULL DEFAULT '' COMMENT 'Cadence封装符号',
  `foot_print_cds_olb` varchar(100) NOT NULL DEFAULT '' COMMENT 'Cadence封装符号olb',
  `foot_print_cds_svg` varchar(100) NOT NULL DEFAULT '' COMMENT 'Cadence封装符号svg',
  `model_3d` varchar(20) NOT NULL DEFAULT '' COMMENT '三维模型',
  `model_3d_path` varchar(100) NOT NULL DEFAULT '' COMMENT '三维模型path',
  `manufacturer_` varchar(20) NOT NULL DEFAULT '' COMMENT '生产厂家',
  `pre_ranking` varchar(20) NOT NULL DEFAULT '' COMMENT '优选等级/质量等级',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `uk_add_revise` (`add_apply_id`,`revise_apply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1034399139118243841 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='元器件修改过程公共属性数据包';

/*Table structure for table `component_revise_special_data` */

DROP TABLE IF EXISTS `component_revise_special_data`;

CREATE TABLE `component_revise_special_data` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `add_apply_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '元器件新增申请id',
  `revise_apply_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '元器件修改申请id',
  `special_property_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '特殊属性id',
  `value_` varchar(20) NOT NULL DEFAULT '' COMMENT 'value',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `uk_add_revise` (`add_apply_id`,`revise_apply_id`,`special_property_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1034399139273433089 DEFAULT CHARSET=utf8 COMMENT='元器件修改申请特殊属性数据表';

/*Table structure for table `component_special_property` */

DROP TABLE IF EXISTS `component_special_property`;

CREATE TABLE `component_special_property` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `number_` varchar(20) NOT NULL DEFAULT '' COMMENT '属性编号',
  `name_` varchar(20) NOT NULL DEFAULT '' COMMENT '属性名称',
  `show_name` varchar(20) NOT NULL DEFAULT '' COMMENT '显示名称',
  `english_name` varchar(50) NOT NULL DEFAULT '' COMMENT '英文名称',
  `data_type` varchar(50) NOT NULL DEFAULT '' COMMENT '数据类型（完全限定名？，具体怎么定）',
  `category_` varchar(20) NOT NULL DEFAULT '' COMMENT '所属类别',
  `sort_no` int(4) NOT NULL DEFAULT '9999' COMMENT '排序号',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB AUTO_INCREMENT=1034287233804025857 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='元器件特殊属性表';

/*Table structure for table `component_svg_library` */

DROP TABLE IF EXISTS `component_svg_library`;

CREATE TABLE `component_svg_library` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `olb_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'olb库id',
  `svg_name` varchar(20) NOT NULL DEFAULT '' COMMENT 'svg_name',
  `svg_path` varchar(200) NOT NULL DEFAULT '' COMMENT 'svg文件地址',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`),
  KEY `idx_old_id` (`olb_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1034392920638939137 DEFAULT CHARSET=utf8 COMMENT='svg库';

/*Table structure for table `component_version_log` */

DROP TABLE IF EXISTS `component_version_log`;

CREATE TABLE `component_version_log` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `version_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '元器件版本id',
  `special_property_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '特殊属性id',
  `property_name` varchar(20) NOT NULL DEFAULT '' COMMENT '属性名称',
  `value_` varchar(255) NOT NULL DEFAULT '' COMMENT 'value',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`),
  KEY `idx_version_id` (`version_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1034399908206796801 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='元器件修改记录表(若导入一些没有定义属性的值，special_property_id=0即可)';

/*Table structure for table `module_order` */

DROP TABLE IF EXISTS `module_order`;

CREATE TABLE `module_order` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `unit_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户id',
  `unit_name` varchar(20) NOT NULL DEFAULT '' COMMENT '单位名称',
  `unit_code` varchar(100) NOT NULL DEFAULT '' COMMENT '单位编号',
  `order_no` char(16) NOT NULL DEFAULT '' COMMENT '订单编号',
  `sum_` decimal(14,2) NOT NULL DEFAULT '0.00' COMMENT '金额',
  `phone` varchar(20) NOT NULL DEFAULT '' COMMENT '填写订单时预留的接收短信的手机号（运维人员添加的订单不填）',
  `person_num` bigint(20) NOT NULL DEFAULT '0' COMMENT '使用人数',
  `validity_time` varchar(20) NOT NULL DEFAULT '' COMMENT '有效期(从页面中传递)',
  `operating_personnel` varchar(20) DEFAULT NULL COMMENT '操作人',
  `enable_` tinyint(1) NOT NULL DEFAULT '1' COMMENT '启用状态',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '原因',
  `create_time` datetime NOT NULL,
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB AUTO_INCREMENT=1030276066324598785 DEFAULT CHARSET=utf8 COMMENT='模块订单表---';

/*Table structure for table `module_order_son` */

DROP TABLE IF EXISTS `module_order_son`;

CREATE TABLE `module_order_son` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `model_name` varchar(20) NOT NULL DEFAULT '' COMMENT '模块名称',
  `model_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '模块id',
  `number_` int(11) NOT NULL DEFAULT '1' COMMENT '数量',
  `price_` decimal(14,2) NOT NULL DEFAULT '0.00' COMMENT '单价',
  `rebate_` float NOT NULL DEFAULT '0' COMMENT '折扣',
  `payment_method` varchar(20) NOT NULL DEFAULT '' COMMENT '付款方式',
  `final_price` decimal(14,2) NOT NULL DEFAULT '0.00' COMMENT '最终价格',
  `expiry_time` char(19) NOT NULL DEFAULT '' COMMENT '到期时间   yyyy-MM-dd HH:mm:ss',
  `order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '订单主表id',
  `enable_` tinyint(1) NOT NULL DEFAULT '1' COMMENT '启用状态',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '原因',
  `create_time` datetime NOT NULL,
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_`),
  KEY `idx_modelId` (`model_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1030276066374930433 DEFAULT CHARSET=utf8 COMMENT='模块订单子表';

/*Table structure for table `preferential` */

DROP TABLE IF EXISTS `preferential`;

CREATE TABLE `preferential` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `price_` decimal(14,2) NOT NULL DEFAULT '0.00' COMMENT '金额',
  `preferential_title` varchar(50) DEFAULT '' COMMENT '优惠信息的标题',
  `preferential_content` varchar(100) DEFAULT '' COMMENT '优惠信息内容',
  `model_id` varchar(100) NOT NULL DEFAULT '' COMMENT '模块id 多个用逗号分隔',
  `enable_` tinyint(1) NOT NULL DEFAULT '1' COMMENT '启用状态',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime NOT NULL,
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0',
  `validity_time` varchar(20) NOT NULL DEFAULT '' COMMENT '有效期',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='优惠方案';

/*Table structure for table `process_form` */

DROP TABLE IF EXISTS `process_form`;

CREATE TABLE `process_form` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `form_name` varchar(100) NOT NULL COMMENT '表单名称',
  `form_sign` varchar(20) NOT NULL COMMENT '流程key(表单唯一标识)',
  `form_url` varchar(50) NOT NULL COMMENT '表单绑定的页面URL',
  `form_logo` varchar(100) NOT NULL COMMENT '表单图标',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB AUTO_INCREMENT=1030409704932294657 DEFAULT CHARSET=utf8 COMMENT='流程表单';

/*Table structure for table `process_user` */

DROP TABLE IF EXISTS `process_user`;

CREATE TABLE `process_user` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `proc_def_id` varchar(50) NOT NULL COMMENT '流程定义ID',
  `proc_key` varchar(20) NOT NULL DEFAULT '' COMMENT '流程KEY',
  `task_def_key` varchar(20) NOT NULL COMMENT '流程定义节点',
  `user_id` varchar(300) NOT NULL COMMENT '用户ID',
  `account_` varchar(300) NOT NULL COMMENT '用户账号',
  `assignee_list` varchar(20) NOT NULL COMMENT '人员存储集合名称',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `remark_` varchar(100) NOT NULL,
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='流程节点人员表';

/*Table structure for table `qrtz_blob_triggers` */

DROP TABLE IF EXISTS `qrtz_blob_triggers`;

CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `qrtz_cron_triggers` */

DROP TABLE IF EXISTS `qrtz_cron_triggers`;

CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `qrtz_fired_triggers` */

DROP TABLE IF EXISTS `qrtz_fired_triggers`;

CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `qrtz_job_details` */

DROP TABLE IF EXISTS `qrtz_job_details`;

CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `qrtz_locks` */

DROP TABLE IF EXISTS `qrtz_locks`;

CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `qrtz_paused_trigger_grps` */

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;

CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `qrtz_scheduler_state` */

DROP TABLE IF EXISTS `qrtz_scheduler_state`;

CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `qrtz_simple_triggers` */

DROP TABLE IF EXISTS `qrtz_simple_triggers`;

CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `qrtz_simprop_triggers` */

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;

CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `qrtz_triggers` */

DROP TABLE IF EXISTS `qrtz_triggers`;

CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `rule_base` */

DROP TABLE IF EXISTS `rule_base`;

CREATE TABLE `rule_base` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) NOT NULL DEFAULT '' COMMENT '规则名称',
  `role_number` varchar(200) NOT NULL DEFAULT '' COMMENT '规则编码',
  `sort_` int(4) NOT NULL DEFAULT '1' COMMENT '排序',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `uk_number` (`role_number`)
) ENGINE=InnoDB AUTO_INCREMENT=1031435347037855745 DEFAULT CHARSET=utf8 COMMENT='规则库';

/*Table structure for table `rule_maintenance_item` */

DROP TABLE IF EXISTS `rule_maintenance_item`;

CREATE TABLE `rule_maintenance_item` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `type_` varchar(20) NOT NULL DEFAULT '' COMMENT '类型  REGION（指定区域）；GATEGORY（器件种类）；EDGE（边）；TYPE（类型）；LEVEL（级别）；MODE（方式）；',
  `value_` varchar(20) NOT NULL DEFAULT '' COMMENT '值',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`),
  KEY `idx_type` (`type_`)
) ENGINE=InnoDB AUTO_INCREMENT=1031435347037855745 DEFAULT CHARSET=utf8 COMMENT='规则--维护项';

/*Table structure for table `rule_model` */

DROP TABLE IF EXISTS `rule_model`;

CREATE TABLE `rule_model` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `rule_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '规则id',
  `model_name` varchar(20) NOT NULL DEFAULT '' COMMENT '模型名称',
  `model_number` varchar(200) NOT NULL DEFAULT '' COMMENT '模型编码',
  `version_` varchar(20) NOT NULL DEFAULT '' COMMENT '版本',
  `sort_` int(4) NOT NULL DEFAULT '1' COMMENT '排序',
  `revise_time` datetime NOT NULL COMMENT '更新当前版本数据--记录时间',
  `revise_user` varchar(20) NOT NULL DEFAULT '' COMMENT '更新当前版本数据--记录人员',
  `create_name` varchar(20) NOT NULL DEFAULT '' COMMENT '创建新版本--记录人员',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `uk_number` (`model_number`),
  KEY `idx_r_s` (`rule_id`,`sort_`)
) ENGINE=InnoDB AUTO_INCREMENT=1031435347037855745 DEFAULT CHARSET=utf8 COMMENT='规则--模型';

/*Table structure for table `rule_model_entry` */

DROP TABLE IF EXISTS `rule_model_entry`;

CREATE TABLE `rule_model_entry` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `model_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '模型id',
  `entry_name` varchar(20) NOT NULL DEFAULT '' COMMENT '条目名称',
  `entry_identification` varchar(20) NOT NULL DEFAULT '' COMMENT '条目标识',
  `region_` varchar(20) NOT NULL DEFAULT '' COMMENT '指定区域',
  `gategory_one` varchar(20) NOT NULL DEFAULT '' COMMENT '器件种类1',
  `gategory_two` varchar(20) NOT NULL DEFAULT '' COMMENT '器件种类2',
  `edge_one` varchar(20) NOT NULL DEFAULT '' COMMENT '边1',
  `edge_two` varchar(20) NOT NULL DEFAULT '' COMMENT '边2',
  `type_` varchar(20) NOT NULL DEFAULT '' COMMENT '类型',
  `value_` varchar(20) NOT NULL DEFAULT '' COMMENT '值',
  `level_` varchar(20) NOT NULL DEFAULT '' COMMENT '级别',
  `mode_` varchar(20) NOT NULL DEFAULT '' COMMENT '方式',
  `on_line_value` varchar(20) NOT NULL DEFAULT '' COMMENT '线上检测值',
  `img_` varchar(200) NOT NULL DEFAULT '' COMMENT '图片',
  `technology_files` varchar(200) NOT NULL DEFAULT '' COMMENT '技术文档',
  `sort_` int(4) NOT NULL DEFAULT '1' COMMENT '排序',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '备注--条目描述',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`),
  KEY `idx_model_id` (`model_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1031435347037855745 DEFAULT CHARSET=utf8 COMMENT='规则--模型--条目';

/*Table structure for table `sys_article` */

DROP TABLE IF EXISTS `sys_article`;

CREATE TABLE `sys_article` (
  `id_` bigint(20) NOT NULL,
  `type_` varchar(2) DEFAULT NULL COMMENT '类型',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `author_` varchar(16) DEFAULT NULL COMMENT '作者',
  `title_` varchar(128) DEFAULT NULL COMMENT '标题',
  `content_` longtext COMMENT '内容',
  `out_url` varchar(512) DEFAULT NULL COMMENT '外部链接',
  `seo_keyword` varchar(64) DEFAULT NULL COMMENT 'seo关键字',
  `seo_description` varchar(256) DEFAULT NULL COMMENT 'seo描述',
  `is_top` tinyint(1) DEFAULT NULL COMMENT '是否置顶',
  `enable_` tinyint(1) DEFAULT NULL COMMENT '启用状态',
  `remark_` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章';

/*Table structure for table `sys_dept` */

DROP TABLE IF EXISTS `sys_dept`;

CREATE TABLE `sys_dept` (
  `id_` bigint(20) NOT NULL COMMENT '部门编号',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户id',
  `dept_name` varchar(20) NOT NULL DEFAULT '' COMMENT '部门名称',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '上级部门id',
  `manager_id` bigint(20) NOT NULL DEFAULT '0',
  `leading_official` bigint(20) NOT NULL DEFAULT '0' COMMENT '负责人',
  `sort_no` int(3) NOT NULL DEFAULT '999' COMMENT '排序号',
  `leaf_` tinyint(1) NOT NULL DEFAULT '0' COMMENT '叶子节点(0:树枝节点;1:叶子节点)',
  `icon_` varchar(100) NOT NULL DEFAULT '' COMMENT '图标',
  `enable_` tinyint(1) NOT NULL DEFAULT '1' COMMENT '启用状态',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '备注(部门编号)',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `uk_t_dn` (`tenant_id`,`dept_name`),
  KEY `idx_parentId` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门';

/*Table structure for table `sys_dic` */

DROP TABLE IF EXISTS `sys_dic`;

CREATE TABLE `sys_dic` (
  `id_` bigint(20) NOT NULL,
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户id',
  `type_` varchar(20) NOT NULL DEFAULT '',
  `code_` varchar(20) NOT NULL DEFAULT '',
  `code_text` varchar(20) NOT NULL DEFAULT '',
  `parent_type` varchar(20) NOT NULL DEFAULT '',
  `parent_code` varchar(20) NOT NULL DEFAULT '',
  `sort_no` int(2) NOT NULL DEFAULT '99',
  `editable_` tinyint(1) NOT NULL DEFAULT '1',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `remark_` varchar(100) NOT NULL DEFAULT '',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `type__code_` (`type_`,`code_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典明细表';

/*Table structure for table `sys_discount_program` */

DROP TABLE IF EXISTS `sys_discount_program`;

CREATE TABLE `sys_discount_program` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `model_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '模块id',
  `enable_` tinyint(1) NOT NULL DEFAULT '1' COMMENT '启用状态',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '原因',
  `create_time` datetime NOT NULL,
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用模块的优惠方案';

/*Table structure for table `sys_email` */

DROP TABLE IF EXISTS `sys_email`;

CREATE TABLE `sys_email` (
  `id_` bigint(20) NOT NULL COMMENT '邮件编号',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `email_name` varchar(128) NOT NULL COMMENT '邮件名称',
  `sender_` varchar(32) DEFAULT NULL COMMENT '使用发送',
  `email_title` varchar(256) DEFAULT NULL COMMENT '发送标题',
  `email_content` text COMMENT '发送内容',
  `remark_` varchar(500) DEFAULT NULL,
  `enable_` tinyint(1) DEFAULT '1',
  `create_by` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件表';

/*Table structure for table `sys_email_config` */

DROP TABLE IF EXISTS `sys_email_config`;

CREATE TABLE `sys_email_config` (
  `id_` bigint(20) NOT NULL COMMENT '邮件配置编号',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `smtp_host` varchar(32) NOT NULL COMMENT 'SMTP服务器',
  `smtp_port` varchar(8) NOT NULL COMMENT 'SMTP服务器端口',
  `send_method` varchar(16) NOT NULL COMMENT '发送方式',
  `sender_name` varchar(64) NOT NULL COMMENT '名称',
  `sender_account` varchar(32) NOT NULL COMMENT '发邮件邮箱账号',
  `sender_password` varchar(32) NOT NULL COMMENT '发邮件邮箱密码',
  `remark_` varchar(500) DEFAULT NULL,
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件配置表';

/*Table structure for table `sys_email_template` */

DROP TABLE IF EXISTS `sys_email_template`;

CREATE TABLE `sys_email_template` (
  `id_` bigint(20) NOT NULL COMMENT '邮件模版编号',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `email_name` varchar(128) NOT NULL COMMENT '邮件名称',
  `email_account` varchar(32) DEFAULT NULL COMMENT '发送邮件帐号',
  `sort_` int(5) DEFAULT NULL COMMENT '排序号',
  `title_` varchar(512) DEFAULT NULL COMMENT '标题模版',
  `template_` text COMMENT '内容模板',
  `remark_` varchar(500) DEFAULT NULL,
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件模版表';

/*Table structure for table `sys_event` */

DROP TABLE IF EXISTS `sys_event`;

CREATE TABLE `sys_event` (
  `id_` bigint(20) NOT NULL,
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `title_` varchar(50) DEFAULT NULL,
  `request_uri` varchar(2000) DEFAULT NULL,
  `parameters_` varchar(500) DEFAULT NULL,
  `method_` varchar(20) DEFAULT NULL,
  `client_host` varchar(50) DEFAULT NULL,
  `user_agent` varchar(300) DEFAULT NULL,
  `status_` int(3) DEFAULT NULL,
  `enable_` tinyint(1) DEFAULT NULL,
  `remark_` text,
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id_` bigint(20) NOT NULL COMMENT '菜单编号',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户id',
  `menu_name` varchar(20) NOT NULL DEFAULT '' COMMENT '菜单名称',
  `menu_type` smallint(2) NOT NULL DEFAULT '2' COMMENT '菜单类型(0:CURD;1:系统菜单;2:业务菜单;)',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '上级菜单编号',
  `iconcls_` varchar(50) NOT NULL DEFAULT '' COMMENT '节点图标CSS类名',
  `request_` varchar(100) NOT NULL DEFAULT '' COMMENT '请求地址',
  `expand_` tinyint(1) NOT NULL DEFAULT '0' COMMENT '展开状态(1:展开;0:收缩)',
  `sort_no` int(2) NOT NULL DEFAULT '99' COMMENT '排序号',
  `is_show` tinyint(1) NOT NULL DEFAULT '1' COMMENT '叶子节点(0:树枝节点;1:叶子节点)',
  `permission_` varchar(50) NOT NULL DEFAULT '' COMMENT '权限标识',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单';

/*Table structure for table `sys_model` */

DROP TABLE IF EXISTS `sys_model`;

CREATE TABLE `sys_model` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `model_no` varchar(36) NOT NULL DEFAULT '' COMMENT '模块编号',
  `model_logo` varchar(255) NOT NULL DEFAULT '' COMMENT '模块配图',
  `model_name` varchar(255) NOT NULL DEFAULT '' COMMENT '模块名称',
  `model_url` varchar(2000) NOT NULL DEFAULT '' COMMENT '模块路径',
  `model_desc` varchar(200) NOT NULL DEFAULT '' COMMENT '模块简单描述',
  `desc` varchar(200) NOT NULL DEFAULT '' COMMENT '描述',
  `model_lable` char(10) NOT NULL DEFAULT '' COMMENT '模块标签（##）',
  `model_info` char(10) NOT NULL DEFAULT '' COMMENT '模块信息',
  `remark_` varchar(200) NOT NULL DEFAULT '' COMMENT '模块描述',
  `enabled` char(1) NOT NULL DEFAULT 'Y' COMMENT '是否可用Y=可用，N=不可用',
  `is_basic` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否为基础应用：0:为否 1：是 2为全局',
  `type` varchar(6) NOT NULL DEFAULT 'WEB' COMMENT 'WEB/PAD/APP/PC',
  `pub_time` datetime NOT NULL COMMENT '发布日期',
  `sort` int(11) NOT NULL DEFAULT '99' COMMENT '排序',
  `price` decimal(10,2) NOT NULL DEFAULT '99.00' COMMENT '模块价格',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `model_no` (`model_no`),
  KEY `idx_type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='系统模块表';

/*Table structure for table `sys_model_menu` */

DROP TABLE IF EXISTS `sys_model_menu`;

CREATE TABLE `sys_model_menu` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `model_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '所属模块ID',
  `model_name` varchar(20) NOT NULL DEFAULT '' COMMENT '所属模块名称',
  `menu_name` varchar(20) NOT NULL DEFAULT '' COMMENT '菜单名称',
  `menu_order` int(11) NOT NULL DEFAULT '99' COMMENT '菜单排序',
  `menu_url` varchar(20) NOT NULL DEFAULT '' COMMENT '菜单地址',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父菜单ID',
  `enabled` char(1) NOT NULL DEFAULT 'Y' COMMENT '是否可用Y=可用，N=不可用',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `menu_icon` varchar(20) NOT NULL DEFAULT '' COMMENT '菜单图标',
  `access_str` char(7) NOT NULL DEFAULT '' COMMENT '菜单颜色',
  `permission_` varchar(50) NOT NULL DEFAULT '' COMMENT '权限',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`),
  KEY `idx_model_id` (`model_id`,`menu_order`)
) ENGINE=InnoDB AUTO_INCREMENT=403 DEFAULT CHARSET=utf8 COMMENT='系统模块菜单表';

/*Table structure for table `sys_msg` */

DROP TABLE IF EXISTS `sys_msg`;

CREATE TABLE `sys_msg` (
  `id_` bigint(20) NOT NULL,
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `biz_id` varchar(64) NOT NULL COMMENT '平台编号',
  `type_` varchar(32) NOT NULL COMMENT '类型',
  `phone_` varchar(20) NOT NULL COMMENT '接收短信号码',
  `content_` varchar(256) NOT NULL COMMENT '短信内容',
  `send_state` varchar(1) NOT NULL COMMENT '发送状态',
  `enable_` tinyint(1) DEFAULT NULL COMMENT '启用状态',
  `remark_` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信';

/*Table structure for table `sys_msg_config` */

DROP TABLE IF EXISTS `sys_msg_config`;

CREATE TABLE `sys_msg_config` (
  `id_` bigint(20) NOT NULL,
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `sms_plat_url` varchar(128) DEFAULT NULL COMMENT '短信平台地址',
  `sms_plat_account` varchar(32) DEFAULT NULL COMMENT '短信平台帐号',
  `sms_plat_password` varchar(64) DEFAULT NULL COMMENT '短信平台密码',
  `send_phone` varchar(11) DEFAULT NULL COMMENT '发送短信',
  `sender_name` varchar(32) DEFAULT NULL COMMENT '发送短信签名',
  `order_is_send` tinyint(1) DEFAULT NULL COMMENT '客户下订单时是否给商家发短信',
  `pay_is_send` tinyint(1) DEFAULT NULL COMMENT '客户付款时是否给商家发短信',
  `send_goods_is_send` tinyint(1) DEFAULT NULL COMMENT '商家发货时是否给客户发短信',
  `regist_is_send` tinyint(1) DEFAULT NULL COMMENT '用户注册时是否给客户发短信',
  `advice_goods_is_send` tinyint(1) DEFAULT NULL COMMENT '用户付款后是否给客户发收货验证码',
  `enable_` tinyint(1) DEFAULT NULL COMMENT '启用状态',
  `remark_` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sys_news` */

DROP TABLE IF EXISTS `sys_news`;

CREATE TABLE `sys_news` (
  `id_` bigint(20) NOT NULL COMMENT '新闻编号',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `news_title` varchar(64) NOT NULL COMMENT '新闻标题',
  `news_type` varchar(8) NOT NULL COMMENT '新闻类型',
  `send_time` datetime NOT NULL COMMENT '发布时间',
  `author_` varchar(32) NOT NULL COMMENT '作者',
  `editor_` varchar(32) NOT NULL COMMENT '编辑',
  `tags_` varchar(128) DEFAULT NULL COMMENT 'Tag标签',
  `keys_` varchar(128) DEFAULT NULL COMMENT '关键字',
  `content_` text COMMENT '内容',
  `reader_times` int(11) NOT NULL DEFAULT '0' COMMENT '阅读次数',
  `status_` varchar(2) NOT NULL DEFAULT '1' COMMENT '发布状态',
  `remark_` varchar(500) DEFAULT NULL,
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='新闻表';

/*Table structure for table `sys_notice` */

DROP TABLE IF EXISTS `sys_notice`;

CREATE TABLE `sys_notice` (
  `id_` bigint(20) NOT NULL COMMENT '公告编号',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `notice_title` varchar(128) NOT NULL COMMENT '公告标题',
  `notice_type` varchar(8) NOT NULL COMMENT '公告类型',
  `send_time` datetime DEFAULT NULL COMMENT '发布时间',
  `info_sources` varchar(256) DEFAULT NULL COMMENT '信息来源',
  `sources_url` varchar(2048) DEFAULT NULL COMMENT '来源地址',
  `content_` text COMMENT '内容',
  `reader_times` int(11) NOT NULL DEFAULT '0' COMMENT '阅读次数',
  `status_` varchar(2) NOT NULL DEFAULT '1' COMMENT '发布状态',
  `remark_` varchar(500) DEFAULT NULL,
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `create_by` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通知公告表';

/*Table structure for table `sys_param` */

DROP TABLE IF EXISTS `sys_param`;

CREATE TABLE `sys_param` (
  `id_` bigint(20) NOT NULL COMMENT '参数编号',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `param_key` varchar(50) DEFAULT NULL COMMENT '参数键名',
  `param_value` varchar(100) DEFAULT NULL COMMENT '参数键值',
  `catalog_id` bigint(20) DEFAULT NULL,
  `remark_` varchar(100) DEFAULT NULL COMMENT '备注',
  `enable_` tinyint(1) DEFAULT '1',
  `create_by` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='全局参数表';

/*Table structure for table `sys_position` */

DROP TABLE IF EXISTS `sys_position`;

CREATE TABLE `sys_position` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` bigint(20) NOT NULL DEFAULT '0',
  `position_title` varchar(20) NOT NULL DEFAULT '' COMMENT '职位名称',
  `position_no` varchar(20) NOT NULL DEFAULT '' COMMENT '职位编号',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父级id',
  `dept_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '部门id',
  `enable_` tinyint(1) NOT NULL DEFAULT '1' COMMENT '启用状态',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime NOT NULL,
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='职位表';

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户id',
  `role_name` varchar(20) NOT NULL DEFAULT '' COMMENT '角色名称',
  `dept_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '所属部门id',
  `sort_no` int(5) NOT NULL DEFAULT '99',
  `role_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '角色类型(1:业务角色;2:管理角色 ;3:系统内置角色)',
  `model_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '模块id',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  `sort` int(1) NOT NULL DEFAULT '99',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `uk_t_rn` (`tenant_id`,`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1029574062488989697 DEFAULT CHARSET=utf8 COMMENT='角色信息表';

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户id',
  `role_id` bigint(20) NOT NULL DEFAULT '0',
  `menu_id` bigint(20) NOT NULL DEFAULT '0',
  `permission_` varchar(20) NOT NULL DEFAULT '' COMMENT '权限标识',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `remark_` varchar(100) NOT NULL DEFAULT '',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `uk_r_m_p` (`role_id`,`menu_id`,`permission_`)
) ENGINE=InnoDB AUTO_INCREMENT=1021584152112827493 DEFAULT CHARSET=utf8 COMMENT='角色授权表';

/*Table structure for table `sys_session` */

DROP TABLE IF EXISTS `sys_session`;

CREATE TABLE `sys_session` (
  `id_` bigint(20) NOT NULL,
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户id',
  `session_id` char(37) NOT NULL DEFAULT '',
  `account_` bigint(20) NOT NULL DEFAULT '0',
  `ip_` varchar(50) NOT NULL DEFAULT '',
  `start_time` datetime NOT NULL,
  `type_` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1 JSESSIONID/2 TOKEN/3 C-TOKEN',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `remark_` varchar(100) NOT NULL DEFAULT '',
  `create_time` datetime NOT NULL,
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_`),
  KEY `idx_a_t` (`account_`,`type_`),
  KEY `idx_session_id` (`session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会话管理';

/*Table structure for table `sys_tenant_model` */

DROP TABLE IF EXISTS `sys_tenant_model`;

CREATE TABLE `sys_tenant_model` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `unit_name` varchar(20) NOT NULL DEFAULT '' COMMENT '租户名称',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户id',
  `model_name` varchar(20) NOT NULL DEFAULT '' COMMENT '模块名称',
  `model_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '模块id',
  `model_validate` datetime NOT NULL COMMENT '应用有效期',
  `buy` tinyint(1) NOT NULL DEFAULT '0' COMMENT '1已购买',
  `payment_` varchar(20) NOT NULL DEFAULT '' COMMENT '支付方式',
  `enable_` tinyint(1) NOT NULL DEFAULT '1' COMMENT '启用状态',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime NOT NULL,
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_name` varchar(20) NOT NULL DEFAULT '' COMMENT '创建人',
  `update_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `uk_t_m` (`tenant_id`,`model_id`),
  KEY `idx_m_t_e` (`model_id`,`model_validate`,`tenant_id`),
  KEY `idx_t_m_e` (`tenant_id`,`model_validate`)
) ENGINE=InnoDB AUTO_INCREMENT=1034362276475117570 DEFAULT CHARSET=utf8 COMMENT='租户模块表';

/*Table structure for table `sys_unit` */

DROP TABLE IF EXISTS `sys_unit`;

CREATE TABLE `sys_unit` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `unit_code` char(16) NOT NULL DEFAULT '' COMMENT '单位编号',
  `unit_name` varchar(20) NOT NULL DEFAULT '' COMMENT '单位名称',
  `principal_` varchar(20) NOT NULL DEFAULT '' COMMENT '负责人',
  `phone_` varchar(11) NOT NULL DEFAULT '' COMMENT '联系电话',
  `address_` varchar(200) NOT NULL DEFAULT '' COMMENT '地址',
  `business` varchar(200) NOT NULL DEFAULT '' COMMENT '行业',
  `home_page` varchar(100) NOT NULL DEFAULT '' COMMENT '公司主页',
  `turnover` decimal(14,2) NOT NULL DEFAULT '0.00' COMMENT '年营业额',
  `staff_num` varchar(50) NOT NULL DEFAULT '' COMMENT '员工数量',
  `postal_code` varchar(20) NOT NULL DEFAULT '' COMMENT '邮政编码',
  `company_introduction` text COMMENT '公司介绍',
  `sort_` int(5) NOT NULL DEFAULT '0' COMMENT '排序号',
  `apply_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '临时租户申请表id',
  `enable_` tinyint(1) NOT NULL DEFAULT '1' COMMENT '启用状态',
  `remark_` varchar(100) NOT NULL DEFAULT '',
  `create_time` datetime NOT NULL,
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `uk_unitCode` (`unit_code`),
  KEY `idx_phone` (`phone_`),
  KEY `idx_applyId` (`apply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1024919287474454529 DEFAULT CHARSET=utf8 COMMENT='单位表';

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户id',
  `account_` varchar(20) NOT NULL DEFAULT '' COMMENT '登陆帐户',
  `password_` varchar(100) NOT NULL DEFAULT '' COMMENT '密码',
  `user_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '用户类型(1普通用户2管理员3系统用户)',
  `user_name` varchar(20) NOT NULL DEFAULT '' COMMENT '姓名',
  `name_pinyin` varchar(50) NOT NULL DEFAULT '' COMMENT '姓名拼音',
  `sex_` tinyint(1) NOT NULL DEFAULT '0' COMMENT '性别(0:未知;1:男;2:女)',
  `avatar_` varchar(200) NOT NULL DEFAULT '' COMMENT '头像',
  `phone_` varchar(11) NOT NULL DEFAULT '' COMMENT '电话',
  `email_` varchar(64) NOT NULL DEFAULT '' COMMENT '邮箱',
  `id_card` varchar(18) NOT NULL DEFAULT '' COMMENT '身份证号码',
  `wei_xin` varchar(32) NOT NULL DEFAULT '' COMMENT '微信',
  `wei_bo` varchar(32) NOT NULL DEFAULT '' COMMENT '微博',
  `qq_` varchar(32) NOT NULL DEFAULT '' COMMENT 'QQ',
  `birth_day` date DEFAULT NULL COMMENT '出生日期',
  `dept_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '部门编号',
  `other_dept_id` bigint(20) NOT NULL DEFAULT '0',
  `position_id` bigint(20) NOT NULL DEFAULT '0',
  `position_` varchar(20) NOT NULL DEFAULT '' COMMENT '职位',
  `user_superior` bigint(20) NOT NULL DEFAULT '0',
  `address_` varchar(256) NOT NULL DEFAULT '' COMMENT '详细地址',
  `staff_no` varchar(32) NOT NULL DEFAULT '' COMMENT '工号',
  `entry_time` datetime DEFAULT NULL COMMENT '入职日期',
  `login_` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1 允许登陆，2 禁止登陆',
  `enable_` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1 正常，2 停用',
  `remark_` varchar(100) NOT NULL DEFAULT '',
  `create_time` datetime NOT NULL,
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `account_tenant` (`account_`,`tenant_id`),
  KEY `idx_dept` (`dept_id`,`enable_`)
) ENGINE=InnoDB AUTO_INCREMENT=1029679860997246977 DEFAULT CHARSET=utf8 COMMENT='用户管理';

/*Table structure for table `sys_user_menu` */

DROP TABLE IF EXISTS `sys_user_menu`;

CREATE TABLE `sys_user_menu` (
  `id_` bigint(20) NOT NULL,
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户id',
  `user_id` bigint(20) NOT NULL DEFAULT '0',
  `menu_id` bigint(20) NOT NULL DEFAULT '0',
  `permission_` varchar(50) NOT NULL DEFAULT '' COMMENT '权限标识',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `remark_` varchar(100) NOT NULL DEFAULT '',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `uk_u_m_p` (`user_id`,`menu_id`,`permission_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户授权表';

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户id',
  `user_id` bigint(20) NOT NULL DEFAULT '0',
  `role_id` bigint(20) NOT NULL DEFAULT '0',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `remark_` varchar(100) NOT NULL DEFAULT '',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `uk_r_u` (`role_id`,`user_id`),
  KEY `idx_u_r` (`user_id`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1021995644465410087 DEFAULT CHARSET=utf8 COMMENT='用户授权表';

/*Table structure for table `sys_user_thirdparty` */

DROP TABLE IF EXISTS `sys_user_thirdparty`;

CREATE TABLE `sys_user_thirdparty` (
  `id_` bigint(20) NOT NULL,
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户id',
  `user_id` bigint(20) NOT NULL DEFAULT '0',
  `provider_` varchar(50) NOT NULL DEFAULT '' COMMENT '第三方类型',
  `open_id` varchar(50) NOT NULL DEFAULT '' COMMENT '第三方Id',
  `enable_` tinyint(1) NOT NULL DEFAULT '1',
  `remark_` varchar(100) NOT NULL DEFAULT '',
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `uk_t_e_u_p_o` (`tenant_id`,`enable_`,`user_id`,`provider_`,`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='第三方用户';

/*Table structure for table `task_fire_log` */

DROP TABLE IF EXISTS `task_fire_log`;

CREATE TABLE `task_fire_log` (
  `id_` bigint(20) NOT NULL,
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `group_name` varchar(50) NOT NULL,
  `task_name` varchar(50) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL,
  `status_` varchar(1) NOT NULL DEFAULT 'I',
  `server_host` varchar(50) DEFAULT NULL COMMENT '服务器名',
  `server_duid` varchar(50) DEFAULT NULL COMMENT '服务器网卡序列号',
  `fire_info` text,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `group_name_task_name_start_time` (`group_name`,`task_name`,`start_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `tenant_register` */

DROP TABLE IF EXISTS `tenant_register`;

CREATE TABLE `tenant_register` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mobile` char(50) NOT NULL COMMENT '手机号',
  `unit_name` varchar(255) NOT NULL COMMENT '企业名称',
  `real_name` varchar(32) NOT NULL COMMENT '真实姓名',
  `email` varchar(128) NOT NULL COMMENT '电子邮箱',
  `password` char(32) NOT NULL COMMENT '密码',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `audited` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否审核1=是，0=否',
  `audited_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audited_uid` bigint(20) DEFAULT NULL COMMENT '审核用户ID',
  PRIMARY KEY (`id`),
  KEY `mobile` (`mobile`),
  KEY `audited` (`audited`),
  KEY `audited_uid` (`audited_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='租户注册信息表';

/*Table structure for table `unit_register_apply` */

DROP TABLE IF EXISTS `unit_register_apply`;

CREATE TABLE `unit_register_apply` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `unit_name` varchar(20) NOT NULL DEFAULT '' COMMENT '单位名称',
  `principal_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '负责人id',
  `principal_` varchar(20) NOT NULL DEFAULT '' COMMENT '负责人',
  `phone_` varchar(11) NOT NULL DEFAULT '' COMMENT '联系电话',
  `user_name` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名称',
  `email_` varchar(50) NOT NULL DEFAULT '' COMMENT '电子邮箱',
  `password_` varchar(100) NOT NULL DEFAULT '' COMMENT '密码',
  `address_` varchar(200) NOT NULL DEFAULT '' COMMENT '地址',
  `status_` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 初始态，刚刚申请 ; 1 申请通过，插入到sys_user； 2 申请打回',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `enable_` tinyint(1) NOT NULL DEFAULT '1' COMMENT '启用状态',
  `remark_` varchar(100) NOT NULL DEFAULT '' COMMENT '原因',
  `create_time` datetime NOT NULL,
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  `update_by` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB AUTO_INCREMENT=1024858206454448129 DEFAULT CHARSET=utf8 COMMENT='单位--注册申请表';

/* Procedure structure for procedure `clean_event` */

/*!50003 DROP PROCEDURE IF EXISTS  `clean_event` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `clean_event`(in mt int)
BEGIN
    DELETE FROM sys_event WHERE create_time < DATE_ADD(NOW(), INTERVAL -mt MONTH);
 END */$$
DELIMITER ;

/* Procedure structure for procedure `f_dim_day` */

/*!50003 DROP PROCEDURE IF EXISTS  `f_dim_day` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `f_dim_day`(IN start_date VARCHAR(20),IN date_count INT)
BEGIN
DECLARE i INT DEFAULT 0;
    DELETE FROM dim_day;
    WHILE i < date_count DO  
        INSERT INTO dim_day (DAY_SHORT_DESC,DAY_LONG_DESC,WEEK_DESC,WEEK_ID,WEEK_LONG_DESC,MONTH_ID,MONTH_LONG_DESC,QUARTER_ID,QUARTER_LONG_DESC,YEAR_ID,YEAR_LONG_DESC)  
            SELECT  
                DATE_FORMAT(STR_TO_DATE(start_date,'%Y-%m-%d %H:%i:%s'),'%Y-%m-%d') DAY_SHORT_DESC,  
                DATE_FORMAT(STR_TO_DATE(start_date,'%Y-%m-%d %H:%i:%s'),'%Y年%m月%d日') DAY_LONG_DESC,  
                CASE DAYOFWEEK(STR_TO_DATE(start_date,'%Y-%m-%d %H:%i:%s'))  WHEN 1 THEN '星期日' WHEN 2 THEN '星期一' WHEN 3 THEN '星期二' WHEN 4 THEN '星期三' WHEN 5 THEN '星期四' WHEN 6 THEN '星期五' WHEN 7 THEN '星期六' END WEEK_DESC,  
                DATE_FORMAT(STR_TO_DATE(start_date,'%Y-%m-%d %H:%i:%s'),'%Y%u') WEEK_ID,  
                DATE_FORMAT(STR_TO_DATE(start_date,'%Y-%m-%d %H:%i:%s'),'%Y年第%u周') WEEK_LONG_DESC,  
                DATE_FORMAT(STR_TO_DATE(start_date,'%Y-%m-%d %H:%i:%s'),'%Y%m') MONTH_ID,  
                DATE_FORMAT(STR_TO_DATE(start_date,'%Y-%m-%d %H:%i:%s'),'%Y年第%m月') MONTH_LONG_DESC,  
                CONCAT(DATE_FORMAT(STR_TO_DATE(start_date,'%Y-%m-%d %H:%i:%s'),'%Y'),QUARTER(STR_TO_DATE( start_date,'%Y-%m-%d %H:%i:%s'))) QUARTER_ID,  
                CONCAT(DATE_FORMAT(STR_TO_DATE(start_date,'%Y-%m-%d %H:%i:%s'),'%Y'),'年第',QUARTER(STR_TO_DATE(start_date,'%Y-%m-%d %H:%i:%s')),'季度') QUARTER_LONG_DESC,  
                DATE_FORMAT(STR_TO_DATE(start_date,'%Y-%m-%d %H:%i:%s'),'%Y') YEAR_ID,  
                DATE_FORMAT(STR_TO_DATE(start_date,'%Y-%m-%d %H:%i:%s'),'%Y年') YEAR_LONG_DESC  
        FROM DUAL;  
        SET i=i+1;  
        SET start_date = DATE_FORMAT(DATE_ADD(STR_TO_DATE(start_date,'%Y-%m-%d %H:%i:%s'),INTERVAL 1 DAY),'%Y-%m-%d');
    END WHILE;
END */$$
DELIMITER ;

/* Procedure structure for procedure `myproc` */

/*!50003 DROP PROCEDURE IF EXISTS  `myproc` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `myproc`()
BEGIN
      DECLARE Done INT DEFAULT 0;
      DECLARE v1 BIGINT(20);
      DECLARE rs CURSOR FOR select id_ from tblbarcodeprocess where diameter is null and ProcessId = 'DD' and StartDate > '2018-01-15 00:00:00';
 
      DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET Done = 1;
      OPEN rs;  
      FETCH NEXT FROM rs INTO  v1; 
      REPEAT
	      IF NOT Done THEN	
	         UPDATE tblbarcodeprocess SET tblbarcodeprocess.diameter = FORMAT((round(rand() *((1.295*1000+20)-(1.295*1000-10))+(1.295*1000-10)))/1000,3),tblbarcodeprocess.ovality = FORMAT((ROUND(RAND()*(7-3)+3))/1000,3) WHERE id_ = v1;
	      END IF;
	      FETCH NEXT FROM rs INTO  v1;
      UNTIL Done END REPEAT;
      CLOSE rs;
 END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
