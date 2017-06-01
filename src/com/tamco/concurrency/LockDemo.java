package com.tamco.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Tam-CO on 6/1/17.
 */
public class LockDemo {

    public static int NUM_ITERATIONS = 10;

    // Sharing resource
    private static int balance = 100;

    private static Lock lock = new ReentrantLock();

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

        System.out.println("Balance after 2 threads execution: " + balance);
    }

    private static void increase() {
        lock.lock();
        try {
            balance++;
        } finally {
            lock.unlock();
        }
    }

    private static void decrease() {
        lock.lock();
        try {
            balance--;
        } finally {
            lock.unlock();
        }
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
