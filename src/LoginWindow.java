import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.awt.event.ActionEvent;

public class LoginWindow extends JFrame {

	private static final String TAG = "LoginWindow";
	private static Session session;
	private static String username;
	private static String password;
	
	private JPanel contentPane;
	private JTextField tfUsuario;
	private JPasswordField passwordField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow loginWindow = new LoginWindow();
					loginWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginWindow() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 256, 258);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnSalir.setBounds(121, 159, 97, 25);
		contentPane.add(btnSalir);

		/** Botón a la escucha que se encarga de iniciar 
		 * sesión en mi servidor de correo y comprobar que la cuenta
		 * existe y la contraseña es correcta
		 * */
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				/** Necesitaremos crear una nueva instancia de la clase Properties y añadirle las propiedades */
				Properties props = new Properties();

				/** Añadidimos a la propiedads la propiedad que define el nombre a asignar y el host */
				props.setProperty("mail.smtp.host", "localhost");
				/** Si utilizamos tls tendremos que indicarlo con la siguiente propiedad */
				props.setProperty("mail.smtp.starttls.enable", "true");
				/** Indicar el puerto por el que se va a conectar, en mi caso el 25 */
				props.setProperty("mail.smtp.port", "25");
				/** Indiciar si se va autentificar directamente al servidor */
				props.setProperty("mail.smtp.auth", "true");

				/** Obtengo lo que ha introducido el usuario en los TextField 
				 * para comprobar que todo es correcto*/
				username = tfUsuario.getText();
				password = passwordField.getText();

				/** Obtengo una instancia de la clase Session a la que le pasaré las propiedades y un 
				 * nuevo Autenticator, el cual se encargará de validar el usuario y la contraseña */
				session = Session.getInstance(props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

				/** A la hora de capturar la excepción por si la contraseña no es incorrecta he 
				 * tenido que usar la clase Transport para conectarme al servidor y la excepción 
				 * AuthenticationFailedException para controlar dicha excepción y mostrar un mensaje en caso 
				 * de que nosea correcta. */
				try {
					Transport transport = session.getTransport("smtp");
					transport.connect("localhost", username, password);
					transport.close();
					
					/** La contraseña introducida es correcta, así que 
					 * no salta la excepción y abro la bandeja de entrada. */

					JOptionPane.showMessageDialog(null, "Bienvenido, ha accedido a su cuenta de correo correctamente.",
							"Sesión iniciada", JOptionPane.INFORMATION_MESSAGE);

					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								InboxWindow inboxWindow = new InboxWindow();
								inboxWindow.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});

					dispose();

				} catch (AuthenticationFailedException ae) {
					/** El usuario o contraseña no son correctos, por lo que salta la siguiente ventana. */
					JOptionPane.showMessageDialog(null,
							"El usuario y la contraseña parecen no coincidir. Inténtelo de nuevo.",
							"Error al iniciar sesión", JOptionPane.WARNING_MESSAGE);
				} catch (MessagingException me) {
					me.printStackTrace();
				}
			}
		});

		btnLogin.setBounds(12, 159, 97, 25);
		contentPane.add(btnLogin);

		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(12, 63, 72, 16);
		contentPane.add(lblUsuario);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setBounds(12, 109, 72, 16);
		contentPane.add(lblContrasea);

		tfUsuario = new JTextField();
		tfUsuario.setBounds(102, 60, 116, 22);
		contentPane.add(tfUsuario);
		tfUsuario.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(102, 106, 116, 22);
		contentPane.add(passwordField);

		JLabel lblIniciarSesin = new JLabel("INICIAR SESI\u00D3N");
		lblIniciarSesin.setBounds(12, 13, 109, 16);
		contentPane.add(lblIniciarSesin);
	}

	/** Metodo estatico para obtener la sesión a 
	 * la hora de enviar un mensaje en la ventana MessageWindow */
	public static Session GetSesion() {
		return session;
	}
	
	/** Metodo estatico para obtener el usuario a 
	 * la hora de enviar un mensaje en la ventana MessageWindow */
	public static String GetUsername() {
		return username;
	}
	
	/** Metodo estatico para obtener la contraseña a 
	 * la hora de enviar un mensaje en la ventana MessageWindow */
	public static String GetPassword() {
		return password;
	}

}
