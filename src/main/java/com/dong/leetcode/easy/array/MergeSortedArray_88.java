package com.dong.leetcode.easy.array;

import java.util.Arrays;

/**
 * 合并两个有序数组
 *
 * 给你两个有序整数数组 nums1 和 nums2，请你将 nums2 合并到 nums1 中，使 nums1 成为一个有序数组。
 * 初始化 nums1 和 nums2 的元素数量分别为 m 和 n 。你可以假设 nums1 的空间大小等于 m + n，这样它就有足够的空间保存来自 nums2 的元素。
 *
 * 示例 1：
 *      输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
 *      输出：[1,2,2,3,5,6]
 *
 * 示例 2：
 *      输入：nums1 = [1], m = 1, nums2 = [], n = 0
 *      输出：[1]
 *
 * 提示：
 *      nums1.length == m + n
 *      nums2.length == n
 *      0 <= m, n <= 200
 *      1 <= m + n <= 200
 *      -109 <= nums1[i], nums2[i] <= 109
 *
 */
public class MergeSortedArray_88 {

    private static void merge(int[] nums1, int m, int[] nums2, int n) {
        for (int i = m - 1, j = n - 1, z = m + n - 1; i >= 0 || j >= 0; z--) {
            if (i == -1) {
                nums1[z] = nums2[j--];
            } else if (j == -1) {
                nums1[z] = nums1[i--];
            } else if (nums1[i] > nums2[j]) {
                nums1[z] = nums1[i--];
            } else {
                nums1[z] = nums2[j--];
            }
        }
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int[] nums2 = {2, 5, 6};
        merge(nums1, 3, nums2, 3);
        System.out.println(Arrays.toString(nums1));
        System.out.println(Arrays.toString(nums2));
    }

}
