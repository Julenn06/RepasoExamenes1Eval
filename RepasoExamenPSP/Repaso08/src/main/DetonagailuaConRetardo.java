package main;

public class DetonagailuaConRetardo {

	// - Haria exekutatzen denean, idatzi haren izena eta kontagailua. Ondoren,
	// kontagailuaren balioa 1 murrizten du. Errepikatu ekintza horiek
	// kontagailuaren balioa 0 izan arte - Ataza osatzean, amaitu dela jakinarazten
	// du.

	private String izena;
	private int retardo;

	public DetonagailuaConRetardo(String izena, int retardo) {
		this.izena = izena;
		this.retardo = retardo;
	}

	public void start() {
		Thread thread = new Thread(() -> {
			for (int i = retardo; i >= 0; i--) {
				System.out.println(izena + " - Kontagailua: " + i);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			System.out.println(izena + " - Detonazioa!");
		});
		thread.start();
	}
}
