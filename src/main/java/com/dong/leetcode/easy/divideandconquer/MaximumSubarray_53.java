package com.dong.leetcode.easy.divideandconquer;

/**
 * 最大子序和
 *
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 *
 * 示例 1：
 *      输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 *      输出：6
 *      解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
 *
 * 示例 2：
 *      输入：nums = [1]
 *      输出：1
 *
 * 示例 3：
 *      输入：nums = [0]
 *      输出：0
 *
 * 示例 4：
 *      输入：nums = [-1]
 *      输出：-1
 *
 * 示例 5：
 *      输入：nums = [-100000]
 *      输出：-100000
 *
 * 提示：
 *      1 <= nums.length <= 3 * 104
 *      -10^5 <= nums[i] <= 10^5
 *
 * 进阶：如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的 分治法 求解。
 */
public class MaximumSubarray_53 {

    public static void main(String[] args) {
//        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int[] nums = {-2, 1};
        System.out.println(maxSubArray1(nums));
    }

    private static int maxSubArray1(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }

        Integer max = null;
        for (int i = 0; i < nums.length; i++) {
            int sum = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                sum += nums[j];
                if (max == null || sum > max) {
                    max = sum;
                }
            }
        }
        return max;
    }

}
