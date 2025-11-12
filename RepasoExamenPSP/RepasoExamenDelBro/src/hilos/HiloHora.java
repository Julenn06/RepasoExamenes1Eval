package hilos;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;

public class HiloHora extends Thread {
	private boolean enEjecucion;
	private JLabel textoHora;
	private JLabel estado;

	public HiloHora(JLabel textoHora, JLabel estado) {
		super();
		// Configurar hilo
		setName("<<hora>>");
		setPriority(NORM_PRIORITY);
		this.textoHora = textoHora;
		this.estado = estado;
	}

	public void parar() {
		enEjecucion = false;
		estado.setText("El hilo " + getName() + " ha finalizado");
	}

	@Override
	public void run() {
		enEjecucion = true;
		estado.setText("El hilo " + getName() + " ha comenzado");
		while (enEjecucion) {
			Date date = new Date(); // Hora actual
			// Formatear hora
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String horaActual = sdf.format(date);
			textoHora.setText(horaActual); // Cambiar la hora en la ventana
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}