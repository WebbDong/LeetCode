package com.dong.leetcode.medium.multithreading;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 哲学家进餐
 *
 * 5 个沉默寡言的哲学家围坐在圆桌前，每人面前一盘意面。叉子放在哲学家之间的桌面上。（5 个哲学家，5 根叉子）
 *
 * 所有的哲学家都只会在思考和进餐两种行为间交替。哲学家只有同时拿到左边和右边的叉子才能吃到面，而同一根叉子在同一时间只能被一个哲学家使用。
 * 每个哲学家吃完面后都需要把叉子放回桌面以供其他哲学家吃面。只要条件允许，哲学家可以拿起左边或者右边的叉子，但在没有同时拿到左右叉子时不能进食。
 *
 * 假设面的数量没有限制，哲学家也能随便吃，不需要考虑吃不吃得下。
 *
 * 设计一个进餐规则（并行算法）使得每个哲学家都不会挨饿；也就是说，在没有人知道别人什么时候想吃东西或思考的情况下，每个哲学家都可以在吃饭和
 * 思考之间一直交替下去。
 *
 * 哲学家从 0 到 4 按 顺时针 编号。请实现函数
 *      void wantsToEat(philosopher, pickLeftFork, pickRightFork, eat, putLeftFork, putRightFork)：
 *      1、philosopher 哲学家的编号。
 *      2、pickLeftFork 和 pickRightFork 表示拿起左边或右边的叉子。
 *      3、eat 表示吃面。
 *      4、putLeftFork 和 putRightFork 表示放下左边或右边的叉子。
 *      5、由于哲学家不是在吃面就是在想着啥时候吃面，所以思考这个方法没有对应的回调。
 *
 * 给你 5 个线程，每个都代表一个哲学家，请你使用类的同一个对象来模拟这个过程。在最后一次调用结束之前，可能会为同一个哲学家多次调用该函数。
 *
 * 示例：
 *      输入：n = 1
 *      输出：[[4,2,1],[4,1,1],[0,1,1],[2,2,1],[2,1,1],[2,0,3],[2,1,2],[2,2,2],[4,0,3],[4,1,2],[0,2,1],[4,2,2],[3,2,1],[3,1,1],[0,0,3],[0,1,2],[0,2,2],[1,2,1],[1,1,1],[3,0,3],[3,1,2],[3,2,2],[1,0,3],[1,1,2],[1,2,2]]
 *      解释:
 *          n 表示每个哲学家需要进餐的次数。
 *          输出数组描述了叉子的控制和进餐的调用，它的格式如下：
 *          output[i] = [a, b, c] (3个整数)
 *          - a 哲学家编号。
 *          - b 指定叉子：{1 : 左边, 2 : 右边}.
 *          - c 指定行为：{1 : 拿起, 2 : 放下, 3 : 吃面}。
 *          如 [4,2,1] 表示 4 号哲学家拿起了右边的叉子。
 *
 * 提示：
 *      1 <= n <= 60
 */
public class TheDiningPhilosophers_1226 {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static final class EatTask implements Runnable {

        private int philosopher;

        private int n;

        private DiningPhilosophers dp;

        private final Runnable pickLeftFork = () -> {
            final StringBuilder sb = new StringBuilder();
            sb.append("[")
                    .append(philosopher).append(",")
                    .append(1).append(",")
                    .append(1)
                    .append("], ");
            System.out.print(sb.toString());
        };

        private final Runnable pickRightFork = () -> {
            final StringBuilder sb = new StringBuilder();
            sb.append("[")
                    .append(philosopher).append(",")
                    .append(2).append(",")
                    .append(1)
                    .append("], ");
            System.out.print(sb.toString());
        };

        private final Runnable eat = () -> {
            final StringBuilder sb = new StringBuilder();
            sb.append("[")
                    .append(philosopher).append(",")
                    .append(0).append(",")
                    .append(3)
                    .append("], ");
            System.out.print(sb.toString());
        };

