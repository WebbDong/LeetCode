package com.dong.leetcode.simple;

/**
 * 回文数
 *      判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 *
 * 示例 1:
 *      输入: 121
 *      输出: true
 *
 * 示例 2:
 *      输入: -121
 *      输出: false
 *      解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 *
 * 示例 3:
 *      输入: 10
 *      输出: false
 *      解释: 从右向左读, 为 01 。因此它不是一个回文数。
 *
 * 进阶:
 *      你能不将整数转为字符串来解决这个问题吗？
 */
public class PalindromeNumber_9 {

    public static void main(String[] args) {
        System.out.println(isPalindrome_v3(-121));
    }

    public static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        String str = String.valueOf(x);
        final char[] chars = str.toCharArray();
        for (int i = 0, j = chars.length - 1, len = chars.length / 2; i < len; i++, j--) {
            if (chars[i] != chars[j]) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPalindrome_v2(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        int rev = 0;
        int lastDigit;
        while (x != 0 && x > rev) {
            lastDigit = x % 10;
            rev = rev * 10 + lastDigit;
            x /= 10;
        }
        return rev == x || rev / 10 == x;
    }

    // 将x反转，然后判断和之前的数是否相等
    public static boolean isPalindrome_v3(int x) {
        if (x < 0) {
            return false;
        }
        int num = x;
        int tmp = 0;
        int remainder;
        while (num != 0) {
            remainder = num % 10;
            num /= 10;
            tmp = tmp * 10 + remainder;
        }
        return tmp == x;
    }

}
