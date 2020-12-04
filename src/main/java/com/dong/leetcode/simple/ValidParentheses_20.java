package com.dong.leetcode.simple;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

/**
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 *
 * 有效字符串需满足：
 *      左括号必须用相同类型的右括号闭合。
 *      左括号必须以正确的顺序闭合。
 *      注意空字符串可被认为是有效字符串。
 *
 * 示例 1:
 *      输入: "()"
 *      输出: true
 *
 * 示例 2:
 *      输入: "()[]{}"
 *      输出: true
 *
 * 示例 3:
 *      输入: "(]"
 *      输出: false
 *
 * 示例 4:
 *      输入: "([)]"
 *      输出: false
 *
 * 示例 5:
 *      输入: "{[]}"
 *      输出: true
 */
public class ValidParentheses_20 {

    private static final Map<Character, Character> parenthesesMap = new HashMap<>(3);

    private static final ArrayDeque<Character> queue = new ArrayDeque<>();

    static {
        parenthesesMap.put(')', '(');
        parenthesesMap.put(']', '[');
        parenthesesMap.put('}', '{');
    }

    public static void main(String[] args) {
        System.out.println(isValid_v5(")(){}"));
    }

    public static boolean isValid(String s) {
        if (s.length() == 0) {
            return true;
        }
        if (s.length() % 2 != 0) {
            return false;
        }
        final char[] chars = s.toCharArray();
        int leftParentheses = 0;
        int rightParentheses = 0;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '(' || c == '[' || c == '{') {
                queue.addFirst(c);
                leftParentheses++;
            } else {
                if (Objects.equals(parenthesesMap.get(c), queue.peek())) {
                    queue.poll();
                }
                rightParentheses++;
            }
        }
        return queue.size() == 0 && leftParentheses == rightParentheses;
    }

    public static boolean isValid_v2(String s) {
        if (s.length() % 2 == 1) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char theChar = s.charAt(i);
            if (theChar == '(' || theChar == '{' || theChar == '[') {
                stack.push(theChar);
            } else {
                // 栈中已无左括号，此时字符为右括号，无法匹配。
                if (stack.empty()) {
                    return false;
                }
                char preChar = stack.peek();
                if ((preChar == '{' && theChar == '}')
                        || (preChar == '(' && theChar == ')')
                        || (preChar == '[' && theChar == ']')) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        return stack.empty();
    }

    public static boolean isValid_v3(String s) {
        int len = s.length();
        char[] arr = new char[s.length()];
        char[] charArr = s.toCharArray();
        char m = charArr[0];
        if (len % 2 != 0) {
            return false;
        } else {
            arr[0] = m;
        }
        int n = 0;
        for (int i = 1; i < len; i++) {
            char t = charArr[i];
            if (n < 0) {
                if (m == ')' || m == '}' || m == ']') {
                    return false;
                }
                arr[++n] = t;
            }
            if ((t == ')' && arr[n] == '(') ||
                    (t == '}' && arr[n] == '{') ||
                    (t == ']' && arr[n] == '[')) {
                arr[n--] = ' ';
                continue;
            }
            arr[++n] = t;
        }
        if (n >= 1) {
            return false;
        }
        return true;
    }

    public static boolean isValid_v4(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(')');
            } else if (c == '[') {
                stack.push(']');
            } else if (c == '{') {
                stack.push('}');
            } else if (stack.isEmpty() || c != stack.pop()) {
                return false;
            }
        }
        return stack.isEmpty();
    }

    public static boolean isValid_v5(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.addFirst(')');
            } else if (c == '[') {
                stack.addFirst(']');
            } else if (c == '{') {
                stack.addFirst('}');
            } else if (stack.isEmpty() || c != stack.poll()) {
                return false;
            }
        }
        return stack.isEmpty();
    }

}
