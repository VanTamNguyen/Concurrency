package com.tamco.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo {
    public static int NUM_ITERATIONS = 10000;
    private static AtomicInteger balance = new AtomicInteger(100);

    public static void main(String[] args) {
        Thread threadA = new Thread(new IncreaseTask());
        Thread threadB = new Thread(new DecreaseTask());

        threadA.start();
        threadB.start();

        try {
            threadA.join();
            threadB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Balance after 2 threads execution: " + balance.get());
    }

    private static void increase() {
        balance.incrementAndGet();
    }

    private static void decrease() {
        balance.decrementAndGet();
    }

    public static class IncreaseTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < NUM_ITERATIONS; i++) {
                increase();
            }
        }
    }

    public static class DecreaseTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < NUM_ITERATIONS; i++) {
                decrease();
            }
        }
    }
} 