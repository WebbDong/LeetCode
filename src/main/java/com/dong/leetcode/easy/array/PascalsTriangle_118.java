package com.dong.leetcode.easy.array;

import java.util.ArrayList;
import java.util.List;

/**
 * 杨辉三角 PascalsTriangle
 *
 * 给定一个非负整数 numRows，生成杨辉三角的前 numRows 行。
 *
 * 示例:
 * 输入: 5
 * 输出:
 * [
 *      [1],
 *     [1,1],
 *    [1,2,1],
 *   [1,3,3,1],
 *  [1,4,6,4,1]
 * ]
 */
public class PascalsTriangle_118 {

    private static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> ptList = new ArrayList<>(numRows);
        for (int s = 0; s < numRows; s++) {
            List<Integer> row = new ArrayList<>();
            row.add(1);
            for (int i = 1; i <= s; i++) {
                row.add((int) ((long) row.get(i - 1) * (s - i + 1) / i));
            }
            ptList.add(row);
        }
        return ptList;
    }

    public static void main(String[] args) {
        System.out.println(generate(5));
    }

}
