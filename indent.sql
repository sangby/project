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

 Date: 26/04/2024 23:08:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for indent
-- ----------------------------
DROP TABLE IF EXISTS `indent`;
CREATE TABLE `indent`  (
  `pid` int(11) NOT NULL COMMENT '付款方',
  `aid` int(11) NOT NULL COMMENT '收款方',
  `money` int(11) NOT NULL COMMENT '金额',
  `state` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '等待响应' COMMENT '支付状态,等待响应/成功',
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '订单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of indent
-- ----------------------------
INSERT INTO `indent` VALUES (1, 1000, 10, '成功', 9);
INSERT INTO `indent` VALUES (1, 1000, 50, '成功', 10);
INSERT INTO `indent` VALUES (1, 1000, 2, '成功', 11);
INSERT INTO `indent` VALUES (1, 1000, 2, '成功', 12);
INSERT INTO `indent` VALUES (1, 1000, 500, '成功', 13);
INSERT INTO `indent` VALUES (1, 1000, 1, '成功', 14);
INSERT INTO `indent` VALUES (1, 2, 1, '成功', 17);
INSERT INTO `indent` VALUES (1, 2, 1, '成功', 18);
INSERT INTO `indent` VALUES (1000, 2, 1, '成功', 19);
INSERT INTO `indent` VALUES (1000, 2, 1, '成功', 20);

SET FOREIGN_KEY_CHECKS = 1;
