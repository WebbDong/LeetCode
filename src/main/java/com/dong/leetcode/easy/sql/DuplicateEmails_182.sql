/*
182. 查找重复的电子邮箱

编写一个 SQL 查询，查找 Person 表中所有重复的电子邮箱。

示例：
+----+---------+
| Id | Email   |
+----+---------+
| 1  | a@b.com |
| 2  | c@d.com |
| 3  | a@b.com |
+----+---------+

DROP TABLE IF EXISTS `person`;
CREATE TABLE `person`  (
  `Id` integer NOT NULL AUTO_INCREMENT,
  `Email` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `person` VALUES (1, 'a@b.com');
INSERT INTO `person` VALUES (2, 'c@d.com');
INSERT INTO `person` VALUES (3, 'a@b.com');

根据以上输入，你的查询应返回以下结果：
+---------+
| Email   |
+---------+
| a@b.com |
+---------+
说明：所有电子邮箱都是小写字母。
 */
SELECT Email
FROM person
GROUP BY Email
HAVING COUNT(Email) > 1