import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class InitConfig extends JDialog {

	private static final long serialVersionUID = 6L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldSSID;
	private JLabel lblNetworkName;
	private JLabel lblPassword;
	private JPasswordField passwordField;
	private char defaultEchoChar;
	private JCheckBox chckbxNewCheckBox;
	private int portNumber = 50000;
	ServerSocket serverSocket;

	public InitConfig() 
	{
		///TODO : change the call to a generic stopHostednetwork function instead of windows only.
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				WindowsNetwork.stopHostednetwork();
				dispose();
				try {
					if(serverSocket != null)
						serverSocket.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}		
			}
		});

		setTitle("Network configuration");
		setVisible(true);
		setBounds(100, 100, 301, 211);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblCreateNetwork = new JLabel("Create a new network");
			lblCreateNetwork.setForeground(Color.DARK_GRAY);
			lblCreateNetwork.setHorizontalAlignment(SwingConstants.CENTER);
			lblCreateNetwork.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblCreateNetwork.setBounds(10, 11, 265, 16);
			contentPanel.add(lblCreateNetwork);
		}

		textFieldSSID = new JTextField(System.getProperty("user.name"));
		textFieldSSID.setToolTipText("4-20 alphanumeric characters");
		textFieldSSID.setBounds(124, 44, 130, 26);
		textFieldSSID.setColumns(10);

		textFieldSSID.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) { checkSSID(); }
			public void removeUpdate(DocumentEvent e) { checkSSID(); }
			public void insertUpdate(DocumentEvent e) { checkSSID(); }


		});

		passwordField = new JPasswordField();
		defaultEchoChar = passwordField.getEchoChar();

		passwordField.setToolTipText("8-20 alphanumeric characters");
		passwordField.setBounds(124, 77, 130, 26);
		passwordField.setColumns(10);
		passwordField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) { checkPassword(); }
			public void removeUpdate(DocumentEvent e) { checkPassword(); }
			public void insertUpdate(DocumentEvent e) { checkPassword(); }		
		});

		lblNetworkName = new JLabel("Network name");
		lblNetworkName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNetworkName.setBounds(21, 49, 91, 16);

		lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(51, 82, 61, 16);

		contentPanel.add(textFieldSSID);
		contentPanel.add(passwordField);
		contentPanel.add(lblNetworkName);
		contentPanel.add(lblPassword);

		chckbxNewCheckBox = new JCheckBox("show password");
		chckbxNewCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					passwordField.setEchoChar('\0');
				} else {
					passwordField.setEchoChar(defaultEchoChar);
				}
			}
		});
		
		chckbxNewCheckBox.setBounds(124, 108, 130, 23);
		contentPanel.add(chckbxNewCheckBox);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnCreate = new JButton("create");
				buttonPane.add(btnCreate);
				btnCreate.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(checkSSID() && checkPassword())
						{
							String setResult = WindowsNetwork.setHostednetwork(textFieldSSID.getText(), new String(passwordField.getPassword()));
							String startResult = WindowsNetwork.startHostednetwork();
							if(WindowsNetwork.checkHostednetwork())
							{
								new Thread("wait connection").start();
							}
							else
							{
								String message = "Sorry, your computer can't create a hostednetwork:\n" + setResult + "\n" + startResult;
								JOptionPane.showMessageDialog(new JFrame(), message, "Impossible to connect",
										JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				});
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public boolean checkPassword() {
		String password = new String(passwordField.getPassword());
		if(password.length() >= 8  &&  password.length() <= 20 && password.matches("[a-zA-Z0-9]+")){
			passwordField.setBackground(new Color(152, 251, 152));
			return true;
		}
		else{
			passwordField.setBackground(new Color(255, 153, 153));
			return false;
		}
	}

	public boolean checkSSID() {
		String SSID = textFieldSSID.getText();
		if(SSID.length() >= 4 && SSID.length() <= 20 && SSID.matches("[a-zA-Z0-9]+")){
			textFieldSSID.setBackground(new Color(152, 251, 152));
			return true;
		}
		else{
			textFieldSSID.setBackground(new Color(255, 153, 153));
			return false;
		}
	}
	
	public void run()
	{
		System.out.println("SERVERWAIT: waiting for an handshake");
		
		//create
		try {
			serverSocket = new ServerSocket(portNumber);
		}
		catch (IOException e) { 
			e.printStackTrace();
			return;
		}
		
		//accept
		try(Socket clientSocket = serverSocket.accept();
				BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
				DataInputStream input = new DataInputStream(clientSocket.getInputStream())){

			System.out.println("SERVERWAIT: connected to " + clientSocket.getInetAddress());

			// check for input request type
			String line = reader.readLine(); 

			if(line.equals("handshake")){
				// send acceptation
				System.out.println("SERVER: Sending handshake");

				writer.write("handshake");
				writer.newLine();
				writer.flush();
			}
			else
			{
				System.out.println("faiulure...");
			}

		}
		catch(IOException e){
			e.printStackTrace();
			return;
		}
	}
}
