import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class StartConfig extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldSSID;
	private JLabel lblNetworkName;
	private JLabel lblPassword;
	private JPasswordField textFieldPassword;
	private char defaultEchoChar;
	private JCheckBox chckbxNewCheckBox;

	/**
	 * Create the dialog.
	 */
	public StartConfig() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 395, 205);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblCreateNetwork = new JLabel("CREATE A NEW NETWORK");
			lblCreateNetwork.setBounds(115, 19, 165, 16);
			contentPanel.add(lblCreateNetwork);
		}

		textFieldSSID = new JTextField(System.getProperty("user.name"));
		textFieldSSID.setToolTipText("4-20 alphanumeric characters");
		textFieldSSID.setBounds(177, 52, 130, 26);
		textFieldSSID.setColumns(10);
		 
		textFieldSSID.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) { checkSSID(); }
			public void removeUpdate(DocumentEvent e) { checkSSID(); }
			public void insertUpdate(DocumentEvent e) { checkSSID(); }

		
		});
		
		textFieldPassword = new JPasswordField();
		defaultEchoChar = textFieldPassword.getEchoChar();
		
		textFieldPassword.setToolTipText("8-20 alphanumeric characters");
		textFieldPassword.setBounds(177, 85, 130, 26);
		textFieldPassword.setColumns(10);
		textFieldPassword.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) { checkPassword(); }
			public void removeUpdate(DocumentEvent e) { checkPassword(); }
			public void insertUpdate(DocumentEvent e) { checkPassword(); }		
		});
		
		lblNetworkName = new JLabel("Network name");
		lblNetworkName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNetworkName.setBounds(74, 57, 91, 16);
		
		lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(104, 90, 61, 16);
		
		contentPanel.add(textFieldSSID);
		contentPanel.add(textFieldPassword);
		contentPanel.add(lblNetworkName);
		contentPanel.add(lblPassword);
		
		chckbxNewCheckBox = new JCheckBox("show password");
		chckbxNewCheckBox.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		        if (e.getStateChange() == ItemEvent.SELECTED) {
		            textFieldPassword.setEchoChar('\0');
		        } else {
		        	textFieldPassword.setEchoChar(defaultEchoChar);
		        }
		    }
		});
		chckbxNewCheckBox.setBounds(177, 115, 130, 23);
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
							Utils.windowsSetHostednetwork(textFieldSSID.getText(), textFieldPassword.getText());
							Utils.windowsStartHostednetwork();
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
		String password = textFieldPassword.getPassword().toString();
		if(password.length() >= 8  &&  password.length() <= 20 && password.matches("[a-zA-Z0-9]+")){
			textFieldPassword.setBackground(Color.green);
			return true;
		}
		else{
			textFieldPassword.setBackground(Color.red);
			return false;
		}
	}
	
	public boolean checkSSID() {
		String SSID = textFieldSSID.getText();
		if(SSID.length() >= 4 && SSID.length() <= 20 && SSID.matches("[a-zA-Z0-9]+")){
			textFieldSSID.setBackground(Color.green);
			return true;
		}
		else{
			textFieldSSID.setBackground(Color.red);
			return false;
		}
	}
}
