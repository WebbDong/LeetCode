package com.dong.leetcode.simple.linkedlist;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static class ListNode {

        private int val;

        private ListNode next;

        ListNode() {}

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

    }

    private static void printList(ListNode node) {
        if (node == null) {
            System.out.println("[]");
            return;
        }

        ListNode currentNode = node;
        System.out.print("[");
        while (currentNode != null) {
            System.out.print(currentNode.val);
            currentNode = currentNode.next;
            if (currentNode == null) {
                System.out.print("]");
            } else {
                System.out.print(",");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pattern pattern = Pattern.compile("(\\[(\\d+,)*\\d+\\])+");
        ListNode[] listNodes = new ListNode[2];

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            Matcher matcher = pattern.matcher(line);

            for (int i = 0; matcher.find(); i++) {
                String str = matcher.group();
                str = str.substring(1, str.length() - 1);
                String[] numberStrs = str.split(",");
                ListNode currentNode = null;

                for (int j = 0; j < numberStrs.length; j++) {
                    String numberStr = numberStrs[j];
                    if (currentNode == null) {
                        currentNode = new ListNode(Integer.parseInt(numberStr));
                        listNodes[i] = currentNode;
                    } else {
                        currentNode.next = new ListNode(Integer.parseInt(numberStr));
                        currentNode = currentNode.next;
                    }
                }
            }

            printList(mergeTwoLists3(listNodes[0], listNodes[1]));
            listNodes[0] = null;
            listNodes[1] = null;
        }
    }

    private static ListNode mergeTwoLists1(ListNode l1, ListNode l2) {
        ListNode currentNode1 = l1;
        ListNode currentNode2 = l2;
        ListNode newCurrentNode = null;
        ListNode newHeadNode = null;

        while (currentNode1 != null && currentNode2 != null) {
            int val;
            if (currentNode1.val < currentNode2.val) {
                val = currentNode1.val;
                currentNode1 = currentNode1.next;
            } else {
                val = currentNode2.val;
                currentNode2 = currentNode2.next;
            }

            if (newCurrentNode == null) {
                newCurrentNode = new ListNode(val);
                newHeadNode = newCurrentNode;
            } else {
                newCurrentNode.next = new ListNode(val);
                newCurrentNode = newCurrentNode.next;
            }
        }

        while (currentNode1 != null) {
            if (newCurrentNode == null) {
                newCurrentNode = new ListNode(currentNode1.val);
                newHeadNode = newCurrentNode;
            } else {
                newCurrentNode.next = new ListNode(currentNode1.val);
                newCurrentNode = newCurrentNode.next;
            }
            currentNode1 = currentNode1.next;
        }

        while (currentNode2 != null) {
            if (newCurrentNode == null) {
                newCurrentNode = new ListNode(currentNode2.val);
                newHeadNode = newCurrentNode;
            } else {
                newCurrentNode.next = new ListNode(currentNode2.val);
                newCurrentNode = newCurrentNode.next;
            }
            currentNode2 = currentNode2.next;
        }
        return newHeadNode;
    }

    /**
     * 递归方式
     * @param l1
     * @param l2
     * @return
     */
    private static ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        } else if (l1.val < l2.val) {
            l1.next = mergeTwoLists2(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists2(l1, l2.next);
            return l2;
        }
    }

    /**
     * 迭代方式
     * @param l1
     * @param l2
     * @return
     */
    private static ListNode mergeTwoLists3(ListNode l1, ListNode l2) {
        ListNode preHead = new ListNode(-1);
        ListNode pre = preHead;

        while (l1 != null && l2 != null) {
            if (l2.val < l1.val) {
                pre.next = l2;
                l2 = l2.next;
            } else {
                pre.next = l1;
                l1 = l1.next;
            }
            pre = pre.next;
        }
        pre.next = l1 != null ? l1 : l2;
        return preHead.next;
    }

}
