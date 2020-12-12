package com.dong.leetcode.simple;

import java.util.Arrays;

/**
 * 移除元素
 *      给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
 *      不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
 *      元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
 *
 *      示例 1:
 *          给定 nums = [3,2,2,3], val = 3,
 *          函数应该返回新的长度 2, 并且 nums 中的前两个元素均为 2。
 *          你不需要考虑数组中超出新长度后面的元素。
 *
 *      示例 2:
 *          给定 nums = [0,1,2,2,3,0,4,2], val = 2,
 *          函数应该返回新的长度 5, 并且 nums 中的前五个元素为 0, 1, 3, 0, 4。
 *          注意这五个元素可为任意顺序。你不需要考虑数组中超出新长度后面的元素。
 */
public class RemoveElement_27 {

    public static void main(String[] args) {
//        int[] arr = {3, 2, 2, 3};
//        int[] arr = {0, 1, 2, 2, 3, 0, 4, 2};
        int[] arr = {4, 1, 2, 3, 5};
        int val = 4;
        System.out.println(removeElement_v2(arr, val));
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 双指针
     * @param nums
     * @param val
     * @return
     */
    private static int removeElement_v1(int[] nums, int val) {
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != val) {
                nums[i++] = nums[j];
            }
        }
        return i;
    }

    /**
     * 双指针 —— 当要删除的元素很少时
     *      现在考虑数组包含很少的要删除的元素的情况。例如，num=[1，2，3，5，4]，Val=4num=[1，2，3，5，4]，Val=4。
     *      之前的算法会对前四个元素做不必要的复制操作。另一个例子是 num=[4，1，2，3，5]，Val=4num=[4，1，2，3，5]，Val=4。
     *      似乎没有必要将 [1，2，3，5][1，2，3，5] 这几个元素左移一步，因为问题描述中提到元素的顺序可以更改。
     * @param nums
     * @param val
     * @return
     */
    private static int removeElement_v2(int[] nums, int val) {
        int i = 0;
        int n = nums.length;
        while (i < n) {
            if (nums[i] == val) {
                nums[i] = nums[--n];
            } else {
                i++;
            }
        }
        return n;
    }

}
