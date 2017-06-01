package com.tamco.concurrency;

/**
 * Created by Tam-CO on 6/1/17.
 */
public class TestAndSet {

    public static int NUM_ITERATIONS = 10000;

    // Sharing resource
    private static int balance = 100;

    private static Boolean flag = Boolean.FALSE;

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

    private synchronized static boolean testAndSet() {
        boolean initial = flag.booleanValue();
        flag = Boolean.TRUE;
        return initial;
    }

    private synchronized static void release() {
        flag = Boolean.FALSE;
    }

    private static void increase() {
        while (testAndSet()) { ; /* Waiting here */ }
        balance++;
        release();
    }

    private static void decrease() {
        while (testAndSet()) { ; /* Waiting here */ }
        balance--;
        release();
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
