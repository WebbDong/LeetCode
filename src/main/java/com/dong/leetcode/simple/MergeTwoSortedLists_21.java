package com.dong.leetcode.simple;

import java.util.Scanner;

/**
 * 合并两个有序链表
 *      将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 *
 * 示例 1:
 *      输入：l1 = [1,2,4], l2 = [1,3,4]
 *      输出：[1,1,2,3,4,4]
 *
 * 示例 2:
 *      输入：l1 = [], l2 = []
 *      输出：[]
 *
 * 示例 3:
 *      输入：l1 = [], l2 = [0]
 *      输出：[0]
 *
 * 提示:
 *      1、两个链表的节点数目范围是 [0, 50]
 *      2、-100 <= Node.val <= 100
 *      3、l1 和 l2 均按 非递减顺序 排列
 */
public class MergeTwoSortedLists_21 {

    public class ListNode {

        int val;

        ListNode next;

        ListNode() {}

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        return null;
    }

}
