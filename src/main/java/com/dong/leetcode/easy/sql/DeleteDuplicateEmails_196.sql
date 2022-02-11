/*
196. 删除重复的电子邮箱

编写一个 SQL 查询，来删除Person表中所有重复的电子邮箱，重复的邮箱里只保留Id最小的那个。
+----+------------------+
| Id | Email            |
+----+------------------+
| 1  | john@example.com |
| 2  | bob@example.com  |
| 3  | john@example.com |
+----+------------------+
Id 是这个表的主键。

DROP TABLE IF EXISTS `person`;
CREATE TABLE `person`  (
  `Id` integer NOT NULL AUTO_INCREMENT,
  `Email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `person` VALUES (1, 'john@example.com');
INSERT INTO `person` VALUES (2, 'bob@example.com');
INSERT INTO `person` VALUES (3, 'john@example.com');
INSERT INTO `person` VALUES (4, 'Webb@example.com');
INSERT INTO `person` VALUES (5, 'bob@example.com');
INSERT INTO `person` VALUES (6, 'bob@example.com');
INSERT INTO `person` VALUES (7, 'bob@example.com');

例如，在运行你的查询语句之后，上面的 Person 表应返回以下几行:
+----+------------------+
| Id | Email            |
+----+------------------+
| 1  | john@example.com |
| 2  | bob@example.com  |
+----+------------------+

提示：
    执行 SQL 之后，输出是整个 Person表。
    使用 delete 语句。
 */
DELETE FROM person p1
WHERE p1.Id NOT IN (
    SELECT Id
    FROM (
        SELECT MIN(p2.Id) AS Id
        FROM person p2
        GROUP BY p2.Email
    ) t
)

DELETE p1 FROM person p1, person p2
WHERE p1.Email = p2.Email AND p1.Id > p2.Id