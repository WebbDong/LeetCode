package com.dong.leetcode.easy.string;

/**
 * 给你一个字符串 s，由若干单词组成，单词前后用一些空格字符隔开。返回字符串中 最后一个 单词的长度。
 *
 * 单词 是指仅由字母组成、不包含任何空格字符的最大子字符串。
 *
 *
 *
 * 示例 1：
 *
 * 输入：s = "Hello World"
 * 输出：5
 * 解释：最后一个单词是“World”，长度为5。
 * 示例 2：
 *
 * 输入：s = "   fly me   to   the moon  "
 * 输出：4
 * 解释：最后一个单词是“moon”，长度为4。
 * 示例 3：
 *
 * 输入：s = "luffy is still joyboy"
 * 输出：6
 * 解释：最后一个单词是长度为6的“joyboy”。
 *
 *
 * 提示：
 * 1 <= s.length <= 104
 * s 仅有英文字母和空格 ' ' 组成
 * s 中至少存在一个单词
 */
public class LengthOfLastWord_58 {

    public static void main(String[] args) {
        String s = "Hello World";
        System.out.println(lengthOfLastWord2(s));

        s = "   fly me   to   the moon  ";
        System.out.println(lengthOfLastWord2(s));

        s = "luffy is still joyboy";
        System.out.println(lengthOfLastWord2(s));
    }

    public static int lengthOfLastWord1(String s) {
        char[] chars = s.toCharArray();
        int len = 0;
        boolean isFirstFindLetter = false;
        for (int i = chars.length - 1; i >= 0; i--) {
            char c = chars[i];
            if (isFirstFindLetter && c == ' ') {
                break;
            } else if (c == ' ') {
                continue;
            }
            len++;
            isFirstFindLetter = true;
        }
        return len;
    }

    public static int lengthOfLastWord2(String s) {
        int len = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (len == 0 && c == ' ') {
                continue;
            } else if (c == ' ') {
                break;
            }
            len++;
        }
        return len;
    }

}
