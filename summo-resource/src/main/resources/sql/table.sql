/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.21 : Database - vueadmin
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`vueadmin` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `vueadmin`;

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` bigint DEFAULT NULL COMMENT '上级菜单ID',
  `title` varchar(255) DEFAULT NULL COMMENT '菜单标题',
  `name` varchar(255) DEFAULT NULL COMMENT '组件名称',
  `component` varchar(255) DEFAULT NULL COMMENT '组件',
  `menu_sort` int DEFAULT NULL COMMENT '排序',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `path` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `redirect` varchar(255) DEFAULT NULL COMMENT '重定向地址',
  `i_frame` tinyint(1) DEFAULT '0' COMMENT '是否外链',
  `cache` tinyint(1) DEFAULT '0' COMMENT '缓存',
  `hidden` tinyint(1) DEFAULT '0' COMMENT '隐藏',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`menu_id`) USING BTREE,
  UNIQUE KEY `uniq_title` (`title`),
  UNIQUE KEY `uniq_name` (`name`),
  KEY `inx_pid` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统菜单';

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`menu_id`,`pid`,`title`,`name`,`component`,`menu_sort`,`icon`,`path`,`redirect`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,0,'系统管理','System','Layout',1,'el-icon-s-tools','/system','User',0,0,0,NULL,NULL,NULL,'2018-12-18 15:11:29','2021-11-20 14:56:36'),(2,1,'用户管理','User','User',0,'el-icon-user-solid','user','UserList',0,0,0,NULL,NULL,NULL,'2018-12-18 15:14:44','2021-11-20 14:56:46'),(3,1,'角色管理','Role','Role',1,'el-icon-s-management','role','RoleList',0,0,0,NULL,NULL,NULL,'2018-12-18 15:16:07','2021-11-20 16:16:33'),(4,1,'菜单管理','Menu','Menu',2,'el-icon-menu','menu','MenuList',0,0,0,NULL,NULL,NULL,'2018-12-18 15:17:28','2021-11-20 14:58:01'),(5,2,'用户列表','UserList',NULL,0,'tre','list',NULL,0,0,1,'user:list',NULL,NULL,'2019-10-29 12:56:00','2021-11-20 16:52:45'),(6,2,'用户新增','UserAdd',NULL,1,'tree','add',NULL,0,0,1,'user:add',NULL,NULL,'2019-10-29 10:59:46',NULL),(7,2,'用户编辑','UserEdit',NULL,2,'tree','edit',NULL,0,0,1,'user:edit',NULL,NULL,'2019-10-29 11:00:08',NULL),(8,2,'用户删除','UserDelete',NULL,3,NULL,'delete',NULL,0,0,1,'user:delete',NULL,NULL,'2019-10-29 11:00:23',NULL),(9,3,'角色列表','RoleList',NULL,1,'el-icon-s-order','list',NULL,0,0,1,'role:list',NULL,NULL,'2019-10-29 12:56:00','2021-11-20 16:11:23'),(10,3,'角色创建','RoleAdd',NULL,2,'tree','add',NULL,0,0,1,'role:add',NULL,NULL,'2019-10-29 12:45:34',NULL),(11,3,'角色修改','RoleEdit',NULL,3,'tree','edit',NULL,0,0,1,'role:edit',NULL,NULL,'2019-10-29 12:46:16',NULL),(12,3,'角色删除','RoleDelete',NULL,4,'tree','delete',NULL,0,0,1,'role:delete','','',NULL,'2021-11-20 16:07:15'),(13,4,'菜单列表','MenuList',NULL,1,'tree','list',NULL,0,0,1,'menu:list',NULL,NULL,'2019-10-29 12:56:00',NULL),(14,4,'菜单新增','MenuAdd',NULL,2,'tree','add',NULL,0,0,1,'menu:add',NULL,NULL,'2019-10-29 12:55:07',NULL),(15,4,'菜单编辑','MenuEdit',NULL,3,'tree','edit',NULL,0,0,1,'menu:edit',NULL,NULL,'2019-10-29 12:55:40',NULL),(16,4,'菜单删除','MenuDelete',NULL,4,'tree','delete',NULL,0,0,1,'menu:delete',NULL,NULL,'2019-10-29 12:56:00',NULL);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE KEY `uniq_name` (`name`),
  KEY `role_name_index` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色表';

/*Data for the table `sys_role` */

insert  into `sys_role`(`role_id`,`name`,`description`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,'超级管理员','超级管理员','admin','admin','2018-11-23 11:04:37','2020-08-06 16:10:24'),(2,'普通用户','这是一个 超长长长长长长长长长长长长长长长长长长长长长长长长长长的描述 ','admin','admin','2018-11-23 13:09:06','2020-09-05 10:45:12');

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `gender` varchar(2) DEFAULT NULL COMMENT '性别',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '头像地址',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `roles` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色',
  `enabled` tinyint NOT NULL DEFAULT '0' COMMENT '状态：1启用、0禁用',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `pwd_reset_time` datetime DEFAULT NULL COMMENT '修改密码的时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `uniq_username` (`username`),
  UNIQUE KEY `UK_kpubos9gc2cvtkb0thktkbkes` (`email`) USING BTREE,
  UNIQUE KEY `uniq_email` (`email`),
  KEY `FKpq2dhypk2qgt68nauh2by22jb` (`avatar`) USING BTREE,
  KEY `inx_enabled` (`enabled`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统用户';

/*Data for the table `sys_user` */

insert  into `sys_user`(`user_id`,`username`,`nick_name`,`gender`,`phone`,`email`,`avatar`,`password`,`roles`,`enabled`,`create_by`,`update_by`,`pwd_reset_time`,`create_time`,`update_time`) values (1,'admin','管理员','男','18888888888','201507802@qq.com','none','$2a$10$cBDBo89DbfbI9KKCOEYhV.7MZnHLK4YQ/4r1.psRDNdVY5SCN6zWu','1',1,NULL,'admin','2020-05-03 16:38:31','2018-08-23 09:11:56','2021-11-20 10:14:32'),(2,'test','测试','男','19999999999','231@qq.com',NULL,'$2a$10$cBDBo89DbfbI9KKCOEYhV.7MZnHLK4YQ/4r1.psRDNdVY5SCN6zWu','0',1,'admin','admin',NULL,'2020-05-05 11:15:49','2021-11-13 10:58:27');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
