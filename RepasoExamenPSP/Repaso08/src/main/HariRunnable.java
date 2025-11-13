package main;

public class HariRunnable implements Runnable {

	private String izena;

	public HariRunnable(String izena) {
		this.izena = izena;
	}

	@Override
	public void run() {
		for (int i = 1; i <= 5; i++) {
			System.out.println(izena + " - Iteracion: " + i);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
}