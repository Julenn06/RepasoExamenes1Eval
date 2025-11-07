package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import controller.DBConnection;
import model.Alumnos;

public class AlumnoDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JTextField txtName = new JTextField(20);
	private final JTextField txtAge = new JTextField(5);
	private final String docId; // null => add, otherwise edit
	private final Runnable onSuccess;

	public AlumnoDialog(JFrame owner, String docId, Alumnos alumno, Runnable onSuccess) {
		super(owner, true);
		this.docId = docId;
		this.onSuccess = onSuccess;
		setTitle(docId == null ? "Añadir Alumno" : "Editar Alumno");
		setLayout(new BorderLayout(8, 8));

		JPanel form = new JPanel(new GridLayout(2, 2, 8, 8));
		form.add(new JLabel("Nombre:"));
		form.add(txtName);
		form.add(new JLabel("Edad:"));
		form.add(txtAge);
		add(form, BorderLayout.CENTER);

		if (alumno != null) {
			txtName.setText(alumno.getName());
			txtAge.setText(String.valueOf(alumno.getAge()));
		}

		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton btnOk = new JButton("OK");
		JButton btnCancel = new JButton("Cancelar");

		btnOk.addActionListener((ActionEvent e) -> onOk());
		btnCancel.addActionListener((ActionEvent e) -> dispose());

		buttons.add(btnOk);
		buttons.add(btnCancel);
		add(buttons, BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(owner);
	}

	private void onOk() {
		String name = txtName.getText().trim();
		String ageText = txtAge.getText().trim();
		if (name.isEmpty() || ageText.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Rellena nombre y edad", "Validación", JOptionPane.WARNING_MESSAGE);
			return;
		}
		int age;
		try {
			age = Integer.parseInt(ageText);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Edad no válida", "Validación", JOptionPane.WARNING_MESSAGE);
			return;
		}

		new Thread(() -> {
			try {
				if (docId == null) {
					// add
					DBConnection.getFirestore().collection("alumnos").add(new Alumnos(name, age)).get();
				} else {
					// edit
					DBConnection.getFirestore().collection("alumnos").document(docId).update("name", name, "age", age)
							.get();
				}
				if (onSuccess != null) {
					SwingUtilities.invokeLater(() -> onSuccess.run());
				}
			} catch (Exception e) {
				e.printStackTrace();
				SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this,
						"Error al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE));
			}
			SwingUtilities.invokeLater(() -> dispose());
		}).start();
	}

}
