/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : centralis

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 22/10/2022 01:03:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for instructor
-- ----------------------------
DROP TABLE IF EXISTS `instructor`;
CREATE TABLE `instructor`  (
  `INID` int(0) NOT NULL AUTO_INCREMENT,
  `UNAME` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `PSWD` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`INID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 333 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of instructor
-- ----------------------------
INSERT INTO `instructor` VALUES (333, 'test1', 'test1');

-- ----------------------------
-- Table structure for session
-- ----------------------------
DROP TABLE IF EXISTS `session`;
CREATE TABLE `session`  (
  `SEID` int(0) NOT NULL,
  `DATE` datetime(0) NULL DEFAULT NULL,
  `STAGE` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`SEID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of session
-- ----------------------------

-- ----------------------------
-- Table structure for survey
-- ----------------------------
DROP TABLE IF EXISTS `survey`;
CREATE TABLE `survey`  (
  `SUID` int(0) NOT NULL AUTO_INCREMENT,
  `TMID` int(0) NULL DEFAULT NULL,
  `SEID` int(0) NULL DEFAULT NULL,
  `SVGRP` int(0) NOT NULL COMMENT 'Can only be 1 or 2 since there are only two surveys',
  `Q1` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `Q2` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `Q3` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `Q4` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `Q5` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SUID`) USING BTREE,
  INDEX `SEID`(`SEID`) USING BTREE,
  INDEX `TMID`(`TMID`) USING BTREE,
  CONSTRAINT `SEID` FOREIGN KEY (`SEID`) REFERENCES `session` (`SEID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `TMID` FOREIGN KEY (`TMID`) REFERENCES `team` (`TMID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for team
-- ----------------------------
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team`  (
  `TMID` int(0) NOT NULL AUTO_INCREMENT,
  `SEID` int(0) NULL DEFAULT NULL,
  `TEAMNAME` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`TMID`) USING BTREE,
  INDEX `TMID`(`TMID`) USING BTREE,
  INDEX `SEID_s`(`SEID`) USING BTREE,
  CONSTRAINT `SEID_s` FOREIGN KEY (`SEID`) REFERENCES `session` (`SEID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of team
-- ----------------------------


SET FOREIGN_KEY_CHECKS = 1;
