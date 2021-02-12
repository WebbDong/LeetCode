package com.dong.leetcode.easy.stack;

/**
 * 使用队列实现栈
 *
 * 使用队列实现栈的下列操作：
 *      push(x) -- 元素 x 入栈
 *      pop() -- 移除栈顶元素
 *      top() -- 获取栈顶元素
 *      empty() -- 返回栈是否为空
 *
 * 注意:
 *      你只能使用队列的基本操作-- 也就是 push to back, peek/pop from front, size, 和 is empty 这些操作是合法的。
 *      你所使用的语言也许不支持队列。你可以使用 list 或者 deque（双端队列）来模拟一个队列, 只要是标准的队列操作即可。
 *      你可以假设所有操作都是有效的（例如, 对一个空的栈不会调用 pop 或者 top 操作）。
 */
public class ImplementStackUsingQueues_225 {

    private int[] datum;

    private int size;

    private int cap;

    private int curIndex;

    public ImplementStackUsingQueues_225() {
        cap = 10;
        datum = new int[cap];
    }

    public void push(int x) {
        if (size == cap) {
            cap *= 2;
            int[] newDatum = new int[cap];
            System.arraycopy(datum, 0, newDatum, 0, datum.length);
            datum = newDatum;
        }
        datum[curIndex++] = x;
        size++;
    }

    public int pop() {
        final int data = datum[--curIndex];
        size--;
        return data;
    }

    public int top() {
        return datum[size - 1];
    }

    public boolean empty() {
        return size == 0;
    }

    public static void main(String[] args) {
        ImplementStackUsingQueues_225 stack = new ImplementStackUsingQueues_225();
        for (int i = 10; i < 50; i++) {
            stack.push(i);
        }
        System.out.println(stack.top());
        while (!stack.empty()) {
            System.out.println(stack.pop());
        }

        for (int i = 100; i < 500; i++) {
            stack.push(i);
        }

        System.out.println("-----------------------------");
        while (!stack.empty()) {
            System.out.println(stack.pop());
        }
    }

}
