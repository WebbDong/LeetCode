package com.dong.leetcode.easy.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
 *
 * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
 *
 * 你可以假设除了整数 0 之外，这个整数不会以零开头。
 *
 *
 *
 * 示例 1：
 *
 * 输入：digits = [1,2,3]
 * 输出：[1,2,4]
 * 解释：输入数组表示数字 123。
 * 示例 2：
 *
 * 输入：digits = [4,3,2,1]
 * 输出：[4,3,2,2]
 * 解释：输入数组表示数字 4321。
 * 示例 3：
 *
 * 输入：digits = [0]
 * 输出：[1]
 *
 *
 * 提示：
 *
 * 1 <= digits.length <= 100
 * 0 <= digits[i] <= 9
 */
public class PlusOne_66 {

    public static void main(String[] args) {
        int[] digits = {9, 9, 9};
        System.out.println(Arrays.toString(plusOne3(digits)));
    }

    public static int[] plusOne1(int[] digits) {
        List<Integer> newDigits = new ArrayList<>();
        boolean isCarry = true;
        for (int i = digits.length - 1; i >= 0; i--) {
            int d = digits[i];
            if (isCarry) {
                if (d + 1 == 10) {
                    newDigits.add(0);
                    if (i == 0) {
                        newDigits.add(1);
                    }
                } else {
                    newDigits.add(d + 1);
                    isCarry = false;
                }
            } else {
                newDigits.add(d);
            }
        }
        Collections.reverse(newDigits);
        int[] arr = new int[newDigits.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = newDigits.get(i);
        }
        return arr;
    }

    /**
     * 找出最长的后缀 9
     * 当我们对数组 digits 加一时，我们只需要关注 digits 的末尾出现了多少个 9 即可。我们可以考虑如下的三种情况：
     *  1.如果 digits 的末尾没有 9，例如 [1,2,3]，那么我们直接将末尾的数加一，得到 [1,2,4] 并返回；
     *  2.如果 digits 的末尾有若干个 9，例如 [1,2,3,9,9]，那么我们只需要找出从末尾开始的第一个不为 9 的元素，即 3，将该元素加一，
     *      得到 [1,2,4,9,9]。随后将末尾的 9 全部置零，得到 [1,2,4,0,0] 并返回。
     *  3.如果 digits 的所有元素都是 9，例如 [9,9,9,9,9]，那么答案为 [1,0,0,0,0,0]。
     *      我们只需要构造一个长度比 digits 多 1 的新数组，将首元素置为 1，其余元素置为 0 即可。
     *
     * 只需要对数组 digits 进行一次逆序遍历，找出第一个不为 9 的元素，将其加一并将后续所有元素置零即可。
     * 如果 digits 中所有的元素均为 9，那么对应着「思路」部分的第三种情况，我们需要返回一个新的数组。
     */
    public static int[] plusOne2(int[] digits) {
        int n = digits.length;
        for (int i = n - 1; i >= 0; --i) {
            if (digits[i] != 9) {
                digits[i]++;
                for (int j = i + 1; j < n; ++j) {
                    digits[j] = 0;
                }
                return digits;
            }
        }

        // digits 中所有的元素均为 9
        int[] newDigits = new int[n + 1];
        newDigits[0] = 1;
        return newDigits;
    }

    public static int[] plusOne3(int[] digits) {
        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] == 9) {
                digits[i] = 0;
            } else {
                digits[i] += 1;
                return digits;
            }
        }
        // 如果所有位都是进位，则长度+1，第一个元素为1，其余都为0
        digits = new int[digits.length + 1];
        digits[0] = 1;
        return digits;
    }

}
