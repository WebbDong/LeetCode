package com.dong.leetcode.medium.multithreading;

import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 打印零与奇偶数
 *
 * 假设有这么一个类:
 * class ZeroEvenOdd {
 *   public ZeroEvenOdd(int n) { ... }      // 构造函数
 *   public void zero(printNumber) { ... }  // 仅打印出 0
 *   public void even(printNumber) { ... }  // 仅打印出 偶数
 *   public void odd(printNumber) { ... }   // 仅打印出 奇数
 * }
 *
 * 相同的一个 ZeroEvenOdd 类实例将会传递给三个不同的线程:
 *      线程 A 将调用 zero()，它只输出 0 。
 *      线程 B 将调用 even()，它只输出偶数。
 *      线程 C 将调用 odd()，它只输出奇数。
 *
 * 每个线程都有一个 printNumber 方法来输出一个整数。请修改给出的代码以输出整数序列 010203040506... ，其中序列的长度必须为 2n。
 *
 * 示例 1：
 *      输入：n = 2
 *      输出："0102"
 *      说明：三条线程异步执行，其中一个调用 zero()，另一个线程调用 even()，最后一个线程调用odd()。正确的输出为 "0102"。
 *
 * 示例 2：
 *      输入：n = 5
 *      输出："0102030405"
 */
public class PrintZeroEvenOdd_1116 {

    /**
     * 方法一、使用信号量
     */
    private static class ZeroEvenOdd1 {

        private int n;

        private final Semaphore zeroSemaphore = new Semaphore(1);

        private final Semaphore oddSemaphore = new Semaphore(0);

        private final Semaphore evenSemaphore = new Semaphore(0);

        public void setN(int n) {
            this.n = n;
        }

        public void zero() throws InterruptedException {
            for (int i = 1; i <= n; i++) {
                zeroSemaphore.acquire();
                System.out.print(0);
                if (i % 2 == 0) {
                    evenSemaphore.release();
                } else {
                    oddSemaphore.release();
                }
            }
        }

        public void even() throws InterruptedException {
            for (int i = 2; i <= n; i += 2) {
                evenSemaphore.acquire();
                System.out.print(i);
                zeroSemaphore.release();
            }
        }

        public void odd() throws InterruptedException {
            for (int i = 1; i <= n; i += 2) {
                oddSemaphore.acquire();
                System.out.print(i);
                zeroSemaphore.release();
            }
        }

    }

    /**
     * 方法二、使用 Lock 和 Condition
     */
    private static class ZeroEvenOdd2 {

        private int n;

        private final Lock lock = new ReentrantLock();

        private final Condition zeroCondition = lock.newCondition();

        private final Condition evenCondition = lock.newCondition();

        private final Condition oddCondition = lock.newCondition();

        private int state = 0;

        public void setN(int n) {
            this.n = n;
        }

        public void zero() throws InterruptedException {
            for (int i = 1; i <= n; i++) {
                try {
                    lock.lock();
                    System.out.print(0);
                    if (i % 2 == 0) {
                        evenCondition.signalAll();
                        state = 2;
                    } else {
                        oddCondition.signalAll();
                        state = 1;
                    }
                    while (state != 0) {
                        zeroCondition.await();
                    }
                } finally {
                    lock.unlock();
                }
            }
        }

        public void even() throws InterruptedException {
            for (int i = 2; i <= n; i += 2) {
                try {
                    lock.lock();
                    while (state != 2) {
                        evenCondition.await();
                    }
                    System.out.print(i);
                    state = 0;
                    zeroCondition.signalAll();
                } finally {
                    lock.unlock();
                }
            }
        }

        public void odd() throws InterruptedException {
            for (int i = 1; i <= n; i += 2) {
                try {
                    lock.lock();
                    while (state != 1) {
                        oddCondition.await();
                    }
                    System.out.print(i);
                    state = 0;
                    zeroCondition.signalAll();
                } finally {
                    lock.unlock();
                }
            }
        }

    }

    /**
     * 方法三、使用 synchronized
     */
    private static class ZeroEvenOdd3 {

        private int n;

        private int state = 0;

        public void setN(int n) {
            this.n = n;
        }

        public void zero() throws InterruptedException {
            for (int i = 1; i <= n; i++) {
                synchronized (this) {
                    System.out.print(0);
                    if (i % 2 == 0) {
                        state = 2;
                    } else {
                        state = 1;
                    }
                    this.notifyAll();
                    while (state != 0) {
                        this.wait();
                    }
                }
            }
        }

        public void even() throws InterruptedException {
            for (int i = 2; i <= n; i += 2) {
                synchronized (this) {
                    while (state != 2) {
                        this.wait();
                    }
                    System.out.print(i);
                    state = 0;
                    this.notifyAll();
                }
            }
        }

        public void odd() throws InterruptedException {
            for (int i = 1; i <= n; i += 2) {
                synchronized (this) {
                    while (state != 1) {
                        this.wait();
                    }
                    System.out.print(i);
                    state = 0;
                    this.notifyAll();
                }
            }
        }

    }

    /**
     * 方法四、使用 CyclicBarrier
     */
    private static class ZeroEvenOdd4 {

        private int n;

        private volatile int state = 0;

        private final CyclicBarrier cb = new CyclicBarrier(2);

        public void setN(int n) {
            this.n = n;
        }

        public void zero() throws InterruptedException {
            for (int i = 1; i <= n; i++) {
                System.out.print(0);
                if (i % 2 == 0) {
                    state = 2;
                } else {
                    state = 1;
                }
                try {
                    cb.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }

        public void even() throws InterruptedException {
            for (int i = 2; i <= n; i += 2) {
                while (state != 2) {
                    Thread.yield();
                }
                System.out.print(i);
                state = 0;
                try {
                    cb.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }

        public void odd() throws InterruptedException {
            for (int i = 1; i <= n; i += 2) {
                while (state != 1) {
                    Thread.yield();
                }
                System.out.print(i);
                state = 0;
                try {
                    cb.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pattern p = Pattern.compile("\\d+");
        ZeroEvenOdd4 zeroEvenOdd = new ZeroEvenOdd4();

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            Matcher matcher = p.matcher(line);

            while (matcher.find()) {
                zeroEvenOdd.setN(Integer.parseInt(matcher.group()));
                Thread t1 = new Thread(() -> {
                    try {
                        zeroEvenOdd.zero();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                Thread t2 = new Thread(() -> {
                    try {
                        zeroEvenOdd.even();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                Thread t3 = new Thread(() -> {
                    try {
                        zeroEvenOdd.odd();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

                t1.start();
                t2.start();
                t3.start();
            }
        }
    }

}
