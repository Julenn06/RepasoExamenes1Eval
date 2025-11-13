package main;

import static main.NagusiaArik4.detonagailuaSortu;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		int aukera = 0;

		System.out.println("Auperatu zer egin nahi duzun:");
		System.out.println("1. Contar hasta 1000 ");
		System.out.println("2. Hilos con Runnable");
		System.out.println("3. Hilos con Thread");
		System.out.println("4. Hilos con clase Persona");
		System.out.println("5. Escritor y Lector");
		System.out.println("6. Productor y Consumidor");

		aukera = sc.nextInt();

		switch (aukera) {
		case 1:
			Thread hilo = new Thread(new ContarHasta1000());
			hilo.start();
			break;

		case 2:
			Thread hilo1 = new Thread(new HariRunnable("Hilo 1"));
			Thread hilo2 = new Thread(new HariRunnable("Hilo 2"));
			hilo1.start();
			hilo2.start();

			while (hilo1.isAlive() || hilo2.isAlive()) {
				System.out.println("Esperando a que los hilos terminen...");
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			System.out.println("Ambos hilos han terminado.");
			break;

		case 3:
			HiloThread hiloT1 = new HiloThread("Hilo T1");
			HiloThread hiloT2 = new HiloThread("Hilo T2");
			hiloT1.start();	
			hiloT2.start();
			break;

		case 4:
			break;

		case 5:
			break;
			
		case 6:
			detonagailuaSortu();
			break;

		}
		sc.close();
	}
}