/*
175.组合两个表

表1: Person
+-------------+---------+
| 列名         | 类型     |
+-------------+---------+
| PersonId    | int     |
| FirstName   | varchar |
| LastName    | varchar |
+-------------+---------+
PersonId 是上表主键

DROP TABLE IF EXISTS `person`;
CREATE TABLE `person`  (
  `PersonId` integer NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `LastName` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`PersonId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `person` VALUES (1, 'WebBin', 'Dong');
INSERT INTO `person` VALUES (2, 'Kobe', 'Bryant');

表2: Address
+-------------+---------+
| 列名         | 类型    |
+-------------+---------+
| AddressId   | int     |
| PersonId    | int     |
| City        | varchar |
| State       | varchar |
+-------------+---------+
AddressId 是上表主键

DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `AddressId` integer NOT NULL AUTO_INCREMENT,
  `PersonId` integer NULL DEFAULT NULL,
  `City` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `State` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`AddressId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `address` VALUES (1, 1, 'Shanghai', 'Shanghai');

编写一个 SQL 查询，满足条件：无论 person 是否有地址信息，都需要基于上述两表提供 person 的以下信息：
    FirstName, LastName, City, State
 */
SELECT p.FirstName, p.LastName, a.City, a.State
FROM person p
LEFT JOIN address a
ON p.PersonId = a.PersonId

SELECT p.FirstName, p.LastName, a.City, a.State
FROM address a
RIGHT JOIN person p
ON p.PersonId = a.PersonId