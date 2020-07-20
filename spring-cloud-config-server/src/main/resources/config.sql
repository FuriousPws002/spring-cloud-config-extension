-- ----------------------------
-- Table structure for application
-- ----------------------------
DROP TABLE IF EXISTS `application`;
CREATE TABLE `application`  (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `name_` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `desc_` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `date_` datetime(0) NULL DEFAULT NULL COMMENT '插入时间',
  PRIMARY KEY (`id_`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of application
-- ----------------------------
INSERT INTO `application` VALUES (1, 'app-1', '应用1', NULL);

-- ----------------------------
-- Table structure for properties
-- ----------------------------
DROP TABLE IF EXISTS `properties`;
CREATE TABLE `properties`  (
  `application_` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '应用名称，对应spring.application.name',
  `profile_` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `label_` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `key_` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `value_` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of properties
-- ----------------------------
INSERT INTO `properties` VALUES ('app-1', 'test', 'label1', 'key1[0]', 'value1');
INSERT INTO `properties` VALUES ('app-1', 'test', 'label1', 'test.key1[0]', '我是中文1');
INSERT INTO `properties` VALUES ('app-1', 'test', 'master', 'key2', '我是中文');
INSERT INTO `properties` VALUES ('app-1', 'test', 'label1', 'test.key1[1]', '我是中文2');
INSERT INTO `properties` VALUES ('app-1', 'test', 'label1', 'key1[1]', '我是中文');
INSERT INTO `properties` VALUES ('app-1', 'test', 'label2', 'key3', 't3');
INSERT INTO `properties` VALUES ('app-1', 'test', 'label1', 'key3', 't2');
INSERT INTO `properties` VALUES ('app-1', 'test', 'master', 'key3', 't1');
INSERT INTO `properties` VALUES ('app-1', 'test', 'label4', 'server.port', '8082');
INSERT INTO `properties` VALUES ('app-1', 'test', 'label4', 'app-2.ribbon.listOfServers', 'localhost:8080');
INSERT INTO `properties` VALUES ('app-1', 'test', 'label4', 'app-5.ribbon.listOfServers', 'localhost:8080');
INSERT INTO `properties` VALUES ('app-1', 'test', 'master', 'test.users[0].username', 'user1');
INSERT INTO `properties` VALUES ('app-1', 'test', 'master', 'test.users[0].age', '22');
INSERT INTO `properties` VALUES ('app-1', 'test', 'master', 'test.users[1].username', 'user2');
INSERT INTO `properties` VALUES ('app-1', 'test', 'master', 'test.users[1].age', '23');

-- ----------------------------
-- Table structure for properties_desc
-- ----------------------------
DROP TABLE IF EXISTS `properties_desc`;
CREATE TABLE `properties_desc`  (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `application_` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `profile_` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `label_` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type_` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'properties或者yaml',
  `rel_` bigint(20) NULL DEFAULT NULL COMMENT '关联其他的配置id',
  `value_` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`id_`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of properties_desc
-- ----------------------------
INSERT INTO `properties_desc` VALUES (1, 'app-1', 'test', 'master', 'properties', NULL, '...');
INSERT INTO `properties_desc` VALUES (2, 'app-1', 'test', 'label1', 'properties', 1, '...');
INSERT INTO `properties_desc` VALUES (3, 'app-1', 'test', 'label2', 'properties', 2, '...');
INSERT INTO `properties_desc` VALUES (6, 'app-1', 'test', 'label4', 'properties', NULL, 'server.port=8082\napp-2.ribbon.listOfServers=localhost:8080\n#app-3.url=localhost:8080\n#app-4.ribbon.listOfServers=localhost:8080\napp-5.ribbon.listOfServers=localhost:8080');

-- ----------------------------
-- Table structure for properties_log
-- ----------------------------
DROP TABLE IF EXISTS `properties_log`;
CREATE TABLE `properties_log`  (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `application_` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `profile_` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `label_` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type_` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'properties或者yaml',
  `value_` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `date_` datetime(0) NULL DEFAULT NULL COMMENT '插入时间',
  PRIMARY KEY (`id_`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;