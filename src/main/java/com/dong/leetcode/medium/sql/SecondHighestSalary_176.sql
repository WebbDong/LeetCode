/*
176. 第二高的薪水

Employee 表：
+-------------+------+
| Column Name | Type |
+-------------+------+
| id          | int  |
| salary      | int  |
+-------------+------+
id 是这个表的主键。
表的每一行包含员工的工资信息。

DROP TABLE IF EXISTS `Employee`;
CREATE TABLE `Employee`  (
  `id` integer NOT NULL AUTO_INCREMENT,
  `salary` integer NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `Employee` VALUES (1, 100);
INSERT INTO `Employee` VALUES (2, 200);
INSERT INTO `Employee` VALUES (3, 300);

编写一个 SQL 查询，获取并返回 Employee表中第二高的薪水 。如果不存在第二高的薪水，查询应该返回 null 。

查询结果如下例所示。



示例 1：

输入：
Employee 表：
+----+--------+
| id | salary |
+----+--------+
| 1  | 100    |
| 2  | 200    |
| 3  | 300    |
+----+--------+
输出：
+---------------------+
| SecondHighestSalary |
+---------------------+
| 200                 |
+---------------------+
示例 2：

输入：
Employee 表：
+----+--------+
| id | salary |
+----+--------+
| 1  | 100    |
+----+--------+
输出：
+---------------------+
| SecondHighestSalary |
+---------------------+
| null                |
+---------------------+
 */
SELECT (SELECT DISTINCT salary
        FROM Employee
        ORDER BY salary DESC
        LIMIT 1, 1) SecondHighestSalary