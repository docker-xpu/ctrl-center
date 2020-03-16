/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : mydocker

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 16/03/2020 09:18:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for host_entity
-- ----------------------------
DROP TABLE IF EXISTS `host_entity`;
CREATE TABLE `host_entity`  (
  `host_ip` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `host_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '',
  `host_cpu_number` int(11) DEFAULT NULL,
  `host_os` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `host_status` tinyint(11) DEFAULT NULL,
  PRIMARY KEY (`host_ip`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of host_entity
-- ----------------------------
INSERT INTO `host_entity` VALUES ('139.159.234.67', 'IP为139.159.234.67的主机', 1, 'linux', 1);
INSERT INTO `host_entity` VALUES ('139.159.254.242', 'IP为139.159.254.242的主机', 1, 'linux', 1);
INSERT INTO `host_entity` VALUES ('192.168.0.102', 'IP为192.168.0.102的主机', 4, 'linux', 1);
INSERT INTO `host_entity` VALUES ('192.168.0.105', 'IP为192.168.0.105的主机', 2, 'linux', 0);
INSERT INTO `host_entity` VALUES ('192.168.0.106', 'IP为192.168.0.106的主机', 2, 'linux', 0);
INSERT INTO `host_entity` VALUES ('192.168.0.107', 'IP为192.168.0.107的主机', 1, 'linux', 0);
INSERT INTO `host_entity` VALUES ('192.168.0.108', 'IP为192.168.0.108的主机', 4, 'linux', 0);
INSERT INTO `host_entity` VALUES ('192.168.0.109', 'IP为192.168.0.109的主机', 4, 'linux', 1);
INSERT INTO `host_entity` VALUES ('192.168.0.110', 'IP为192.168.0.110的主机', 4, 'linux', 0);
INSERT INTO `host_entity` VALUES ('192.168.2.2', 'IP为192.168.2.2的主机', 4, 'linux', 0);

-- ----------------------------
-- Table structure for host_entity_hard_ware
-- ----------------------------
DROP TABLE IF EXISTS `host_entity_hard_ware`;
CREATE TABLE `host_entity_hard_ware`  (
  `update_time` bigint(20) NOT NULL,
  `ip` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `cpu_info` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `disk_info` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `host_info` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `io_counters` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `load_info` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `mem_info` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `process` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `proto_counters` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `sensors` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`update_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for host_license
-- ----------------------------
DROP TABLE IF EXISTS `host_license`;
CREATE TABLE `host_license`  (
  `license_id` int(11) NOT NULL AUTO_INCREMENT,
  `license_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `license_passwd` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`license_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of host_license
-- ----------------------------
INSERT INTO `host_license` VALUES (1, '华为主机1号', '123456');
INSERT INTO `host_license` VALUES (9, '刘景亮', '1Huaweiyun$..');
INSERT INTO `host_license` VALUES (11, '刘景亮', '1Huaweiyun$..');
INSERT INTO `host_license` VALUES (12, '刘景亮', '1Huaweiyun$..');
INSERT INTO `host_license` VALUES (16, '测试', '123');
INSERT INTO `host_license` VALUES (18, 'sss', 'ssss');
INSERT INTO `host_license` VALUES (19, 'dl', 'ndl.04551');
INSERT INTO `host_license` VALUES (27, 'AAA', 'lhl123456an+');
INSERT INTO `host_license` VALUES (28, 'AA', 'lhl123456an+');

SET FOREIGN_KEY_CHECKS = 1;
