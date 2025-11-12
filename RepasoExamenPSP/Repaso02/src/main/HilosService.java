package main;

import javax.swing.JLabel;

public class HilosService implements Runnable {

	private JLabel lblCronometro;
	private boolean enPausa;
	private boolean terminado = false;

	public HilosService(JLabel lblCronometro) {
		this.lblCronometro = lblCronometro;
	}

	public void run() {

		int total = 5400;
		long inicio = System.currentTimeMillis();
		long pausaAcum = 0L;
		long inicioPausaLocal = 0L;

		while (!terminado) {
			if (enPausa) {
				if (inicioPausaLocal == 0L) {
					inicioPausaLocal = System.currentTimeMillis();
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
}