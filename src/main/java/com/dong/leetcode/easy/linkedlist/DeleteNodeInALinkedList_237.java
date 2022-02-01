package com.dong.leetcode.easy.linkedlist;

/**
 * 删除链表中的节点
 *
 * 请编写一个函数，用于 删除单链表中某个特定节点 。在设计函数时需要注意，你无法访问链表的头节点 head ，
 * 只能直接访问 要被删除的节点 。题目数据保证需要删除的节点不是末尾节点 。
 *
 * 输入：head = [4,5,1,9], node = 5
 * 输出：[4,1,9]
 * 解释：指定链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9
 * @author: Webb Dong
 * @date: 2021-12-25 1:13 AM
 */
public class DeleteNodeInALinkedList_237 {

    public static void main(String[] args) {
        ListNode first = new ListNode(4);
        ListNode n = first;
        n.next = new ListNode(5);
        n = n.next;
        n.next = new ListNode(1);
        n = n.next;
        n.next = new ListNode(9);

        print(first);
        deleteNode(first.next);
        print(first);
    }

    public static void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

    private static void print(ListNode first) {
        while (first != null) {
            System.out.print(first.val + " -> ");
            first = first.next;
        }
        System.out.println();
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
        }
    }

}
