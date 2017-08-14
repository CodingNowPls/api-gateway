/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-08-14 11:11:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_t
-- ----------------------------
DROP TABLE IF EXISTS `user_t`;
CREATE TABLE `user_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `age` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of user_t
-- ----------------------------
INSERT INTO `user_t` VALUES ('1', '辅导费', '43234', '43');
INSERT INTO `user_t` VALUES ('2', '东方时代', '544', '423');
INSERT INTO `user_t` VALUES ('3', '对方身份', '5345', '34');
INSERT INTO `user_t` VALUES ('4', '士大夫3', '6546', '545');
INSERT INTO `user_t` VALUES ('5', '房东', '3534', '324');
INSERT INTO `user_t` VALUES ('6', '股份', '2312', '2342');
INSERT INTO `user_t` VALUES ('7', '个人股', '45', '34');
