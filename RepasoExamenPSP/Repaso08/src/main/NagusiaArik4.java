package main;

public class NagusiaArik4 {

	public static void detonagailuaSortu() {
		DetonagailuaConRetardo hilo1 = new DetonagailuaConRetardo("Hilo 1", 1000);
		DetonagailuaConRetardo hilo2 = new DetonagailuaConRetardo("Hilo 2", 2000);
		hilo1.start();
		hilo2.start();
	}
}
