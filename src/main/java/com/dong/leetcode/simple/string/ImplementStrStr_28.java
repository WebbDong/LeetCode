package com.dong.leetcode.simple.string;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 实现 strStr()
 *      给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。
 *      如果不存在，则返回 -1。
 *
 * 示例 1:
 *      输入: haystack = "hello", needle = "ll"
 *      输出: 2
 *
 * 示例 2:
 *      输入: haystack = "aaaaa", needle = "bba"
 *      输出: -1
 *
 * 示例 3:
 *      输入: haystack = "mississippi", needle = "issi"
 *      输出: 1
 *
 * 示例 4:
 *      输入: haystack = "mississippi", needle = "issipi"
 *      输出: -1
 *
 * 示例 5:
 *      输入: haystack = "mississippi", needle = "issip"
 *      输出: 4
 *
 * 示例6:
 *      输入: haystack = "is", needle = "issip"
 *      输出: -1
 *
 * 说明:
 * 当 needle 是空字符串时，我们应当返回什么值呢？这是一个在面试中很好的问题。
 *
 * 对于本题而言，当 needle 是空字符串时我们应当返回 0 。这与 C 语言的 strstr() 以及 Java的 indexOf() 定义相符。
 *
 * haystack = "mississippi", needle = "a"
 */
public class ImplementStrStr_28 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pattern p = Pattern.compile("(\\\"\\w+\\\")+");

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            Matcher matcher = p.matcher(line);
            String[] strs = new String[2];

            for (int i = 0; matcher.find(); i++) {
                strs[i] = matcher.group().replaceAll("\\\"", "");
            }
            System.out.println(strStr4(strs[0], strs[1]));
        }
    }

    /**
     * 双指针
     * @param haystack
     * @param needle
     * @return
     */
    private static int strStr1(String haystack, String needle) {
        if (haystack == null || needle == null) {
            return -1;
        }

        final int nLen = needle.length();
        if (nLen == 0) {
            return 0;
        }

        final int hsLen = haystack.length();
        for (int i = 0, len = hsLen - nLen + 1; i < len; i++) {
            if (haystack.charAt(i) == needle.charAt(0)) {
                int n = 1;
                for (int j = i + 1; n < nLen; j++, n++) {
                    if (haystack.charAt(j) != needle.charAt(n)) {
                        break;
                    }
                }
                if (n == nLen) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 子串逐一比较
     * @param haystack
     * @param needle
     * @return
     */
    private static int strStr2(String haystack, String needle) {
        final int hsLen = haystack.length();
        final int nLen = needle.length();

        for (int i = 0, len = hsLen - nLen + 1; i < len; i++) {
            if (haystack.substring(i, i + nLen).equals(needle)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 双指针
     * @param haystack
     * @param needle
     * @return
     */
    private static int strStr3(String haystack, String needle) {
        final int nLen = needle.length();
        if (nLen == 0) {
            return 0;
        }

        final int hsLen = haystack.length();
        for (int i = 0, len = hsLen - nLen + 1; i < len;) {
            while (i < len && haystack.charAt(i) != needle.charAt(0)) {
                i++;
            }

            int n = 0;
            while (i < hsLen && n < nLen && haystack.charAt(i) == needle.charAt(n)) {
                i++;
                n++;
            }

            if (n == nLen) {
                return i - nLen;
            }

            i = i - n + 1;
        }
        return -1;
    }

    /**
     * Rabin Karp 算法
     * @param haystack
     * @param needle
     * @return
     */
    private static int strStr4(String haystack, String needle) {
        final int nLen = needle.length();
        final int hsLen = haystack.length();
        if (nLen > hsLen) {
            return -1;
        }

        int a = 26;
        long modulus = (long) Math.pow(2, 31);

        long h = 0, refH = 0;
        for (int i = 0; i < nLen; i++) {
            h = (h * a + charToInt(i, haystack)) % modulus;
            refH = (refH * a + charToInt(i, needle)) % modulus;
        }
        if (h == refH) {
            return 0;
        }

        long aL = 1;
        for (int i = 1; i <= nLen; i++) {
            aL = (aL * a) % modulus;
        }

        for (int start = 1; start < hsLen - nLen + 1; start++) {
            h = (h * a - charToInt(start - 1, haystack) * aL
                    + charToInt(start + nLen - 1, haystack)) % modulus;
            if (h == refH) {
                return start;
            }
        }
        return -1;
    }

    private static int charToInt(int idx, String s) {
        return (int) s.charAt(idx) - (int) 'a';
    }

    /**
     * KMP 算法
     * @param haystack
     * @param needle
     * @return
     */
    private static int strStr5(String haystack, String needle) {
        return -1;
    }

}
