package main;

import java.awt.EventQueue;
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
	private HilosService hilosService;

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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCronometro = new JLabel("00:00:00");
		lblCronometro.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 24));
		lblCronometro.setBounds(152, 58, 120, 38);
		contentPane.add(lblCronometro);

		JButton btnEmpezar = new JButton("Empezar");
		btnEmpezar.setBounds(37, 198, 84, 20);
		contentPane.add(btnEmpezar);

		JButton btnParar = new JButton("Parar");
		btnParar.setBounds(152, 198, 120, 20);
		btnParar.setEnabled(false);
		contentPane.add(btnParar);

		JButton btnFinalizar = new JButton("Finalizar");
		btnFinalizar.setBounds(309, 198, 84, 20);
		btnFinalizar.setEnabled(false);
		contentPane.add(btnFinalizar);

		btnEmpezar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empezar(btnEmpezar, btnParar, btnFinalizar, lblCronometro);
			}
		});

		btnParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parar(btnEmpezar, btnParar, btnFinalizar);
			}
		});

		btnFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finalizar(btnEmpezar, btnParar, btnFinalizar);
			}
		});
	}

	private void empezar(JButton btnEmpezar, JButton btnParar, JButton btnFinalizar, JLabel lblCronometro) {
		btnEmpezar.setEnabled(false);
		btnParar.setEnabled(true);
		btnFinalizar.setEnabled(true);
		hilosService = new HilosService(lblCronometro);
		Thread hiloCronometro = new Thread(hilosService);
		hiloCronometro.start();
	}

	private void parar(JButton btnEmpezar, JButton btnParar, JButton btnFinalizar) {
		if (btnParar.getText().equals("Parar")) {
			btnParar.setText("Continuar");
		} else {
			btnParar.setText("Parar");
		}
		hilosService.pararContinuar();
	}

	private void finalizar(JButton btnEmpezar, JButton btnParar, JButton btnFinalizar) {
		btnEmpezar.setEnabled(true);
		btnParar.setEnabled(false);
		btnFinalizar.setEnabled(false);
		hilosService.terminar();
	}
}
