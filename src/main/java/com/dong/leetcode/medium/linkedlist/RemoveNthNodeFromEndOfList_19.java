package com.dong.leetcode.medium.linkedlist;

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
        RemoveNthNodeFromEndOfList_19 list = new RemoveNthNodeFromEndOfList_19();
        int[] arr = {1, 2, 3, 4, 5};
        ListNode head = makeList(arr);
        list.removeNthFromEnd(head, 2);
    }

    public static ListNode makeList(int[] arr) {
        ListNode head = new ListNode(arr[0]);
        ListNode temp = head;
        for (int i = 1; i < arr.length; i++) {
            ListNode newNode = new ListNode(arr[i]);
            temp.next = newNode;
            temp = newNode;
        }
        return head;
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        return null;
    }

}
