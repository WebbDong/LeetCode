/*
181. 超过经理收入的员工

Employee表包含所有员工，他们的经理也属于员工。每个员工都有一个 Id，此外还有一列对应员工的经理的 Id。

+----+-------+--------+-----------+
| Id | Name  | Salary | ManagerId |
+----+-------+--------+-----------+
| 1  | Joe   | 70000  | 3         |
| 2  | Henry | 80000  | 4         |
| 3  | Sam   | 60000  | NULL      |
| 4  | Max   | 90000  | NULL      |
+----+-------+--------+-----------+

DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `Id` int(0) NOT NULL AUTO_INCREMENT,
  `Name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `Salary` decimal(10, 2) NULL DEFAULT NULL,
  `ManagerId` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `employee` VALUES (1, 'Joe', 70000.00, 3);
INSERT INTO `employee` VALUES (2, 'Henry', 80000.00, 4);
INSERT INTO `employee` VALUES (3, 'Sam', 60000.00, NULL);
INSERT INTO `employee` VALUES (4, 'Max', 90000.00, NULL);

给定Employee表，编写一个 SQL 查询，该查询可以获取收入超过他们经理的员工的姓名。在上面的表格中，Joe 是唯一一个收入超过他的经理的员工。

+----------+
| Employee |
+----------+
| Joe      |
+----------+
 */
SELECT e.`name` AS Employee
FROM Employee m
INNER JOIN Employee e
ON m.Id = e.ManagerId
WHERE e.Salary > m.Salary

SELECT e.`name` AS Employee
FROM Employee m
INNER JOIN Employee e
ON m.Id = e.ManagerId AND e.Salary > m.Salary