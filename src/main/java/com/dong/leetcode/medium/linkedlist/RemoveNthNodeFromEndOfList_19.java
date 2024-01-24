package com.dong.leetcode.medium.linkedlist;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
 * 示例 1：
 *  输入：head = [1,2,3,4,5], n = 2
 *  输出：[1,2,3,5]
 *
 * 示例 2：
 *  输入：head = [1], n = 1
 *  输出：[]
 *
 * 示例 3：
 *  输入：head = [1,2], n = 1
 *  输出：[1]
 *
 * 提示：
 *  链表中结点的数目为 sz
 *  1 <= sz <= 30
 *  0 <= Node.val <= 100
 *  1 <= n <= sz
 */
public class RemoveNthNodeFromEndOfList_19 {

    private static class ListNode {
        private int val;
        private ListNode next;
        public ListNode() {}
        public ListNode(int val) {
            this.val = val;
        }
        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        ListNode head = makeLinkedList(arr);
        head = removeNthFromEnd2(head, 1);
        printLinkedList(head);
        head = removeNthFromEnd2(head, 4);
        printLinkedList(head);
        head = removeNthFromEnd2(head, 2);
        printLinkedList(head);
        head = removeNthFromEnd3(head, 1);
        printLinkedList(head);
    }

    public static ListNode makeLinkedList(int[] arr) {
        ListNode head = new ListNode(arr[0]);
        ListNode temp = head;
        for (int i = 1; i < arr.length; i++) {
            ListNode newNode = new ListNode(arr[i]);
            temp.next = newNode;
            temp = newNode;
        }
        return head;
    }

    private static void printLinkedList(ListNode head) {
        ListNode temp = head;
        while (temp != null) {
            System.out.print(temp.val + ", ");
            temp = temp.next;
        }
        System.out.println();
    }

    /**
     * 1.统计链表长度方式
     */
    public static ListNode removeNthFromEnd1(ListNode head, int n) {
        int size = 0;
        ListNode temp = head;
        while (temp != null) {
            size++;
            temp = temp.next;
        }

        if (size == n) {
            return head.next;
        }

        temp = head;
        for (int i = 0, len = size - n - 1; i < len; i++) {
            temp = temp.next;
        }
        temp.next = temp.next.next;
        return head;
    }

    /**
     * 2.栈
     */
    public static ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        Deque<ListNode> stack = new LinkedList<>();
        ListNode temp = dummy;
        while (temp != null) {
            stack.push(temp);
            temp = temp.next;
        }
        for (int i = 0; i < n; i++) {
            stack.pop();
        }

        ListNode delNode = stack.pop();
        delNode.next = delNode.next.next;
        return dummy.next;
    }

    /**
     * 双指针
     */
    public static ListNode removeNthFromEnd3(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        ListNode first = head;
        ListNode second = dummy;
        for (int i = 0; i < n; i++) {
            first = first.next;
        }
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;
        return dummy.next;
    }

}
