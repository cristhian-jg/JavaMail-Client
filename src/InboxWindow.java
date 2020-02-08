import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class InboxWindow extends JFrame {

	private static final String TAG = "InboxWindow";

	private JPanel contentPane;

	private Message[] messages;
	private JList messageList;
	private JTextArea textArea;

	private Store store;
	private Folder inbox;

	private String username;
	private String password;

	public InboxWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 637, 374);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		/** Obtengo la sesión y contraseña para poder trabajar con 
		 * ellas y no tener que pedirsela constantemente al usuario */
		username = LoginWindow.GetUsername();
		password = LoginWindow.GetPassword();

		/** Esto es solo un aditivo en el que muestro que sesión está iniciada en ese momento */
		JLabel lblHaIniciadoSesin = new JLabel("Sesi\u00F3n iniciada: " + LoginWindow.GetUsername());
		lblHaIniciadoSesin.setBounds(320, 14, 284, 14);
		contentPane.add(lblHaIniciadoSesin);

		/** Dentro de un bloque try ... catch necesitaré crear unas nuevas propiedades para conectarme
		 * a mi servidor mediante IMAP para poder leer los correos. */
		try {
			Properties properties = new Properties();
			/** Defino el protocol, en mi caso IMAP */
			properties.put("mail.store.protocol", "imap");
			/** Defino el host, en mi caso al tener un servidor de correo propio pongo localhost */
			properties.put("mail.imap.host", "localhost");
			/** Defino el puerto, que por defecto es el 143 */
			properties.put("mail.imap.port", "143");
			/**  Indicar que utilizamos tls */
			properties.put("mail.imap.starttls.enable", "true");

			/** Dado que ya tengo una sesión creada en la ventana de Login, obtengo 
			 * esa sesión atraves de mi metodo estatico GETSESSION()*/
			Session session = LoginWindow.GetSesion();
			/** Se necesita un objeto Store y Folder definidos en las propiedades de 
			 * la clase para obtener los correos */
			store = session.getStore("imap");
			store.connect("localhost", 143, username, password);
			/** Con esto obtenemos la carpeta INBOX, la cual almacenamos en nuestro objeto Folder.*/
			inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);

			/** Guardamos los mensajes de la carpeta/folder en un vector de mensajes definido 
			 * tambien en las propiedades de la clase*/
			messages = inbox.getMessages();

			/** Botón a la escucha que nos permitirá acceder a la ventana de nuevo mensaje */
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

			/** Botón a la escucha con el que podremos salir del programa */
			JButton btnSalir = new JButton("SALIR");
			btnSalir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					/** Cuando terminar habrá que cerrar las conexiones a la clase Folder y Store. */
					try {
						inbox.close(false);
						store.close();
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.exit(0);
				}
			});
			btnSalir.setBounds(522, 295, 82, 25);
			contentPane.add(btnSalir);
			btnNuevoMensaje.setBounds(372, 295, 140, 25);
			contentPane.add(btnNuevoMensaje);

			/** Defino una area de Texto donde se mostrara el contenido de los mensajes */
			textArea = new JTextArea();
			textArea.setBounds(219, 42, 392, 242);
			contentPane.add(textArea);

			/** Defino un modelo de lista donde obtengo cada mensaje de la cuenta para 
			 * luego mostrarlo en un JList */
			DefaultListModel model = new DefaultListModel<>();
			for (int i = 0; i < messages.length; i++) {
				model.addElement("<html>" + "<body>" + "De: " + messages[i].getFrom()[0].toString() + "<br>"
						+ "Subject:" + messages[i].getSubject() + "</body></html>");

			}

			/** Creo el JList y le defino el modelo implementado anteriormente */
			messageList = new JList();
			messageList.setBounds(12, 42, 197, 278);
			contentPane.add(messageList);
			messageList.setModel(model);
			/** Pongo el JList a la escucha de evento para que al precionar uno de los elementos del JList 
			 * muetro en el textArea una vista más detallada de los mensajes. */
			messageList.addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {
					// TODO Auto-generated method stub
					int index = messageList.getSelectedIndex();
					try {
						textArea.setText("De: " + messages[index].getFrom()[0].toString() + "\n" + "Asunto: "
								+ messages[index].getSubject() + "\n" + "Contenido: \n" + messages[index].getContent());
					} catch (MessagingException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		JLabel lblEntrada = new JLabel("BANDEJA DE ENTRADA");
		lblEntrada.setBounds(12, 13, 129, 16);
		contentPane.add(lblEntrada);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(219, 81, 141, -65);
		contentPane.add(scrollPane);

	}
}
