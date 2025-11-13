package main;

import javax.swing.JLabel;

public class HilosService implements Runnable {

	private JLabel lblCronometro;
	private JLabel lblDescanso;
	private boolean enPausa;
	private boolean terminado = false;
	private boolean enDescanso = false;
	private long inicioDescanso = 0L;

	public HilosService(JLabel lblCronometro, JLabel lblDescanso) {
		this.lblCronometro = lblCronometro;
		this.lblDescanso = lblDescanso;
	}

	public void run() {

		int total = 5400;
		long inicio = System.currentTimeMillis();
		long pausaAcum = 0L;
		long inicioPausaLocal = 0L;

		while (!terminado) {
			if (enPausa || enDescanso) {
				if (inicioPausaLocal == 0L) {
					inicioPausaLocal = System.currentTimeMillis();
				}

				// Si estamos en descanso, actualizar el contador de descanso
				if (enDescanso) {
					long tiempoDescanso = (System.currentTimeMillis() - inicioDescanso) / 1000;
					lblDescanso.setText(formatear((int) tiempoDescanso));
				}
			} else {
				if (inicioPausaLocal != 0L) {
					pausaAcum += System.currentTimeMillis() - inicioPausaLocal;
					inicioPausaLocal = 0L;
				}

				long ahora = System.currentTimeMillis();
				long trans = (ahora - inicio - pausaAcum);
				int restante = (int) Math.max(0, total - (trans / 1000));
				lblCronometro.setText(formatear(restante));

				if (restante <= 0) {
					terminado = true;
				}
			}

			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/* Cronometro a la inversa */
	/*
	 * public void run() {
	 * 
	 * long inicio = System.currentTimeMillis(); long pausaAcum = 0L; long
	 * inicioPausaLocal = 0L;
	 * 
	 * while (!terminado) { if (enPausa) { if (inicioPausaLocal == 0L) {
	 * inicioPausaLocal = System.currentTimeMillis(); } } else { if
	 * (inicioPausaLocal != 0L) { pausaAcum += System.currentTimeMillis() -
	 * inicioPausaLocal; inicioPausaLocal = 0L; }
	 * 
	 * long ahora = System.currentTimeMillis(); long trans = (ahora - inicio -
	 * pausaAcum); int transcurrido = (int) (trans / 1000);
	 * lblCronometro.setText(formatear(transcurrido)); }
	 * 
	 * try { Thread.sleep(200); } catch (InterruptedException e) {
	 * e.printStackTrace(); } } }
	 */

	private String formatear(int tsegundos) {
		int hh = (tsegundos / 3600);
		int mm = ((tsegundos - hh * 3600) / 60);
		int ss = tsegundos - (hh * 3600 + mm * 60);
		return String.format("%02d:%02d:%02d", hh, mm, ss);
	}

	public void terminar() {
		terminado = true;
		enPausa = false;
	}

	public void pararContinuar() {
		if (enPausa) {
			enPausa = false;
		} else {
			enPausa = true;
		}
	}

	public void descanso(JLabel lblDescanso) {
		if (!enDescanso) {
			// Iniciar descanso
			enDescanso = true;
			enPausa = false; // Asegurar que no esté en pausa normal
			inicioDescanso = System.currentTimeMillis();
		} else {
			// Terminar descanso
			enDescanso = false;
			lblDescanso.setText("00:00:00");
		}
	}
}