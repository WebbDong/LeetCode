package com.dong.leetcode.medium.multithreading;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
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
//            System.out.print("\nfizz breakout");
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
//            System.out.print("\nbuzz breakout");
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
//            System.out.print("\nfizzbuzz breakout");
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
//            System.out.print("\nnumber breakout");
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

        private final Lock lock = new ReentrantLock();

        private final Condition fizzCondition = lock.newCondition();

        private final Condition buzzCondition = lock.newCondition();

        private final Condition fizzbuzzCondition = lock.newCondition();

        private final Condition numberCondition = lock.newCondition();

        /**
         * 0: number, 1: buzz, 2: fizzbuzz, 3: fizz
         */
        private int state;

        public void setN(int n) {
            this.n = n;
        }

        public void fizz(Runnable printFizz) throws InterruptedException {
            try {
                lock.lock();
                BreakOutLoop:
                for (int i = 3; i <= n; i += 3) {
                    while (state != 3) {
                        fizzCondition.await();
                        if (state == 0) {
                            break BreakOutLoop;
                        }
                    }
                    printFizz.run();
                    state = 0;
                    numberCondition.signal();
                }
            } finally {
                lock.unlock();
            }
//            System.out.print("\nfizz breakout");
        }

        public void buzz(Runnable printBuzz) throws InterruptedException {
            try {
                lock.lock();
                BreakOutLoop:
                for (int i = 5; i <= n; i += 5) {
                    while (state != 1) {
                        buzzCondition.await();
                        if (state == 0) {
                            break BreakOutLoop;
                        }
                    }
                    printBuzz.run();
                    state = 0;
                    numberCondition.signal();
                }
            } finally {
                lock.unlock();
            }
//            System.out.print("\nbuzz breakout");
        }

        public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
            try {
                lock.lock();
                BreakOutLoop:
                for (int i = 15; i <= n; i += 15) {
                    while (state != 2) {
                        fizzbuzzCondition.await();
                        if (state == 0) {
                            break BreakOutLoop;
                        }
                    }
                    printFizzBuzz.run();
                    state = 0;
                    numberCondition.signal();
                }
            } finally {
                lock.unlock();
            }
//            System.out.print("\nfizzbuzz breakout");
        }

        public void number(IntConsumer printNumber) throws InterruptedException {
            try {
                lock.lock();
                for (int i = 1; i <= n; i++) {
                    if (i % 15 == 0) {
                        state = 2;
                        fizzbuzzCondition.signal();
                    } else if (i % 3 == 0) {
                        state = 3;
                        fizzCondition.signal();
                    } else if (i % 5 == 0) {
                        state = 1;
                        buzzCondition.signal();
                    } else {
                        state = 0;
                        printNumber.accept(i);
                    }
                    while (state != 0) {
                        numberCondition.await();
                    }
                }
                // 唤醒其他线程并结束线程
                state = 0;
                fizzbuzzCondition.signal();
                fizzCondition.signal();
                buzzCondition.signal();
            } finally {
                lock.unlock();
            }
//            System.out.print("\nnumber breakout");
        }

    }

    /**
     * 方法三、使用 volatile 变量 + 自旋
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class FizzBuzz3 implements FizzBuzz {

        private int n;

        /**
         * 0: number, 1: buzz, 2: fizzbuzz, 3: fizz
         */
        private volatile int state;

        public void setN(int n) {
            this.n = n;
        }

        public void fizz(Runnable printFizz) throws InterruptedException {
            for (int i = 3; i <= n; i += 3) {
                // 15的倍数不处理
                if (i % 15 == 0) {
                    continue;
                }
                while (state != 3) {
                    Thread.yield();
                }
                printFizz.run();
                state = 0;
            }
//            System.out.print("\nfizz breakout");
        }

        public void buzz(Runnable printBuzz) throws InterruptedException {
            for (int i = 5; i <= n; i += 5) {
                // 15的倍数不处理
                if (i % 15 == 0) {
                    continue;
                }
                while (state != 1) {
                    Thread.yield();
                }
                printBuzz.run();
                state = 0;
            }
//            System.out.print("\nbuzz breakout");
        }

        public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
            for (int i = 15; i <= n; i += 15) {
                while (state != 2) {
                    Thread.yield();
                }
                printFizzBuzz.run();
                state = 0;
            }
//            System.out.print("\nfizzbuzz breakout");
        }

        public void number(IntConsumer printNumber) throws InterruptedException {
            for (int i = 1; i <= n; i++) {
                while (state != 0) {
                    Thread.yield();
                }

                // 0: number, 1: buzz, 2: fizzbuzz, 3: fizz
                if (i % 15 == 0) {
                    state = 2;
                } else if (i % 3 == 0) {
                    state = 3;
                } else if (i % 5 == 0) {
                    state = 1;
                } else {
                    printNumber.accept(i);
                }
            }
//            System.out.print("\nnumber breakout");
        }

    }

    /**
     * 方法四、使用信号量
     */
    @Data
    @AllArgsConstructor
    private static class FizzBuzz4 implements FizzBuzz {

        private int n;

        private final Semaphore fizzSemaphore;

        private final Semaphore buzzSemaphore;

        private final Semaphore fizzbuzzSemaphore;

        private final Semaphore numberSemaphore;

        /**
         * 0: number, 1: buzz, 2: fizzbuzz, 3: fizz
         */
        private volatile int state;

        public FizzBuzz4() {
            fizzSemaphore = new Semaphore(0);
            buzzSemaphore = new Semaphore(0);
            fizzbuzzSemaphore = new Semaphore(0);
            numberSemaphore = new Semaphore(1);
        }

        public void setN(int n) {
            this.n = n;
        }

        public void fizz(Runnable printFizz) throws InterruptedException {
            for (int i = 3; i <= n; i += 3) {
                if (i % 15 == 0) {
                    continue;
                }
                fizzSemaphore.acquire();
                printFizz.run();
                numberSemaphore.release();
            }
//            System.out.print("\nfizz breakout");
        }

        public void buzz(Runnable printBuzz) throws InterruptedException {
            for (int i = 5; i <= n; i += 5) {
                if (i % 15 == 0) {
                    continue;
                }
                buzzSemaphore.acquire();
                printBuzz.run();
                numberSemaphore.release();
            }
//            System.out.print("\nbuzz breakout");
        }

        public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
            for (int i = 15; i <= n; i += 15) {
                fizzbuzzSemaphore.acquire();
                printFizzBuzz.run();
                numberSemaphore.release();
            }
//            System.out.print("\nfizzbuzz breakout");
        }

        public void number(IntConsumer printNumber) throws InterruptedException {
            for (int i = 1; i <= n; i++) {
                numberSemaphore.acquire();
                if (i % 15 == 0) {
                    fizzbuzzSemaphore.release();
                } else if (i % 3 == 0) {
                    fizzSemaphore.release();
                } else if (i % 5 == 0) {
                    buzzSemaphore.release();
                } else {
                    printNumber.accept(i);
                    numberSemaphore.release();
                }
            }
//            System.out.print("\nnumber breakout");
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
        final FizzBuzz fizzBuzz = new FizzBuzz4();
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
