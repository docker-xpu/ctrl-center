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

 Date: 02/04/2020 22:35:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cluster_info
-- ----------------------------
DROP TABLE IF EXISTS `cluster_info`;
CREATE TABLE `cluster_info`  (
  `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pod_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `node_number` int(11) NOT NULL DEFAULT 3,
  `node_port` int(11) DEFAULT NULL,
  `nginx_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` bigint(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cluster_info
-- ----------------------------
INSERT INTO `cluster_info` VALUES ('299137', 'blog-1', 10, 8091, 'blog-1.master.192.168.2.2', 1585709537533, 0);

-- ----------------------------
-- Table structure for container_create
-- ----------------------------
DROP TABLE IF EXISTS `container_create`;
CREATE TABLE `container_create`  (
  `container_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_from` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `host_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `cluster_id` int(11) DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of container_create
-- ----------------------------
INSERT INTO `container_create` VALUES ('192.168.2.2mynginx', '{\"createForm\":{\"cmd\":[\"nginx\",\"-g\",\"daemon off;\"],\"container_name\":\"mynginx\",\"container_port\":\"80\",\"container_port_proto\":\"tcp\",\"cpu_shares\":1024,\"host_port\":\"8025\",\"image_name\":\"mynginx:0.1\",\"memory\":40000000,\"volumes\":[{\"container_volume\":\"/root\",\"host_volume\":\"/root\"}],\"working_dir\":\"/root\"},\"ip\":\"192.168.2.2\"}', '192.168.2.2', NULL);
INSERT INTO `container_create` VALUES ('192.168.2.6blog-1.0.192.168.2.6', '{\"createForm\":{\"cmd\":[\"nginx\",\"-g\",\"daemon off;\"],\"container_name\":\"blog-1.0.192.168.2.6\",\"container_port\":\"80\",\"container_port_proto\":\"tcp\",\"cpu_shares\":1024,\"host_port\":\"10001\",\"image_name\":\"mynginx:0.1\",\"memory\":40000000,\"volumes\":[{\"container_volume\":\"/root\",\"host_volume\":\"/root\"}],\"working_dir\":\"/root\"},\"ip\":\"192.168.2.6\"}', '192.168.2.6', 299137);
INSERT INTO `container_create` VALUES ('192.168.2.4blog-1.0.192.168.2.4', '{\"createForm\":{\"cmd\":[\"nginx\",\"-g\",\"daemon off;\"],\"container_name\":\"blog-1.0.192.168.2.4\",\"container_port\":\"80\",\"container_port_proto\":\"tcp\",\"cpu_shares\":1024,\"host_port\":\"10000\",\"image_name\":\"mynginx:0.1\",\"memory\":40000000,\"volumes\":[{\"container_volume\":\"/root\",\"host_volume\":\"/root\"}],\"working_dir\":\"/root\"},\"ip\":\"192.168.2.4\"}', '192.168.2.4', 299137);
INSERT INTO `container_create` VALUES ('192.168.2.6blog-1.1.192.168.2.6', '{\"createForm\":{\"cmd\":[\"nginx\",\"-g\",\"daemon off;\"],\"container_name\":\"blog-1.1.192.168.2.6\",\"container_port\":\"80\",\"container_port_proto\":\"tcp\",\"cpu_shares\":1024,\"host_port\":\"10002\",\"image_name\":\"mynginx:0.1\",\"memory\":40000000,\"volumes\":[{\"container_volume\":\"/root\",\"host_volume\":\"/root\"}],\"working_dir\":\"/root\"},\"ip\":\"192.168.2.6\"}', '192.168.2.6', 299137);
INSERT INTO `container_create` VALUES ('192.168.2.4blog-1.1.192.168.2.4', '{\"createForm\":{\"cmd\":[\"nginx\",\"-g\",\"daemon off;\"],\"container_name\":\"blog-1.1.192.168.2.4\",\"container_port\":\"80\",\"container_port_proto\":\"tcp\",\"cpu_shares\":1024,\"host_port\":\"10001\",\"image_name\":\"mynginx:0.1\",\"memory\":40000000,\"volumes\":[{\"container_volume\":\"/root\",\"host_volume\":\"/root\"}],\"working_dir\":\"/root\"},\"ip\":\"192.168.2.4\"}', '192.168.2.4', 299137);
INSERT INTO `container_create` VALUES ('192.168.2.6blog-1.2.192.168.2.6', '{\"createForm\":{\"cmd\":[\"nginx\",\"-g\",\"daemon off;\"],\"container_name\":\"blog-1.2.192.168.2.6\",\"container_port\":\"80\",\"container_port_proto\":\"tcp\",\"cpu_shares\":1024,\"host_port\":\"10003\",\"image_name\":\"mynginx:0.1\",\"memory\":40000000,\"volumes\":[{\"container_volume\":\"/root\",\"host_volume\":\"/root\"}],\"working_dir\":\"/root\"},\"ip\":\"192.168.2.6\"}', '192.168.2.6', 299137);
INSERT INTO `container_create` VALUES ('192.168.2.4blog-1.2.192.168.2.4', '{\"createForm\":{\"cmd\":[\"nginx\",\"-g\",\"daemon off;\"],\"container_name\":\"blog-1.2.192.168.2.4\",\"container_port\":\"80\",\"container_port_proto\":\"tcp\",\"cpu_shares\":1024,\"host_port\":\"10002\",\"image_name\":\"mynginx:0.1\",\"memory\":40000000,\"volumes\":[{\"container_volume\":\"/root\",\"host_volume\":\"/root\"}],\"working_dir\":\"/root\"},\"ip\":\"192.168.2.4\"}', '192.168.2.4', 299137);
INSERT INTO `container_create` VALUES ('192.168.2.6blog-1.3.192.168.2.6', '{\"createForm\":{\"cmd\":[\"nginx\",\"-g\",\"daemon off;\"],\"container_name\":\"blog-1.3.192.168.2.6\",\"container_port\":\"80\",\"container_port_proto\":\"tcp\",\"cpu_shares\":1024,\"host_port\":\"10004\",\"image_name\":\"mynginx:0.1\",\"memory\":40000000,\"volumes\":[{\"container_volume\":\"/root\",\"host_volume\":\"/root\"}],\"working_dir\":\"/root\"},\"ip\":\"192.168.2.6\"}', '192.168.2.6', 299137);
INSERT INTO `container_create` VALUES ('192.168.2.4blog-1.3.192.168.2.4', '{\"createForm\":{\"cmd\":[\"nginx\",\"-g\",\"daemon off;\"],\"container_name\":\"blog-1.3.192.168.2.4\",\"container_port\":\"80\",\"container_port_proto\":\"tcp\",\"cpu_shares\":1024,\"host_port\":\"10003\",\"image_name\":\"mynginx:0.1\",\"memory\":40000000,\"volumes\":[{\"container_volume\":\"/root\",\"host_volume\":\"/root\"}],\"working_dir\":\"/root\"},\"ip\":\"192.168.2.4\"}', '192.168.2.4', 299137);
INSERT INTO `container_create` VALUES ('192.168.2.6blog-1.4.192.168.2.6', '{\"createForm\":{\"cmd\":[\"nginx\",\"-g\",\"daemon off;\"],\"container_name\":\"blog-1.4.192.168.2.6\",\"container_port\":\"80\",\"container_port_proto\":\"tcp\",\"cpu_shares\":1024,\"host_port\":\"10005\",\"image_name\":\"mynginx:0.1\",\"memory\":40000000,\"volumes\":[{\"container_volume\":\"/root\",\"host_volume\":\"/root\"}],\"working_dir\":\"/root\"},\"ip\":\"192.168.2.6\"}', '192.168.2.6', 299137);
INSERT INTO `container_create` VALUES ('192.168.2.4blog-1.4.192.168.2.4', '{\"createForm\":{\"cmd\":[\"nginx\",\"-g\",\"daemon off;\"],\"container_name\":\"blog-1.4.192.168.2.4\",\"container_port\":\"80\",\"container_port_proto\":\"tcp\",\"cpu_shares\":1024,\"host_port\":\"10004\",\"image_name\":\"mynginx:0.1\",\"memory\":40000000,\"volumes\":[{\"container_volume\":\"/root\",\"host_volume\":\"/root\"}],\"working_dir\":\"/root\"},\"ip\":\"192.168.2.4\"}', '192.168.2.4', 299137);
INSERT INTO `container_create` VALUES ('192.168.2.2blog-1.master.192.168.2.2', '{\"createForm\":{\"cmd\":[\"nginx\",\"-g\",\"daemon off;\"],\"container_name\":\"blog-1.master.192.168.2.2\",\"container_port\":\"80\",\"container_port_proto\":\"tcp\",\"cpu_shares\":1024,\"host_port\":\"8091\",\"image_name\":\"mynginx:0.1\",\"memory\":40000000,\"volumes\":[{\"container_volume\":\"/root\",\"host_volume\":\"/root\"},{\"container_volume\":\"/etc/nginx/nginx.conf\",\"host_volume\":\"/root/299137.nginx.conf\"}],\"working_dir\":\"/root\"},\"ip\":\"192.168.2.2\"}', '192.168.2.2', NULL);
INSERT INTO `container_create` VALUES ('192.168.2.4mynginx', '{\"createForm\":{\"cmd\":[\"nginx\",\"-g\",\"daemon off;\"],\"container_name\":\"mynginx\",\"container_port\":\"80\",\"container_port_proto\":\"tcp\",\"cpu_shares\":1024,\"host_port\":\"10005\",\"image_name\":\"192.168.2.2:5000/mynginx:0.1\",\"memory\":40000000,\"volumes\":[{\"container_volume\":\"/root\",\"host_volume\":\"/root\"}],\"working_dir\":\"/root\"},\"ip\":\"192.168.2.4\"}', '192.168.2.4', NULL);
INSERT INTO `container_create` VALUES ('192.168.2.2mysql57-test', '{\"createForm\":{\"cmd\":[\"mysql\",\"-e MYSQL_ROOT_PASSWORD=123456\"],\"container_name\":\"mysql57-test\",\"container_port\":\"3306\",\"container_port_proto\":\"tcp\",\"cpu_shares\":1024,\"host_port\":\"8028\",\"image_name\":\"mysql57:0.1\",\"memory\":40000000,\"volumes\":[{\"container_volume\":\"/root\",\"host_volume\":\"/root\"}],\"working_dir\":\"/root\"},\"ip\":\"192.168.2.2\"}', '192.168.2.2', NULL);

-- ----------------------------
-- Table structure for create_args_form
-- ----------------------------
DROP TABLE IF EXISTS `create_args_form`;
CREATE TABLE `create_args_form`  (
  `cluster_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_args` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`cluster_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of create_args_form
-- ----------------------------
INSERT INTO `create_args_form` VALUES ('299137', '{\"container_num\":10,\"container_port\":\"80\",\"container_port_proto\":\"tcp\",\"host_port\":\"8091\",\"image_name\":\"mynginx:0.1\",\"pod_name\":\"blog-1\",\"run_command\":[\"nginx\",\"-g\",\"daemon off;\"],\"volumes\":[{\"container_volume\":\"/root\",\"host_volume\":\"/root\"}]}');

-- ----------------------------
-- Table structure for host_cluster
-- ----------------------------
DROP TABLE IF EXISTS `host_cluster`;
CREATE TABLE `host_cluster`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `port` int(11) DEFAULT NULL,
  `pod_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `container_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 165 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of host_cluster
-- ----------------------------
INSERT INTO `host_cluster` VALUES (155, '192.168.2.6', 10001, '299137', 'blog-1.0.192.168.2.6');
INSERT INTO `host_cluster` VALUES (156, '192.168.2.4', 10000, '299137', 'blog-1.0.192.168.2.4');
INSERT INTO `host_cluster` VALUES (157, '192.168.2.6', 10002, '299137', 'blog-1.1.192.168.2.6');
INSERT INTO `host_cluster` VALUES (158, '192.168.2.4', 10001, '299137', 'blog-1.1.192.168.2.4');
INSERT INTO `host_cluster` VALUES (159, '192.168.2.6', 10003, '299137', 'blog-1.2.192.168.2.6');
INSERT INTO `host_cluster` VALUES (160, '192.168.2.4', 10002, '299137', 'blog-1.2.192.168.2.4');
INSERT INTO `host_cluster` VALUES (161, '192.168.2.6', 10004, '299137', 'blog-1.3.192.168.2.6');
INSERT INTO `host_cluster` VALUES (162, '192.168.2.4', 10003, '299137', 'blog-1.3.192.168.2.4');
INSERT INTO `host_cluster` VALUES (163, '192.168.2.6', 10005, '299137', 'blog-1.4.192.168.2.6');
INSERT INTO `host_cluster` VALUES (164, '192.168.2.4', 10004, '299137', 'blog-1.4.192.168.2.4');

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
INSERT INTO `host_entity` VALUES ('139.159.254.242', 'IP为139.159.254.242的主机', 1, 'linux', 1);
INSERT INTO `host_entity` VALUES ('192.168.2.2', 'IP为192.168.2.2的主机', 4, 'linux', 0);
INSERT INTO `host_entity` VALUES ('192.168.2.4', 'IP为192.168.2.4的主机', 4, 'linux', 1);
INSERT INTO `host_entity` VALUES ('192.168.2.6', 'IP为192.168.2.6的主机', 4, 'linux', 1);
INSERT INTO `host_entity` VALUES ('192.168.2.8', 'IP为192.168.2.8的主机', 4, 'linux', 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of host_license
-- ----------------------------
INSERT INTO `host_license` VALUES (1, '192.168.2.2测试机器', '123456');
INSERT INTO `host_license` VALUES (31, 'huaweicloud', '1Huaweiyun$..');
INSERT INTO `host_license` VALUES (32, 'dali', 'ndl.04551');

-- ----------------------------
-- Table structure for migrate_info
-- ----------------------------
DROP TABLE IF EXISTS `migrate_info`;
CREATE TABLE `migrate_info`  (
  `migrate_id` bigint(11) NOT NULL,
  `migrate_log` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`migrate_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of migrate_info
-- ----------------------------
INSERT INTO `migrate_info` VALUES (1585651027521, '单容器迁移 [src-IPAndPort :192.168.2.4:8085 --> desc-IPAndPort:192.168.2.6:10000]');
INSERT INTO `migrate_info` VALUES (1585819251585, '单容器迁移 [src-IPAndPort :192.168.2.6:10000 --> desc-IPAndPort:192.168.2.4:10005]');

-- ----------------------------
-- Table structure for storage_info
-- ----------------------------
DROP TABLE IF EXISTS `storage_info`;
CREATE TABLE `storage_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` bigint(11) DEFAULT NULL,
  `content` varchar(10000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of storage_info
-- ----------------------------
INSERT INTO `storage_info` VALUES (1, 1585821492782, 'XXXXXXXXXXXXXX');
INSERT INTO `storage_info` VALUES (4, 1585822336446, 'AAAAAAAAAAA');

SET FOREIGN_KEY_CHECKS = 1;
