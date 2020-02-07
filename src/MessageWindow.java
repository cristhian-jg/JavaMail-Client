import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.awt.event.ActionEvent;

public class MessageWindow extends JFrame {

	private static final String TAG = "MessageWindow";
	
	private JPanel contentPane;
	private JTextField tfTo;
	private JTextField tfSubject;
	private JTextPane tpContent;

	public MessageWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		/** Botón a la escucha que se encarga de enviar los mensajes al correo
		 *  deseado, en mi caso pruebauser2 o pruebauser1.
		 * */
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				/** Cuando el usuario pulse en el botón
				 * 	Se obtendrá la sesión que uso en la ventana LoginWindow
				 */
				try {
					Session session = LoginWindow.GetSesion();
					
					/** Además obtendrá el usuario y la contraseña anteriormente insertadas.*/
					String remitter = LoginWindow.GetUsername();
					String password = LoginWindow.GetPassword();
					/** Por otro lado almaceno en variables lo que el usuario ha escrito en los TextField.*/
					String receiver = tfTo.getText();
					String subject = tfSubject.getText();
					String message = tpContent.getText();

					/** Se debe crear una clase MimeMessage para enviar el mensaje a la que le pasaremos la sesión.*/
					MimeMessage mimeMessage = new MimeMessage(session);

					/** Se ha de añadir la información necesaria a este mimeMessage para que pueda enviar el mensaje.*/
					mimeMessage.setFrom(new InternetAddress(remitter));
					mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
					mimeMessage.setSubject(subject);
					mimeMessage.setText(message);
					
					/** Utilizo la clase transport para finalmente enviar el mimeMessage con el metodo sendMessage.*/
					Transport transport = session.getTransport("smtp");
					transport.connect(remitter, password);
					transport.sendMessage(mimeMessage, mimeMessage.getRecipients(Message.RecipientType.TO));
					transport.close();
					
					/** Si todo ha ido bien saltará el siguiente dialogo y se cerrará la ventana. */
					JOptionPane.showMessageDialog(null, "Se ha enviado el correo correctamente.");
					dispose();
					
				} catch (AddressException e) {
					e.printStackTrace();
				} catch (MessagingException e) {
					e.printStackTrace();
				}

			}
		});
		
		/** El siguiente contenido solo son los elementos 
		 * añadididos al diseño de la aplicación. */
		
		btnEnviar.setBounds(214, 215, 97, 25);
		contentPane.add(btnEnviar);

		JLabel lblPara = new JLabel("Para:");
		lblPara.setBounds(12, 37, 55, 16);
		contentPane.add(lblPara);

		JLabel lblAsunto = new JLabel("Asunto:");
		lblAsunto.setBounds(12, 79, 56, 16);
		contentPane.add(lblAsunto);

		tpContent = new JTextPane();
		tpContent.setBounds(12, 111, 408, 91);
		contentPane.add(tpContent);

		tfTo = new JTextField();
		tfTo.setBounds(52, 34, 368, 22);
		contentPane.add(tfTo);
		tfTo.setColumns(10);

		tfSubject = new JTextField();
		tfSubject.setBounds(62, 76, 358, 22);
		contentPane.add(tfSubject);
		tfSubject.setColumns(10);

		JLabel lblMensajeNuevo = new JLabel("MENSAJE NUEVO");
		lblMensajeNuevo.setBounds(11, 8, 98, 16);
		contentPane.add(lblMensajeNuevo);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(323, 215, 97, 25);
		contentPane.add(btnCancelar);
	}
	
}
