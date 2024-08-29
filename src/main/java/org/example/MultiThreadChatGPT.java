package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class MultiThreadChatGPT {
    private static final long nbPoint = 10_000_000_000L;
    private static final int nbThread = 8;
    private static final AtomicLong count = new AtomicLong(0);

    public static void main(String[] args) {
        IntStream.range(0, nbThread).parallel().forEach(i -> {
            Random random = new Random();
            int countThread = 0;
            for (long j = 0; j < nbPoint / nbThread; j++) {
                double x = -1 + 2 * random.nextDouble();
                double y = -1 + 2 * random.nextDouble();
                if (x * x + y * y <= 1) {
                    countThread++;
                }
            }
            count.addAndGet(countThread);
        });
        double pi = 4 * (double) count.get() / (double) nbPoint;
        System.out.println("Estimated value of Pi: " + pi); //3,141592_653589793
    }
}
