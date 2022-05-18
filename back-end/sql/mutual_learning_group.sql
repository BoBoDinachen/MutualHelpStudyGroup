/*
 Navicat Premium Data Transfer

 Source Server         : 薛定谔的猫er
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : mutual_learning_group

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 18/05/2022 19:11:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for plan
-- ----------------------------
DROP TABLE IF EXISTS `plan`;
CREATE TABLE `plan`  (
  `plan_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学习计划编号',
  `student_id` int(11) NOT NULL COMMENT '学生Id',
  `group_id` int(11) NOT NULL COMMENT '小组id',
  `plan_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '计划标题',
  `target` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '计划目标',
  `start_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结束时间',
  `is_finish` int(1) NOT NULL DEFAULT 0 COMMENT '是否完成',
  PRIMARY KEY (`plan_id`) USING BTREE,
  INDEX `fk_plan_studentId`(`student_id`) USING BTREE,
  INDEX `fk_plan_groupId`(`group_id`) USING BTREE,
  CONSTRAINT `fk_plan_groupId` FOREIGN KEY (`group_id`) REFERENCES `study_group` (`group_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_plan_studentId` FOREIGN KEY (`student_id`) REFERENCES `student` (`s_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plan
-- ----------------------------
INSERT INTO `plan` VALUES (4, 1, 8, '计划1', '完成', '2022-01-04 17:31:53', '2020-10-08 00:56:45', 0);
INSERT INTO `plan` VALUES (5, 1, 8, '计划2', '完成', '2022-01-04 17:32:16', '2020-10-08 00:56:45', 0);
INSERT INTO `plan` VALUES (8, 1, 8, '吃饭', '早饭要吃，午饭要吃，晚饭要吃。', '2022-01-05 13:50:12', '2022-01-05', 1);
INSERT INTO `plan` VALUES (9, 1, 8, '喝水', '早上要喝水，中午要喝水，晚上也要喝水', '2022-01-05 14:15:19', '2022-01-05', 1);
INSERT INTO `plan` VALUES (16, 1, 11, '没有添加子任务', '2333', '2022-01-05 14:50:33', '2022-01-31', 1);
INSERT INTO `plan` VALUES (17, 1, 11, '添加子任务', '1111', '2022-01-05 14:51:14', '2022-02-01', 0);
INSERT INTO `plan` VALUES (19, 1, 8, '晚上吃饭', '吃饱肚子', '2022-01-05 17:04:57', '2022-01-05', 1);
INSERT INTO `plan` VALUES (20, 1, 8, '233', '233', '2022-01-05 23:41:34', '2022-01-20', 1);
INSERT INTO `plan` VALUES (21, 1, 8, '网易云APP', '使用vue和typeScript构建一个音乐APP', '2022-01-17 14:05:32', '2022-02-22', 0);
INSERT INTO `plan` VALUES (22, 1, 8, '计划三', '完成', '2022-01-17 14:10:10', '无期限', 0);

-- ----------------------------
-- Table structure for plan_audit
-- ----------------------------
DROP TABLE IF EXISTS `plan_audit`;
CREATE TABLE `plan_audit`  (
  `audit_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '计划审核id',
  `group_id` int(11) NOT NULL COMMENT '小组id',
  `student_id` int(11) NOT NULL COMMENT '学生id',
  `plan_id` int(11) NOT NULL COMMENT '计划id',
  `pass_number` int(1) NOT NULL DEFAULT 0 COMMENT '通过数量',
  PRIMARY KEY (`audit_id`) USING BTREE,
  INDEX `fk_planAudit_groupId`(`group_id`) USING BTREE,
  INDEX `fk_planAudit_studentId`(`student_id`) USING BTREE,
  INDEX `fk_planAudit_planId`(`plan_id`) USING BTREE,
  CONSTRAINT `fk_planAudit_groupId` FOREIGN KEY (`group_id`) REFERENCES `study_group` (`group_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_planAudit_planId` FOREIGN KEY (`plan_id`) REFERENCES `plan` (`plan_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_planAudit_studentId` FOREIGN KEY (`student_id`) REFERENCES `student` (`s_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plan_audit
-- ----------------------------
INSERT INTO `plan_audit` VALUES (2, 8, 1, 9, 9);

-- ----------------------------
-- Table structure for plan_issue
-- ----------------------------
DROP TABLE IF EXISTS `plan_issue`;
CREATE TABLE `plan_issue`  (
  `report_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学习情况主键id',
  `student_id` int(11) NOT NULL COMMENT '学生Id',
  `group_id` int(11) NOT NULL COMMENT '小组id',
  `plan_id` int(11) NOT NULL COMMENT '计划id',
  `issue` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '问题',
  `issue_explain` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '说明',
  `accessory` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件---文件路径',
  `report_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '汇报时间',
  PRIMARY KEY (`report_id`) USING BTREE,
  INDEX `fk_condition_planId`(`plan_id`) USING BTREE,
  INDEX `fk_planIssue_groupId`(`group_id`) USING BTREE,
  INDEX `fk_planIssue_studentId`(`student_id`) USING BTREE,
  CONSTRAINT `fk_condition_planId` FOREIGN KEY (`plan_id`) REFERENCES `plan` (`plan_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_planIssue_groupId` FOREIGN KEY (`group_id`) REFERENCES `study_group` (`group_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_planIssue_studentId` FOREIGN KEY (`student_id`) REFERENCES `student` (`s_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plan_issue
-- ----------------------------
INSERT INTO `plan_issue` VALUES (5, 1, 8, 4, 'labore dolor ut et', NULL, NULL, '2022-01-05 18:16:49');
INSERT INTO `plan_issue` VALUES (6, 1, 8, 4, '问题太大了', NULL, NULL, '2022-01-05 18:29:46');

-- ----------------------------
-- Table structure for plan_task
-- ----------------------------
DROP TABLE IF EXISTS `plan_task`;
CREATE TABLE `plan_task`  (
  `task_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '计划任务id',
  `plan_id` int(11) NOT NULL COMMENT '计划id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务标题',
  `number` int(2) NOT NULL DEFAULT 0 COMMENT '任务序号',
  `is_finish2` int(1) NOT NULL DEFAULT 0 COMMENT '是否完成',
  PRIMARY KEY (`task_id`) USING BTREE,
  INDEX `fk_planTask_planId`(`plan_id`) USING BTREE,
  CONSTRAINT `fk_planTask_planId` FOREIGN KEY (`plan_id`) REFERENCES `plan` (`plan_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plan_task
-- ----------------------------
INSERT INTO `plan_task` VALUES (16, 4, '任务一', 0, 1);
INSERT INTO `plan_task` VALUES (17, 4, '任务二', 1, 0);
INSERT INTO `plan_task` VALUES (18, 5, '任务一', 0, 1);
INSERT INTO `plan_task` VALUES (19, 5, '任务二', 1, 0);
INSERT INTO `plan_task` VALUES (22, 17, '123', 0, 0);
INSERT INTO `plan_task` VALUES (23, 19, '拿筷子', 0, 0);
INSERT INTO `plan_task` VALUES (24, 19, '端碗', 0, 0);
INSERT INTO `plan_task` VALUES (25, 19, '付钱', 0, 0);
INSERT INTO `plan_task` VALUES (26, 20, '2333', 0, 0);
INSERT INTO `plan_task` VALUES (27, 21, '搭建环境', 0, 0);
INSERT INTO `plan_task` VALUES (28, 22, '第一步', 0, 0);
INSERT INTO `plan_task` VALUES (29, 22, '第二步', 0, 0);
INSERT INTO `plan_task` VALUES (30, 22, '第三步', 1, 0);

-- ----------------------------
-- Table structure for sticky_notes
-- ----------------------------
DROP TABLE IF EXISTS `sticky_notes`;
CREATE TABLE `sticky_notes`  (
  `sticker_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `student_id` int(11) NOT NULL COMMENT '学生id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `create_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`sticker_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sticky_notes
-- ----------------------------
INSERT INTO `sticky_notes` VALUES (2, 2, '道南性路命', '2021-12-31 08:57:07');
INSERT INTO `sticky_notes` VALUES (10, 1, '今天逗猫', '2022-01-17 13:47:51');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `s_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学生主键',
  `s_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学号',
  `email` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '女' COMMENT '性别',
  `age` int(3) NULL DEFAULT NULL COMMENT '年龄',
  `portrait_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像路径',
  PRIMARY KEY (`s_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, '', '492697494@qq.com', '123456', '薛定谔的猫猫虫', '女', 18, 'F:\\MyCode\\Vue_Projects\\MutualHelp-Study-Group\\back-end\\target\\classes\\static\\files\\avatar\\1-1652869140158@OIP-C.jpeg');
INSERT INTO `student` VALUES (2, '91921380103', '464564556@qq.com', '123', '杨言', '男', 20, 'F:\\MyCode\\Vue_Projects\\MutualHelp-Study-Group\\back-end\\target\\classes\\static\\files\\avatar\\default-avatar.png');
INSERT INTO `student` VALUES (3, '91921380119', '465465454@qq.com', '123', '全鑫', '女', 20, 'F:\\MyCode\\Vue_Projects\\MutualHelp-Study-Group\\back-end\\target\\classes\\static\\files\\avatar\\default-avatar.png');
INSERT INTO `student` VALUES (21, NULL, '78946541@qq.com', '123', '可爱萌新', '女', 18, 'F:\\MyCode\\Vue_Projects\\MutualHelp-Study-Group\\back-end\\target\\classes\\static\\files\\avatar\\default-avatar.png');
INSERT INTO `student` VALUES (22, NULL, '497846437649@qq.com', '123', '可爱萌新', '女', 18, '/F:/MyCode/MutualHelp-Study-Group/back-end/target/classes/static/files/avatar/default-avatar.png');

-- ----------------------------
-- Table structure for student_group
-- ----------------------------
DROP TABLE IF EXISTS `student_group`;
CREATE TABLE `student_group`  (
  `member_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学生小组id',
  `student_id` int(11) NOT NULL COMMENT '学生id',
  `group_id` int(11) NOT NULL COMMENT '小组id',
  `is_leader` int(1) NOT NULL DEFAULT 0 COMMENT '是否是组长',
  `join_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加入时间',
  PRIMARY KEY (`member_id`) USING BTREE,
  INDEX `fk_sudent_group_groupId`(`group_id`) USING BTREE,
  INDEX `fk_student_group_studentId`(`student_id`) USING BTREE,
  CONSTRAINT `fk_student_group_studentId` FOREIGN KEY (`student_id`) REFERENCES `student` (`s_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_sudent_group_groupId` FOREIGN KEY (`group_id`) REFERENCES `study_group` (`group_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_group
-- ----------------------------
INSERT INTO `student_group` VALUES (10, 1, 8, 1, '2022-01-03 18:41:49');
INSERT INTO `student_group` VALUES (15, 2, 11, 1, '2022-01-04 09:03:14');
INSERT INTO `student_group` VALUES (16, 3, 12, 1, '2022-01-04 09:03:21');
INSERT INTO `student_group` VALUES (17, 2, 13, 1, '2022-01-04 09:03:27');
INSERT INTO `student_group` VALUES (18, 3, 14, 1, '2022-01-04 09:03:36');
INSERT INTO `student_group` VALUES (28, 1, 11, 0, '2022-01-04 16:18:15');
INSERT INTO `student_group` VALUES (32, 3, 8, 0, '2022-01-04 16:43:29');
INSERT INTO `student_group` VALUES (34, 1, 15, 1, '2022-01-04 17:06:00');
INSERT INTO `student_group` VALUES (36, 1, 12, 0, '2022-01-05 00:12:38');
INSERT INTO `student_group` VALUES (39, 2, 14, 0, '2022-01-05 01:41:31');
INSERT INTO `student_group` VALUES (40, 1, 13, 0, '2022-01-05 09:03:43');

-- ----------------------------
-- Table structure for study_group
-- ----------------------------
DROP TABLE IF EXISTS `study_group`;
CREATE TABLE `study_group`  (
  `group_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '小组id',
  `leader` int(11) NULL DEFAULT NULL COMMENT '组长id --- 学生',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小组名称',
  `create_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `introduce` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小组介绍',
  `team_head` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小组头像',
  PRIMARY KEY (`group_id`) USING BTREE,
  INDEX `fk_group_uid`(`leader`) USING BTREE,
  CONSTRAINT `fk_group_uid` FOREIGN KEY (`leader`) REFERENCES `student` (`s_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of study_group
-- ----------------------------
INSERT INTO `study_group` VALUES (8, 1, '小组一', '2022-01-03 18:41:49', '简短介绍', 'F:\\MyCode\\Vue_Projects\\MutualHelp-Study-Group\\back-end\\target\\classes\\static\\files\\teamHead\\8-1652869687245@OIP-C.jpg');
INSERT INTO `study_group` VALUES (11, 2, '小组五', '2022-01-04 09:03:14', '23333', 'F:\\MyCode\\Vue_Projects\\MutualHelp-Study-Group\\back-end\\target\\classes\\static\\files\\teamHead\\default-team.png');
INSERT INTO `study_group` VALUES (12, 3, '小组二', '2022-01-04 09:03:21', 'exercitation', 'F:\\MyCode\\Vue_Projects\\MutualHelp-Study-Group\\back-end\\target\\classes\\static\\files\\teamHead\\default-team.png');
INSERT INTO `study_group` VALUES (13, 2, '小组三', '2022-01-04 09:03:27', 'esse est', 'F:\\MyCode\\Vue_Projects\\MutualHelp-Study-Group\\back-end\\target\\classes\\static\\files\\teamHead\\default-team.png');
INSERT INTO `study_group` VALUES (14, 3, '广题又风', '2022-01-04 09:03:36', 'ut ullamco', 'F:\\MyCode\\Vue_Projects\\MutualHelp-Study-Group\\back-end\\target\\classes\\static\\files\\teamHead\\default-team.png');
INSERT INTO `study_group` VALUES (15, 1, '小组四', '2022-01-04 17:06:00', '哈哈哈哈', 'F:\\MyCode\\Vue_Projects\\MutualHelp-Study-Group\\back-end\\target\\classes\\static\\files\\teamHead\\15-1652869699607@OIP-C.jpg');

SET FOREIGN_KEY_CHECKS = 1;
