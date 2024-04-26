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

 Date: 26/04/2024 23:08:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for firm
-- ----------------------------
DROP TABLE IF EXISTS `firm`;
CREATE TABLE `firm`  (
  `fid` int(11) NOT NULL AUTO_INCREMENT COMMENT '群组id,从1000开始算',
  `firmname` varchar(23) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '企业名非空',
  `introduction` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '企业简介',
  `open` tinyint(1) NULL DEFAULT 1 COMMENT '是否公开,默认公开',
  `fund` int(11) NULL DEFAULT 0 COMMENT '企业余额,默认是0,等你充钱',
  `block` tinyint(1) NULL DEFAULT 0 COMMENT '是否封禁,默认未封禁',
  `memberNum` int(11) NULL DEFAULT 1 COMMENT '企业人数,创建之后默认是1',
  `headPhoto` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT 'http://localhost:8080/QG_war_exploded/img/1058be4a672dc28dc01e3ad527701a47_512_512.jpg' COMMENT '头像路径',
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1006 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of firm
-- ----------------------------
INSERT INTO `firm` VALUES (1000, '腾讯', '腾讯，全称腾空通讯有限公司，在2024年4月19日由张三(id:1)创立', 1, 563, 0, 3, 'http://localhost:8080/QG_war_exploded/img/1058be4a672dc28dc01e3ad527701a47_512_512.jpg');
INSERT INTO `firm` VALUES (1001, '阿里巴巴', '让天下没有难做的生意', 1, 0, 0, 3, 'http://localhost:8080/QG_war_exploded/img/1058be4a672dc28dc01e3ad527701a47_512_512.jpg');
INSERT INTO `firm` VALUES (1002, '字节', '在2024年4月25日由张三创建', 1, 0, 0, 1, 'http://localhost:8080/QG_war_exploded/img/1058be4a672dc28dc01e3ad527701a47_512_512.jpg');
INSERT INTO `firm` VALUES (1003, '美团', '张三', 0, 0, 0, 1, 'http://localhost:8080/QG_war_exploded/img/1058be4a672dc28dc01e3ad527701a47_512_512.jpg');
INSERT INTO `firm` VALUES (1004, '饿了么', '张三', 1, 0, 0, 3, 'http://localhost:8080/QG_war_exploded/img/1058be4a672dc28dc01e3ad527701a47_512_512.jpg');
INSERT INTO `firm` VALUES (1005, '昆', '在2024年4月25日由张三创建', 1, 0, 0, 1, 'http://localhost:8080/QG_war_exploded/img/1058be4a672dc28dc01e3ad527701a47_512_512.jpg');

SET FOREIGN_KEY_CHECKS = 1;
