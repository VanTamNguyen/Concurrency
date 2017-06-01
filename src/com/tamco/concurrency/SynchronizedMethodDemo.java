package com.tamco.concurrency;

public class SynchronizedMethodDemo {

	public static int NUM_ITERATIONS = 10;

	// Sharing resource
	private static int balance = 100;

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

	private synchronized static void increase() {
		balance++;
	}

	private synchronized static void decrease() {
		balance--;
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



