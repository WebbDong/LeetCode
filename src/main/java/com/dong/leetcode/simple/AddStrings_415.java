package com.dong.leetcode.simple;

/**
 * 给定两个字符串形式的非负整数 num1 和num2 ，计算它们的和。
 * <p>
 * 注意：
 * <p>
 * num1 和num2 的长度都小于 5100.
 * num1 和num2 都只包含数字 0-9.
 * num1 和num2 都不包含任何前导零。
 * 你不能使用任何內建 BigInteger 库， 也不能直接将输入的字符串转换为整数形式。
 */
public class AddStrings_415 {

    public static void main(String[] args) {
        final String num1 = "6913259244";
        final String num2 = "71103343";
        System.out.println(addStrings(num1, num2));
        System.out.println(addStrings_v2(num1, num2));
    }

    public static String addStrings(String num1, String num2) {
        String longNum;
        String shortNum;
        if (num1.length() > num2.length()) {
            longNum = num1;
            shortNum = num2;
        } else {
            longNum = num2;
            shortNum = num1;
        }

        StringBuilder sb = new StringBuilder();
        int carry = 0;
        for (int i = longNum.length() - 1, j = shortNum.length() - 1; i >= 0; i--, j--) {
            int n1 = longNum.charAt(i) - '0';
            int n2;

            if (j < 0) {
                n2 = 0;
            } else {
                n2 = shortNum.charAt(j) - '0';
            }

            int sum = n1 + n2 + carry;
            sb.append(sum % 10);
            carry = sum / 10;
        }
        if (carry != 0) {
            sb.append(carry);
        }
        return sb.reverse().toString();
    }

    public static String addStrings_v2(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        for (int i = num1.length() - 1, j = num2.length() - 1, carry = 0;
             i >= 0 || j >= 0 || carry > 0;
             i--, j--) {
            int x = i < 0 ? 0 : num1.charAt(i) - '0';
            int y = j < 0 ? 0 : num2.charAt(j) - '0';
            int sum = x + y + carry;
            sb.append(sum % 10);
            carry = sum / 10;
        }
        return sb.reverse().toString();
    }

}
