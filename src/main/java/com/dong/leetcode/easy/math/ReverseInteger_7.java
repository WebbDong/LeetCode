package com.dong.leetcode.easy.math;

/**
 * 整数反转
 *
 * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 *      输入: 123
 *      输出: 321
 *
 *      假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−231,  231 − 1]。
 *      请根据这个假设，如果反转后整数溢出那么就返回 0。
 */
public class ReverseInteger_7 {

    public static void main(String[] args) {
        final int newNum = reverse_v2(-123);
        System.out.println("newNum = " + newNum);
    }

    public static int reverse_v1(int x) {
        StringBuilder sb = new StringBuilder();
        if (x < 0) {
            sb.append("-");
        }
        x = Math.abs(x);
        do {
            sb.append(x % 10);
        } while ((x /= 10) != 0);
        try {
            return Integer.parseInt(sb.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static int reverse_v2(int x) {
        boolean isNegative = (x < 0);
        if (isNegative) {
            x = -x;
        }
        int res = 0;
        while (x > 0) {
            int pop = x % 10;
            if (res > Integer.MAX_VALUE / 10 || (res == Integer.MAX_VALUE && pop > 7)) {
                return 0;
            }
            int temp = res * 10 + pop;
            if (temp < res) {
                return 0;
            }
            res = temp;
            x /= 10;
        }
        return isNegative ? -res : res;
    }

}
