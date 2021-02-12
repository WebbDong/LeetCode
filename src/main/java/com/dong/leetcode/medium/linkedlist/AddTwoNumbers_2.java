package com.dong.leetcode.medium.linkedlist;

import lombok.Data;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 两数相加
 *
 * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 * 示例 1:
 *      输入：l1 = [2,4,3], l2 = [5,6,4]
 *      输出：[7,0,8]
 *      解释：342 + 465 = 807.
 *
 * 示例 2：
 *      输入：l1 = [0], l2 = [0]
 *      输出：[0]
 *
 * 示例 3：
 *      输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 *      输出：[8,9,9,9,0,0,0,1]
 *
 * 提示：
 *      每个链表中的节点数在范围 [1, 100] 内
 *      0 <= Node.val <= 9
 *      题目数据保证列表表示的数字不含前导零
 */
public class AddTwoNumbers_2 {

    @Data
    public static class ListNode {

        int val;
        ListNode next;

        ListNode() {}

        ListNode(int val) { this.val = val; }

        ListNode(int val, ListNode next) { this.val = val; this.next = next; }

    }

    private interface AddTwoNumbers {

        ListNode addTwoNumbers(ListNode l1, ListNode l2);

    }

    private static class AddTwoNumbers1 implements AddTwoNumbers {

        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode resultHead = null;
            ListNode current = null;
            // 进位值
            int carry = 0;
            while (l1 != null && l2 != null) {
                int sum = l1.val + l2.val + carry;
                int newVal = sum % 10;
                carry = sum / 10;
                if (resultHead == null) {
                    resultHead = new ListNode(newVal);
                    current = resultHead;
                } else {
                    ListNode newNode = new ListNode(newVal);
                    current.next = newNode;
                    current = newNode;
                }
                l1 = l1.next;
                l2 = l2.next;
            }

            while (l1 != null) {
                int sum = l1.val + 0 + carry;
                int newVal = sum % 10;
                carry = sum / 10;
                ListNode newNode = new ListNode(newVal);
                current.next = newNode;
                current = newNode;
                l1 = l1.next;
            }

            while (l2 != null) {
                int sum = l2.val + 0 + carry;
                int newVal = sum % 10;
                carry = sum / 10;
                ListNode newNode = new ListNode(newVal);
                current.next = newNode;
                current = newNode;
                l2 = l2.next;
            }
            if (carry != 0) {
                current.next = new ListNode(carry);
            }
            return resultHead;
        }

    }

    private static class AddTwoNumbers2 implements AddTwoNumbers {

        @Override
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode head = null;
            ListNode tail = null;
            int carry = 0;
            while (l1 != null || l2 != null) {
                int v1 = l1 != null ? l1.val : 0;
                int v2 = l2 != null ? l2.val : 0;
                int sum = v1 + v2 + carry;
                int newVal = sum % 10;
                carry = sum / 10;
                if (head == null) {
                    head = new ListNode(newVal);
                    tail = head;
                } else {
                    tail.next = new ListNode(newVal);
                    tail = tail.next;
                }

                if (l1 != null) {
                    l1 = l1.next;
                }
                if (l2 != null) {
                    l2 = l2.next;
                }
            }
            if (carry != 0) {
                tail.next = new ListNode(carry);
            }
            return head;
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pattern pattern = Pattern.compile("(\\[(\\d+,)*\\d+\\])+");
        AddTwoNumbers atn = new AddTwoNumbers2();
        ListNode[] nodes = new ListNode[2];
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            Matcher matcher = pattern.matcher(line);
            for (int i = 0; matcher.find(); i++) {
                nodes[i] = createListNode(matcher.group());
            }

            print(atn.addTwoNumbers(nodes[0], nodes[1]));
        }
    }

    private static void print(ListNode node) {
        if (node == null) {
            return;
        }
        System.out.print("[");
        while (node != null) {
            System.out.print(node.val + ",");
            node = node.next;
        }
        System.out.print("]");
    }

    private static Pattern numPattern = Pattern.compile("\\d+");

    private static ListNode createListNode(String group) {
        Matcher matcher = numPattern.matcher(group);
        ListNode head = null;
        ListNode current = null;
        while (matcher.find()) {
            if (head == null) {
                current = new ListNode(Integer.parseInt(matcher.group()));
                head = current;
            } else {
                ListNode newNode = new ListNode(Integer.parseInt(matcher.group()));
                current.next = newNode;
                current = newNode;
            }
        }
        return head;
    }

}
