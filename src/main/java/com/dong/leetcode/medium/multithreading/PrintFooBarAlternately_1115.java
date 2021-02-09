package com.dong.leetcode.medium.multithreading;

import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 交替打印 FooBar
 *
 * 我们提供一个类：
 * class FooBar {
 *   public void foo() {
 *     for (int i = 0; i < n; i++) {
 *       print("foo");
 *     }
 *   }
 *
 *   public void bar() {
 *     for (int i = 0; i < n; i++) {
 *       print("bar");
 *     }
 *   }
 * }
 *
 * 两个不同的线程将会共用一个 FooBar 实例。其中一个线程将会调用 foo() 方法，另一个线程将会调用 bar() 方法。
 *
 * 请设计修改程序，以确保 "foobar" 被输出 n 次。
 *
 * 示例 1:
 *
 * 输入: n = 1
 * 输出: "foobar"
 * 解释: 这里有两个线程被异步启动。其中一个调用 foo() 方法, 另一个调用 bar() 方法，"foobar" 将被输出一次。
 * 示例 2:
 *
 * 输入: n = 2
 * 输出: "foobarfoobar"
 * 解释: "foobar" 将被输出两次。
 */
public class PrintFooBarAlternately_1115 {

    /**
     * 方法一、使用 volatile 标记变量
     */
    private static class FooBar1 {

        private int n;

        private volatile boolean isFoo = true;

        public void setN(int n) {
            this.n = n;
        }

        public void foo() {
            for (int i = 0; i < n;) {
                if (isFoo) {
                    System.out.print("foo");
                    isFoo = false;
                    i++;
                } else {
                    // 释放 CPU 时间片，防止 LeetCode 报超时错误
                    Thread.yield();
                }
            }
        }

        public void bar() {
            for (int i = 0; i < n;) {
                if (!isFoo) {
                    System.out.print("bar");
                    isFoo = true;
                    i++;
                } else {
                    Thread.yield();
                }
            }
        }

    }

    /**
     * 方法二、使用信号量
     */
    private static class FooBar2 {

        private int n;

        private Semaphore fooSemaphore = new Semaphore(1);

        private Semaphore barSemaphore = new Semaphore(0);

        public void setN(int n) {
            this.n = n;
        }

        public void foo() throws InterruptedException {
            for (int i = 0; i < n;) {
                fooSemaphore.acquire();
                System.out.print("foo");
                i++;
                barSemaphore.release();
            }
        }

        public void bar() throws InterruptedException {
            for (int i = 0; i < n;) {
                barSemaphore.acquire();
                System.out.print("bar");
                i++;
                fooSemaphore.release();
            }
        }

    }

    /**
     * 方法三、使用锁
     */
    private static class FooBar3 {

        private int n;

        private boolean flag = true;

        public void setN(int n) {
            this.n = n;
        }

        public void foo() {
            for (int i = 0; i < n;) {
                synchronized (this) {
                    if (!flag) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("foo");
                    flag = false;
                    i++;
                    this.notifyAll();
                }
            }
        }

        public void bar() {
            for (int i = 0; i < n;) {
                synchronized (this) {
                    if (flag) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("bar");
                    flag = true;
                    i++;
                    this.notifyAll();
                }
            }
        }

    }

    /**
     * 方法四、使用 CyclicBarrier
     */
    private static class FooBar4 {

        private int n;

        private final CyclicBarrier cb = new CyclicBarrier(2);

        private volatile boolean flag = true;

        public void setN(int n) {
            this.n = n;
        }

        public void foo() throws Exception {
            for (int i = 0; i < n; i++) {
                while (!flag)
                    ;
                System.out.print("foo");
                flag = false;
                cb.await();
            }
        }

        public void bar() throws Exception {
            for (int i = 0; i < n; i++) {
                cb.await();
                System.out.print("bar");
                flag = true;
            }
        }

    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Pattern p = Pattern.compile("\\d+");
        FooBar4 fooBar = new FooBar4();

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            Matcher matcher = p.matcher(line);
            Thread t1 = new Thread(() -> {
                try {
                    fooBar.foo();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            Thread t2 = new Thread(() -> {
                try {
                    fooBar.bar();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            while (matcher.find()) {
                fooBar.setN(Integer.parseInt(matcher.group()));
                t1.start();
                t2.start();
            }
        }
    }

}
