package main;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	// Servicios de hilos independientes
	private HilosService hilosService1;
	private HilosService hilosService2;
	private HilosService hilosService3;

	// Hilos
	private Thread hilo1;
	private Thread hilo2;
	private Thread hilo3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// ==================== CRONÓMETRO 1 ====================
		JLabel lblTitulo1 = new JLabel("Cronómetro 1");
		lblTitulo1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitulo1.setBounds(30, 20, 120, 20);
		contentPane.add(lblTitulo1);

		JLabel lblCrono1 = new JLabel("00:00:00");
		lblCrono1.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblCrono1.setBounds(180, 15, 130, 30);
		contentPane.add(lblCrono1);

		JButton btnIniciar1 = new JButton("Iniciar");
		btnIniciar1.setBounds(330, 15, 80, 25);
		contentPane.add(btnIniciar1);

		JButton btnPausar1 = new JButton("Pausar");
		btnPausar1.setBounds(30, 50, 90, 25);
		btnPausar1.setEnabled(false);
		contentPane.add(btnPausar1);

		JButton btnContinuar1 = new JButton("Continuar");
		btnContinuar1.setBounds(130, 50, 100, 25);
		btnContinuar1.setEnabled(false);
		contentPane.add(btnContinuar1);

		JButton btnResetear1 = new JButton("Resetear");
		btnResetear1.setBounds(240, 50, 90, 25);
		btnResetear1.setEnabled(false);
		contentPane.add(btnResetear1);

		// ==================== CRONÓMETRO 2 ====================
		JLabel lblTitulo2 = new JLabel("Cronómetro 2");
		lblTitulo2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitulo2.setBounds(30, 110, 120, 20);
		contentPane.add(lblTitulo2);

		JLabel lblCrono2 = new JLabel("00:00:00");
		lblCrono2.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblCrono2.setBounds(180, 105, 130, 30);
		contentPane.add(lblCrono2);

		JButton btnIniciar2 = new JButton("Iniciar");
		btnIniciar2.setBounds(330, 105, 80, 25);
		contentPane.add(btnIniciar2);

		JButton btnPausar2 = new JButton("Pausar");
		btnPausar2.setBounds(30, 140, 90, 25);
		btnPausar2.setEnabled(false);
		contentPane.add(btnPausar2);

		JButton btnContinuar2 = new JButton("Continuar");
		btnContinuar2.setBounds(130, 140, 100, 25);
		btnContinuar2.setEnabled(false);
		contentPane.add(btnContinuar2);

		JButton btnResetear2 = new JButton("Resetear");
		btnResetear2.setBounds(240, 140, 90, 25);
		btnResetear2.setEnabled(false);
		contentPane.add(btnResetear2);

		// ==================== CRONÓMETRO 3 ====================
		JLabel lblTitulo3 = new JLabel("Cronómetro 3");
		lblTitulo3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitulo3.setBounds(30, 200, 120, 20);
		contentPane.add(lblTitulo3);

		JLabel lblCrono3 = new JLabel("00:00:00");
		lblCrono3.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblCrono3.setBounds(180, 195, 130, 30);
		contentPane.add(lblCrono3);

		JButton btnIniciar3 = new JButton("Iniciar");
		btnIniciar3.setBounds(330, 195, 80, 25);
		contentPane.add(btnIniciar3);

		JButton btnPausar3 = new JButton("Pausar");
		btnPausar3.setBounds(30, 230, 90, 25);
		btnPausar3.setEnabled(false);
		contentPane.add(btnPausar3);

		JButton btnContinuar3 = new JButton("Continuar");
		btnContinuar3.setBounds(130, 230, 100, 25);
		btnContinuar3.setEnabled(false);
		contentPane.add(btnContinuar3);

		JButton btnResetear3 = new JButton("Resetear");
		btnResetear3.setBounds(240, 230, 90, 25);
		btnResetear3.setEnabled(false);
		contentPane.add(btnResetear3);

		// ==================== LISTENERS CRONÓMETRO 1 ====================
		btnIniciar1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciar(1, lblCrono1, btnIniciar1, btnPausar1, btnContinuar1, btnResetear1);
			}
		});

		btnPausar1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pausar(1, btnPausar1, btnContinuar1);
			}
		});

		btnContinuar1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				continuar(1, btnPausar1, btnContinuar1);
			}
		});

		btnResetear1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetear(1);
			}
		});

		// ==================== LISTENERS CRONÓMETRO 2 ====================
		btnIniciar2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciar(2, lblCrono2, btnIniciar2, btnPausar2, btnContinuar2, btnResetear2);
			}
		});

		btnPausar2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pausar(2, btnPausar2, btnContinuar2);
			}
		});

		btnContinuar2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				continuar(2, btnPausar2, btnContinuar2);
			}
		});

		btnResetear2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetear(2);
			}
		});

		// ==================== LISTENERS CRONÓMETRO 3 ====================
		btnIniciar3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciar(3, lblCrono3, btnIniciar3, btnPausar3, btnContinuar3, btnResetear3);
			}
		});

		btnPausar3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pausar(3, btnPausar3, btnContinuar3);
			}
		});

		btnContinuar3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				continuar(3, btnPausar3, btnContinuar3);
			}
		});

		btnResetear3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetear(3);
			}
		});
	}

	private void iniciar(int numero, JLabel lblCrono, JButton btnIniciar, JButton btnPausar, JButton btnContinuar,
			JButton btnResetear) {
		btnIniciar.setEnabled(false);
		btnPausar.setEnabled(true);
		btnResetear.setEnabled(true);

		if (numero == 1) {
			hilosService1 = new HilosService(lblCrono);
			hilo1 = new Thread(hilosService1);
			hilo1.start();
		} else if (numero == 2) {
			hilosService2 = new HilosService(lblCrono);
			hilo2 = new Thread(hilosService2);
			hilo2.start();
		} else if (numero == 3) {
			hilosService3 = new HilosService(lblCrono);
			hilo3 = new Thread(hilosService3);
			hilo3.start();
		}
	}

	private void pausar(int numero, JButton btnPausar, JButton btnContinuar) {
		btnPausar.setEnabled(false);
		btnContinuar.setEnabled(true);

		if (numero == 1 && hilosService1 != null) {
			hilosService1.pausar();
		} else if (numero == 2 && hilosService2 != null) {
			hilosService2.pausar();
		} else if (numero == 3 && hilosService3 != null) {
			hilosService3.pausar();
		}
	}

	private void continuar(int numero, JButton btnPausar, JButton btnContinuar) {
		btnPausar.setEnabled(true);
		btnContinuar.setEnabled(false);

		if (numero == 1 && hilosService1 != null) {
			hilosService1.continuar();
		} else if (numero == 2 && hilosService2 != null) {
			hilosService2.continuar();
		} else if (numero == 3 && hilosService3 != null) {
			hilosService3.continuar();
		}
	}

	private void resetear(int numero) {
		if (numero == 1 && hilosService1 != null) {
			hilosService1.resetear();
		} else if (numero == 2 && hilosService2 != null) {
			hilosService2.resetear();
		} else if (numero == 3 && hilosService3 != null) {
			hilosService3.resetear();
		}
	}
}
