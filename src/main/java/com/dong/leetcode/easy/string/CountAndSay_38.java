package com.dong.leetcode.easy.string;

/**
 * 外观数列
 *
 * 给定一个正整数 n ，输出外观数列的第 n 项。「外观数列」是一个整数序列，从数字 1 开始，序列中的每一项都是对前一项的描述。
 *
 * 你可以将其视作是由递归公式定义的数字字符串序列：
 *      countAndSay(1) = "1"
 *      countAndSay(n) 是对 countAndSay(n-1) 的描述，然后转换成另一个数字字符串。
 *
 * 前五项如下：
 *      1.     1
 *      2.     11
 *      3.     21
 *      4.     1211
 *      5.     111221
 *      第一项是数字 1
 *      描述前一项，这个数是 1 即 “ 一 个 1 ”，记作 "11"
 *      描述前一项，这个数是 11 即 “ 二 个 1 ” ，记作 "21"
 *      描述前一项，这个数是 21 即 “ 一 个 2 + 一 个 1 ” ，记作 "1211"
 *      描述前一项，这个数是 1211 即 “ 一 个 1 + 一 个 2 + 二 个 1 ” ，记作 "111221"
 *
 * 要 描述 一个数字字符串，首先要将字符串分割为 最小 数量的组，每个组都由连续的最多 相同字符 组成。然后对于每个组，先描述字符的数量，
 * 然后描述字符，形成一个描述组。要将描述转换为数字字符串，先将每组中的字符数量用数字替换，再将所有描述组连接起来。
 *
 * 示例 1：
 *      输入：n = 1
 *      输出："1"
 *      解释：这是一个基本样例。
 *
 * 示例 2：
 *      输入：n = 4
 *      输出："1211"
 *      解释：
 *          countAndSay(1) = "1"
 *          countAndSay(2) = 读 "1" = 一 个 1 = "11"
 *          countAndSay(3) = 读 "11" = 二 个 1 = "21"
 *          countAndSay(4) = 读 "21" = 一 个 2 + 一 个 1 = "12" + "11" = "1211"
 *
 * 提示：
 *  1 <= n <= 30
 */
public class CountAndSay_38 {

    private static void printCountAndSay(int n) {
        if (n < 1) {
            return;
        }

        StringBuilder temp = new StringBuilder();
        temp.append(1);
        System.out.println(1);
        for (int i = 1; i < n; i++) {
            StringBuilder row = new StringBuilder();
            int s = 0;
            for (int j = 1; j < temp.length(); j++) {
                if (temp.charAt(s) != temp.charAt(j)) {
                    row.append(j - s).append(temp.charAt(s));
                    s = j;
                }
            }
            row.append(temp.length() - s).append(temp.charAt(s));
            System.out.println(row);
            temp = row;
        }
    }

    /**
     * 方法一、迭代
     * @param n
     * @return
     */
    private static String countAndSay1(int n) {
        StringBuilder result = new StringBuilder();
        result.append(1);
        for (int i = 1; i < n; i++) {
            // 记录当前行的字符串
            StringBuilder row = new StringBuilder();
            // 记录每个数字的开始索引
            int s = 0;
            int resultLen = result.length();
            for (int j = 1; j < resultLen; j++) {
                // 当数字发生改变时执行
                if (result.charAt(j) != result.charAt(s)) {
                    row.append(j - s).append(result.charAt(s));
                    s = j;
                }
            }
            // 处理最后一个数字
            row.append(resultLen - s).append(result.charAt(s));
            result = row;
        }
        return result.toString();
    }

    /**
     * 方法二、递归
     * @param n
     * @return
     */
    private static String countAndSay2(int n) {
        // 递归终止条件
        if (n == 1) {
            return "1";
        }
        // 获取到上一层的字符串
        String row = countAndSay2(n - 1);
        StringBuilder result = new StringBuilder();
        int s = 0;
        for (int i = 1; i < row.length(); i++) {
            // 当数字发生改变时执行
            if (row.charAt(i) != row.charAt(s)) {
                result.append(i - s).append(row.charAt(s));
                s = i;
            }
        }
        result.append(row.length() - s).append(row.charAt(s));
        return result.toString();
    }

    public static void main(String[] args) {
//        printCountAndSay(5);
        System.out.println(countAndSay2(10));
    }

}
