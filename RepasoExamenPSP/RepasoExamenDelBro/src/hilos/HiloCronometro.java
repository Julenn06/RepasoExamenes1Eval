package hilos;

import javax.swing.JButton;
import javax.swing.JLabel;

public class HiloCronometro implements Runnable {

	private JLabel lblEstado;
	private JButton btnIniciar;
	private JButton btnParar;
	private JButton btnPausarReanudar;
	private JLabel lblCronometro;
	private boolean enPausa;
	private boolean terminado = false;
	private final Object bloqueoPausa = new Object(); // monitor para pausar/reanudar

	public HiloCronometro(JLabel lblEstado, JButton btnIniciar, JButton btnParar, JButton btnPausarReanudar,
			JLabel lblCronometro) {
		super();
		this.lblEstado = lblEstado;
		this.btnIniciar = btnIniciar;
		this.btnParar = btnParar;
		this.btnPausarReanudar = btnPausarReanudar;
		this.lblCronometro = lblCronometro;
	}

	@Override
	public void run() {
		lblEstado.setText("El hilo <<reloj>> ha comenzado");
		actualizarBotones();

		int total = 5400;// 1h 30 min en segundos
		long inicio = System.currentTimeMillis();
		long pausaAcum = 0L; // milisegundos acumulados en pausas
		long inicioPausaLocal = 0L;

		while (!terminado) {
			// Si hay pausa, registramos cuándo empieza y bloqueamos hasta la reanudación.
			if (enPausa) {
				inicioPausaLocal = System.currentTimeMillis();
				synchronized (bloqueoPausa) {
					while (enPausa) {
						try {
							bloqueoPausa.wait();
						} catch (InterruptedException ignored) {
						}
					}
				}
				// Acumulamos el tiempo que ha durado la pausa local para descontarlo
				// del tiempo transcurrido de la cuenta atrás.
				pausaAcum += System.currentTimeMillis() - inicioPausaLocal;
			}

			long ahora = System.currentTimeMillis();
			long trans = (ahora - inicio - pausaAcum);
			int restante = (int) Math.max(0, total - (trans / 1000)); // Calculamos segundos restantes (no negativos)
			lblCronometro.setText(formatear(restante));
			if (restante <= 0)
				terminado = true;
			try {
				Thread.sleep(200); // frecuencia de actualización
			} catch (InterruptedException ignored) {
			}
		}
		actualizarBotones();
		lblEstado.setText("El hilo <<reloj>> ha terminado");
	}

	private String formatear(int tsegundos) {
		int hh = (tsegundos / 3600);
		int mm = ((tsegundos - hh * 3600) / 60);
		int ss = tsegundos - (hh * 3600 + mm * 60);
		return String.format("%02d:%02d:%02d", hh, mm, ss);
	}

	private void actualizarBotones() {
		btnPausarReanudar.setEnabled(!terminado);
		btnParar.setEnabled(!terminado);
		btnIniciar.setEnabled(terminado);
	}

	public void pausaReanudar() {
		if (terminado)
			return;
		enPausa = !enPausa;
		if (enPausa) {
			btnPausarReanudar.setText("Reanudar");
		} else {
			btnPausarReanudar.setText("Pausar");
			synchronized (bloqueoPausa) {
				bloqueoPausa.notifyAll();
			}
		}
	}

	public void parar() {
		terminado = true;
		enPausa = false;
		synchronized (bloqueoPausa) {
			bloqueoPausa.notifyAll();
		}
	}

}