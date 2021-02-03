package com.dong.leetcode.simple;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用两个栈实现一个队列。队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，分别完成在队列尾部插入整数和在队列头部删除整数的功能。
 * (若队列中没有元素，deleteHead 操作返回 -1 )
 *
 * 示例 1：
 *  输入：
 *      ["CQueue","appendTail","deleteHead","deleteHead"]
 *      [[],[3],[],[]]
 *  输出：[null,null,3,-1]
 *
 * 示例 2：
 *  输入：
 *      ["CQueue","deleteHead","appendTail","appendTail","deleteHead","deleteHead"]
 *      [[],[],[5],[2],[],[]]
 *  输出：[null,-1,null,null,5,2]
 *
 * 提示：
 *      1 <= values <= 10000
 *      最多会对 appendTail、deleteHead 进行 10000 次调用
 */
public class ImplementQueueWithTwoStacks_offer09 {

    // ---------------------------- 方案一 --------------------------------

    private static Deque<Integer> stack1 = new LinkedList<>();

    private static Deque<Integer> stack2 = new LinkedList<>();

    public static void appendTail1(int value) {
        stack1.push(value);
    }

    public static int deleteHead1() {
        if (stack2.isEmpty()) {
            if (stack1.isEmpty()) {
                return -1;
            }

            while (!stack1.isEmpty()) {
                stack2.push(stack1.poll());
            }
        }
        return stack2.poll();
    }

    // ---------------------------- 方案二、此方案不符合题目描述的使用两个栈实现 --------------------------------

    private static int[] arr = new int[10000];

    /**
     * 写入指针
     */
    private static int writeIndex = 0;

    /**
     * 读取指针
     */
    private static int readIndex = 0;

    public static void appendTail2(int value) {
        // 插入则向s1进行插入
        arr[writeIndex++] = value;
    }

    public static int deleteHead2() {
        if (readIndex == writeIndex) {
            return -1;
        }
        return arr[readIndex++];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final Pattern pattern1 = Pattern.compile("[a-zA-Z]+");
        final Pattern pattern2 = Pattern.compile("\\d+");
        List<String> commandList = new ArrayList<>();
        List<String> operatorList = new ArrayList<>();
        List<String> resList = new ArrayList<>();
        String line1, line2;
        while (scanner.hasNext()) {
            line1 = scanner.nextLine();
            line2 = scanner.nextLine();
            Matcher matcher1 = pattern1.matcher(line1);
            Matcher matcher2 = pattern2.matcher(line2);
            while (matcher1.find()) {
                commandList.add(matcher1.group());
            }
            while (matcher2.find()) {
                operatorList.add(matcher2.group());
            }

            for (int i = 0, j = 0, size = commandList.size(); i < size; i++) {
                String command = commandList.get(i);
                if ("CQueue".equals(command)) {
                    resList.add("null");
                } else if ("deleteHead".equals(command)) {
                    int res = deleteHead2();
                    resList.add(String.valueOf(res));
                } else if ("appendTail".equals(command)) {
                    appendTail2(Integer.parseInt(operatorList.get(j++)));
                    resList.add("null");
                }
            }

            System.out.println(resList);

            commandList.clear();
            operatorList.clear();
            resList.clear();
        }
    }

}
