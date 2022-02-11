/*
183. 从不订购的客户

某网站包含两个表，Customers 表和 Orders 表。编写一个 SQL 查询，找出所有从不订购任何东西的客户。

Customers 表：
+----+-------+
| Id | Name  |
+----+-------+
| 1  | Joe   |
| 2  | Henry |
| 3  | Sam   |
| 4  | Max   |
+----+-------+

DROP TABLE IF EXISTS `Customers`;
CREATE TABLE `Customers`  (
  `Id` integer NOT NULL AUTO_INCREMENT,
  `Name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `customers` VALUES (1, 'Joe');
INSERT INTO `customers` VALUES (2, 'Henry');
INSERT INTO `customers` VALUES (3, 'Sam');
INSERT INTO `customers` VALUES (4, 'Max');

Orders 表：
+----+------------+
| Id | CustomerId |
+----+------------+
| 1  | 3          |
| 2  | 1          |
+----+------------+

DROP TABLE IF EXISTS `Orders`;
CREATE TABLE `Orders`  (
  `Id` integer NOT NULL AUTO_INCREMENT,
  `CustomerId` integer,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `Orders` VALUES (1, 3);
INSERT INTO `Orders` VALUES (2, 1);

例如给定上述表格，你的查询应返回：
+-----------+
| Customers |
+-----------+
| Henry     |
| Max       |
+-----------+
 */
SELECT c.`Name` AS Customers
FROM customers c
LEFT JOIN orders o
ON c.Id = o.CustomerId
WHERE o.Id IS NULL

SELECT c.`Name` AS Customers
FROM customers c
WHERE c.Id NOT IN(
    SELECT o.CustomerId
    FROM orders o
)