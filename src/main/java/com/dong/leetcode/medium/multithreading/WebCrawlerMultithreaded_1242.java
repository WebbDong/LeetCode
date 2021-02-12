package com.dong.leetcode.medium.multithreading;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * 多线程网页爬虫
 */
public class WebCrawlerMultithreaded_1242 {

    interface HtmlParser {

        List<String> getUrls(String url);

    }

    private static class Solution1 {

        private BlockingQueue<String> urlQueue = new LinkedBlockingDeque<>();
        private BlockingQueue<String> statusQueue = new LinkedBlockingDeque<>();
        private Map<String, Integer> urlStatus = new ConcurrentHashMap<>();
        private volatile String mainHost;

        //    ExecutorService service=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);
        public List<String> crawl(String startUrl, HtmlParser htmlParser) {
            int n = Runtime.getRuntime().availableProcessors() * 2 + 2;
            urlQueue.add(startUrl);
            mainHost = getHost(startUrl);
            for (int i = 0; i < n; ++i) {
                new Thread(new Crawler(htmlParser)).start();
            }
            String msg;
            while (true) {
                try {
                    msg = statusQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
                if (msg != null) {
                    if (msg.equals("--")) {
                        n--;
                        if (n == 0) {
                            break;
                        }
                    } else {
                        urlStatus.put(msg, 1);
                    }
                }
            }
            return new ArrayList<>(urlStatus.keySet());
        }

        private String getHost(String startUrl) {
            if(startUrl.startsWith("http://")){
                return startUrl.replace("http://","").split("/")[0];
            }
            throw new IllegalArgumentException("url illegal");
        }

        private class Crawler implements Runnable {

            private final HtmlParser htmlParser;

            public Crawler(HtmlParser htmlParser) {
                this.htmlParser = htmlParser;
            }

            @Override
            public void run() {
                String url;
                int emptyTimes = 0;
                try {
                    while (true) {
                        url = urlQueue.poll(17, TimeUnit.MILLISECONDS);
                        if (url == null) {
                            emptyTimes++;
                            if (emptyTimes > 2) {
                                statusQueue.offer("--");
                                break;
                            }
                        } else {
                            if (!urlStatus.containsKey(url)) {
                                List<String> urls = htmlParser.getUrls(url);
                                statusQueue.offer(url);
                                boolean hasNew = false;
                                if (urls != null && urls.size() > 0) {
                                    for (String u : urls) {
                                        if (!urlStatus.containsKey(u) && getHost(u).equals(mainHost)) {
                                            hasNew = true;
                                            urlQueue.offer(u);
                                        }
                                    }
                                }
                                if (!hasNew && urlQueue.size() == 0) {
                                    statusQueue.offer("--");
                                    break;
                                }
                            }
                            if (emptyTimes > 0) emptyTimes--;
                        }
                    }
                } catch (InterruptedException ex) {
                    statusQueue.offer("--");
                    ex.printStackTrace();
                }
            }
        }

    }

    private static class Solution2 {

        // ExecutorService service=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);
        public List<String> crawl(String startUrl, HtmlParser htmlParser) {
            String hostName = startUrl.split("/")[2];
            Crawler mainCrawler = new Crawler(startUrl, hostName, htmlParser);
            mainCrawler.result = new HashSet<>();
            Thread thread = new Thread(mainCrawler);
            thread.start();
            // 等待线程执行完毕
            try {
                thread.join();
            } catch (Exception e) {
            }
            return new ArrayList<>(mainCrawler.result);
        }

        private static class Crawler implements Runnable {

            private String startUrl;
            private String hostName;
            private HtmlParser htmlParser;

            // volatile: we ask processor not to reorder any
            // instruction involving the volatile variable. Also, processors understand
            // that they should flush any updates to these variables right away.
            // static: Only one result exist per JVM per class.
            private static volatile Set<String> result;

            public Crawler(String url, String host, HtmlParser htmlParser) {
                this.startUrl = url;
                this.hostName = host;
                this.htmlParser = htmlParser;
            }

            @Override
            public void run() {
                if (!startUrl.contains(hostName)
                        || result.contains(startUrl)) {
                    return;
                }
                addUrl(result, startUrl);
                List<Thread> threads = new ArrayList<>();

                for (String url : htmlParser.getUrls(startUrl)) {
                    Crawler childCrawler = new Crawler(url, hostName, htmlParser);
                    Thread childThread = new Thread(childCrawler);
                    // Child thread can start now
                    childThread.start();
                    threads.add(childThread);
                }

                // Wait for all child threads finish before terminating current thread.
                // Otherwise it will return partial results.
                for (Thread thread : threads) {
                    try {
                        thread.join();
                    } catch (Exception e) {
                    }
                }
            }

            // thus all synchronized blocks of the same object can have only one thread
            // executing them at the same time.
            public static synchronized void addUrl(Set<String> result, String url) {
                result.add(url);
            }

        }

    }

    public static void main(String[] args) {
        String[] splits = "http://news.yahoo.com/news/topics/".split("/");
        System.out.println(splits);
    }

}
