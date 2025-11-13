package ariketa01;

public class HariRunnable implements Runnable {
	
	private String izena;

	public HariRunnable(String izena) {
		this.izena = izena;
	}

	@Override
	public void run() {
		for (int i = 1; i <= 1000; i++) {
			System.out.println(izena + ": " + i);
			try {
				Thread.sleep(2); // Pixka bat motelago egingo dugu
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.out.println(izena + " etenda izan da.");
				return;
			}
		}
		System.out.println(izena + " amaituta!");
	}
}