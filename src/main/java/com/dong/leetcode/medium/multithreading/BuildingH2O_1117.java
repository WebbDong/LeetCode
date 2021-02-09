package com.dong.leetcode.medium.multithreading;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * H2O
 *
 * 现在有两种线程，氧 oxygen 和氢 hydrogen，你的目标是组织这两种线程来产生水分子。
 *
 * 存在一个屏障（barrier）使得每个线程必须等候直到一个完整水分子能够被产生出来。
 *
 * 氢和氧线程会被分别给予 releaseHydrogen 和 releaseOxygen 方法来允许它们突破屏障。
 *
 * 这些线程应该三三成组突破屏障并能立即组合产生一个水分子。
 *
 * 你必须保证产生一个水分子所需线程的结合必须发生在下一个水分子产生之前。
 *
 * 换句话说:
 *      如果一个氧线程到达屏障时没有氢线程到达，它必须等候直到两个氢线程到达。
 *      如果一个氢线程到达屏障时没有其它线程到达，它必须等候直到一个氧线程和另一个氢线程到达。
 *
 * 书写满足这些限制条件的氢、氧线程同步代码。
 *
 * 示例 1:
 *      输入: "HOH"
 *      输出: "HHO"
 *      解释: "HOH" 和 "OHH" 依然都是有效解。
 *
 * 示例 2:
 *      输入: "OOHHHH"
 *      输出: "HHOHHO"
 *      解释: "HOHHHO", "OHHHHO", "HHOHOH", "HOHHOH", "OHHHOH", "HHOOHH", "HOHOHH" 和 "OHHOHH" 依然都是有效解。
 *
 * 提示：
 *      输入字符串的总长将会是 3n, 1 ≤ n ≤ 50；
 *      输入字符串中的 “H” 总数将会是 2n 。
 *      输入字符串中的 “O” 总数将会是 n 。
 */
public class BuildingH2O_1117 {

    private interface H2O {

        void hydrogen() throws InterruptedException;

        void oxygen() throws InterruptedException;

    }

    /**
     * 方法一、使用 synchronized
     */
    private static class H2O1 implements H2O {

        private int hydrogenCount = 0;

        public void hydrogen() throws InterruptedException {
            synchronized (this) {
                while (hydrogenCount == 2) {
                    this.wait();
                }
                System.out.print("H");
                hydrogenCount++;
                this.notifyAll();
            }
        }

        public void oxygen() throws InterruptedException {
            synchronized (this) {
                while (hydrogenCount != 2) {
                    this.wait();
                }
                System.out.print("O");
                hydrogenCount = 0;
                this.notifyAll();
            }
        }

    }

    /**
     * 方法二、使用 Semaphore
     */
    private static class H2O2 implements H2O {

        private Semaphore hydrogenSemaphore = new Semaphore(2);

        private Semaphore oxygenSemaphore = new Semaphore(0);

        private volatile int hydrogenCount = 0;

        public void hydrogen() throws InterruptedException {
            hydrogenSemaphore.acquire();
            System.out.print("H");
            hydrogenCount++;
            if (hydrogenCount == 2) {
                oxygenSemaphore.release();
            }
        }

        public void oxygen() throws InterruptedException {
            oxygenSemaphore.acquire();
            System.out.print("O");
            hydrogenCount = 0;
            hydrogenSemaphore.release(2);
        }

    }

    /**
     * 方法三、使用 Lock 和 Condition
     */
    private static class H2O3 implements H2O {

        private final Lock lock = new ReentrantLock();

        private final Condition hydrogenCondition = lock.newCondition();

        private final Condition oxygenCondition = lock.newCondition();

        private volatile int hydrogenCount = 0;

        public void hydrogen() throws InterruptedException {
            try {
                lock.lock();
                while (hydrogenCount == 2) {
                    hydrogenCondition.await();
                }
                System.out.print("H");
                hydrogenCount++;
                if (hydrogenCount == 2) {
                    // 唤醒任意一个 oxygen 线程
                    oxygenCondition.signal();
                }
            } finally {
                lock.unlock();
            }
        }

        public void oxygen() throws InterruptedException {
            try {
                lock.lock();
                while (hydrogenCount != 2) {
                    oxygenCondition.await();
                }
                System.out.print("O");
                hydrogenCount = 0;
                hydrogenCondition.signalAll();
            } finally {
                lock.unlock();
            }
        }

    }

    /**
     * 方法四、使用 Semaphore 和 CyclicBarrier，第一种方式
     */
    private static class H2O4 implements H2O {

        private Semaphore hydrogenSemaphore = new Semaphore(2);

        private Semaphore oxygenSemaphore = new Semaphore(0);

        private final CyclicBarrier cb = new CyclicBarrier(2, () -> oxygenSemaphore.release());

        public void hydrogen() throws InterruptedException {
            hydrogenSemaphore.acquire();
            System.out.print("H");
            try {
                cb.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        public void oxygen() throws InterruptedException {
            oxygenSemaphore.acquire();
            System.out.print("O");
            hydrogenSemaphore.release(2);
        }

    }

    /**
     * 方法五、使用 Semaphore 和 CyclicBarrier，第二种方式
     */
    private static class H2O5 implements H2O {

        private Semaphore hydrogenSemaphore = new Semaphore(2);

        private Semaphore oxygenSemaphore = new Semaphore(1);

        private final CyclicBarrier cb = new CyclicBarrier(3, () -> {
            hydrogenSemaphore.release(2);
            oxygenSemaphore.release(1);
        });

        public void hydrogen() throws InterruptedException {
            hydrogenSemaphore.acquire();
            System.out.print("H");
            try {
                cb.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        public void oxygen() throws InterruptedException {
            oxygenSemaphore.acquire();
            System.out.print("O");
            try {
                cb.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

    }

    private static volatile int hCount = 0;

    private static volatile int oCount = 0;

    private static ThreadPoolExecutor pool1 = new ThreadPoolExecutor(
            50, 100, 5, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(5),
            new ThreadPoolExecutor.AbortPolicy());

    private static ThreadPoolExecutor pool2 = new ThreadPoolExecutor(
            50, 100, 5, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(5),
            new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        H2O h2o = new H2O5();

//        while (scanner.hasNext()) {
        while (true) {
            hCount = 0;
            oCount = 0;
//            String line = scanner.nextLine();
            String line = "OOHHHH";
            for (int i = 0, len = line.length(); i < len; i++) {
                if ('H' == line.charAt(i)) {
                    hCount++;
                } else if ('O' == line.charAt(i)) {
                    oCount++;
                }
            }

            System.out.println();
            if (hCount == 0 && oCount == 0) {
                continue;
            }

//            createAndStartTwoThreads(h2o);
            startJobs(h2o);

            TimeUnit.MILLISECONDS.sleep(50);
        }

//        pool1.shutdown();
//        pool2.shutdown();
    }

    private static void startJobs(H2O h2o) {
        for (int i = 0; i < hCount; i++) {
            pool1.execute(() -> {
                try {
                    h2o.hydrogen();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        for (int i = 0; i < oCount; i++) {
            pool2.execute(() -> {
                try {
                    h2o.oxygen();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