        private final Runnable putLeftFork = () -> {
            final StringBuilder sb = new StringBuilder();
            sb.append("[")
                    .append(philosopher).append(",")
                    .append(1).append(",")
                    .append(2)
                    .append("], ");
            System.out.print(sb.toString());
        };

        private final Runnable putRightFork = () -> {
            final StringBuilder sb = new StringBuilder();
            sb.append("[")
                    .append(philosopher).append(",")
                    .append(2).append(",")
                    .append(2)
                    .append("], ");
            System.out.print(sb.toString());
        };

        @SneakyThrows
        @Override
        public void run() {
            for (int i = 0; i < n; i++) {
                dp.wantsToEat(philosopher, pickLeftFork, pickRightFork,
                        eat, putLeftFork, putRightFork);
            }
//            System.out.println(philosopher + " done");
        }

    }

    private interface DiningPhilosophers {

        void wantsToEat(int philosopher,
                        Runnable pickLeftFork,
                        Runnable pickRightFork,
                        Runnable eat,
                        Runnable putLeftFork,
                        Runnable putRightFork) throws InterruptedException;

    }

    /**
     * 方法一、允许最多四个哲学家持有叉子，可保证至少一名哲学家可以吃面
     */
    private static class DiningPhilosophers1 implements DiningPhilosophers {

        /**
         * 每一个 fork 代表一个 ReentrantLock，一共五个 fork
         */
        private final Lock[] locks = {
                new ReentrantLock(),
                new ReentrantLock(),
                new ReentrantLock(),
                new ReentrantLock(),
                new ReentrantLock(),
        };

        /**
         * 最多只能有四个哲学家持有 fork
         */
        private final Semaphore eatLimit = new Semaphore(4);

        public DiningPhilosophers1() {

        }

        /**
         * @param philosopher       哲学家的编号
         * @param pickLeftFork      拿起左边的叉子
         * @param pickRightFork     拿起右边的叉子
         * @param eat               吃面
         * @param putLeftFork       放下左边的叉子
         * @param putRightFork      放下右边的叉子
         * @throws InterruptedException
         */
        public void wantsToEat(int philosopher,
                               Runnable pickLeftFork,
                               Runnable pickRightFork,
                               Runnable eat,
                               Runnable putLeftFork,
                               Runnable putRightFork) throws InterruptedException {
            // 左边叉子的编号
            int leftForkNum = (philosopher + 1) % 5;
            // 右边叉子的编号
            int rightForkNum = philosopher;

            // 最多允许四个哲学家持有叉子，可保证至少一名哲学家可以吃面
            eatLimit.acquire();

            // 拿起叉子后锁住
            locks[leftForkNum].lock();
            locks[rightForkNum].lock();
            try {
                pickLeftFork.run();
                pickRightFork.run();
                eat.run();
                putLeftFork.run();
                putRightFork.run();
            } finally {
                // 吃完后释放叉子的锁
                locks[leftForkNum].unlock();
                locks[rightForkNum].unlock();
                eatLimit.release();
            }
        }

    }

    /**
     * 方法二、只允许1个哲学家就餐
     */
    private static class DiningPhilosophers2 implements DiningPhilosophers {

        private final Lock[] locks = {
                new ReentrantLock(),
                new ReentrantLock(),
                new ReentrantLock(),
                new ReentrantLock(),
                new ReentrantLock(),
        };

        private final Lock eatLock = new ReentrantLock();

        public DiningPhilosophers2() {

        }

        public void wantsToEat(int philosopher,
                               Runnable pickLeftFork,
                               Runnable pickRightFork,
                               Runnable eat,
                               Runnable putLeftFork,
                               Runnable putRightFork) throws InterruptedException {
            int leftForkNum = (philosopher + 1) % 5;
            int rightForkNum = philosopher;

            eatLock.lock();
            locks[leftForkNum].lock();
            locks[rightForkNum].lock();
            try {
                pickLeftFork.run();
                pickRightFork.run();
                eat.run();
                putLeftFork.run();
                putRightFork.run();
            } finally {
                locks[leftForkNum].unlock();
                locks[rightForkNum].unlock();
                eatLock.unlock();
            }
        }

    }

