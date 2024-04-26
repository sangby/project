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

 Date: 26/04/2024 23:08:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `uid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id,主键自增',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名,非空',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码,非空',
  `block` tinyint(1) NULL DEFAULT 0 COMMENT '是否封禁,默认没有',
  `money` int(11) NULL DEFAULT 1000 COMMENT '资金默认1000圆子',
  `headPhoto` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT 'http://localhost:8080/QG_war_exploded/img/2064731432.jpg' COMMENT '头像路径',
  `signature` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '这个人很懒,没有签名' COMMENT '个性签名',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手机号',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '学生用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'zhangsan', 'UUdRR1FHISEh0nal9eZZuvHNReqFdyhtnA==', 0, 433, 'http://localhost:8080/QG_war_exploded/img/QQ鍥剧墖20231001155902.gif', '每个人心中都有一个张三', '11111111111');
INSERT INTO `user` VALUES (2, 'mayun', 'UUdRR1FHISEh0nal9eZZuvHNReqFdyhtnA==', 0, 1004, 'http://localhost:8080/QG_war_exploded/img/2064731432.jpg', '我对钱没有兴趣', '11111111111');
INSERT INTO `user` VALUES (3, 'mahuateng', 'UUdRR1FHISEh0nal9eZZuvHNReqFdyhtnA==', 0, 1000, 'http://localhost:8080/QG_war_exploded/img/2064731432.jpg', '仔细动脑筋想一想,不充钱?不充钱你会变得强大吗??', '22222222222');
INSERT INTO `user` VALUES (4, '1  2', 'UUdRR1FHISEh0nal9eZZuvHNReqFdyhtnA==', 0, 1000, 'http://localhost:8080/QG_war_exploded/img/2064731432.jpg', '', NULL);
INSERT INTO `user` VALUES (5, '123', 'UUdRR1FHISEhCII+5t/UcC7kQEtQnXWCPw==', 0, 1000, 'http://localhost:8080/QG_war_exploded/img/2064731432.jpg', '这个人很懒,没有签名', NULL);
INSERT INTO `user` VALUES (7, '哈希', 'UUdRR1FHISEh0nal9eZZuvHNReqFdyhtnA==', 0, 1000, 'http://localhost:8080/QG_war_exploded/img/2064731432.jpg', '这个人很懒,没有签名', NULL);

SET FOREIGN_KEY_CHECKS = 1;
