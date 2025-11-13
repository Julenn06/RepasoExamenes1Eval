package ariketa02;

public class Main {

	public static void main(String[] args) {
		// Ariketa 02

		// HariRunnable izen klase bat sortzea. Programa nagusiak bi hari jaurtiko ditu
		// (Runnable interfazea erabiliz) eta, ondoren, begizta bat exekutatuko du.
		// Erabili isAlive hariak noiz amaitzen diren egiaztatzeko
		
		HariRunnable hr1 = new HariRunnable("Hilo 1");
		HariRunnable hr2 = new HariRunnable("Hilo 2");
		Thread hilo1 = new Thread(hr1);
		Thread hilo2 = new Thread(hr2);
		hilo1.start();
		hilo2.start();

		// Begizta bat exekutatu hariak amaitu arte
		System.out.println("Programa nagusia: hariak kontrolatzen...");
		
		while (hilo1.isAlive() || hilo2.isAlive()) {
			System.out.println("Hilo 1 bizirik: " + hilo1.isAlive() + 
							   ", Hilo 2 bizirik: " + hilo2.isAlive());
			try {
				Thread.sleep(500); // 500ms itxaron hurrengo egiaztapen baino lehen
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
		
		System.out.println("Bi hariak amaitu dira!");
		System.out.println("Programa nagusia amaitzen da.");
	}

}