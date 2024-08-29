package org.example;

import java.util.Random;

public class Main {
    private static final long nbPoint = 10_000_000_000L;
    private static long count = 0;

    public static void main(String[] args) {
        System.out.println("Monte Carlo Simulation to estimate Pi");
        long startTime = System.currentTimeMillis();
        for (long i = 0; i < nbPoint; i++) {
            Random random = new Random();
            double x = -1 + 2 * random.nextDouble();
            double y = -1 + 2 * random.nextDouble();
            if (x * x + y * y <= 1) {
                count++;
            }
        }
        double pi = 4 * (double) count / (double) nbPoint;
        System.out.println("Estimated value of Pi: " + pi);
        long endTime = System.currentTimeMillis();
        long elapsedTimeMillis = endTime - startTime;
        double elapsedTimeSeconds = elapsedTimeMillis / 1000.0;
        System.out.println("Elapsed time in seconds: " + elapsedTimeSeconds);
    }
}