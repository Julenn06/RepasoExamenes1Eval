package ariketa01;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		int aukera = 0;

		System.out.println("1. Ariketa 1");
		aukera = sc.nextInt();

		switch (aukera) {

		case 1:
			// Ariketa 01
			HariRunnable hr1 = new HariRunnable("Hilo 1");
			HariRunnable hr2 = new HariRunnable("Hilo 2");
			Thread hilo1 = new Thread(hr1);
			Thread hilo2 = new Thread(hr2);
			hilo1.start();
			hilo2.start();
			break;

		}

		sc.close();
	}
}