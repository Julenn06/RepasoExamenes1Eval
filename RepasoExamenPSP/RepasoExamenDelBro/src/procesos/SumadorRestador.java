package procesos;

import java.util.Scanner;

public class SumadorRestador {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int resultado = 0;
		int opcion = sc.nextInt();
		int operador1 = sc.nextInt();
		int operador2 = sc.nextInt();

		if (opcion == 1)
			resultado = operador1 + operador2;
		else
			resultado = operador1 - operador2;
		System.out.println("Resultado:" + resultado);
		sc.close();

	}

}
