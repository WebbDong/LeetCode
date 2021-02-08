package com.dong.leetcode.simple.string;

/**
 * 二进制求和
 *      给定两个二进制字符串，返回他们的和（用二进制表示）。
 *      输入为非空字符串且只包含数字 1 和 0。
 *
 * 示例 1:
 *      输入: a = "11", b = "1"
 *      输出: "100"
 *
 * 示例 2:
 *      输入: a = "1010", b = "1011"
 *      输出: "10101"
 */
public class AddBinary_67 {
    public static void main(String[] args) {
        System.out.println(addBinary_v1("1010", "1011"));
        System.out.println(addBinary_v2("1010", "1011"));
        System.out.println(0.0f == -0.0f);
        System.out.println(1.0f / 0.0f);
        System.out.println(1.0f / -0.0f);
        System.out.println(0.0f / 0.0f);
        System.out.println((0.0f / 0.0f) != (0.0f / 0.0f));
    }

    public static String addBinary_v1(String a, String b) {
        char[] aArr;
        char[] bArr;
        if (a.length() >= b.length()) {
            aArr = a.toCharArray();
            bArr = b.toCharArray();
        } else {
            bArr = a.toCharArray();
            aArr = b.toCharArray();
        }

        StringBuilder sb = new StringBuilder();
        int carry = 0; // 是否进位
        for (int i = aArr.length - 1, j = bArr.length - 1; i >= 0; i--) {
            int ca = aArr[i] - '0';
            int cb;
            if (j < 0) {
                cb = 0; // 补0
            } else {
                cb = bArr[j--] - '0';
            }
            int sum = ca + cb + carry;
            if (sum == 3) {
                sb.append(1);
                carry = 1;
            } else if (sum == 2) {
                sb.append(0);
                carry = 1;
            } else if (sum == 1) {
                sb.append(1);
                carry = 0;
            } else {
                sb.append(0);
                carry = 0;
            }
        }
        if (carry == 1) {
            sb.append(1);
        }
        return sb.reverse().toString();
    }

    public static String addBinary_v2(String a, String b) {
        int len1 = a.length(), len2 = b.length();
        StringBuilder sb = new StringBuilder();
        int i = len1 - 1, j = len2 - 1, carry = 0;
        while (i >= 0 || j >= 0) {
            int x = (i >= 0) ? a.charAt(i) - '0' : 0;
            int y = (j >= 0) ? b.charAt(j) - '0' : 0;
            int sum = x + y + carry;
            carry = sum / 2;
            sb.append(sum % 2);
            i--;
            j--;
        }
        if (carry > 0) {
            sb.append(carry);
        }
        return sb.reverse().toString();
    }
}
