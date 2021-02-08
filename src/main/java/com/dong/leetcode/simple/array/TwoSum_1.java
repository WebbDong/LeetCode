package com.dong.leetcode.simple.array;

import java.util.Arrays;

/**
 * 两数之和
 *
 *      给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那两个整数，并返回他们的数组下标。
 *      你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 *
 *      给定 nums = [2, 7, 11, 15], target = 9
 *      因为 nums[0] + nums[1] = 2 + 7 = 9
 *      所以返回 [0, 1]
 */
public class TwoSum_1 {

    public static void main(String[] args) {
//        int[] nums = {2, 7, 11, 15};
        int[] nums = {2, 5, 5, 11};
        int target = 10;
        final int[] indexes = twoSum_v2(nums, target);
        System.out.println(Arrays.toString(indexes));
    }

    public static int[] twoSum_v1(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i == j) {
                    continue;
                }
                if (nums[i] + nums[j] == target) {
                    return new int[] {i, j};
                }
            }
        }
        return null;
    }

    public static int[] twoSum_v2(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j<nums.length; j++) {
                if(nums[i] + nums[j] == target) {
                    return new int[] {i, j};
                }
            }
        }
        return null;
    }

}
