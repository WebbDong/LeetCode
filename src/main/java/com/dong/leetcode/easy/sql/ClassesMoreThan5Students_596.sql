/*
596. 超过5名学生的课

有一个 courses 表 ，有: student(学生) 和 class (课程)。

请列出所有超过或等于5名学生的课。

例如，表：

+---------+------------+
| student | class      |
+---------+------------+
| A       | Math       |
| B       | English    |
| C       | Math       |
| D       | Biology    |
| E       | Math       |
| F       | Computer   |
| G       | Math       |
| H       | Math       |
| I       | Math       |
+---------+------------+

DROP TABLE IF EXISTS `courses`;
CREATE TABLE `courses`  (
  `student` varchar(10),
  `class` varchar(30)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `courses` VALUES ('A', 'Math');
INSERT INTO `courses` VALUES ('B', 'English');
INSERT INTO `courses` VALUES ('C', 'Math');
INSERT INTO `courses` VALUES ('D', 'Biology');
INSERT INTO `courses` VALUES ('E', 'Math');
INSERT INTO `courses` VALUES ('F', 'Computer');
INSERT INTO `courses` VALUES ('G', 'Math');
INSERT INTO `courses` VALUES ('H', 'Math');
INSERT INTO `courses` VALUES ('I', 'Math');

应该输出:

+---------+
| class   |
+---------+
| Math    |
+---------+


提示：

学生在每个课中不应被重复计算。

 */
SELECT class
FROM `courses`
GROUP BY class
HAVING count(class) >= 5