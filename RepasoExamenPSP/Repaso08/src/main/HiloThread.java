package main;

public class HiloThread {
	private String izena;

	public HiloThread(String izena) {
		this.izena = izena;
	}

	public void start() {
		Thread thread = new Thread(() -> {
			for (int i = 1; i <= 5; i++) {
				System.out.println(izena + " - Iteracion: " + i);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		});
		thread.start();
	}

}
