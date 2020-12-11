package com.dong.leetcode.simple;

/**
 * 最长公共前缀
 *      编写一个函数来查找字符串数组中的最长公共前缀。
 *      如果不存在公共前缀，返回空字符串 ""。
 *
 *      示例 1:
 *          输入: ["flower","flow","flight"]
 *          输出: "fl"
 *
 *      示例 2:
 *          输入: ["dog","racecar","car"]
 *          输出: ""
 *          解释: 输入不存在公共前缀。
 *
 *      说明: 所有输入只包含小写字母 a-z 。
 */
public class LongestCommonPrefix_14 {

    public static void main(String[] args) {
        String[] strs = {"flower", "fl", "flow", "flight"};
        System.out.println(longestCommonPrefix_v4(strs));
    }

    /**
     * 横向扫描法
     * @param strs
     * @return
     */
    private static String longestCommonPrefix_v1(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        String prefix = strs[0];
        int count = strs.length;
        for (int i = 1; i < count; i++) {
            prefix = longestCommonPrefix_v1(prefix, strs[i]);
            if (prefix.length() == 0) {
                break;
            }
        }
        return prefix;
    }

    private static String longestCommonPrefix_v1(String str1, String str2) {
        int length = Math.min(str1.length(), str2.length());
        int index = 0;
        while (index < length && str1.charAt(index) == str2.charAt(index)) {
            index++;
        }
        return str1.substring(0, index);
    }

    /**
     * 纵向扫描
     * @param strs
     * @return
     */
    private static String longestCommonPrefix_v2(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        int length = strs[0].length();
        int count = strs.length;
        for (int i = 0; i < length; i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < count; j++) {
                if (i == strs[j].length() || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }
        return strs[0];
    }

    /**
     * 分治
     * @param strs
     * @return
     */
    public static String longestCommonPrefix_v3(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        } else {
            return longestCommonPrefix_v3(strs, 0, strs.length - 1);
        }
    }

    public static String longestCommonPrefix_v3(String[] strs, int start, int end) {
        if (start == end) {
            return strs[start];
        } else {
            int mid = (end - start) / 2 + start;
            String lcpLeft = longestCommonPrefix_v3(strs, start, mid);
            String lcpRight = longestCommonPrefix_v3(strs, mid + 1, end);
            return commonPrefix(lcpLeft, lcpRight);
        }
    }

    public static String commonPrefix(String lcpLeft, String lcpRight) {
        int minLength = Math.min(lcpLeft.length(), lcpRight.length());
        for (int i = 0; i < minLength; i++) {
            if (lcpLeft.charAt(i) != lcpRight.charAt(i)) {
                return lcpLeft.substring(0, i);
            }
        }
        return lcpLeft.substring(0, minLength);
    }

    /**
     * 二分查找
     * @param strs
     * @return
     */
    public static String longestCommonPrefix_v4(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        int minLength = Integer.MAX_VALUE;
        for (String str : strs) {
            minLength = Math.min(minLength, str.length());
        }
        int low = 0, high = minLength;
        while (low < high) {
            int mid = (high - low + 1) / 2 + low;
            if (isCommonPrefix(strs, mid)) {
                low = mid;
            } else {
                high = mid - 1;
            }
        }
        return strs[0].substring(0, low);
    }

    public static boolean isCommonPrefix(String[] strs, int length) {
        String str0 = strs[0].substring(0, length);
        int count = strs.length;
        for (int i = 1; i < count; i++) {
            String str = strs[i];
            for (int j = 0; j < length; j++) {
                if (str0.charAt(j) != str.charAt(j)) {
                    return false;
                }
            }
        }
        return true;
    }

}
