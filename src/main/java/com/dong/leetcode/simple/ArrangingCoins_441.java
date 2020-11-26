package com.dong.leetcode.simple;

/**
 *
 你总共有 n 枚硬币，你需要将它们摆成一个阶梯形状，第 k 行就必须正好有 k 枚硬币。
 给定一个数字 n，找出可形成完整阶梯行的总行数。
 n 是一个非负整数，并且在32位有符号整型的范围内。

 示例 1:

 n = 5

 硬币可排列成以下几行:
 ¤
 ¤ ¤
 ¤ ¤

 因为第三行不完整，所以返回2.

 示例 2:

 n = 8

 硬币可排列成以下几行:
 ¤
 ¤ ¤
 ¤ ¤ ¤
 ¤ ¤

 因为第四行不完整，所以返回3.
 */
public class ArrangingCoins_441 {

    public static void main(String[] args) {
        System.out.println("line = " + arrangeCoins_v3(5));
    }

    public static int arrangeCoins_v1(int n) {
        if (n == 0) {
            return 0;
        }

        int line = 0;
        int fullLineCount = 0;
        while (n > 0) {
            int i = 0;
            for (; i <= line && n > 0; i++, n--) {
//                System.out.print("¤ ");
            }
//            System.out.println();
            if (i == (line + 1)) {
                fullLineCount++;
            }
            line++;
        }
        return fullLineCount;
    }

    // 等差数列
    public static int arrangeCoins_v2(int n) {
        return (int) ((Math.sqrt((long) 8 * n + 1) - 1) / 2);
    }

    public static int arrangeCoins_v3(int n) {
        return (int) (Math.sqrt(0.25 + 2 * (long) n) - 0.5);
    }

}
