package com.dong.leetcode.easy.array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 找到所有数组中消失的数字
 *
 * 给定一个范围在  1 ≤ a[i] ≤ n ( n = 数组大小 ) 的 整型数组，数组中的元素一些出现了两次，另一些只出现一次。
 * 找到所有在 [1, n] 范围之间没有出现在数组中的数字。
 *
 * 您能在不使用额外空间且时间复杂度为O(n)的情况下完成这个任务吗? 你可以假定返回的数组不算在额外空间内。
 *
 * 示例:
 *      输入: [4,3,2,7,8,2,3,1]
 *      输出: [5,6]
 *
 *      输入: [1,1]
 *      输出: [2]
 */
public class FindAllNumbersDisappearedInAnArray_448 {

    /**
     * 方法一、LeetCode 超时
     * @param nums
     * @return
     */
    private static List<Integer> findDisappearedNumbers1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return Collections.EMPTY_LIST;
        }

        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= nums.length; i++) {
            if (!isInArr(nums, i)) {
                list.add(i);
            }
        }
        return list;
    }

    private static boolean isInArr(int[] nums, int n) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == n) {
                return true;
            }
        }
        return false;
    }

    /**
     * 方法二、原地修改
     * 用一个哈希表记录数组 nums 中的数字，由于数字范围均在 [1,n] 中，记录数字后我们再利用哈希表检查 [1,n]
     * 中的每一个数是否出现，从而找到缺失的数字。
     *
     * 遍历 nums，每遇到一个数 xx，就让 nums[x−1] 增加 nn。由于 nums 中所有数均在 [1,n] 中，增加以后，这些数必然大于 n。
     * 最后我们遍历 nums，若 nums[i] 未大于 n，就说明没有遇到过数 i+1。这样我们就找到了缺失的数字。
     * @param nums
     * @return
     */
    private static List<Integer> findDisappearedNumbers2(int[] nums) {
        final int len = nums.length;
        for (int i = 0; i < len; i++) {
            int x = (nums[i] - 1) % len;
            nums[x] += len;
        }
        List<Integer> ret = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            if (nums[i] <= len) {
                ret.add(i + 1);
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        int[] nums = new int[] {4, 3, 2, 7, 8, 2, 3, 1};
//        int[] nums = new int[] {1, 1};
        List<Integer> disappearedNumbers = findDisappearedNumbers2(nums);
        System.out.println(disappearedNumbers);
    }

}
