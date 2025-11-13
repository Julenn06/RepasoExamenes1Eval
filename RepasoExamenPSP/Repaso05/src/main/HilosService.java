package main;

import javax.swing.JLabel;

public class HilosService implements Runnable {

	private JLabel lblCronometro;
	private boolean enPausa = false;
	private boolean terminado = false;
	private long tiempoTranscurrido = 0L; // En milisegundos
	private long ultimoTiempo = 0L;

	public HilosService(JLabel lblCronometro) {
		this.lblCronometro = lblCronometro;
	}

	@Override
	public void run() {
		ultimoTiempo = System.currentTimeMillis();

		while (!terminado) {
			if (!enPausa) {
				long ahora = System.currentTimeMillis();
				tiempoTranscurrido += (ahora - ultimoTiempo);
				ultimoTiempo = ahora;

				int segundosTotales = (int) (tiempoTranscurrido / 1000);
				lblCronometro.setText(formatear(segundosTotales));
			} else {
				// Actualizar el último tiempo para evitar saltos al reanudar
				ultimoTiempo = System.currentTimeMillis();
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private String formatear(int tsegundos) {
		int hh = (tsegundos / 3600);
		int mm = ((tsegundos - hh * 3600) / 60);
		int ss = tsegundos - (hh * 3600 + mm * 60);
		return String.format("%02d:%02d:%02d", hh, mm, ss);
	}

	public void pausar() {
		enPausa = true;
	}

	public void continuar() {
		enPausa = false;
	}

	public void resetear() {
		tiempoTranscurrido = 0L;
		lblCronometro.setText("00:00:00");
	}

	public void terminar() {
		terminado = true;
	}

	public boolean isPausado() {
		return enPausa;
	}
}
