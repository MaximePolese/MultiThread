package org.example;

import java.util.Random;

public class Main {
    private static final int nbPoint = 1000000000;
    private static int count = 0;

    public static void main(String[] args) {
        System.out.println("Monte Carlo Simulation to estimate Pi");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < nbPoint; i++) {
            Random random = new Random();
            double x = -1 + 2 * random.nextDouble();
            double y = -1 + 2 * random.nextDouble();
            if (x * x + y * y <= 1) {
                count++;
            }
        }
        double pi = 4 * (double) count / nbPoint;
        System.out.println("Estimated value of Pi: " + pi);
        long endTime = System.currentTimeMillis();
        long elapsedTimeMillis = endTime - startTime;
        double elapsedTimeSeconds = elapsedTimeMillis / 1000.0;
        System.out.println("Elapsed time in seconds: " + elapsedTimeSeconds);
    }
}