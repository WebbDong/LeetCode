package com.dong.leetcode.medium.multithreading;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 设计有限阻塞队列
 *
 * 实现一个拥有如下方法的线程安全有限阻塞队列：
 *      1、BoundedBlockingQueue(int capacity) 构造方法初始化队列，其中capacity代表队列长度上限。
 *      2、void enqueue(int element) 在队首增加一个element. 如果队列满，调用线程被阻塞直到队列非满。
 *      3、int dequeue() 返回队尾元素并从队列中将其删除. 如果队列为空，调用线程被阻塞直到队列非空。
 *      4、int size() 返回当前队列元素个数。
 *
 * 你的实现将会被多线程同时访问进行测试。每一个线程要么是一个只调用enqueue方法的生产者线程，要么是一个只调用dequeue方法的消费者线程。
 * size方法将会在每一个测试用例之后进行调用。
 *
 * 请不要使用内置的有限阻塞队列实现，否则面试将不会通过。
 *
 * 示例 1:
 * 输入:
 *      1
 *      1
 *      ["BoundedBlockingQueue","enqueue","dequeue","dequeue","enqueue","enqueue","enqueue","enqueue","dequeue"]
 *      [[2],[1],[],[],[0],[2],[3],[4],[]]
 *
 * 输出:
 *      [1,0,2,2]
 *
 * 解释:
 *      生产者线程数目 = 1
 *      消费者线程数目 = 1
 *
 *      BoundedBlockingQueue queue = new BoundedBlockingQueue(2);   // 使用capacity = 2初始化队列。
 *
 *      queue.enqueue(1);   // 生产者线程将1插入队列。
 *      queue.dequeue();    // 消费者线程调用dequeue并返回1。
 *      queue.dequeue();    // 由于队列为空，消费者线程被阻塞。
 *      queue.enqueue(0);   // 生产者线程将0插入队列。消费者线程被解除阻塞同时将0弹出队列并返回。
 *      queue.enqueue(2);   // 生产者线程将2插入队列。
 *      queue.enqueue(3);   // 生产者线程将3插入队列。
 *      queue.enqueue(4);   // 生产者线程由于队列长度已达到上限2而被阻塞。
 *      queue.dequeue();    // 消费者线程将2从队列弹出并返回。生产者线程解除阻塞同时将4插入队列。
 *      queue.size();       // 队列中还有2个元素。size()方法在每组测试用例最后调用。
 *
 * 示例 2:
 * 输入:
 *      3
 *      4
 *      ["BoundedBlockingQueue","enqueue","enqueue","enqueue","dequeue","dequeue","dequeue","enqueue"]
 *      [[3],[1],[0],[2],[],[],[],[3]]
 *
 * 输出:
 *      [1,0,2,1]
 *
 * 解释:
 *      生产者线程数目 = 3
 *      消费者线程数目 = 4
 *
 *      BoundedBlockingQueue queue = new BoundedBlockingQueue(3);   // 使用capacity = 3初始化队列。
 *
 *      queue.enqueue(1);   // 生产者线程P1将1插入队列。
 *      queue.enqueue(0);   // 生产者线程P2将0插入队列。
 *      queue.enqueue(2);   // 生产者线程P3将2插入队列。
 *      queue.dequeue();    // 消费者线程C1调用dequeue。
 *      queue.dequeue();    // 消费者线程C2调用dequeue。
 *      queue.dequeue();    // 消费者线程C3调用dequeue。
 *      queue.enqueue(3);   // 其中一个生产者线程将3插入队列。
 *      queue.size();       // 队列中还有1个元素。
 *
 * 由于生产者/消费者线程的数目可能大于1，我们并不知道线程如何被操作系统调度，即使输入看上去隐含了顺序。
 * 因此任意一种输出[1,0,2]或[1,2,0]或[0,1,2]或[0,2,1]或[2,0,1]或[2,1,0]都可被接受。
 */
public class DesignBoundedBlockingQueue_1188 {

    private interface Queue {

        void enqueue(int element) throws InterruptedException;

        int dequeue() throws InterruptedException;

        int size();

    }

    /**
     * 方法一、使用 Lock 和 Condition
     */
    private static class BoundedBlockingQueue1 implements Queue {

        private int[] items;

        private int count;

        private int putIndex;

        private int takeIndex;

        private final Lock lock = new ReentrantLock();

        private final Condition notFull = lock.newCondition();

        private final Condition notEmpty = lock.newCondition();

        public BoundedBlockingQueue1(int capacity) {
            items = new int[capacity];
        }

        public void enqueue(int element) throws InterruptedException {
            try {
                lock.lock();
                while (count == items.length) {
                    // 队列已满，阻塞入队操作
                    notFull.await();
                }
                items[putIndex++] = element;
                if (putIndex == items.length) {
                    putIndex = 0;
                }
                count++;
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }

        public int dequeue() throws InterruptedException {
            try {
                lock.lock();
                while (count == 0) {
                    // 队列已空，阻塞出队操作
                    notEmpty.await();
                }
                int data = items[takeIndex++];
                if (takeIndex == items.length) {
                    takeIndex = 0;
                }
                count--;
                notFull.signal();
                return data;
            } finally {
                lock.unlock();
            }
        }

        public int size() {
            try {
                lock.lock();
                return count;
            } finally {
                lock.unlock();
            }
        }

    }

