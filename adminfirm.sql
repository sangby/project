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

 Date: 26/04/2024 23:08:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for adminfirm
-- ----------------------------
DROP TABLE IF EXISTS `adminfirm`;
CREATE TABLE `adminfirm`  (
  `fid` int(11) NULL DEFAULT NULL COMMENT '企业id',
  `uid` int(11) NULL DEFAULT NULL COMMENT '负责人id',
  `money` int(11) NULL DEFAULT 0 COMMENT '负责人可支配资金'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of adminfirm
-- ----------------------------
INSERT INTO `adminfirm` VALUES (1000, 1, 359);
INSERT INTO `adminfirm` VALUES (1001, 2, 0);
INSERT INTO `adminfirm` VALUES (1002, 1, 0);
INSERT INTO `adminfirm` VALUES (1003, 1, 0);
INSERT INTO `adminfirm` VALUES (1004, 1, 0);
INSERT INTO `adminfirm` VALUES (1005, 1, 0);

SET FOREIGN_KEY_CHECKS = 1;
