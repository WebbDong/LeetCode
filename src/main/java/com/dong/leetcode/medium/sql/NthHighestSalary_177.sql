/*
177. 第N高的薪水

编写一个 SQL 查询，获取 Employee 表中第n高的薪水（Salary）。

+----+--------+
| Id | Salary |
+----+--------+
| 1  | 100    |
| 2  | 200    |
| 3  | 300    |
+----+--------+

DROP TABLE IF EXISTS `Employee`;
CREATE TABLE `Employee`  (
  `id` integer NOT NULL AUTO_INCREMENT,
  `salary` integer NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `Employee` VALUES (1, 100);
INSERT INTO `Employee` VALUES (2, 200);
INSERT INTO `Employee` VALUES (3, 300);

例如上述Employee表，n = 2时，应返回第二高的薪水200。如果不存在第n高的薪水，那么查询应返回null。

+------------------------+
| getNthHighestSalary(2) |
+------------------------+
| 200                    |
+------------------------+

 */
CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
    SET N = N - 1;
    RETURN (
        SELECT DISTINCT salary
        FROM Employee
        ORDER BY salary DESC
        LIMIT N,1
    );
END