package com.dong.leetcode.simple.array;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * 杨辉三角 II  PascalsTriangle II
 *
 * 给定一个非负索引 k，其中 k ≤ 33，返回杨辉三角的第 k 行。
 *
 * 示例:
 *      输入: 3
 *      输出: [1,3,3,1]
 *
 * 进阶:
 *      你可以优化你的算法到 O(k) 空间复杂度吗？
 */
public class PascalsTriangleII_119 {

    private interface PascalsTriangle {

        List<Integer> getRow(int rowIndex);

    }

    /**
     * 打印 Pascal's Triangle，方式一、数组备份，空间复杂度太高
     * @param n
     */
    private static void printPascalsTriangle1(int n) {
        int[] temp = new int[n + 1];
        int[] current = new int[n + 1];
        for (int i = 1, len = n + 1; i <= len; i++) {
            for (int j = 1, cti = 0, pti = 0; j <= i; j++) {
                if (j == 1 || j == i) {
                    System.out.print("1, ");
                    current[cti++] = 1;
                } else {
                    int e = temp[pti] + temp[pti + 1];
                    pti++;
                    System.out.print(e + ", ");
                    current[cti++] = e;
                }
            }
            System.arraycopy(current, 0, temp, 0, current.length);
            System.out.println();
        }
    }

    /**
     * 打印 Pascal's Triangle，方式二、递推
     * @param n
     */
    private static void printPascalsTriangle2(int n) {
        List<Integer> row = new ArrayList<>();
        for (int s = 0, len = n; s <= len; s++) {
            row.add(1);
            for (int i = 1; i <= s; i++) {
                row.add(0);
                for (int j = i; j > 0; j--) {
                    row.set(j, row.get(j) + row.get(j - 1));
                }
            }
            System.out.println(row);
            row.clear();
        }
    }

    /**
     * 打印 Pascal's Triangle，方式三、线性递推
     * @param n
     */
    private static void printPascalsTriangle3(int n) {
        List<Integer> row = new ArrayList<>();
        for (int s = 0, len = n; s <= len; s++) {
            row.add(1);
            for (int i = 1; i <= s; i++) {
                row.add((int) ((long) row.get(i - 1) * (s - i + 1) / i));
            }
            System.out.println(row);
            row.clear();
        }
    }

    /**
     * 方法一、备份数组、空间复杂度过高
     */
    @Data
    @AllArgsConstructor
    private static class PascalsTriangle1 implements PascalsTriangle {

        private int n;

        @Override
        public List<Integer> getRow(int rowIndex) {
            Integer[] temp = new Integer[rowIndex + 1];
            Integer[] current = new Integer[rowIndex + 1];
            for (int i = 1, len = rowIndex + 1; i <= len; i++) {
                for (int j = 1, cti = 0, pti = 0; j <= i; j++) {
                    if (j == 1 || j == i) {
                        current[cti++] = 1;
                    } else {
                        int e = temp[pti] + temp[pti + 1];
                        pti++;
                        current[cti++] = e;
                    }
                }
                System.arraycopy(current, 0, temp, 0, current.length);
            }
            return Arrays.asList(current);
        }

    }

    /**
     * 方法二、递推
     */
    @Data
    @AllArgsConstructor
    private static class PascalsTriangle2 implements PascalsTriangle {

        private int n;

        @Override
        public List<Integer> getRow(int rowIndex) {
            List<Integer> row = new ArrayList<>();
            row.add(1);
            for (int i = 1; i <= rowIndex; i++) {
                row.add(0);
                for (int j = i; j > 0; j++) {
                    row.set(j, row.get(j) + row.get(j - 1));
                }
            }
            return row;
        }

    }

    /**
     * 方法三、线性递推
     */
    @Data
    @AllArgsConstructor
    private static class PascalsTriangle3 implements PascalsTriangle {

        private int n;

        @Override
        public List<Integer> getRow(int rowIndex) {
            List<Integer> row = new ArrayList<>();
            row.add(1);
            for (int i = 1; i <= rowIndex; i++) {
                row.add((int) ((long) row.get(i - 1) * (rowIndex - i + 1) / i));
            }
            return row;
        }

    }

    public static void main(String[] args) {
//        printPascalsTriangle1(10);
//        printPascalsTriangle2(10);
//        printPascalsTriangle3(10);

        Scanner scanner = new Scanner(System.in);
        PascalsTriangle pt;
        while (scanner.hasNext()) {
            int n;
            try {
                n = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                continue;
            }
            pt = new PascalsTriangle3(n);
            List<Integer> rowList = pt.getRow(n);
            System.out.println(rowList);
        }
    }

}
