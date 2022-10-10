/*
 Navicat Premium Data Transfer

 Source Server         : mydb5.1
 Source Server Type    : MySQL
 Source Server Version : 50173
 Source Host           : localhost:3306
 Source Schema         : centralis

 Target Server Type    : MySQL
 Target Server Version : 50173
 File Encoding         : 65001

 Date: 09/10/2022 21:06:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for instructor
-- ----------------------------
DROP TABLE IF EXISTS `instructor`;
CREATE TABLE `instructor`  (
  `INID` int(11) NOT NULL,
  `UNAME` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `PSWD` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`INID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for session
-- ----------------------------
DROP TABLE IF EXISTS `session`;
CREATE TABLE `session`  (
  `SEID` int(11) NOT NULL,
  `DATE` datetime NULL DEFAULT NULL,
  `STAGE` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`SEID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for survey_a
-- ----------------------------
DROP TABLE IF EXISTS `survey_a`;
CREATE TABLE `survey_a`  (
  `SAID` int(11) NOT NULL,
  `SQID` int(11) NULL DEFAULT NULL,
  `TMID` int(11) NULL DEFAULT NULL,
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
  `SQID` int(11) NOT NULL,
  `SEID` int(11) NULL DEFAULT NULL,
  `QUESTION` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SQID`) USING BTREE,
  INDEX `SEID`(`SEID`) USING BTREE,
  CONSTRAINT `SEID` FOREIGN KEY (`SEID`) REFERENCES `session` (`SEID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for team
-- ----------------------------
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team`  (
  `TMID` int(11) NOT NULL,
  `SEID` int(11) NULL DEFAULT NULL,
  `ROOM_NUM` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`TMID`) USING BTREE,
  INDEX `TMID`(`TMID`) USING BTREE,
  INDEX `SEID_s`(`SEID`) USING BTREE,
  CONSTRAINT `SEID_s` FOREIGN KEY (`SEID`) REFERENCES `session` (`SEID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
