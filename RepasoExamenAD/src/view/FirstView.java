package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import services.DATController;
import services.XMLController;

public class FirstView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public FirstView() {
		setTitle("Gestión de Alumnos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Use BorderLayout for header + content
		contentPane = new JPanel(new BorderLayout(12, 12));
		contentPane.setBorder(new EmptyBorder(12, 12, 12, 12));
		setContentPane(contentPane);

		// Header
		JLabel lblTitle = new JLabel("Panel de Acciones", SwingConstants.CENTER);
		lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblTitle.setOpaque(true);
		lblTitle.setBackground(new Color(0x2E86C1));
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setPreferredSize(new Dimension(100, 48));
		contentPane.add(lblTitle, BorderLayout.NORTH);

		// Grid of buttons: 5 rows x 2 cols to match 10 actions
		JPanel buttonGrid = new JPanel(new GridLayout(5, 2, 10, 10));

		JButton btn1 = createButton("1 - Ver Alumnos", "Muestra todos los alumnos");
		btn1.addActionListener(e -> {
			AlumnosTableView tv = new AlumnosTableView();
			tv.setVisible(true);
		});
		buttonGrid.add(btn1);

		JButton btn2 = createButton("2 - Añadir Alumno", "Añade un nuevo alumno");
		btn2.addActionListener(e -> {
			AlumnoDialog dlg = new AlumnoDialog(this, null, null,
					() -> JOptionPane.showMessageDialog(this, "Alumno guardado"));
			dlg.setVisible(true);
		});
		buttonGrid.add(btn2);

		JButton btn3 = createButton("3 - Editar Alumno", "Editar alumno existente");
		btn3.addActionListener(e -> {
			AlumnosTableView tv = new AlumnosTableView();
			tv.setVisible(true);
		});
		buttonGrid.add(btn3);

		JButton btn4 = createButton("4 - Eliminar Alumno", "Eliminar un alumno");
		btn4.addActionListener(e -> {
			AlumnosTableView tv = new AlumnosTableView();
			tv.setVisible(true);
		});
		buttonGrid.add(btn4);

		JButton btn5 = createButton("5 - Guardar en XML", "Exportar alumnos a XML");
		btn5.addActionListener(e -> new Thread(() -> {
			try {
				new XMLController().saveToXML();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}).start());
		buttonGrid.add(btn5);

		JButton btn6 = createButton("6 - Leer desde XML", "Importar alumnos desde XML");
		btn6.addActionListener(e -> {
			AlumnosTableView tv = new AlumnosTableView();
			tv.setVisible(true);
			tv.loadFromXML();
		});
		buttonGrid.add(btn6);

		JButton btn7 = createButton("7 - Guardar en DAT", "Exportar alumnos a DAT");
		btn7.addActionListener(e -> new Thread(() -> {
			try {
				new DATController().saveToDAT();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}).start());
		buttonGrid.add(btn7);

		JButton btn8 = createButton("8 - Leer desde DAT", "Importar alumnos desde DAT");
		btn8.addActionListener(e -> {
			AlumnosTableView tv = new AlumnosTableView();
			tv.setVisible(true);
			tv.loadFromDAT();
		});
		buttonGrid.add(btn8);

		JButton btn9 = createButton("9 - Abrir vista gráfica", "Abrir otra ventana gráfica");
		btn9.addActionListener(e -> new Thread(() -> {
			try {
				FirstView v = new FirstView();
				v.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}).start());
		buttonGrid.add(btn9);

		JButton btn10 = createButton("10 - Salir", "Cerrar la aplicación");
		btn10.addActionListener(e -> System.exit(0));
		buttonGrid.add(btn10);

		contentPane.add(buttonGrid, BorderLayout.CENTER);

		// finalize window
		pack();
		setMinimumSize(new Dimension(420, getHeight()));
		setLocationRelativeTo(null); // center on screen
	}

	private JButton createButton(String text, String tooltip) {
		JButton b = new JButton(text);
		b.setFont(new Font("SansSerif", Font.PLAIN, 14));
		b.setToolTipText(tooltip);
		return b;
	}

}
