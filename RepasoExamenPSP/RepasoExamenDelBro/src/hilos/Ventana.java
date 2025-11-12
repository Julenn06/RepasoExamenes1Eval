package hilos;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Ventana extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private HiloCronometro cronometro;
	private JLabel lblEstado;
	private JButton btnPausarReanudar;
	private JButton btnParar;
	private JButton btnIniciar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana frame = new Ventana();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Ventana() {
		setTitle("Daniel Alonso Mendez");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 565, 254);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCronometro = new JLabel("01:30:00");
		lblCronometro.setHorizontalAlignment(SwingConstants.CENTER);
		lblCronometro.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblCronometro.setBounds(10, 24, 529, 65);
		contentPane.add(lblCronometro);

		btnIniciar = new JButton("Iniciar");
		btnIniciar.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnIniciar.setBounds(10, 100, 133, 32);
		contentPane.add(btnIniciar);

		btnPausarReanudar = new JButton("Pausar");
		btnPausarReanudar.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnPausarReanudar.setBounds(153, 100, 150, 32);
		contentPane.add(btnPausarReanudar);

		btnParar = new JButton("Parar");
		btnParar.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnParar.setBounds(313, 100, 118, 32);
		contentPane.add(btnParar);

		lblEstado = new JLabel("ESTADO");
		lblEstado.setHorizontalAlignment(SwingConstants.CENTER);
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEstado.setBounds(10, 156, 529, 32);
		contentPane.add(lblEstado);

		JLabel lblHoraActual = new JLabel("");
		lblHoraActual.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHoraActual.setHorizontalAlignment(SwingConstants.CENTER);
		lblHoraActual.setBounds(452, 100, 64, 27);
		contentPane.add(lblHoraActual);

		// Action Listeners:
		// Boton Iniciar
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Crear clase del hilo
				cronometro = new HiloCronometro(lblEstado, btnIniciar, btnParar, btnPausarReanudar, lblCronometro);
				Thread hiloCronometro = new Thread(cronometro);
				// Configurar hilo
				hiloCronometro.setName("<<reloj>>");
				hiloCronometro.setPriority(Thread.NORM_PRIORITY);
				hiloCronometro.start(); // Empezar hilo
			}
		});
		// Boton Pausar o Reanudar
		btnPausarReanudar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cronometro.pausaReanudar();
			}
		});
		// Boton Parar
		btnParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cronometro.parar();
			}
		});
		// Deshabilitar botones
		btnParar.setEnabled(false);
		btnPausarReanudar.setEnabled(false);
		// Empezar el conteo de hora
		HiloHora hiloHora = new HiloHora(lblHoraActual, lblEstado);
		hiloHora.start();

	}
}
