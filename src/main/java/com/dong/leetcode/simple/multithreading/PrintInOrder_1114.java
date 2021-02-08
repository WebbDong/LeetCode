package com.dong.leetcode.simple.multithreading;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 按序打印
 *
 * 我们提供了一个类：
 *      public class Foo {
 *          public void first() { print("first"); }
 *          public void second() { print("second"); }
 *          public void third() { print("third"); }
 *      }
 *
 * 三个不同的线程 A、B、C 将会共用一个 Foo 实例。
 *      一个将会调用 first() 方法
 *      一个将会调用 second() 方法
 *      还有一个将会调用 third() 方法
 *
 * 请设计修改程序，以确保 second() 方法在 first() 方法之后被执行，third() 方法在 second() 方法之后被执行。
 *
 * 示例 1:
 *      输入: [1,2,3]
 *      输出: "firstsecondthird"
 *      解释:
 *          有三个线程会被异步启动。
 *          输入 [1,2,3] 表示线程 A 将会调用 first() 方法，线程 B 将会调用 second() 方法，线程 C 将会调用 third() 方法。
 *          正确的输出是 "firstsecondthird"。
 *
 * 示例 2:
 *      输入: [1,3,2]
 *      输出: "firstsecondthird"
 *      解释:
 *          输入 [1,3,2] 表示线程 A 将会调用 first() 方法，线程 B 将会调用 third() 方法，线程 C 将会调用 second() 方法。
 *          正确的输出是 "firstsecondthird"。
 *
 * 提示：
 *      尽管输入中的数字似乎暗示了顺序，但是我们并不保证线程在操作系统中的调度顺序。
 *      你看到的输入格式主要是为了确保测试的全面性。
 */
public class PrintInOrder_1114 {

    private static class Printer {

        public static void printFirst() {
            System.out.print("first");
        }

        public static void printSecond() {
            System.out.print("second");
        }

        public static void printThird() {
            System.out.println("third");
        }

    }

    /**
     * 方法一、使用 CountDownLatch
     */
    private static class Foo1 {

        private CountDownLatch cld1 = new CountDownLatch(1);

        private CountDownLatch cld2 = new CountDownLatch(1);

        public void first() throws InterruptedException {
            Printer.printFirst();
            cld1.countDown();
        }

        public void second() throws InterruptedException {
            cld1.await();
            Printer.printSecond();
            cld2.countDown();
        }

        public void third() throws InterruptedException {
            cld2.await();
            Printer.printThird();
            // 重置 CountDownLatch
            reset();
        }

        public void reset() {
            // CountDownLatch 只能使用一次，计数器无法重置或者重新设置
            cld1 = new CountDownLatch(1);
            cld2 = new CountDownLatch(1);
        }

    }

    /**
     * 方法二、使用 volatile 标记变量
     */
    private static class Foo2 {

        private volatile int flag = 0;

        public void first() throws InterruptedException {
            Printer.printFirst();
            flag++;
        }

        public void second() throws InterruptedException {
            while (flag != 1)
                ;
            Printer.printSecond();
            flag++;
        }

        public void third() throws InterruptedException {
            while (flag != 2)
                ;
            Printer.printThird();
            reset();
        }

        public void reset() {
            flag = 0;
        }

    }

    /**
     * 方法三、使用原子类
     */
    private static class Foo3 {

        private AtomicBoolean firstDone = new AtomicBoolean(false);

        private AtomicBoolean secondDone = new AtomicBoolean(false);

        public void first() throws InterruptedException {
            Printer.printFirst();
            firstDone.set(true);
        }

        public void second() throws InterruptedException {
            while (!firstDone.get())
                ;
            Printer.printSecond();
            secondDone.set(true);
        }

        public void third() throws InterruptedException {
            while (!secondDone.get())
                ;
            Printer.printThird();
            reset();
        }

        public void reset() {
            firstDone.set(false);
            secondDone.set(false);
        }

    }

    /**
     * 方法四、使用信号量
     */
    private static class Foo4 {

        private Semaphore secondSemaphore = new Semaphore(0);

        private Semaphore thirdSemaphore = new Semaphore(0);

        public void first() throws InterruptedException {
            Printer.printFirst();
            secondSemaphore.release();
        }

        public void second() throws InterruptedException {
            secondSemaphore.acquire();
            Printer.printSecond();
            thirdSemaphore.release();
        }

        public void third() throws InterruptedException {
            thirdSemaphore.acquire();
            Printer.printThird();
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pattern p = Pattern.compile("\\d+");
        Thread[] threads = new Thread[3];
        Foo4 foo = new Foo4();

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            Matcher matcher = p.matcher(line);

            for (int i = 0; matcher.find(); i++) {
                String str = matcher.group();
                Thread t;
                if ("1".equals(str)) {
                    t = new Thread(() -> {
                        try {
                            foo.first();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                } else if ("2".equals(str)) {
                    t = new Thread(() -> {
                        try {
                            foo.second();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    t = new Thread(() -> {
                        try {
                            foo.third();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                }
                threads[i] = t;
            }

            for (int i = 0; i < threads.length; i++) {
                threads[i].start();
            }
        }
    }

}
