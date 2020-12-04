package com.dong.leetcode.simple;

/**
 * 717. 1比特与2比特字符
 *
 * 有两种特殊字符。第一种字符可以用一比特0来表示。第二种字符可以用两比特(10 或 11)来表示。
 * 现给一个由若干比特组成的字符串。问最后一个字符是否必定为一个一比特字符。给定的字符串总是由0结束。
 *
 * 示例 1:
 *      输入: bits = [1, 0, 0]
 *      输出: True
 * 解释:
 *      唯一的编码方式是一个两比特字符和一个一比特字符。所以最后一个字符是一比特字符。
 *
 * 示例 2:
 *      输入: bits = [1, 1, 1, 0]
 *      输出: False
 * 解释:
 *      唯一的编码方式是两比特字符和两比特字符。所以最后一个字符不是一比特字符。
 *
 * 注意:
 *      1 <= len(bits) <= 1000.
 *      bits[i] 总是0 或 1.
 */
public class OneBitAndTwoBitCharacters_717 {
    public static void main(String[] args) {
//        int[] bits = {
//                1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0
//        };
        int[] bits = {
                0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0
        };
//        int[] bits = {
//                1, 1, 1, 0
//        };
//        int[] bits = {
//                1, 1, 1, 1, 0
//        };
//        int[] bits = {
//                0
//        };
        System.out.println(isOneBitCharacter_v1(bits));
        System.out.println(isOneBitCharacter_v2(bits));
    }

    public static boolean isOneBitCharacter_v1(int[] bits) {
        if (bits == null || bits.length == 0) {
            return false;
        }

        if (bits.length == 1) {
            return bits[0] == 0;
        }
        if (bits[bits.length - 1] == 1) {
            return false;
        }

        boolean isOnBit = false;
        for (int i = 1, preBit = bits[0]; i < bits.length; i++) {
            if (preBit == 1) {
                isOnBit = false;
                i++;
                if (i < bits.length) {
                    preBit = bits[i];
                }
                if (i == bits.length - 1 && preBit == 0) {
                    isOnBit = true;
                }
            } else {
                isOnBit = true;
                preBit = bits[i];
            }
        }
        return isOnBit;
    }

    public static boolean isOneBitCharacter_v2(int[] bits) {
        if (bits == null || bits.length == 0) {
            return false;
        }
        if (bits[bits.length - 1] == 1) {
            return false;
        }
        if (bits.length == 1) {
            return bits[0] == 0;
        }
        if (bits[bits.length - 2] == 0) {
            return true;
        }

        int i = 0;
        for (; i < bits.length - 1; i++) {
            if (bits[i] == 0) {
                continue;
            } else if (bits[i] == 1) {
                i++;
            }
        }
        System.out.println("i=" + i + ", bits.length=" + bits.length);
        if (i > bits.length - 1) { // 当末尾非1比特时，i会多加一次
            return false;
        }
        return true;
    }
}