    /**
     * 方法三、避免死锁，发生死锁的条件: 当五个哲学家都左手持有左边的叉子或当五个哲学家都右手持有右边的叉子时，会发生死锁
     */
    private static class DiningPhilosophers3 implements DiningPhilosophers {

        private final Lock[] locks = {
                new ReentrantLock(),
                new ReentrantLock(),
                new ReentrantLock(),
                new ReentrantLock(),
                new ReentrantLock(),
        };

        public void wantsToEat(int philosopher,
                               Runnable pickLeftFork,
                               Runnable pickRightFork,
                               Runnable eat,
                               Runnable putLeftFork,
                               Runnable putRightFork) throws InterruptedException {
            int leftForkNum = (philosopher + 1) % 5;
            int rightForkNum = philosopher;

            if (philosopher % 2 == 0) {
                // 偶数编号的哲学家优先拿起左边的叉子，再拿起右边的叉子
                locks[leftForkNum].lock();
                locks[rightForkNum].lock();
            } else {
                // 奇数编号的哲学家优先拿起右边的叉子，再拿起左边的叉子
                locks[rightForkNum].lock();
                locks[leftForkNum].lock();
            }

            try {
                pickLeftFork.run();
                pickRightFork.run();
                eat.run();
                putLeftFork.run();
                putRightFork.run();
            } finally {
                locks[leftForkNum].unlock();
                locks[rightForkNum].unlock();
            }
        }

    }

    /**
     * 方法四、使用位运算来判断叉子的使用状态，使用 AtomicInteger 来使用 CAS + 自旋
     */
    private static class DiningPhilosophers4 implements DiningPhilosophers {

        /**
         * 初始化为0, 二进制为00000，每个二进制位表示一个叉子的使用状态，0: 未使用，1: 已使用
         */
        private AtomicInteger fork = new AtomicInteger(0);

        /**
         * 每个叉子的掩码值，00001, 00010, 00100, 01000, 10000
         */
        private final int[] forkMask = {1, 2, 4, 8, 16};

        private Semaphore eatLimit = new Semaphore(4);

        public void wantsToEat(int philosopher,
                               Runnable pickLeftFork,
                               Runnable pickRightFork,
                               Runnable eat,
                               Runnable putLeftFork,
                               Runnable putRightFork) throws InterruptedException {
            int leftForkMask = forkMask[(philosopher + 1) % 5];
            int rightForkMask = forkMask[philosopher];

            eatLimit.acquire();
            while (!pickFork(leftForkMask)) {
                Thread.yield();
            }
            while (!pickFork(rightForkMask)) {
                Thread.yield();
            }

            pickLeftFork.run();
            pickRightFork.run();
            eat.run();
            putLeftFork.run();
            putRightFork.run();

            while (!putFork(leftForkMask)) {
                Thread.yield();
            }
            while (!putFork(rightForkMask)) {
                Thread.yield();
            }
            eatLimit.release();
        }

        private boolean pickFork(int mask) {
            int expect = fork.get();
            return (expect & mask) > 0 ? false : fork.compareAndSet(expect, expect ^ mask);
        }

        private boolean putFork(int mask) {
            int expect = fork.get();
            return fork.compareAndSet(expect, expect ^ mask);
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pattern p = Pattern.compile("\\d+");
        DiningPhilosophers dp = new DiningPhilosophers4();
        final int philosopherCount = 5;
        final ThreadPoolExecutor philosopherPool = new ThreadPoolExecutor(
                philosopherCount, philosopherCount, 5, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5),
                new ThreadPoolExecutor.AbortPolicy());

        while (scanner.hasNext()) {
            Matcher matcher = p.matcher(scanner.nextLine());
            if (matcher.find()) {
                final int n;
                try {
                    n = Integer.parseInt(matcher.group());
                } catch (NumberFormatException e) {
                    continue;
                }
                for (int i = 0; i < philosopherCount; i++) {
                    philosopherPool.execute(new EatTask(i, n, dp));
                }
            }
        }
    }

}
