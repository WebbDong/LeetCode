package com.dong.leetcode.easy.array;

import java.util.Arrays;

/**
 * 面试题 10.01. 合并排序的数组
 *      给定两个排序后的数组 A 和 B，其中 A 的末端有足够的缓冲空间容纳 B。 编写一个方法，将 B 合并入 A 并排序。
 *      初始化 A 和 B 的元素数量分别为 m 和 n。
 *
 *      示例:
 *          输入:
 *              A = [1,2,3,0,0,0], m = 3
 *              B = [2,5,6],       n = 3
 *
 *          输出: [1,2,2,3,5,6]
 *
 *      说明: A.length == n + m
 */
public class SortedMergeLCCI_10_01 {

    public static void main(String[] args) {
        int[] A = {1, 2, 3, 0, 0, 0, 0};
        int[] B = {2, 5, 6, 7};
        merge_v3(A, 3, B, 4);
        System.out.println(Arrays.toString(A));
        System.out.println(Arrays.toString(B));
    }

    /**
     * 直接合并后排序，最直观的方法是先将数组 B 放进数组 A 的尾部，然后直接对整个数组进行排序。
     * @param A
     * @param m
     * @param B
     * @param n
     */
    private static void merge_v1(int[] A, int m, int[] B, int n) {
        for (int i = 0; i < n; i++) {
            A[m + i] = B[i];
        }
        Arrays.sort(A);
    }

    /**
     * 双指针
     * @param A
     * @param m
     * @param B
     * @param n
     */
    private static void merge_v2(int[] A, int m, int[] B, int n) {
        int[] sorted = new int[m + n];
        for (int i = 0, j = 0, z = 0; i < m || j < n; z++) {
            if (i == m) {
                sorted[z] = B[j++];
            } else if (j == n) {
                sorted[z] = A[i++];
            } else if (A[i] <= B[j]) {
                sorted[z] = A[i++];
            } else {
                sorted[z] = B[j++];
            }
        }
        for (int i = 0; i < sorted.length; i++) {
            A[i] = sorted[i];
        }
    }

    /**
     * 逆向双指针
     * @param A
     * @param m
     * @param B
     * @param n
     */
    private static void merge_v3(int[] A, int m, int[] B, int n) {
        for (int i = m - 1, j = n - 1, z = m + n - 1; i >= 0 || j >= 0; z--) {
            if (i == -1) {
                A[z] = B[j--];
            } else if (j == -1) {
                A[z] = A[i--];
            } else if (A[i] > B[j]) {
                A[z] = A[i--];
            } else {
                A[z] = B[j--];
            }
        }
    }

}
