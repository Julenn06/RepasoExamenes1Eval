package ariketa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		int aukera = 0;

		System.out.println("Aukeratu zbk bat:");
		aukera = sc.nextInt();

		switch (aukera) {

		case 1:
			try {
				ProcessBuilder pb = new ProcessBuilder("notepad");
				pb.start();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			break;

		case 2:
			printCommandOutput(new String[] { "cmd", "/c", "dir" });
			break;

		case 3:
			printCommandOutput(new String[] { "cmd", "/c", "getmac" });
			break;

		case 4:
			printCommandOutput(new String[] { "cmd", "/c", "tasklist" });
			break;

		case 5:
			printCommandOutput(new String[] { "cmd", "/c", "taskkill /IM notepad.exe /F" });
			break;

		case 6:
			printCommandOutput(new String[] { "cmd", "/c", "script.bat" });
			break;

		case 7:
			printCommandOutput(new String[] { "cmd", "/c", "java -cp bin ariketa.EjemploLectura" });
			break;
		}

		sc.close();

	}

	private static void printCommandOutput(String[] command) {
		try {
			ProcessBuilder pb = new ProcessBuilder(command);
			pb.redirectErrorStream(true);
			Process process = pb.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			process.waitFor();
		} catch (IOException | InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}