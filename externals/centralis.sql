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

 Date: 10/10/2022 01:54:00
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
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for simSession
-- ----------------------------
DROP TABLE IF EXISTS `simSession`;
CREATE TABLE `simSession`  (
  `SEID` int(0) NOT NULL,
  `DATE` datetime(0) NULL DEFAULT NULL,
  `STAGE` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`SEID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for survey_a
-- ----------------------------
DROP TABLE IF EXISTS `survey_a`;
CREATE TABLE `survey_a`  (
  `SAID` int(0) NOT NULL AUTO_INCREMENT,
  `SQID` int(0) NULL DEFAULT NULL,
  `TMID` int(0) NULL DEFAULT NULL,
  `ANSWER` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SAID`) USING BTREE,
  INDEX `SQID`(`SQID`) USING BTREE,
  INDEX `TMID`(`TMID`) USING BTREE,
  CONSTRAINT `SQID` FOREIGN KEY (`SQID`) REFERENCES `survey_q` (`SQID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `TMID` FOREIGN KEY (`TMID`) REFERENCES `team` (`TMID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for survey_q
-- ----------------------------
DROP TABLE IF EXISTS `survey_q`;
CREATE TABLE `survey_q`  (
  `SQID` int(0) NOT NULL AUTO_INCREMENT,
  `SEID` int(0) NULL DEFAULT NULL,
  `QUESTION` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SQID`) USING BTREE,
  INDEX `SEID`(`SEID`) USING BTREE,
  CONSTRAINT `SEID` FOREIGN KEY (`SEID`) REFERENCES `simSession` (`SEID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

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
  CONSTRAINT `SEID_s` FOREIGN KEY (`SEID`) REFERENCES `simSession` (`SEID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
