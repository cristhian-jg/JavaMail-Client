import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InboxWindow extends JFrame {

	private static final String TAG = "InboxWindow";
	
	private JPanel contentPane;

	public InboxWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNuevoMensaje = new JButton("NUEVO MENSAJE");
		btnNuevoMensaje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							MessageWindow messageWindow = new MessageWindow();
							messageWindow.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnNuevoMensaje.setBounds(267, 203, 140, 25);
		contentPane.add(btnNuevoMensaje);
		
		JList list = new JList();
		list.setBounds(12, 42, 408, 198);
		contentPane.add(list);
		
		JLabel lblEntrada = new JLabel("BANDEJA DE ENTRADA");
		lblEntrada.setBounds(12, 13, 129, 16);
		contentPane.add(lblEntrada);
	}
}
