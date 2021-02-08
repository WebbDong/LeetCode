package com.dong.leetcode.simple.math;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 各位相加
 *      给定一个非负整数 num，反复将各个位上的数字相加，直到结果为一位数。
 *
 * 示例:
 *      输入: 38
 *      输出: 2
 *      解释: 各位相加的过程为：3 + 8 = 11, 1 + 1 = 2。 由于 2 是一位数，所以返回 2。
 *
 * 进阶:
 *      你可以不使用循环或者递归，且在 O(1) 时间复杂度内解决这个问题吗？
 */
public class AddDigits_258 {

    public static void main(String[] args) throws IOException {
        while (true) {
            System.out.print("> ");
            String input = new BufferedReader(new InputStreamReader(System.in, Charsets.UTF_8)).readLine();
            if (!Strings.isNullOrEmpty(input) && input.trim().equalsIgnoreCase("quit")) {
                System.exit(0);
            }
            System.out.println(addDigits(Integer.valueOf(input)));
            System.out.println(addDigits_v2(Integer.valueOf(input)));
        }
    }

    public static int addDigits(int num) {
        if (num < 10) {
            return num;
        } else {
            int sum = 0;
            long diff = 1;
            while (true) {
                int s;
                if (diff != 1) {
                    int d = (int) (num / diff);
                    if (d == 0) {
                        break;
                    }
                    s = d % 10;
                } else {
                    s = num % 10;
                }
                diff *= 10;
                sum += s;
            }

            if (sum < 10) {
                return sum;
            } else {
                return addDigits(sum);
            }
        }
    }

    public static int addDigits_v2(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num = num / 10;
        }
        if (sum > 9) {
            return addDigits_v2(sum);
        }
        return sum;
    }

}
