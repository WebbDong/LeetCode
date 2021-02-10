package com.dong.leetcode.medium.multithreading;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.IntConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 交替打印字符串
 *
 * 编写一个可以从 1 到 n 输出代表这个数字的字符串的程序，但是：
 *      如果这个数字可以被 3 整除，输出 "fizz"。
 *      如果这个数字可以被 5 整除，输出 "buzz"。
 *      如果这个数字可以同时被 3 和 5 整除，输出 "fizzbuzz"。
 *
 * 例如，当 n = 15，输出： 1, 2, fizz, 4, buzz, fizz, 7, 8, fizz, buzz, 11, fizz, 13, 14, fizzbuzz。
 *
 * 假设有这么一个类:
 * class FizzBuzz {
 *   public FizzBuzz(int n) { ... }               // constructor
 *   public void fizz(printFizz) { ... }          // only output "fizz"
 *   public void buzz(printBuzz) { ... }          // only output "buzz"
 *   public void fizzbuzz(printFizzBuzz) { ... }  // only output "fizzbuzz"
 *   public void number(printNumber) { ... }      // only output the numbers
 * }
 *
 * 请你实现一个有四个线程的多线程版 FizzBuzz， 同一个 FizzBuzz 实例会被如下四个线程使用：
 *      1、线程A将调用 fizz() 来判断是否能被 3 整除，如果可以，则输出 fizz。
 *      2、线程B将调用 buzz() 来判断是否能被 5 整除，如果可以，则输出 buzz。
 *      3、线程C将调用 fizzbuzz() 来判断是否同时能被 3 和 5 整除，如果可以，则输出 fizzbuzz。
 *      4、线程D将调用 number() 来实现输出既不能被 3 整除也不能被 5 整除的数字。
 *
 */
public class FizzBuzzMultithreaded_1195 {

    private interface FizzBuzz {

        void setN(int n);

        int getN();

        // printFizz.run() outputs "fizz".
        void fizz(Runnable printFizz) throws InterruptedException;

        // printBuzz.run() outputs "buzz".
        void buzz(Runnable printBuzz) throws InterruptedException;

        // printFizzBuzz.run() outputs "fizzbuzz".
        void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException;

        // printNumber.accept(x) outputs "x", where x is an integer.
        void number(IntConsumer printNumber) throws InterruptedException;

    }

    /**
     * 方法一、使用 synchronized
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class FizzBuzz1 implements FizzBuzz {

        private int n;

        private int i = 1;

        public void setN(int n) {
            this.n = n;
            this.i = 1;
        }

        public void fizz(Runnable printFizz) throws InterruptedException {
            synchronized (this) {
                BreakOutWhile:
                while (i <= n) {
                    while (i % 3 != 0 || i % 5 == 0) {
                        this.wait();
                        if (i > n) {
                            break BreakOutWhile;
                        }
                    }
                    printFizz.run();
                    i++;
                    this.notifyAll();
                }
            }
        }

        public void buzz(Runnable printBuzz) throws InterruptedException {
            synchronized (this) {
                BreakOutWhile:
                while (i <= n) {
                    while (i % 5 != 0 || i % 3 == 0) {
                        this.wait();
                        if (i > n) {
                            break BreakOutWhile;
                        }
                    }
                    printBuzz.run();
                    i++;
                    this.notifyAll();
                }
            }
        }

        public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
            synchronized (this) {
                BreakOutWhile:
                while (i <= n) {
                    while (i % 5 != 0 || i % 3 != 0) {
                        this.wait();
                        if (i > n) {
                            break BreakOutWhile;
                        }
                    }
                    printFizzBuzz.run();
                    i++;
                    this.notifyAll();
                }
            }
        }

        public void number(IntConsumer printNumber) throws InterruptedException {
            synchronized (this) {
                BreakOutWhile:
                while (i <= n) {
                    while (i % 5 == 0 || i % 3 == 0) {
                        this.wait();
                        if (i > n) {
                            break BreakOutWhile;
                        }
                    }
                    printNumber.accept(i);
                    i++;
                    this.notifyAll();
                }
            }
        }

    }

    /**
     * 方法二、使用 Lock 和 Condition
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class FizzBuzz2 implements FizzBuzz {

        private int n;

        public void setN(int n) {
            this.n = n;
        }

        public void fizz(Runnable printFizz) throws InterruptedException {

            for (int i = 3; i <= n; i += 3) {

            }
            printFizz.run();
        }

        public void buzz(Runnable printBuzz) throws InterruptedException {
            for (int i = 5; i <= n; i += 5) {

            }
            printBuzz.run();
        }

        public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
            for (int i = 15; i <= n; i += 15) {

            }
            printFizzBuzz.run();
        }

        public void number(IntConsumer printNumber) throws InterruptedException {
            for (int i = 1; i <= n; i++) {
                printNumber.accept(i);
            }
        }

    }

    private static ThreadPoolExecutor fizzPool = new ThreadPoolExecutor(
            1, 1, 5, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1),
            new ThreadPoolExecutor.AbortPolicy());

    private static ThreadPoolExecutor buzzPool = new ThreadPoolExecutor(
            1, 1, 5, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1),
            new ThreadPoolExecutor.AbortPolicy());

    private static ThreadPoolExecutor fizzbuzzPool = new ThreadPoolExecutor(
            1, 1, 5, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1),
            new ThreadPoolExecutor.AbortPolicy());

    private static ThreadPoolExecutor numberPool = new ThreadPoolExecutor(
            1, 1, 5, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1),
            new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pattern p = Pattern.compile("\\d+");
        final FizzBuzz fizzBuzz = new FizzBuzz1();
        while (scanner.hasNext()) {
            Matcher matcher = p.matcher(scanner.nextLine());
            while (matcher.find()) {
                int n;
                try {
                    n = Integer.parseInt(matcher.group());
                } catch (NumberFormatException e) {
                    continue;
                }
                fizzBuzz.setN(n);

                fizzPool.execute(() -> {
                    try {
                        fizzBuzz.fizz(() -> System.out.print("fizz, "));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                buzzPool.execute(() -> {
                    try {
                        fizzBuzz.buzz(() -> System.out.print("buzz, "));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                fizzbuzzPool.execute(() -> {
                    try {
                        fizzBuzz.fizzbuzz(() -> System.out.print("fizzbuzz, "));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                numberPool.execute(() -> {
                    try {
                        fizzBuzz.number(num -> System.out.print(num + ", "));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

}