    /**
     * 方法二、使用信号量
     */
    private static class BoundedBlockingQueue2 implements Queue {

        private int[] items;

        private int count;

        private int putIndex;

        private int takeIndex;

        private final Semaphore notEmptySemaphore;

        private final Semaphore notFullSemaphore;

        public BoundedBlockingQueue2(int capacity) {
            items = new int[capacity];
            notEmptySemaphore = new Semaphore(capacity);
            notFullSemaphore = new Semaphore(0);
        }

        public void enqueue(int element) throws InterruptedException {
            notEmptySemaphore.acquire();
            items[putIndex++] = element;
            if (putIndex == items.length) {
                putIndex = 0;
            }
            count++;
            notFullSemaphore.release();
        }

        public int dequeue() throws InterruptedException {
            notFullSemaphore.acquire();
            int data = items[takeIndex++];
            if (takeIndex == items.length) {
                takeIndex = 0;
            }
            count--;
            notEmptySemaphore.release();
            return data;
        }

        public int size() {
            return count;
        }

    }

    /**
     * 方法三、使用 synchronized
     */
    private static class BoundedBlockingQueue3 implements Queue {

        private int[] items;

        private int count;

        private int putIndex;

        private int takeIndex;

        public BoundedBlockingQueue3(int capacity) {
            items = new int[capacity];
        }

        public void enqueue(int element) throws InterruptedException {
            synchronized (this) {
                while (count == items.length) {
                    this.wait();
                }
                items[putIndex++] = element;
                if (putIndex == items.length) {
                    putIndex = 0;
                }
                count++;
                this.notifyAll();
            }
        }

        public int dequeue() throws InterruptedException {
            synchronized (this) {
                while (count == 0) {
                    this.wait();
                }
                int data = items[takeIndex++];
                if (takeIndex == items.length) {
                    takeIndex = 0;
                }
                count--;
                this.notifyAll();
                return data;
            }
        }

        public int size() {
            return count;
        }

    }

    private static ThreadPoolExecutor producerPool = new ThreadPoolExecutor(
            50, 100, 5, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(5),
            new ThreadPoolExecutor.AbortPolicy());

    private static ThreadPoolExecutor consumerPool = new ThreadPoolExecutor(
            50, 100, 5, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(5),
            new ThreadPoolExecutor.AbortPolicy());

    private static Queue queue;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            // 生产者线程数量
            int producerCount;
            // 消费者线程数量
            int consumerCount;
            try {
                producerCount = Integer.parseInt(scanner.nextLine());
                consumerCount = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                continue;
            }
            String command = scanner.next();
            String param = scanner.next();
            TaskGroup taskGroup = parse(command, param);
            startTasks(taskGroup, producerCount, consumerCount);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class TaskGroup {

        private List<Runnable> enqueueTaskList = new ArrayList<>();

        private List<Runnable> dequeueTaskList = new ArrayList<>();

    }

    @Data
    @AllArgsConstructor
    private static class EnqueueTask implements Runnable {

        private int data;

        @SneakyThrows
        @Override
        public void run() {
            queue.enqueue(data);
        }

    }

    private static class DequeueTask implements Runnable {

        @SneakyThrows
        @Override
        public void run() {
            int data = queue.dequeue();
            System.out.print(data + ",");
        }

    }

    private static void startTasks(TaskGroup taskGroup, int producerCount, int consumerCount) {
        taskGroup.enqueueTaskList.forEach(r -> producerPool.execute(r));
        taskGroup.dequeueTaskList.forEach(r -> consumerPool.execute(r));
    }

    private static TaskGroup parse(String command, String param) {
        List<String> commandList = parseCommand(command);
        List<Integer> paramList = parseParam(param);
        List<Runnable> enqueueTaskList = new ArrayList<>();
        List<Runnable> dequeueTaskList = new ArrayList<>();
        for (int i = 0, j = 0, size = commandList.size(); i < size; i++) {
            String c = commandList.get(i);
            if ("BoundedBlockingQueue".equals(c)) {
//                queue = new BoundedBlockingQueue1(paramList.get(j++));
//                queue = new BoundedBlockingQueue2(paramList.get(j++));
                queue = new BoundedBlockingQueue3(paramList.get(j++));
            } else if ("enqueue".equals(c)) {
                enqueueTaskList.add(new EnqueueTask(paramList.get(j++)));
            } else if ("dequeue".equals(c)) {
                dequeueTaskList.add(new DequeueTask());
            }
        }
        return new TaskGroup(enqueueTaskList, dequeueTaskList);
    }

    private static List<Integer> parseParam(String param) {
        List<Integer> paramList = new ArrayList<>();
        Matcher matcher = Pattern.compile("\\d+").matcher(param);
        while (matcher.find()) {
            paramList.add(Integer.parseInt(matcher.group()));
        }
        return paramList;
    }

    private static List<String> parseCommand(String command) {
        List<String> commandList = new ArrayList<>();
        Matcher matcher = Pattern.compile("\\w+").matcher(command);
        while (matcher.find()) {
            commandList.add(matcher.group());
        }
        return commandList;
    }

}
