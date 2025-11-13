package main;

import javax.swing.JLabel;

public class HilosService implements Runnable {

	private JLabel lblHoras;
	private JLabel lblMinutos;
	private JLabel lblSegundos;
	private boolean enPausa;
	private boolean terminado = false;

	public HilosService(JLabel lblHoras, JLabel lblMinutos, JLabel lblSegundos) {
		this.lblHoras = lblHoras;
		this.lblMinutos = lblMinutos;
		this.lblSegundos = lblSegundos;
	}

	public void run() {

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
				int transcurrido = (int) (trans / 1000);
				lblHoras.setText(formatearHoras(transcurrido));
				lblMinutos.setText(formatearMinutos(transcurrido));
				lblSegundos.setText(formatearSegundos(transcurrido));
			}

			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private String formatearHoras(int tsegundos) {
		int hh = (tsegundos / 3600);
		return String.format("%02d", hh);
	}

	private String formatearMinutos(int tsegundos) {
		int hh = (tsegundos / 3600);
		int mm = ((tsegundos - hh * 3600) / 60);
		return String.format("%02d", mm);
	}

	private String formatearSegundos(int tsegundos) {
		int hh = (tsegundos / 3600);
		int mm = ((tsegundos - hh * 3600) / 60);
		int ss = tsegundos - (hh * 3600 + mm * 60);
		return String.format("%02d", ss);
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