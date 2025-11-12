package main;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		int opcion = 0;

		do {

			System.out.println("=== DIFERENTES FORMAS DE CREAR HILOS ===");
			System.out.println("1. Lambda Expression (Runnable)");
			System.out.println("2. Con interrupción de hilo");
			System.out.println("3. Clase anónima (Runnable)");
			System.out.println("4. Extendiendo Thread");
			System.out.println("5. Implementando Runnable");
			System.out.println("6. Con prioridades");
			System.out.println("7. Con nombre personalizado");
			System.out.println("8. Thread Daemon");
			System.out.print("9. Salir");
			opcion = sc.nextInt();

			switch (opcion) {
			case 1:
				// FORMA 1: Lambda Expression (más moderna y concisa)
				System.out.println("\n=== FORMA 1: Lambda Expression ===");
				Thread hilo1 = new Thread(() -> run(1));
				Thread hilo2 = new Thread(() -> run(2));

				hilo1.start();
				hilo2.start();

				try {
					hilo1.join();
					hilo2.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				System.out.println("Ambos hilos han finalizado.");
				break;

			case 2:
				// FORMA 2: Con interrupción de hilos
				System.out.println("\n=== FORMA 2: Con Interrupción ===");
				Thread hiloA = new Thread(() -> run(1));
				Thread hiloB = new Thread(() -> run(2));

				hiloA.start();
				hiloB.start();

				try {
					Thread.sleep(5000);
					hiloB.interrupt();
					hiloA.join();
				} catch (InterruptedException e) {
					System.out.println("Hilo B interrumpido.");
				}

				System.out.println("Hilo 1 ha finalizado. Hilo 2 fue interrumpido.");
				break;

			case 3:
				// FORMA 3: Clase anónima implementando Runnable
				System.out.println("\n=== FORMA 3: Clase Anónima (Runnable) ===");
				Thread hilo3 = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							System.out.println("Hilo anónimo iniciado");
							for (int i = 0; i < 5; i++) {
								Thread.sleep(1000);
								System.out.println("Hilo anónimo: " + (i + 1) + " segundos");
							}
							System.out.println("Hilo anónimo finalizado");
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				});
				hilo3.start();
				try {
					hilo3.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;

			case 4:
				// FORMA 4: Extendiendo la clase Thread
				System.out.println("\n=== FORMA 4: Extendiendo Thread ===");
				MiHilo miHilo1 = new MiHilo("Hilo-A");
				MiHilo miHilo2 = new MiHilo("Hilo-B");

				miHilo1.start();
				miHilo2.start();

				try {
					miHilo1.join();
					miHilo2.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Todos los hilos extendidos han finalizado.");
				break;

			case 5:
				// FORMA 5: Implementando la interfaz Runnable
				System.out.println("\n=== FORMA 5: Implementando Runnable ===");
				MiRunnable tarea1 = new MiRunnable("Tarea-1");
				MiRunnable tarea2 = new MiRunnable("Tarea-2");

				Thread t1 = new Thread(tarea1);
				Thread t2 = new Thread(tarea2);

				t1.start();
				t2.start();

				try {
					t1.join();
					t2.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Todos los Runnables han finalizado.");
				break;

			case 6:
				// FORMA 6: Hilos con prioridades
				System.out.println("\n=== FORMA 6: Hilos con Prioridades ===");
				Thread hiloPrioridadAlta = new Thread(() -> {
					for (int i = 0; i < 5; i++) {
						System.out.println("Hilo ALTA prioridad: " + i);
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				});

				Thread hiloPrioridadBaja = new Thread(() -> {
					for (int i = 0; i < 5; i++) {
						System.out.println("Hilo BAJA prioridad: " + i);
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				});

				hiloPrioridadAlta.setPriority(Thread.MAX_PRIORITY); // 10
				hiloPrioridadBaja.setPriority(Thread.MIN_PRIORITY); // 1

				System.out.println("Prioridad alta: " + hiloPrioridadAlta.getPriority());
				System.out.println("Prioridad baja: " + hiloPrioridadBaja.getPriority());

				hiloPrioridadBaja.start();
				hiloPrioridadAlta.start();

				try {
					hiloPrioridadAlta.join();
					hiloPrioridadBaja.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;

			case 7:
				// FORMA 7: Hilos con nombres personalizados
				System.out.println("\n=== FORMA 7: Hilos con Nombres Personalizados ===");
				Thread hiloNombrado1 = new Thread(() -> {
					System.out.println("Soy el hilo: " + Thread.currentThread().getName());
					for (int i = 0; i < 3; i++) {
						System.out.println(Thread.currentThread().getName() + " - iteración " + i);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}, "HiloTrabajador");

				Thread hiloNombrado2 = new Thread(() -> {
					System.out.println("Soy el hilo: " + Thread.currentThread().getName());
					for (int i = 0; i < 3; i++) {
						System.out.println(Thread.currentThread().getName() + " - iteración " + i);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}, "HiloAsistente");

				hiloNombrado1.start();
				hiloNombrado2.start();

				try {
					hiloNombrado1.join();
					hiloNombrado2.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;

			case 8:
				// FORMA 8: Thread Daemon (hilo en segundo plano)
				System.out.println("\n=== FORMA 8: Thread Daemon ===");
				Thread hiloDaemon = new Thread(() -> {
					System.out.println("Hilo Daemon iniciado (en segundo plano)");
					int contador = 0;
					while (true) {
						System.out.println("Daemon ejecutándose... " + contador++);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							break;
						}
					}
				});

				hiloDaemon.setDaemon(true); // Marcarlo como daemon antes de start()
				System.out.println("¿Es daemon? " + hiloDaemon.isDaemon());
				hiloDaemon.start();

				// Hilo principal que termina pronto
				Thread hiloPrincipal = new Thread(() -> {
					for (int i = 0; i < 3; i++) {
						System.out.println("Hilo principal: " + i);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.println("Hilo principal terminado (el daemon se detendrá automáticamente)");
				});

				hiloPrincipal.start();

				try {
					hiloPrincipal.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// Dar un poco de tiempo para ver el daemon antes de que termine
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			case 9:
				System.out.println("Hasta la Vista Malabarista!");
				break;

			default:
				System.out.println("Opción no válida");
				break;
			}
		} while (opcion != 9);
		sc.close();
	}

	public static void run(int hiloNumero) {
		try {
			System.out.println("Hilo " + hiloNumero + " iniciado");
			for (int i = 0; i < 10; i++) {
				Thread.sleep(1000);
				System.out.println("Hilo " + hiloNumero + ": " + (i + 1) + " segundos");
			}
			System.out.println("Hilo " + hiloNumero + " finalizado");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

// FORMA 4: Clase que extiende Thread
class MiHilo extends Thread {
	private String nombre;

	public MiHilo(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public void run() {
		try {
			System.out.println(nombre + " iniciado");
			for (int i = 0; i < 5; i++) {
				Thread.sleep(1000);
				System.out.println(nombre + ": " + (i + 1) + " segundos");
			}
			System.out.println(nombre + " finalizado");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

// FORMA 5: Clase que implementa Runnable
class MiRunnable implements Runnable {
	private String nombre;

	public MiRunnable(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public void run() {
		try {
			System.out.println(nombre + " iniciado");
			for (int i = 0; i < 5; i++) {
				Thread.sleep(1000);
				System.out.println(nombre + ": " + (i + 1) + " segundos");
			}
			System.out.println(nombre + " finalizado");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}