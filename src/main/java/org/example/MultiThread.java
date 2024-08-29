package org.example;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MultiThread {
    private static final int nbPoint = 1000000000;
    private static final int nbThread = 8;
    private static int count = 0;

    static class Worker implements Runnable {
        public int countWorker = 0;
        private CountDownLatch latch;
//        private Lock lock = new ReentrantLock();

        public Worker(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            Random random = new Random();
            for (int j = 0; j < nbPoint / nbThread; j++) {
                double x = -1 + 2 * random.nextDouble();
                double y = -1 + 2 * random.nextDouble();
                if (x * x + y * y <= 1) {
                    countWorker++;
                }
            }
            // Version 1: Using ReentrantLock
//            lock.lock();
//            count += countWorker;
//            lock.unlock();
            // Version 2: Using synchronized block in Java
//            synchronized (this) {
//                count += countWorker;
//            }
            latch.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Monte Carlo Simulation to estimate Pi");

        CountDownLatch latch = new CountDownLatch(nbThread);

        long startTime = System.currentTimeMillis();
        //CPU load
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        double startCpuLoad = osBean.getProcessCpuLoad();
        if (startCpuLoad < 0) {
            System.out.println("Impossible de mesurer la charge CPU initiale.");
            startCpuLoad = 0;
        }

        Thread[] threads = new Thread[nbThread];
        Worker[] workers = new Worker[nbThread];

        for (int i = 0; i < nbThread; i++) {
            workers[i] = new Worker(latch);
            threads[i] = new Thread(workers[i]);
            threads[i].start();
        }

        latch.await();

        // Version 2: Using join() method and incrementing count after all threads are finished
//        for (Thread thread : threads) {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        for (Worker worker : workers) {
            count += worker.countWorker;
        }

        double pi = 4 * (double) count / nbPoint;
        System.out.println("Estimated value of Pi: " + pi);

        long endTime = System.currentTimeMillis();
        long elapsedTimeMillis = endTime - startTime;
        double elapsedTimeSeconds = elapsedTimeMillis / 1000.0;
        System.out.println("Elapsed time in seconds: " + elapsedTimeSeconds);

        double endCpuLoad = osBean.getProcessCpuLoad();
        if (endCpuLoad < 0) {
            System.out.println("Impossible de mesurer la charge CPU finale.");
        }
        double averageCpuLoad = (startCpuLoad + endCpuLoad) / 2 * 100;
        System.out.printf("Average CPU Load: %.2f%%\n", averageCpuLoad);
    }
}