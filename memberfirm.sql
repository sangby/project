/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50719 (5.7.19-log)
 Source Host           : localhost:3306
 Source Schema         : hotel

 Target Server Type    : MySQL
 Target Server Version : 50719 (5.7.19-log)
 File Encoding         : 65001

 Date: 26/04/2024 23:08:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for memberfirm
-- ----------------------------
DROP TABLE IF EXISTS `memberfirm`;
CREATE TABLE `memberfirm`  (
  `uid` int(11) NOT NULL,
  `fid` int(11) NOT NULL,
  `money` int(11) NULL DEFAULT 0 COMMENT '群员分配的资金',
  `iscollect` tinyint(1) NULL DEFAULT 0 COMMENT '是否可以发起群收款,默认为不能'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '群员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of memberfirm
-- ----------------------------
INSERT INTO `memberfirm` VALUES (2, 1000, 106, 0);
INSERT INTO `memberfirm` VALUES (3, 1000, 100, 0);
INSERT INTO `memberfirm` VALUES (3, 1001, 0, 0);
INSERT INTO `memberfirm` VALUES (1, 1001, 0, 0);

SET FOREIGN_KEY_CHECKS = 1;
