package procesos;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Proceso {
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		try {
			ProcessBuilder pb = new ProcessBuilder("java", "procesos.SumadorRestador");
			pb.directory(new File("bin"));
			pb.redirectOutput(new File("salida.txt"));
			Process proces = pb.start();
			System.out.println("PID: " + proces.pid());
			System.out.println("PID Padre: " + proces.toHandle().parent().get().pid());
			int opcion = 0;
			int operador1;
			int operador2;

			do {
				System.out.println("¿Que quieres hacer?\n1-. Sumar\n2-. Restar");
				opcion = teclado.nextInt();
				if (opcion == 1 || opcion == 2) {
					proces.getOutputStream().write((opcion + "\n").getBytes());
					proces.getOutputStream().flush();
				}
			} while (opcion != 1 && opcion != 2);
			System.out.println("Operador 1:");
			operador1 = teclado.nextInt();
			proces.getOutputStream().write((operador1 + "\n").getBytes());
			proces.getOutputStream().flush();

			System.out.println("Operador 2:");
			operador2 = teclado.nextInt();
			proces.getOutputStream().write((operador2 + "\n").getBytes());
			proces.getOutputStream().flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
		teclado.close();
	}
}
