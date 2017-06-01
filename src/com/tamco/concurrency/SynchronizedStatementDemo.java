package com.tamco.concurrency;

/**
 * Created by Tam-CO on 6/1/17.
 */
public class SynchronizedStatementDemo {

    public static int NUM_ITERATIONS = 10000;

    private static int foo = 100;

    private static int bar = 100;

    private static Object barLock = new Object();

    private static Object fooLock = new Object();

    public static void main(String[] args) {
        Thread threadA = new Thread(new IncreaseFooTask());
        Thread threadB = new Thread(new DecreaseFooTask());
        Thread threadC = new Thread(new IncreaseBarTask());
        Thread threadD = new Thread(new DecreaseBarTask());

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

        try {
            threadA.join();
            threadB.join();
            threadC.join();
            threadD.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Foo after 4 threads execution: " + foo);
        System.out.println("Bar after 4 threads execution: " + bar);
    }

    private static void increaseFoo() {
        synchronized (fooLock) {
            foo++;
        }
    }

    private static void decreaseFoo() {
        synchronized (fooLock) {
            foo--;
        }
    }

    private static void increaseBar() {
        synchronized (barLock) {
            bar++;
        }
    }

    private static void decreaseBar() {
        synchronized (barLock) {
            bar--;
        }
    }

    public static class IncreaseFooTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < NUM_ITERATIONS; i++) {
                increaseFoo();
            }
        }
    }

    public static class DecreaseFooTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < NUM_ITERATIONS; i++) {
                decreaseFoo();
            }
        }
    }

    public static class IncreaseBarTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < NUM_ITERATIONS; i++) {
                increaseBar();
            }
        }
    }

    public static class DecreaseBarTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < NUM_ITERATIONS; i++) {
                decreaseBar();
            }
        }
    }
}
