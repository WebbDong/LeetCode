/*
197. 上升的温度

表 Weather
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| id            | int     |
| recordDate    | date    |
| temperature   | int     |
+---------------+---------+
id 是这个表的主键
该表包含特定日期的温度信息

DROP TABLE IF EXISTS `Weather`;
CREATE TABLE `Weather`  (
  `Id` integer NOT NULL AUTO_INCREMENT,
  `recordDate` date,
	`temperature` integer,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `Weather` VALUES (1, '2015-01-01', 10);
INSERT INTO `Weather` VALUES (2, '2015-01-02', 25);
INSERT INTO `Weather` VALUES (3, '2015-01-03', 20);
INSERT INTO `Weather` VALUES (4, '2015-01-04', 30);

编写一个 SQL 查询，来查找与之前（昨天的）日期相比温度更高的所有日期的 id 。返回结果 不要求顺序 。


查询结果格式如下例：

Weather
+----+------------+-------------+
| id | recordDate | Temperature |
+----+------------+-------------+
| 1  | 2015-01-01 | 10          |
| 2  | 2015-01-02 | 25          |
| 3  | 2015-01-03 | 20          |
| 4  | 2015-01-04 | 30          |
+----+------------+-------------+

Result table:
+----+
| id |
+----+
| 2  |
| 4  |
+----+
2015-01-02 的温度比前一天高（10 -> 25）
2015-01-04 的温度比前一天高（20 -> 30）
 */
SELECT weather.id AS 'Id'
FROM weather
INNER JOIN weather w
ON DATEDIFF(weather.recordDate, w.recordDate) = 1
    AND weather.Temperature > w.Temperature

SELECT day2.id
FROM weather day1
INNER JOIN weather day2
ON date_add(day1.recorddate, INTERVAL 1 DAY) = day2.recorddate
WHERE day2.temperature>day1.temperature