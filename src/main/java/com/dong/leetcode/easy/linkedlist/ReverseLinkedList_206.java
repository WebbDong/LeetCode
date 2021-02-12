package com.dong.leetcode.easy.linkedlist;

/**
 * 反转链表
 *
 * 反转一个单链表。
 *      输入: 1->2->3->4->5->NULL
 *      输出: 5->4->3->2->1->NULL
 *
 * 进阶:
 *      你可以迭代或递归地反转链表。你能否用两种方法解决这道题？
 *
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class ReverseLinkedList_206 {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode node1 = new ListNode(2);
        ListNode node2 = new ListNode(3);
        ListNode node3 = new ListNode(4);
        ListNode node4 = new ListNode(5);
        head.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        printLinkedList(head);
        printLinkedList(reverseList2(head));
    }

    /**
     * 迭代
     * @param head
     * @return
     */
    public static ListNode reverseList1(ListNode head) {
        // 1->2->3->4->5->NULL
        // 遍历的当前节点
        ListNode current = head;
        // 遍历的下一个节点
        ListNode next;
        // 新头节点
        ListNode newHead = null;
        while (current != null) {
            next = current.next;
            current.next = newHead;
            newHead = current;
            current = next;
        }
        return newHead;
    }

    /**
     * 递归
     * @param head
     * @return
     */
    public static ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode newHead = reverseList2(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    public static void printLinkedList(ListNode head) {
        if (head == null) {
            return;
        }

        ListNode node = head;
        do {
            System.out.print(node.val + " ");
            node = node.next;
        } while (node != null);
        System.out.println();
    }

}
