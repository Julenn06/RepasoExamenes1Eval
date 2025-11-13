package main;

public class ContarHasta1000 implements Runnable {

	public static void contarHasta1000() {
		Thread hilo1 = new Thread(() -> {
			for (int i = 1; i <= 500; i++) {
				System.out.println(i);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		});

		Thread hilo2 = new Thread(() -> {
			for (int i = 501; i <= 1000; i++) {
				System.out.println(i);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		});

		hilo1.start();
		if (!hilo1.isAlive()) {
			hilo2.start();
		} else {
			try {
				hilo1.join();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	@Override
	public void run() {
		contarHasta1000();
	}
}
