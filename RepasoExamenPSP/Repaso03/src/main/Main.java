package main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

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
		
		JLabel lblHoras = new JLabel("00");
		lblHoras.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblHoras.setBounds(86, 68, 35, 38);
		contentPane.add(lblHoras);
		
		JLabel lblMinutos = new JLabel("00");
		lblMinutos.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblMinutos.setBounds(175, 68, 35, 38);
		contentPane.add(lblMinutos);
		
		JLabel lblSegundos = new JLabel("00");
		lblSegundos.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblSegundos.setBounds(263, 68, 35, 38);
		contentPane.add(lblSegundos);

		btnEmpezar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empezar(btnEmpezar, btnParar, btnFinalizar, lblHoras, lblMinutos, lblSegundos);
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

	private void empezar(JButton btnEmpezar, JButton btnParar, JButton btnFinalizar, JLabel lblHoras, JLabel lblMinutos, JLabel lblSegundos) {
		btnEmpezar.setEnabled(false);
		btnParar.setEnabled(true);
		btnFinalizar.setEnabled(true);
		hilosService = new HilosService(lblHoras, lblMinutos, lblSegundos);
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
