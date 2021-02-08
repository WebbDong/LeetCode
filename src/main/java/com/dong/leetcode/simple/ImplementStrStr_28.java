package com.dong.leetcode.simple;

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
 * 说明:
 * 当 needle 是空字符串时，我们应当返回什么值呢？这是一个在面试中很好的问题。
 *
 * 对于本题而言，当 needle 是空字符串时我们应当返回 0 。这与 C 语言的 strstr() 以及 Java的 indexOf() 定义相符。
 *
 * haystack = "mississippi", needle = "issi"
 * haystack = "mississippi", needle = "issipi"
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
            System.out.println(strStr1(strs[0], strs[1]));
        }
    }

    /**
     * 自己想到的方式
     * @param haystack
     * @param needle
     * @return
     */
    public static int strStr1(String haystack, String needle) {
        if (haystack == null || needle == null) {
            return -1;
        }
        if (needle.length() > haystack.length()) {
            return -1;
        }
        if ("".equals(needle)) {
            return 0;
        }

        for (int i = 0, len1 = haystack.length(), len2 = needle.length(); i < len1; i++) {
            if (haystack.charAt(i) == needle.charAt(0)) {
                int n = 1;
                for (int j = i + 1; j < len1 && n < len2; j++, n++) {
                    if (haystack.charAt(j) != needle.charAt(n)) {
                        break;
                    }
                }
                if (n == len2) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static int strStr2(String haystack, String needle) {
        final int hsLen = haystack.length();
        final int nLen = needle.length();
        return -1;
    }

}
